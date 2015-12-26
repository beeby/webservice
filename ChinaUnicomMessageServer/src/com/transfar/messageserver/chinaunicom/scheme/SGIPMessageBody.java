package com.transfar.messageserver.chinaunicom.scheme;

public interface SGIPMessageBody {
	/**
	 * 获取消息体的类型
	 * @return
	 */
	public int getMessageType();

	public byte[] getBytes();

	public void setBytes(byte[] messageBytes);
}
