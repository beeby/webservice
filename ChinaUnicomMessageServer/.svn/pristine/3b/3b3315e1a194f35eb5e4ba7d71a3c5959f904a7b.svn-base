package com.transfar.messageserver.chinaunicom.vo;

import java.util.HashMap;
import java.util.Map;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class Send extends InteractionBasicObject {

	protected String taskID;
	protected String mobileNumber;
	protected String channelID;
	protected int messageClass;
	protected String title;
	protected String content;
	protected int method;
	protected String scheduledTime;
	protected Integer repeatTime;
	protected Integer repeatInterval;
	protected Integer repeatUnit;
	protected String permitedPeriod;
	protected int priority;

	/**
	 * Gets the value of the taskID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTaskID() {
		return taskID;
	}

	/**
	 * Sets the value of the taskID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTaskID(String value) {
		this.taskID = value;
	}

	/**
	 * Gets the value of the mobileNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the value of the mobileNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMobileNumber(String value) {
		this.mobileNumber = value;
	}

	/**
	 * Gets the value of the channelID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getChannelID() {
		return channelID;
	}

	/**
	 * Sets the value of the channelID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setChannelID(String value) {
		this.channelID = value;
	}

	/**
	 * Gets the value of the messageClass property.
	 * 
	 */
	public int getMessageClass() {
		return messageClass;
	}

	/**
	 * Sets the value of the messageClass property.
	 * 
	 */
	public void setMessageClass(int value) {
		this.messageClass = value;
	}

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the content property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * Gets the value of the method property.
	 * 
	 */
	public int getMethod() {
		return method;
	}

	/**
	 * Sets the value of the method property.
	 * 
	 */
	public void setMethod(int value) {
		this.method = value;
	}

	/**
	 * Gets the value of the scheduledTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getScheduledTime() {
		return scheduledTime;
	}

	/**
	 * Sets the value of the scheduledTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setScheduledTime(String value) {
		this.scheduledTime = value;
	}

	/**
	 * Gets the value of the repeatTime property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRepeatTime() {
		return repeatTime;
	}

	/**
	 * Sets the value of the repeatTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRepeatTime(Integer value) {
		this.repeatTime = value;
	}

	/**
	 * Gets the value of the repeatInterval property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	/**
	 * Sets the value of the repeatInterval property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRepeatInterval(Integer value) {
		this.repeatInterval = value;
	}

	/**
	 * Gets the value of the repeatUnit property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRepeatUnit() {
		return repeatUnit;
	}

	/**
	 * Sets the value of the repeatUnit property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRepeatUnit(Integer value) {
		this.repeatUnit = value;
	}

	/**
	 * Gets the value of the permitedPeriod property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPermitedPeriod() {
		return permitedPeriod;
	}

	/**
	 * Sets the value of the permitedPeriod property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPermitedPeriod(String value) {
		this.permitedPeriod = value;
	}

	/**
	 * Gets the value of the priority property.
	 * 
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the value of the priority property.
	 * 
	 */
	public void setPriority(int value) {
		this.priority = value;
	}

	@Override
	public String getRootName() {
		// TODO Auto-generated method stub
		return "Send";
	}

	@Override
	protected void setAttribute(String name, String value) throws MessageServerException {
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
		map.put("method", String.valueOf(getMethod()));
		map.put("scheduledTime", getScheduledTime());
		map.put("repeatTime", String.valueOf(getRepeatTime()));
		map.put("repeatInterval", String.valueOf(getRepeatInterval()));
		map.put("repeatUnit",String.valueOf( getRepeatUnit()));
		map.put("permitedPeriod", getPermitedPeriod());
		map.put("priority", String.valueOf(getPriority()));
		return map;
	}
	
	
}
