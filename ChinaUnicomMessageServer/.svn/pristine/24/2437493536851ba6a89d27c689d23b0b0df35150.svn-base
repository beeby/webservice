package com.transfar.messageserver.chinaunicom.scheme;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.scheme.Message;
import com.transfar.messageserver.scheme.MessageSequence;
import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.utils.MessageUtils;

public abstract class SGIPMessage extends Message {

	public static final int SGIP_BIND = 0x1;
	public static final int SGIP_BIND_RESP = 0x80000001;
	public static final int SGIP_UNBIND = 0x2;
	public static final int SGIP_UNBIND_RESP = 0x80000002;
	public static final int SGIP_SUBMIT = 0x3;
	public static final int SGIP_SUBMIT_RESP = 0x80000003;
	public static final int SGIP_DELIVER = 0x4;
	public static final int SGIP_DELIVER_RESP = 0x80000004;
	public static final int SGIP_REPORT = 0x5;
	public static final int SGIP_REPORT_RESP = 0x80000005;
	public static final int SGIP_ADDSP = 0x6;
	public static final int SGIP_ADDSP_RESP = 0x80000006;
	public static final int SGIP_MODIFYSP = 0x7;
	public static final int SGIP_MODIFYSP_RESP = 0x80000007;
	public static final int SGIP_DELETESP = 0x8;
	public static final int SGIP_DELETESP_RESP = 0x80000008;
	public static final int SGIP_QUERYROUTE = 0x9;
	public static final int SGIP_QUERYROUTE_RESP = 0x80000009;
	public static final int SGIP_ADDTELESEG = 0xa;
	public static final int SGIP_ADDTELESEG_RESP = 0x8000000a;
	public static final int SGIP_MODIFYTELESEG = 0xb;
	public static final int SGIP_MODIFYTELESEG_RESP = 0x8000000b;
	public static final int SGIP_DELETETELESEG = 0xc;
	public static final int SGIP_DELETETELESEG_RESP = 0x8000000c;
	public static final int SGIP_ADDSMG = 0xd;
	public static final int SGIP_ADDSMG_RESP = 0x8000000d;
	public static final int SGIP_MODIFYSMG = 0xe;
	public static final int SGIP_MODIFYSMG_RESP = 0x0000000e;
	public static final int SGIP_DELETESMG = 0xf;
	public static final int SGIP_DELETESMG_RESP = 0x8000000f;
	public static final int SGIP_CHECKUSER = 0x10;
	public static final int SGIP_CHECKUSER_RESP = 0x80000010;
	public static final int SGIP_USERRPT = 0x11;
	public static final int SGIP_USERRPT_RESP = 0x80000011;
	public static final int SGIP_TRACE = 0x1000;
	public static final int SGIP_TRACE_RESP = 0x80001000;

	protected MessageSequence sequence;

	public abstract int getMessageType();

	public SGIPMessageSequence getSequence() {
		return (SGIPMessageSequence)sequence;
	}

	public void setSequence(MessageSequence sequence) {
		this.sequence = sequence;
	}

	public void setSequence() {
		this.sequence = new SGIPMessageSequence();
	}

	public byte[] getMessageBytes() {
		byte[] bodyBytes = getMessageBodyBytes();
		if (sequence == null)
			setSequence();
		byte[] msgBytes = new byte[20 + bodyBytes.length];
		msgBytes=MessageUtils.messageFill(msgBytes, msgBytes.length, 0);
		msgBytes=MessageUtils.messageFill(msgBytes, getMessageType(), 4);
		msgBytes=MessageUtils.messageFill(msgBytes, sequence.getBytes(), 8);
		msgBytes=MessageUtils.messageFill(msgBytes, bodyBytes, 20);
		return msgBytes;
	}

	public void setMessageBytes(byte[] msgBytes) throws MessageServerException {
		byte[] seqBytes = MessageUtils.subBytes(msgBytes, 8,12);
		byte[] bodyBytes= MessageUtils.subBytes(msgBytes, 20,msgBytes.length-20);
		this.sequence = new SGIPMessageSequence(seqBytes);
		setMessageBodyBytes(bodyBytes);
	}
}
