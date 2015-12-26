package com.transfar.messageserver.chinaunicom.service;



import com.transfar.messageserver.chinaunicom.scheme.SGIPBind;
import com.transfar.messageserver.chinaunicom.scheme.SGIPBindResp;
import com.transfar.messageserver.chinaunicom.scheme.SGIPMessageFactory;
import com.transfar.messageserver.scheme.Message;
import com.transfar.messageserver.scheme.MessageFactory;
import com.transfar.messageserver.server.MessageServer;
import com.transfar.messageserver.utils.MessageServerException;


public class SGIPServer extends MessageServer {

	private String loginName;
	private String loginPassword;
	protected SGIPMessageFactory msgFactory=new SGIPMessageFactory();

	public SGIPServer(String serverAddress, int serverPort,String loginName,String loginPassword) {

		this.loginName=loginName;
		this.loginPassword=loginPassword;
	}


	@Override
	protected MessageFactory getMessageFactory() {
		return msgFactory;
	}

	@Override
	protected int getQueueLimit() {
		return 32;
	}

	@Override
	protected Message getLoginMessage() {
		SGIPBind bindMsg=new SGIPBind();
		bindMsg.setLoginName(loginName);
		bindMsg.setLoginPassword(loginPassword);
		return bindMsg;
	}


	@Override
	protected void deliver(Message msg) {
		if ( msg.getClass() == SGIPBindResp.class ) {
	 
		
		}
		}


	@Override
	protected void dispatch(Message msg, Message response) {
		
		
	
		
	}


	@Override
	protected boolean checkLoginStatus(Message msg)
			throws MessageServerException {
		if ( msg.getClass().equals(SGIPBindResp.class)) {
			SGIPBindResp resp=(SGIPBindResp)msg;
			if(resp.getResult()==SGIPBindResp.Success) {
				logger.info("收到中国联通网关登录验证结果为Success");
				setConnected();
				return true;
			} else {
				disconnect();
			}
		}
		return false;
	}


	@Override
	protected Message getTerminalMessage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void errorHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	
}
