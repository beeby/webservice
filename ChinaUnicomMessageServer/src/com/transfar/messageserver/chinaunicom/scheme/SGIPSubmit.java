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
	 * 设置SP接入号
	 * 
	 * @param spNumber
	 *            SP的接入号码
	 */
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	/**
	 * 付费号码，手机号码前加“86”国别标志； 当且仅当群发且对用户收费时为空； 如果为空，则该条短消息产生的费用由UserNumber代表的用户支付；
	 * 如果为全零字符串“000000000000000000000”，表示该条短消息产生的费用由SP支付。
	 * 
	 * @param chargeNumber
	 *            付费号码
	 */
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	/**
	 * 设置接收短消息的手机数量，取值范围1至100 return userCount 接收短消息的手机数量，取值范围1至100
	 */
	public byte getUserCount() {
		return userCount;
	}

	public String[] getUserNumber() {
		return userNumber;
	}

	/**
	 * 接收该短消息的手机号，该字段重复UserCount指定的次数， 在发送时，手机号前会自动加86国别标志
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
	 * 企业代码，取值范围0-99999
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
	 * 业务代码，由SP定义
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
	 * 计费类型
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
	 * 取值范围0-99999，该条短消息的收费值，单位为分，由SP定义 对于包月制收费的用户，该值为月租费的值
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
	 * 取值范围0-99999，赠送用户的话费， 单位为分，由SP定义，特指由SP向用户发送广告时的赠送话费
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
	 * 代收费标志，0：应收；1：实收
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
	 * 引起MT消息的原因 0-MO点播引起的第一条MT消息； 1-MO点播引起的非第一条MT消息； 2-非MO点播引起的MT消息；
	 * 3-系统反馈引起的MT消息。
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
	 * 优先级0-9从低到高，默认为0
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
	 * 短消息寿命的终止时间，如果为空，表示使用短消息中心的缺省值。
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
	 * 短消息定时发送的时间，如果为空，表示立刻发送该短消息。
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
	 * 状态报告标记 0-该条消息只有最后出错时要返回状态报告 1-该条消息无论最后是否成功都要返回状态报告 2-该条消息不需要返回状态报告
	 * 3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告 其它-保留 缺省设置为0
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
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.9
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
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
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
	 * 短消息的编码格式。 0：纯ASCII字符串 3：写卡操作 4：二进制编码 8：UCS2编码 15: GBK编码 缺省为GBK
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
	 * 短消息内容
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
	 * 设置长短信总条数
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
	 * 设置长短信中的序号
	 * 
	 * @param pkCount
	 */
	public void setPkCount(byte pkCount) {
		this.pkCount = pkCount;
	}

	/**
	 * 长短信中的参考号，用于区别不同的长短信
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
			logger.error("SGIPSubmit消息内容: \""+messageContent +"\"转换成"+messageCoding+"编码时出错，将采用缺省编码");
		}
		return content;
	}

	@Override
	public byte[] getMessageBodyBytes() {
		int bodyLength=123; //除去MessageContent和userNumber的长度;
		bodyLength+=21*userNumber.length;  //加上电话号码数量*长度（固定21）;
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
		int ptr=43;  //填充位置偏移指针
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
		bodyBytes[ptr+67]=0; //短消息类型，0： 短消息信息
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
