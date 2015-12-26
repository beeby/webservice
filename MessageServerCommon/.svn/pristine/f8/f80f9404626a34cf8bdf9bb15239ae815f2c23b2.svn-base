package com.transfar.messageserver.scheme;

import com.transfar.messageserver.utils.MessageServerException;

public interface MessageSequence {
	/**
	 * 根据byte数组生成序列对象
	 * 
	 * @param seqBytes
	 */
	public void parse(byte[] seqBytes) throws MessageServerException;
	
	/**
	 * 获得String类型的序列号
	 * @return
	 */
	public String getString();
	
	/**
	 * 获得序列号的byte数组
	 * @return
	 */
	public byte[] getBytes();
}
