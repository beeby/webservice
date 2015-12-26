package com.transfar.messageserver.chinaunicom.scheme;

public class SGIPUnbind extends SGIPMessage {

	@Override
	public byte[] getMessageBodyBytes() {
		return new byte[0];
	}

	@Override
	public void setMessageBodyBytes(byte[] content) {

	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return SGIP_UNBIND;
	}

}
