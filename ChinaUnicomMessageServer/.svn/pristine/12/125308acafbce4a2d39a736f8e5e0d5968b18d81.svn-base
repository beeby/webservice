package com.transfar.messageserver.chinaunicom.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.chinaunicom.scheme.AsisInfoSend;
import com.transfar.messageserver.chinaunicom.scheme.AsisInfoSendResponse;
import com.transfar.messageserver.chinaunicom.scheme.SGIPMessageSequence;
import com.transfar.messageserver.chinaunicom.scheme.SGIPUnbind;
import com.transfar.messageserver.chinaunicom.vo.UTSendMsg;
import com.transfar.messageserver.utils.MessageServerException;

public class ChinaUnicomMessageServer extends Thread{
	private Log logger=LogFactory.getLog(getClass());
	private boolean threadStop=false;
	
	private AsisinfoServiceClient webClient;
	private int windowSize=1;
	private ChinaUnicomMessageService service;
	
	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public AsisinfoServiceClient getWebClient() {
		return webClient;
	}

	public void setWebClient(AsisinfoServiceClient webClient) {
		this.webClient = webClient;
	}

	public ChinaUnicomMessageService getService() {
		return service;
	}

	public void setService(ChinaUnicomMessageService service) {
		this.service = service;
	}

	
	
	public void run () {
		logger.info("中国联通短信接口服务进程启动");
		 webClient.init();
		 
		int sleepCount=1;
		while ( ! threadStop ) {
			try {
				if ( ! sendMessages() ) {
					sleep(sleepCount*6000);
					sleepCount+=(sleepCount<10)?1:0;
				} else {
					sleepCount=1;
				}
			} catch (Exception e) {
				logger.error("短信服务进程产生异常："+e.getMessage(),e);
				try {
					sleep(sleepCount*6000);
				} catch (InterruptedException e1) {
					threadStop=true;
				}
				sleepCount+=(sleepCount<10)?1:0;
			} 
		}
		logger.info("中国联通短信接口服务进程停止");
	}
	
	private boolean sendMessages() throws MessageServerException {
		List<UTSendMsg> msgs=service.getMessages(windowSize);
		if ( msgs==null || msgs.size()==0 ) {
			return false;
		}
		for ( UTSendMsg msg: msgs) {
			try {
				AsisInfoSend sendMsg=new AsisInfoSend();
				sendMsg.setChannelID(msg.getChannelID());
				sendMsg.setContent(msg.getContent());
				sendMsg.setMobileNumber(msg.getMobileNumber());
				sendMsg.setPriority(msg.getPriority());
				sendMsg.setTaskID(new SGIPMessageSequence().getString());
				AsisInfoSendResponse resp=webClient.messageTransmit(sendMsg);
				msg.setMessageID(resp.getMsgID());
				msg.setStatus(1);
			} catch (Exception e) {
				 logger.error("联通短信"+msg.getIndexId()+"发送异常："+e.getMessage(),e);
				 msg.setStatus(2);
				 msg.setStatusMsg(e.getMessage());
			} finally {
				try {
					service.updateMessageSendInfo(msg);
				} catch ( Exception e) {
					logger.error("联通短信: indexId="+msg.getIndexId()+"  msgId="+msg.getMessageID()+"  status="+msg.getStatus()+"发送状态更新异常："+e.getMessage(),e);
				}
			}
		}
		return true;
	}
	
	public void serviceStop() {
		this.threadStop=true;
	}
	
}
