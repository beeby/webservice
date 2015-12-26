package com.transfar.messageserver.chinaunicom.scheme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.transfar.messageserver.utils.MessageServerException;

public class UmsReplyConfirmRequest extends UMSRequest  {


	protected String content;
	protected String mobileNumber;	
	protected String taskID;
	protected Date scheduledTime;
	protected Boolean IgnoreErrorNumber;	
	protected String extNumber;
	
	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
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

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	

	public Boolean isIgnoreErrorNumber() {
		return IgnoreErrorNumber;
	}

	public void setIgnoreErrorNumber(Boolean ignoreErrorNumber) {
		IgnoreErrorNumber = ignoreErrorNumber;
	}

	public String getExtNumber() {
		return extNumber;
	}

	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}

	@Override
	public String getRootName() {
		// TODO Auto-generated method stub
		return "Sms";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Map<String, String> getAttributesMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("in0", getSpId());
		map.put("in1", getSpUsername());
		map.put("in2", getSpPassword());
		map.put("in3", getContent());
		map.put("in4", getMobileNumber());
		map.put("in5", getTaskID());
		map.put("in6", (getScheduledTime() == null) ? null : SimpleDateFormat
				.getDateTimeInstance().format(getScheduledTime()));
		map.put("in7", isIgnoreErrorNumber()?"1":"0");
		map.put("in8", "");
		map.put("in9", getExtNumber());
		map.put("in10", "");
	
		return map;
	}
	
}
