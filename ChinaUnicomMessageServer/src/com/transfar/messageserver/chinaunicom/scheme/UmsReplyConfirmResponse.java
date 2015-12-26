package com.transfar.messageserver.chinaunicom.scheme;

import java.util.Map;

import org.apache.axiom.om.OMElement;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class UmsReplyConfirmResponse extends InteractionBasicObject{
	private String msgID;
	
	public UmsReplyConfirmResponse(OMElement element) throws MessageServerException {
		super(element);
	}
	
	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	
	public String getResult() {
		return "";
	}
	
	public String getDescription() {
		return "";
	}

	@Override
	public String getRootName() {
		// TODO Auto-generated method stub
		return "SmsResponse";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		if ( name.equalsIgnoreCase("out")) {
			this.msgID=value;
		}
		
	}

	@Override
	protected Map<String, String> getAttributesMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
