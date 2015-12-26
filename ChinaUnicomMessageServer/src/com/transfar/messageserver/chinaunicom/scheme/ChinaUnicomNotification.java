package com.transfar.messageserver.chinaunicom.scheme;

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMElement;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class ChinaUnicomNotification extends InteractionBasicObject {
	private String channelID;

	private String mobileNumber;

	private String content;

	private String timeStamp;
	
	private String extendNumber;
	
	public ChinaUnicomNotification(OMElement parameter) throws MessageServerException {
		super(parameter);
	}
	
	public ChinaUnicomNotification() {
		
	}
	
	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getExtendNumber() {
		return extendNumber;
	}

	public void setExtendNumber(String extendNumber) {
		this.extendNumber = extendNumber;
	}

	@Override
	public String getRootName() {
		
		return "notification";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		if (name.equalsIgnoreCase("channelID")) {
			this.channelID = value;
		}
		if (name.equalsIgnoreCase("mobileNumber")) {
			this.mobileNumber = value;
		}
		if (name.equalsIgnoreCase("content")) {
			this.content = value;
		}
		if (name.equalsIgnoreCase("timeStamp")) {
			this.timeStamp = value;
		}

	}

	@Override
	protected Map<String, String> getAttributesMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelID", getChannelID());
		map.put("mobileNumber", getMobileNumber());
		map.put("content", getContent());
		map.put("timeStamp", getTimeStamp());
		return map;
	}

}
