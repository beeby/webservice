package com.transfar.messageserver.chinaunicom.scheme;

import com.transfar.messageserver.utils.MessageUtils;

public class SGIPBind extends SGIPMessage {
	
	private String loginName="";
	private String loginPassword="";
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public byte[] getMessageBodyBytes() {
		byte [] content=new byte[41];
		content[0]=1; // 登录类型。1：SP向SMG建立的连接，用于发送命令
		MessageUtils.messageFill(content, loginName, 1,16);
		MessageUtils.messageFill(content, loginPassword, 17,16);
		return content;
	}

	@Override
	public int getMessageType() {
		return SGIP_BIND;
	}

	@Override
	public void setMessageBodyBytes(byte[] content) {
		// TODO Auto-generated method stub
		
	}

}
