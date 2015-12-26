package com.transfar.messageserver.scheme;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.utils.MessageServerException;

public abstract class Message {
	
	protected Log logger=LogFactory.getLog(getClass());
	
	protected int transmitCount;
	
	protected Date transmitTime;
	
	protected MessageSequence sequence;
	
	public abstract int getMessageType();
	
	public abstract void setSequence(MessageSequence sequence);

	public abstract void setSequence();
	
	public abstract MessageSequence getSequence();

	public abstract byte[] getMessageBytes();

	public abstract void setMessageBytes(byte[] msgBytes) throws MessageServerException;
	
	protected abstract byte[] getMessageBodyBytes();

	protected abstract void setMessageBodyBytes(byte[] content) throws MessageServerException;

	
	public int getTransmitCount() {
		return this.transmitCount;
	}
	
	public void setTransmitCount(int count) {
		this.transmitCount=count;
	}

	public Date getTransmitTime() {
		return transmitTime;
	}

	public void setTransmitTime(Date transmitTime) {
		this.transmitTime = transmitTime;
	}
	
	
	
	
}
