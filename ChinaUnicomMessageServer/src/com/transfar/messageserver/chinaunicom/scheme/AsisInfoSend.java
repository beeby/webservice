package com.transfar.messageserver.chinaunicom.scheme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class AsisInfoSend extends InteractionBasicObject {

	protected String taskID;
	protected String mobileNumber;
	protected String channelID;
	protected int messageClass=1;
	protected String title;
	protected String content;
	protected int method=1;
	protected Date scheduledTime;
	protected Integer repeatTime=0;
	protected Integer repeatInterval=0;
	protected Integer repeatUnit=0;
	protected String permitedPeriod="0";
	protected int priority=0;

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

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public int getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(int messageClass) {
		this.messageClass = messageClass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Integer getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(Integer repeatTime) {
		this.repeatTime = repeatTime;
	}

	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public Integer getRepeatUnit() {
		return repeatUnit;
	}

	public void setRepeatUnit(Integer repeatUnit) {
		this.repeatUnit = repeatUnit;
	}

	public String getPermitedPeriod() {
		return permitedPeriod;
	}

	public void setPermitedPeriod(String permitedPeriod) {
		this.permitedPeriod = permitedPeriod;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String getRootName() {
		// TODO Auto-generated method stub
		return "send";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Map<String, String> getAttributesMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskID", getTaskID());
		map.put("mobileNumber", getMobileNumber());
		map.put("channelID", getChannelID());
		map.put("messageClass", String.valueOf(getMessageClass()));
		map.put("title", getTitle());
		map.put("content", getContent());
		map.put("method", "" + getMethod());
		map.put("scheduledTime",
				(getScheduledTime() == null) ? null : SimpleDateFormat
						.getDateTimeInstance().format(getScheduledTime()));
		map.put("repeatTime", String.valueOf(getRepeatTime()));
		map.put("repeatInterval", String.valueOf(getRepeatInterval()));
		map.put("repeatUnit", String.valueOf(getRepeatUnit()));
		map.put("permitedPeriod", getPermitedPeriod());
		map.put("priority", String.valueOf(getPriority()));
		return map;
	}
	
}
