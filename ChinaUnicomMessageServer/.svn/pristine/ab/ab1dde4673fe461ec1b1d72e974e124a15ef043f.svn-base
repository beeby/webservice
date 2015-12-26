package com.transfar.messageserver.chinaunicom.scheme;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.transfar.messageserver.scheme.MessageSequence;
import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.utils.MessageUtils;

public class SGIPMessageSequence implements MessageSequence {
	private static Integer cycleInteger=0;
	private static int getNextInteger() {
		int nextVal;
		synchronized (cycleInteger) {
			nextVal=(cycleInteger++);
			if ( cycleInteger >=1000000 ) 
				cycleInteger=0;
		}
		return nextVal;
	}
	private static String areaCode="";
	private static String corpId="";
	private String sequence;
	
	private long nodeCode;
	private long timeStamp;
	private long uniqId;
	
	
	public SGIPMessageSequence() {
		generateSeqence() ;
	}
	
	public SGIPMessageSequence(byte [] seqBytes) throws MessageServerException {
		parse(seqBytes) ;
	}
	
	public  String getAreaCode() {
		return areaCode;
	}


	public  void setAreaCode(String areaCode) {
		SGIPMessageSequence.areaCode = areaCode;
	}


	public  String getCorpId() {
		return corpId;
	}


	public void setCorpId(String corpId) {
		SGIPMessageSequence.corpId = corpId;
	}


	private void generateSeqence() {
		uniqId=getNextInteger();
		String uniqIdStr=String.valueOf(1000000+uniqId).substring(1);
		String nodeCodeStr=("3"+areaCode+"0000").substring(0,5)+corpId;
		nodeCode=Long.valueOf(nodeCodeStr);
		SimpleDateFormat fmt= new SimpleDateFormat("MMddHHmmss");
		String timeStampStr=fmt.format(Calendar.getInstance().getTime());
		timeStamp=Long.valueOf(timeStampStr);
		sequence=nodeCodeStr+timeStampStr+uniqIdStr;
	}
	
	public String getString() {
		return sequence;
	}
	
	public long getLong() {
		return Long.valueOf(sequence);
	}
	
	public byte [] getBytes() {
		byte [] seqBytes=new byte[12];
		MessageUtils.messageFill(seqBytes,nodeCode,0);
		MessageUtils.messageFill(seqBytes,timeStamp,4);
		MessageUtils.messageFill(seqBytes,uniqId,8);
		return seqBytes;
	}
	
	public void parse(byte [] seqBytes) throws MessageServerException {
		if (seqBytes.length<12) 
			throw new MessageServerException(MessageServerException.MEESAGE_SEQUENCE_PARSE_FAILURE);
		nodeCode=MessageUtils.bytesToInt(seqBytes, 0) & 0xFFFFFFFFL;
		String nodeCodeStr=String.valueOf(10000000000L+nodeCode).substring(1);
		timeStamp=MessageUtils.bytesToInt(seqBytes, 4) & 0xFFFFFFFFL;
		String timeStampStr=String.valueOf(10000000000L+timeStamp).substring(1);
		uniqId=MessageUtils.bytesToInt(seqBytes, 8) & 0xFFFFFFFFL;
		String uniqIdStr=String.valueOf(1000000+uniqId).substring(1);
		sequence=nodeCodeStr+timeStampStr+uniqIdStr;
	}
	

}
