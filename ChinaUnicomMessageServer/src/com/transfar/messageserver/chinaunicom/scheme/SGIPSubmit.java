package com.transfar.messageserver.chinaunicom.scheme;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.transfar.messageserver.utils.MessageUtils;

public class SGIPSubmit extends SGIPMessage {

	private final byte UCS2 = 0x8;
	private final byte GBK = 0x15;
	private String spNumber = "";
	private String chargeNumber = "000000000000000000000";
	private byte userCount;
	private String[] userNumber;
	private String corpId;
	private String serviceType;
	private byte feeType = 0;
	private String feeValue = "0";
	private String giveValue = "0";
	private byte agentFlag = 0;;
	private byte morelatetoMTFlag = 3;
	private byte priority = 0;
	private Date expireTime;
	private Date scheduleTime;
	private byte reportFlag;
	private byte tpPid;
	private byte tpUdhi;
	private byte messageCoding = 0x8;
	private String messageContent;
	private byte messageLength;
	private byte pkTotal;
	private byte pkCount;
	private byte pkSeq;

	public String getSpNumber() {
		return spNumber;
	}

	/**
	 * ����SP�����
	 * 
	 * @param spNumber
	 *            SP�Ľ������
	 */
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	/**
	 * ���Ѻ��룬�ֻ�����ǰ�ӡ�86�������־�� ���ҽ���Ⱥ���Ҷ��û��շ�ʱΪ�գ� ���Ϊ�գ����������Ϣ�����ķ�����UserNumber������û�֧����
	 * ���Ϊȫ���ַ�����000000000000000000000������ʾ��������Ϣ�����ķ�����SP֧����
	 * 
	 * @param chargeNumber
	 *            ���Ѻ���
	 */
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	/**
	 * ���ý��ն���Ϣ���ֻ�������ȡֵ��Χ1��100 return userCount ���ն���Ϣ���ֻ�������ȡֵ��Χ1��100
	 */
	public byte getUserCount() {
		return userCount;
	}

	public String[] getUserNumber() {
		return userNumber;
	}

	/**
	 * ���ոö���Ϣ���ֻ��ţ����ֶ��ظ�UserCountָ���Ĵ����� �ڷ���ʱ���ֻ���ǰ���Զ���86�����־
	 * 
	 * @param userNumber
	 */
	public void setUserNumber(String[] userNumber) {
		this.userNumber = userNumber;
		if (userNumber == null) {
			this.userCount = 0;
		} else {
			this.userCount = (byte) userNumber.length;
		}
	}

	public String getCorpId() {
		return corpId;
	}

	/**
	 * ��ҵ���룬ȡֵ��Χ0-99999
	 * 
	 * @param coprId
	 */
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getServiceType() {
		return serviceType;
	}

	/**
	 * ҵ����룬��SP����
	 * 
	 * @param serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getFeeType() {
		return feeType;
	}

	/**
	 * �Ʒ�����
	 * 
	 * @param feeType
	 */
	public void setFeeType(byte feeType) {
		this.feeType = feeType;
	}

	public String getFeeValue() {
		return feeValue;
	}

	/**
	 * ȡֵ��Χ0-99999����������Ϣ���շ�ֵ����λΪ�֣���SP���� ���ڰ������շѵ��û�����ֵΪ����ѵ�ֵ
	 * 
	 * @param feeValue
	 */
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	public String getGiveValue() {
		return giveValue;
	}

	/**
	 * ȡֵ��Χ0-99999�������û��Ļ��ѣ� ��λΪ�֣���SP���壬��ָ��SP���û����͹��ʱ�����ͻ���
	 * 
	 * @param giveValue
	 */
	public void setGiveValue(String giveValue) {
		this.giveValue = giveValue;
	}

	public int getAgentFlag() {
		return agentFlag;
	}

	/**
	 * ���շѱ�־��0��Ӧ�գ�1��ʵ��
	 * 
	 * @param agentFlag
	 */
	public void setAgentFlag(byte agentFlag) {
		this.agentFlag = agentFlag;
	}

	public int getMorelatetoMTFlag() {
		return morelatetoMTFlag;
	}

	/**
	 * ����MT��Ϣ��ԭ�� 0-MO�㲥����ĵ�һ��MT��Ϣ�� 1-MO�㲥����ķǵ�һ��MT��Ϣ�� 2-��MO�㲥�����MT��Ϣ��
	 * 3-ϵͳ���������MT��Ϣ��
	 * 
	 * @param morelatetoMTFlag
	 */
	public void setMorelatetoMTFlag(byte morelatetoMTFlag) {
		this.morelatetoMTFlag = morelatetoMTFlag;
	}

	public int getPriority() {
		return priority;
	}

	/**
	 * ���ȼ�0-9�ӵ͵��ߣ�Ĭ��Ϊ0
	 * 
	 * @param priority
	 */
	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * ����Ϣ��������ֹʱ�䣬���Ϊ�գ���ʾʹ�ö���Ϣ���ĵ�ȱʡֵ��
	 * 
	 * @param expireTime
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * ����Ϣ��ʱ���͵�ʱ�䣬���Ϊ�գ���ʾ���̷��͸ö���Ϣ��
	 * 
	 * @param scheduleTime
	 */
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public byte getReportFlag() {
		return reportFlag;
	}

	/**
	 * ״̬������ 0-������Ϣֻ��������ʱҪ����״̬���� 1-������Ϣ��������Ƿ�ɹ���Ҫ����״̬���� 2-������Ϣ����Ҫ����״̬����
	 * 3-������Ϣ��Я�����¼Ʒ���Ϣ�����·����û���Ҫ����״̬���� ����-���� ȱʡ����Ϊ0
	 * 
	 * @param reportFlag
	 */
	public void setReportFlag(byte reportFlag) {
		this.reportFlag = reportFlag;
	}

	public byte getTpPid() {
		return tpPid;
	}

	/**
	 * GSMЭ�����͡���ϸ������ο�GSM03.40�е�9.2.3.9
	 * 
	 * @param tpPid
	 */
	private void setTpPid(byte tpPid) {
		this.tpPid = tpPid;
	}

	public byte getTpUdhi() {
		return tpUdhi;
	}

	/**
	 * GSMЭ�����͡���ϸ������ο�GSM03.40�е�9.2.3.23,��ʹ��1λ���Ҷ���
	 * 
	 * @param tpUdhi
	 */
	private void setTpUdhi(byte tpUdhi) {
		this.tpUdhi = tpUdhi;
	}

	public byte getMessageCoding() {
		return messageCoding;
	}

	/**
	 * ����Ϣ�ı����ʽ�� 0����ASCII�ַ��� 3��д������ 4�������Ʊ��� 8��UCS2���� 15: GBK���� ȱʡΪGBK
	 * 
	 * @param messageCoding
	 */
	private void setMessageCoding(byte messageCoding) {
		this.messageCoding = messageCoding;
	}

	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * ����Ϣ����
	 * 
	 * @param messageContent
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public byte getPkTotal() {
		return pkTotal;
	}

	/**
	 * ���ó�����������
	 * 
	 * @param pkTotal
	 */
	public void setPkTotal(byte pkTotal) {
		this.pkTotal = pkTotal;
	}

	public byte getPkCount() {
		return pkCount;
	}

	/**
	 * ���ó������е����
	 * 
	 * @param pkCount
	 */
	public void setPkCount(byte pkCount) {
		this.pkCount = pkCount;
	}

	/**
	 * �������еĲο��ţ���������ͬ�ĳ�����
	 * 
	 * @return
	 */
	public byte getPkSeq() {
		return pkSeq;
	}

	public void setPkSeq(byte pkSeq) {
		this.pkSeq = pkSeq;
	}

	private byte[] getMessageContentBytes() {
		byte[] content = new byte[0];
		try {
			if (messageCoding == UCS2) {
				content = messageContent.getBytes("UTF-16BE");
			} else if (messageCoding == GBK) {
				content = messageContent.getBytes("GBK");
			} else
				content = messageContent.getBytes();
		} catch (UnsupportedEncodingException e) {
			content = messageContent.getBytes();
			logger.error("SGIPSubmit��Ϣ����: \""+messageContent +"\"ת����"+messageCoding+"����ʱ����������ȱʡ����");
		}
		return content;
	}

	@Override
	public byte[] getMessageBodyBytes() {
		int bodyLength=123; //��ȥMessageContent��userNumber�ĳ���;
		bodyLength+=21*userNumber.length;  //���ϵ绰��������*���ȣ��̶�21��;
		byte [] contentBytes=getMessageContentBytes();
		if ( pkTotal >1 ) {
			messageLength=(byte)((contentBytes.length>134)?134:contentBytes.length);
			tpUdhi=0x1;
			bodyLength+=messageLength+6;
		} else {
			messageLength=(byte)((contentBytes.length>70)?140:contentBytes.length);
			tpUdhi=0x0;
			bodyLength+=messageLength;
		}
		byte [] bodyBytes=new byte[bodyLength];
		
		bodyBytes=MessageUtils.messageFill(bodyBytes,spNumber,0,21);
		bodyBytes=MessageUtils.messageFill(bodyBytes,chargeNumber,21,21);
		bodyBytes[42]=userCount;
		int ptr=43;  //���λ��ƫ��ָ��
		for (int i=0;i<userCount;i++) {
			bodyBytes=MessageUtils.messageFill(bodyBytes,userNumber[i],ptr,21);
			ptr+=21;
		}
		bodyBytes=MessageUtils.messageFill(bodyBytes,corpId,ptr,5);
		bodyBytes=MessageUtils.messageFill(bodyBytes,serviceType,ptr+5,10);
		bodyBytes[ptr+15]=feeType;
		bodyBytes=MessageUtils.messageFill(bodyBytes,feeValue,ptr+16,6);
		bodyBytes=MessageUtils.messageFill(bodyBytes,giveValue,ptr+22,6);
		bodyBytes[ptr+28]=agentFlag;
		bodyBytes[ptr+29]=morelatetoMTFlag;
		bodyBytes[ptr+30]=priority;
		if ( expireTime != null )
			bodyBytes=MessageUtils.messageFill(bodyBytes,dateToSGIPTimeString(expireTime),ptr+31,16);
		if ( scheduleTime != null )
			bodyBytes=MessageUtils.messageFill(bodyBytes,dateToSGIPTimeString(scheduleTime),ptr+47,16);
		bodyBytes[ptr+63]=reportFlag;
		bodyBytes[ptr+64]=tpPid;
		bodyBytes[ptr+65]=tpUdhi;
		bodyBytes[ptr+66]=messageCoding;
		bodyBytes[ptr+67]=0; //����Ϣ���ͣ�0�� ����Ϣ��Ϣ
		bodyBytes[ptr+68]=messageLength;
		ptr+=69;
		if ( pkTotal >1 ) {
			bodyBytes[ptr]=0x5;
			bodyBytes[ptr+=1]=0x0;
			bodyBytes[ptr+=1]=0x3;
			bodyBytes[ptr+=1]=pkSeq;
			bodyBytes[ptr+=1]=pkTotal;
			bodyBytes[ptr+=1]=pkCount;
			ptr++;
		} 
		bodyBytes=MessageUtils.messageFill(bodyBytes, contentBytes, ptr, messageLength);
		return bodyBytes;
	}

	private String dateToSGIPTimeString(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmss032+");
		return fmt.format(date);
	}

	@Override
	public void setMessageBodyBytes(byte[] content) {

	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return SGIP_SUBMIT;
	}

}
