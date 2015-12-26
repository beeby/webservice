package com.transfar.messageserver.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.scheme.Message;
import com.transfar.messageserver.scheme.MessageFactory;
import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.utils.MessageUtils;

public abstract class MessageServer extends Thread {
	protected Log logger = LogFactory.getLog(getClass());
	protected Socket socket;
	protected int socketInitialCount;
	protected OutputStream outputBuffer;
	protected InputStream inputBuffer;
	protected String serverAddress;
	protected int serverPort;
	protected boolean isConnected = false;
	protected boolean isInitializing = false;
	protected boolean threadStop = false;
	protected int flowControl = 1000;
	protected long lastSendTime = 0;
	protected long lastReceiveTime = 0;
	protected Thread readThread;
	protected int gatewayTimeout = 900000;

	protected ConcurrentMap<String, Message> waitResponsePool = new ConcurrentHashMap<String, Message>(
			64);
	protected List<Message> msgQueue = Collections
			.synchronizedList(new ArrayList<Message>());
	protected byte[] moBuffer;

	protected abstract MessageFactory getMessageFactory();

	protected abstract int getQueueLimit();
	
	

	/**
	 * ��ȡ��¼��֤��
	 * 
	 * @return
	 */
	protected abstract Message getLoginMessage();

	/**
	 * ��֤��¼��֤���
	 * 
	 * @return
	 */
	protected abstract boolean checkLoginStatus(Message msg)
			throws MessageServerException;

	/**
	 * ��ȡ������
	 * 
	 * @return
	 */
	protected abstract Message getTerminalMessage();

	/**
	 * �յ���Ӧ����Ϣ����
	 * 
	 * @param msg
	 */
	protected abstract void dispatch(Message msg, Message response);

	/**
	 * �յ����·���Ϣ����
	 * 
	 * @param msg
	 */
	protected abstract void deliver(Message msg);
	
	/**
	 * ���ʹ������Ϣ����
	 * 
	 * @param msg
	 */
	protected abstract void errorHandler(Message msg);

	/**
	 * ��ȡ��ǰ���������Ʋ���������ֵΪint�ͣ���ʾÿ�������͵Ķ�������
	 * 
	 * @return
	 */
	public int getFlowControl() {
		return flowControl;
	}

	/**
	 * �����������Ʋ���
	 * 
	 * @param flowControl
	 *            �������Ʋ�������ʾÿ�������͵Ķ�������
	 */
	public void setFlowControl(int flowControl) {
		this.flowControl = flowControl;
	}

	public int getGatewayTimeout() {
		return gatewayTimeout;
	}

	/**
	 * �������س�ʱʱ��
	 * 
	 * @param gatewayTimeout
	 *            �Ժ���Ϊ��λ�����������ʱ��δ�յ����ط����İ�����������������
	 */
	public void setGatewayTimeout(int gatewayTimeout) {
		this.gatewayTimeout = gatewayTimeout;
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * ����ǰ��������Ϊ�Ѿ����ӣ�������dispatch�������յ���֤���ķ���ȷ�Ϻ���е���;
	 */
	protected void setConnected() {
		this.isConnected = true;
		this.isInitializing = false;
	}

	protected void disconnect() {
		this.isConnected = false;
		this.isInitializing = false;
		socketClose();
		socket = null;
	}

	private class MessageResponseServer implements Runnable {
		private MessageServer server;

		public MessageResponseServer(MessageServer server) {
			this.server = server;
		}

		public void run() {
			if (server != null) {
				while (!server.threadStop) {
					server.deliverMessageProcess();
				}
			}
		}
	}

	protected void transmitQueueProcess() {
		try {
			if (isConnected) {
				if (msgQueue.size() > 0) {
					while (Calendar.getInstance().getTimeInMillis()
							- lastSendTime < (1000 / flowControl)) {
						sleep(1);
					}
					lastSendTime=Calendar.getInstance().getTimeInMillis();
					Message msg = msgQueue.get(0);
					synchronized (msg) {
						msg.setSequence();
						msg.setTransmitCount(msg.getTransmitCount() + 1);
						logger.debug("������Ϣ��" + msg.getClass().getSimpleName()
								+ " ���к�:" + msg.getSequence().getString());
						socketWrite(msg.getMessageBytes());
						msg.setTransmitTime(Calendar.getInstance().getTime());
						waitResponsePool
								.put(msg.getSequence().getString(), msg);
						msgQueue.remove(msg);
					}

				} else {
					sleep(5000);
				}
			} else if (!isInitializing) {
				logger.info("��������δ���ӣ���ʼ��������");
				connect();
			} else {
				logger.debug("�������������У����ͽ�����ͣ");
				sleep(1000);
			}
		} catch (MessageServerException mse) {
			disconnect();
			try {
				sleep(60000);
			} catch (InterruptedException e) {
				threadStop = true;
			}
		} catch (InterruptedException e) {
			threadStop = true;
		}
	}

	private int getValidMessageLength(byte[] buffer) {
		int msgLen = 0;
		if (buffer.length >= 4) {
			msgLen = MessageUtils.bytesToInt(buffer, 0);
			if (msgLen > buffer.length) {
				msgLen = 0;
			}
		}
		return msgLen;
	}

	public void deliverMessageProcess() {
		try {
			if (isConnected) {
				try {
					byte[] newBytes = socketRead();
					if (newBytes != null && newBytes.length > 0) {
						moBuffer = MessageUtils.mergeBytes(moBuffer, newBytes);
						int msgLen = 0;
						while ((msgLen = getValidMessageLength(moBuffer)) > 0) {
							byte[] msgBytes = MessageUtils.subBytes(moBuffer,
									0, msgLen);
							moBuffer = MessageUtils.subBytes(moBuffer, msgLen,
									moBuffer.length - msgLen);
							Message msg = getMessageFactory().getMessage(
									msgBytes);
							logger.debug("������Ϣ��"
									+ msg.getClass().getSimpleName() + " ���к�: "
									+ msg.getSequence().getString());
							if ((msg.getMessageType() & 0x80000000) == 0x80000000) {
								Message sendMsg = waitResponsePool.get(msg
										.getSequence().getString());
								if (sendMsg != null) {
									waitResponsePool.remove(sendMsg
											.getSequence().getString());
								} else { 
									logger.debug("���շ��ذ�ʱδ�ҵ����Ͱ������ذ����кţ�"+msg
											.getSequence().getString());
								}
								dispatch(sendMsg, msg);
							} else {
								deliver(msg);
							}

						}
						lastReceiveTime = Calendar.getInstance()
								.getTimeInMillis();
					} else if (Calendar.getInstance().getTimeInMillis()
							- lastReceiveTime > gatewayTimeout) {
						logger.error("���ؽ�����Ϣ��ʱ��������������");
						disconnect();
					} else {
						sleep(1000);
					}
				} catch (Exception mse) {
					logger.error("��ȡ��������Ϣʱ�����쳣: " + mse.getMessage(), mse);
					disconnect();
					sleep(5000);
				}
			} else {
				sleep(5000);
			}
		} catch (InterruptedException e) {
			threadStop = true;
		}
	}
	
	protected void cleanupWaitQueue() {
		try {
			Set<String> keys=waitResponsePool.keySet();
			for ( String key : keys ) {
				Message msg=waitResponsePool.get(key);
				long curTime=Calendar.getInstance().getTimeInMillis();
				if ( curTime - msg.getTransmitTime().getTime() > gatewayTimeout ) {
					waitResponsePool.remove(key);
					logger.debug("������Ϣ�ȴ��ظ���ʱ, ���к�:"+msg.getSequence());
					errorHandler(msg);
				}
			}
		} catch (Exception e) {
			logger.error("����ȴ������й�����Ϣʱ�����쳣:"+e.getMessage(),e);
		}
	}
	
	public void run() {
		readThread = new Thread(new MessageResponseServer(this));
		readThread.setName(getName() + "R");
		readThread.start();
		while (!threadStop || msgQueue.size() > 0) {
			transmitQueueProcess();
			cleanupWaitQueue();
		}
		try {
			socketWrite(getTerminalMessage().getMessageBytes());
		} catch (MessageServerException e) {

		}
		disconnect();
	}

	public void messagetransmit(Message message) {
		msgQueue.add(message);
	}

	public boolean available() {
		if (!isConnected && !isInitializing) {
			try {
				connect();
			} catch (Exception e) {
				logger.error("��ʼ����������ʱ�����쳣", e);
			}
		}
		return (isConnected && msgQueue.size() + waitResponsePool.size() < getQueueLimit());
	}

	protected boolean connect() throws MessageServerException {
		try {
			isInitializing = true;
			logger.info("��ʼ�������ط�����");
			if (socket != null)
				socketClose();
			socket = new Socket(serverAddress, serverPort);
			logger.debug("��ʼ��Socket��ɣ� "+serverAddress+":"+serverPort);
			socket.setKeepAlive(true);
			inputBuffer = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			outputBuffer = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
			Message authMsg = getLoginMessage();
			authMsg.setTransmitTime(Calendar.getInstance().getTime());
			authMsg.setTransmitCount(1);
			authMsg.setSequence();
			socketWrite(authMsg.getMessageBytes());
			logger.info("��¼��֤���ݰ��������");
			long loginStartTime = Calendar.getInstance().getTimeInMillis();
			while (Calendar.getInstance().getTimeInMillis() - loginStartTime < 30000
					&& !isConnected) {
				byte[] msgBytes = socketRead();
				if (msgBytes.length > 0) {
					Message respMsg = getMessageFactory().getMessage(msgBytes);
					if (checkLoginStatus(respMsg)) {
						socketInitialCount = 0;
						setConnected();
						logger.info("��¼���, �ѳɹ����������ط�����");
						lastReceiveTime = Calendar.getInstance()
								.getTimeInMillis();
						return true;
					}
				}
			}
			logger.info("���յ�¼Ӧ�����ʱ���������ط�����ʧ��");
			disconnect();
		} catch (Exception e) {
			logger.error("�������������ط�����������ʱ���ִ���:" + e.getMessage(), e);
			socketInitialCount++;
			disconnect();
			try {
				sleep(10000*(socketInitialCount<10?socketInitialCount:10));
			} catch (InterruptedException e1) {
				threadStop = true;
			}
			if (socketInitialCount > 3) {
				throw new MessageServerException(
						MessageServerException.SOCKET_INITIALIZATION_FAIL);
			}
			
		}
		return false;
	}

	protected void socketWrite(byte[] msgBytes) throws MessageServerException {

		if (outputBuffer == null || socket == null || socket.isOutputShutdown()) {
			throw new MessageServerException(
					MessageServerException.SOCKET_IS_NOT_INITIALIZED);
		}
		synchronized (outputBuffer) {
			if (!socket.isOutputShutdown()) {
				try {
					outputBuffer.write(msgBytes);
					outputBuffer.flush();
				} catch (Exception e) {
					logger.error("�����ط�������������ʱ�����쳣��" + e.getMessage());
					throw new MessageServerException(
							MessageServerException.SOCKET_OUTPUT_STREAM_CLOSED);
				}
			} else {
				throw new MessageServerException(
						MessageServerException.SOCKET_OUTPUT_STREAM_CLOSED);
			}

		}
	}

	protected byte[] socketRead() throws MessageServerException {

		while (inputBuffer == null || socket == null
				|| socket.isInputShutdown()) {
			throw new MessageServerException(
					MessageServerException.SOCKET_IS_NOT_INITIALIZED);
		}
		byte[] readBuffer = new byte[0];
		int buffLength;
		try {
			synchronized (inputBuffer) {
				int buffAvailable = inputBuffer.available();
				if (buffAvailable > 0) {
					readBuffer = new byte[buffAvailable];
					buffLength = inputBuffer.read(readBuffer);
				} else {
					buffLength = 0;
				}
			}
		} catch (Exception e) {
			logger.error("�ӽӿڷ�������ȡ����ʱ���쳣��" + e.getMessage(), e);
			throw new MessageServerException(
					MessageServerException.SOCKET_INPUT_STREAM_CLOSED);
		}
		return readBuffer;
	}

	protected void socketClose() {
		if (socket != null) {
			synchronized (socket) {
				try {
					if (inputBuffer != null) {
						inputBuffer.close();
					}
					logger.info("�ر����������");
				} catch (Exception e) {
					logger.info("�ر�������ʧ��: " + e.getMessage());
				}
				try {
					if (outputBuffer != null) {
						outputBuffer.close();
					}
					logger.info("�ر��������� ");
				} catch (Exception e) {
					logger.info("�ر������ʧ��: " + e.getMessage());
				}

				try {
					socket.close();
					logger.info(" �ر�Socket��� ");
				} catch (Exception e) {
					logger.info("socketClose: �ر�Socketʧ�� " + e.getMessage());
				}
			}
		}
	}

	public void serverStop() {
		threadStop = true;
	}
}
