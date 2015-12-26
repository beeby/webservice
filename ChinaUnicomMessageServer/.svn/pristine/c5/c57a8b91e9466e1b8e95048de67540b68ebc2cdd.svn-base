package com.transfar.messageserver.chinaunicom.scheme;

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMElement;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class ChinaUnicomReport extends InteractionBasicObject {
	
	private String messageID;

	private String mobileNumber;

	private String statusCode;

	private String timeStamp;

	public ChinaUnicomReport(OMElement parameter) throws MessageServerException {
		super(parameter);
	}
	
	public ChinaUnicomReport()  {
		
	}
	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String getRootName() {

		return "report";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		if (name.equalsIgnoreCase("messageID")) {
			this.messageID = value;
		}
		if (name.equalsIgnoreCase("mobileNumber")) {
			this.mobileNumber = value;
		}
		if (name.equalsIgnoreCase("statusCode")) {
			this.statusCode = value;
		}
		if (name.equalsIgnoreCase("timeStamp")) {
			this.timeStamp = value;
		}

	}

	@Override
	protected Map<String, String> getAttributesMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("messageID", getMessageID());
		map.put("mobileNumber", getMobileNumber());
		map.put("statusCode", getStatusCode());
		map.put("timeStamp", getTimeStamp());
		return map;

	}

}
