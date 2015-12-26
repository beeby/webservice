package com.transfar.messageserver.chinaunicom.scheme;

import java.util.Map;

import org.apache.axiom.om.OMElement;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class AsisInfoSendResponse extends InteractionBasicObject{
	private String msgID;
	
	public AsisInfoSendResponse(OMElement element) throws MessageServerException {
		super(element);
	}
	
	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	@Override
	public String getRootName() {
		// TODO Auto-generated method stub
		return "sendResponse";
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
