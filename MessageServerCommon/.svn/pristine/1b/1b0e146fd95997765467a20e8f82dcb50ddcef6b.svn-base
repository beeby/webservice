/**
 * 2008-8-22
 * tiger.亚
 */
package com.transfar.messageserver.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;

public class MessageServerException extends Exception {

	private static HashMap<Integer, String> messageMap = new HashMap<Integer, String>();

	/**
	 * 入参错误
	 */
	public static final int SYSTEM_ERROR = 000000;
	public static final int PARAM_IS_ERROR = 000001;
	public static final int PARAM_NULL = 000002;
	static {
		messageMap.put(PARAM_IS_ERROR, "参数错误");
		messageMap.put(SYSTEM_ERROR, "系统错误");
		messageMap.put(PARAM_NULL, "参数为空");
	}

	/*
	 * 接口异常定义
	 */
	public static final int INTERACTION_TRANSPORT_ERROR = 30000; // 接口数据根元素与预期的值不一致
	
	/**
	 * 接口返回失败信息
	 */
	public static final int INTERACTION_RESPONSE_ERROR = 30001; // 接口返回失败信息
	public static final int INTERACTION_ROOT_ELEMENT_DISMATCH = 30010; // 接口数据根元素与预期的值不一致
	public static final int INTERACTION_EXPECT_ELEMENT_NOT_FOUND = 30011; // 未找到预期的元素
	public static final int INTERACTION_ELEMENT_NOT_DEFINE = 30012; // 未定义的元素

	static {
		messageMap.put(INTERACTION_TRANSPORT_ERROR, "接口数据根元素与预期的值不一致");
		messageMap.put(INTERACTION_RESPONSE_ERROR, "接口返回失败信息");
		messageMap.put(INTERACTION_ROOT_ELEMENT_DISMATCH, "接口数据根元素与预期的值不一致");
		messageMap.put(INTERACTION_EXPECT_ELEMENT_NOT_FOUND, "未找到预期的元素");
	}
	
	/**
	 * 接口服务
	 */
	public static final int INTERACTION_ADDRESS_INVALID = 31001; // 接口返回失败信息
	public static final int INTERACTION_APPID_INVALID = 31002; // 接口数据根元素与预期的值不一致
	public static final int INTERACTION_AUTH_INVALID = 31003; // 未找到预期的元素


	static {
		messageMap.put(INTERACTION_ADDRESS_INVALID, "地址未被授权访问");
		messageMap.put(INTERACTION_APPID_INVALID, "未被授权的应用");
		messageMap.put(INTERACTION_AUTH_INVALID, "验证失败");

	}
	
	/**
	 * 邮件相关失败信息
	 */
	public static final int MAIL_RECIPTIENT_INVALID = 40001; 
	public static final int MAIL_IS_EMPTY = 40002; // 接口返回失败信息
	static {
		messageMap.put(MAIL_RECIPTIENT_INVALID, "非法的邮件接收地址");
		messageMap.put(MAIL_IS_EMPTY, "邮件内容为空");
	}

	/**
	 * 数据库相关错误定义
	 */
	public static final int DATABASE_RESOURCE_BUSY_LOCK = 80001;

	static {
		messageMap.put(DATABASE_RESOURCE_BUSY_LOCK, "访问数据库资源时该资源忙");
	}

	/*
	 *  消息对象操作相关错误定义
	 */
	
	public static final int MESSAGE_FORMAT_ERROR=910001;
	public static final int MEESAGE_SEQUENCE_PARSE_FAILURE=910002;
	public static final int UNKNOW_SMGP_TLV_TAG=920001;
	public static final int SMGP_TLV_BYTE_NEEDED=920002;
	public static final int SMGP_TLV_STRING_NEEDED=920003;
	public static final int SMGP_TLV_FORMAT_INVALID=920004;
	static {
		messageMap.put(MESSAGE_FORMAT_ERROR, "数据包格式错误，无法解析成对应消息");
		messageMap.put(MEESAGE_SEQUENCE_PARSE_FAILURE, "消息序列号数据格式不正确，无法生成");
		messageMap.put(UNKNOW_SMGP_TLV_TAG, "未知的SMGPTLV标签");
		messageMap.put(SMGP_TLV_BYTE_NEEDED, "SMGPTLV标签要求Byte类型，传入的是String类型");
		messageMap.put(SMGP_TLV_STRING_NEEDED, "SMGPTLV标签要求String类型，传入的是Byte类型");
		messageMap.put(SMGP_TLV_FORMAT_INVALID, "SMGPTLV包格式非法");
	}
	
	
	public static final int CANNOT_GET_AVAILABLE_SERVER = 80000;
	static {
		messageMap.put(CANNOT_GET_AVAILABLE_SERVER, "无法获取可用的发送服务进程");
	}
	/**
	 * 网络连接相关定义
	 * 
	 * @param exceptionCode
	 */
	public static final int SOCKET_INITIALIZATION_FAIL = 90000;
	public static final int SOCKET_IS_NOT_INITIALIZED = 90001;
	public static final int SOCKET_OUTPUT_STREAM_CLOSED = 90002;
	public static final int SOCKET_INPUT_STREAM_CLOSED = 90003;

	static {
		messageMap.put(SOCKET_INITIALIZATION_FAIL, "SOCKET初始化失败");
		messageMap.put(SOCKET_IS_NOT_INITIALIZED, "SOCKET没有被初始化");
		messageMap.put(SOCKET_OUTPUT_STREAM_CLOSED, "向网关服务器发送数据输出缓冲区被关闭");
		messageMap.put(SOCKET_INPUT_STREAM_CLOSED, "从网关服务器接收数据输入缓冲区被关闭");
	}

	public MessageServerException(int messageFormatError) {
		super();
		this.exceptionCode = messageFormatError;
		messageCHN = messageMap.get(messageFormatError);
	}

	public MessageServerException(int exceptionCode, Throwable e) {
		super(e);
		this.exceptionCode = exceptionCode;

		messageCHN = messageMap.get(exceptionCode);
		if (e != null) {

			messageExt = e.getMessage();
		}
	}

	/**
	 * new BusException(050501,new String[] { "XY企业"}，e);
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @param params
	 *            需要输出得前缀
	 * @param e
	 */
	public MessageServerException(int exceptionCode, String[] params,
			Throwable e) {
		super(e);
		this.exceptionCode = exceptionCode;

		messageCHN = messageMap.get(exceptionCode);
		if (params != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < params.length; i++) {
				String element = params[i];
				sb.append("\"");
				sb.append(element);
				sb.append("\"");
				sb.append("\t");
			}
			messageExt = sb.toString();
		}
	}

	public MessageServerException(int exceptionCode, String params) {
		super();
		this.exceptionCode = exceptionCode;

		messageCHN = messageMap.get(exceptionCode);
		if (params != null) {

			messageExt = params;
		}
	}

	/**
	 * 暂未考虑对异常进行特殊处理
	 * 
	 * @param e
	 * @throws MessageServerException
	 */
	public static void handleException(Exception e)
			throws MessageServerException {
		if (e instanceof MessageServerException) {
			throw (MessageServerException) e;
		} else {
			e.printStackTrace();
		}
		throw new MessageServerException(MessageServerException.SYSTEM_ERROR, e);
	}

	/**
	 * 处理异常并支持自定义处理
	 * 
	 * @param e
	 *            处理结果返回true继续抛出异常，否则不 new PrivilegedAction() { public Object
	 *            run() { do(); return Boolean.true; } }
	 * @throws MessageServerException
	 */
	public static void handleException(Exception e, PrivilegedAction action)
			throws MessageServerException {

		if (e instanceof MessageServerException) {
			if (((Boolean) AccessController.doPrivileged(action))
					.booleanValue()) {
				throw (MessageServerException) e;
			}
			return;
		} else {
			e.printStackTrace();
		}
		throw new MessageServerException(MessageServerException.SYSTEM_ERROR, e);
	}

	public static MessageServerException buildBusException(int excetionCode) {
		return new MessageServerException(excetionCode);
	}

	/**
	 * 异常编码
	 */
	private int exceptionCode;

	private String messageCHN = null;

	// 补充信息描述
	private String messageExt;

	/*
	 * public AbstractException(String exceptionCode){ this(exceptionCode,null);
	 * }
	 * 
	 * public AbstractException(String exceptionCode,Throwable e){
	 * 
	 * this(exceptionCode,null,e);
	 * 
	 * 
	 * super(e); this.exceptionCode = exceptionCode; ExceptionLocate el =
	 * ExceptionLocate.getInstance(getJTDS()); messageCHN =
	 * el.getMessageCHN(exceptionCode);
	 * 
	 * }
	 */

	public String toString() {
		String s = getClass().getSimpleName();
		String message = exceptionCode + " " + messageCHN
				+ (messageExt == null ? "" : (": " + messageExt));
		return (message != null) ? (s + ": " + message) : s;
	}

	public String getMessage() {
		return getMessageCHN();
	}

	public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessageCHN() {
		String str = messageCHN;
		if (str == null) {
			str = "未描述的错误编码: " + getExceptionCode();
		}
		return str + (messageExt == null ? "" : (": " + messageExt));
	}

	public void setMessageCHN(String messageCHN) {
		this.messageCHN = messageCHN;
	}
	/**
	 * 由子类提供jdbcTemplate或者datasource 两者均可
	 * 
	 * @return
	 */

}
