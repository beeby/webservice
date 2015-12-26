package com.transfar.messageserver.chinaunicom.scheme;

public class SGIPSubmitResp extends SGIPMessage {
	private int result;
	public final int Success=0;
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public byte[] getMessageBodyBytes() {
		byte [] content=new byte[9];
		content[0]=(byte)this.result;
		return content;
	}

	@Override
	public void setMessageBodyBytes(byte[] content) {
		this.result=content[0];

	}

	@Override
	public int getMessageType() {
		return SGIP_SUBMIT_RESP;
	}

}
