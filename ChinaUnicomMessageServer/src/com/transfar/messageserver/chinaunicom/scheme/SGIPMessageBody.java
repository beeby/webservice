package com.transfar.messageserver.chinaunicom.scheme;

public interface SGIPMessageBody {
	/**
	 * ��ȡ��Ϣ�������
	 * @return
	 */
	public int getMessageType();

	public byte[] getBytes();

	public void setBytes(byte[] messageBytes);
}
