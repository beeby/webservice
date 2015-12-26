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
	 * 获取登录认证包
	 * 
	 * @return
	 */
	protected abstract Message getLoginMessage();

	/**
	 * 验证登录认证结果
	 * 
	 * @return
	 */
	protected abstract boolean checkLoginStatus(Message msg)
			throws MessageServerException;

	/**
	 * 获取结束包
	 * 
	 * @return
	 */
	protected abstract Message getTerminalMessage();

	/**
	 * 收到的应答消息处理
	 * 
	 * @param msg
	 */
	protected abstract void dispatch(Message msg, Message response);

	/**
	 * 收到的下发消息处理
	 * 
	 * @param msg
	 */
	protected abstract void deliver(Message msg);
	
	/**
	 * 发送错误的消息处理
	 * 
	 * @param msg
	 */
	protected abstract void errorHandler(Message msg);

	/**
	 * 获取当前的流量控制参数，返回值为int型，表示每秒允许发送的短信数量
	 * 
	 * @return
	 */
	public int getFlowControl() {
		return flowControl;
	}

	/**
	 * 设置流量控制参数
	 * 
	 * @param flowControl
	 *            流量控制参数，表示每秒允许发送的短信数量
	 */
	public void setFlowControl(int flowControl) {
		this.flowControl = flowControl;
	}

	public int getGatewayTimeout() {
		return gatewayTimeout;
	}

	/**
	 * 设置网关超时时间
	 * 
	 * @param gatewayTimeout
	 *            以毫秒为单位，如果超过此时间未收到网关发来的包，则重新连接网关
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
	 * 将当前进程设置为已经连接，必须由dispatch函数在收到认证包的返回确认后进行调用;
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
						logger.debug("发送消息：" + msg.getClass().getSimpleName()
								+ " 序列号:" + msg.getSequence().getString());
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
				logger.info("短信网关未连接，开始进行连接");
				connect();
			} else {
				logger.debug("短信网关连接中，发送进程暂停");
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
							logger.debug("接收信息："
									+ msg.getClass().getSimpleName() + " 序列号: "
									+ msg.getSequence().getString());
							if ((msg.getMessageType() & 0x80000000) == 0x80000000) {
								Message sendMsg = waitResponsePool.get(msg
										.getSequence().getString());
								if (sendMsg != null) {
									waitResponsePool.remove(sendMsg
											.getSequence().getString());
								} else { 
									logger.debug("接收返回包时未找到发送包，返回包序列号："+msg
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
						logger.error("网关接收信息超时，重新连接网关");
						disconnect();
					} else {
						sleep(1000);
					}
				} catch (Exception mse) {
					logger.error("获取服务器消息时发生异常: " + mse.getMessage(), mse);
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
					logger.debug("发送消息等待回复超时, 序列号:"+msg.getSequence());
					errorHandler(msg);
				}
			}
		} catch (Exception e) {
			logger.error("清除等待队列中过期信息时发生异常:"+e.getMessage(),e);
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
				logger.error("初始化网关连接时出现异常", e);
			}
		}
		return (isConnected && msgQueue.size() + waitResponsePool.size() < getQueueLimit());
	}

	protected boolean connect() throws MessageServerException {
		try {
			isInitializing = true;
			logger.info("开始连接网关服务器");
			if (socket != null)
				socketClose();
			socket = new Socket(serverAddress, serverPort);
			logger.debug("初始化Socket完成： "+serverAddress+":"+serverPort);
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
			logger.info("登录认证数据包发送完成");
			long loginStartTime = Calendar.getInstance().getTimeInMillis();
			while (Calendar.getInstance().getTimeInMillis() - loginStartTime < 30000
					&& !isConnected) {
				byte[] msgBytes = socketRead();
				if (msgBytes.length > 0) {
					Message respMsg = getMessageFactory().getMessage(msgBytes);
					if (checkLoginStatus(respMsg)) {
						socketInitialCount = 0;
						setConnected();
						logger.info("登录完成, 已成功连接至网关服务器");
						lastReceiveTime = Calendar.getInstance()
								.getTimeInMillis();
						return true;
					}
				}
			}
			logger.info("接收登录应答包超时，连接网关服务器失败");
			disconnect();
		} catch (Exception e) {
			logger.error("创建到短信网关服务器的连接时出现错误:" + e.getMessage(), e);
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
					logger.error("向网关服务器发送数据时出现异常：" + e.getMessage());
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
			logger.error("从接口服务器读取数据时发异常：" + e.getMessage(), e);
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
					logger.info("关闭输入流完成");
				} catch (Exception e) {
					logger.info("关闭输入流失败: " + e.getMessage());
				}
				try {
					if (outputBuffer != null) {
						outputBuffer.close();
					}
					logger.info("关闭输出流完成 ");
				} catch (Exception e) {
					logger.info("关闭输出流失败: " + e.getMessage());
				}

				try {
					socket.close();
					logger.info(" 关闭Socket完成 ");
				} catch (Exception e) {
					logger.info("socketClose: 关闭Socket失败 " + e.getMessage());
				}
			}
		}
	}

	public void serverStop() {
		threadStop = true;
	}
}
