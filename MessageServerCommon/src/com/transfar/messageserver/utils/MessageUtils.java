package com.transfar.messageserver.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageUtils {
	private static Log logger = LogFactory.getLog(MessageUtils.class);

	public static byte[] stringToBytes(String str, String encode) {
		byte[] strBytes = null;
		try {
			strBytes = str.getBytes(encode);
		} catch (UnsupportedEncodingException e) {
			logger.error("字符串" + str + "按照编码" + encode + "转换成byte数组时发生了异常"
					+ e.getLocalizedMessage() + "，将按照缺省编码进行转换");
			strBytes = str.getBytes();
		}
		return strBytes;

	}
	
	public static String bytesToString(byte[] bytes,int pos,int len) {
		
		byte[] strBytes = subBytes(bytes,pos,len);
		String str=new String(strBytes).trim();
		return str;

	}

	public static String bytesToString(byte[] bytes,int pos,int len, String encode) {
		
		byte[] strBytes = subBytes(bytes,pos,len);
		String str=null;
		try {
			str=new String(strBytes,encode).trim();
		} catch (UnsupportedEncodingException e) {
			logger.error("字符串" + str + "按照编码" + encode + "转换成byte数组时发生了异常"
					+ e.getLocalizedMessage() + "，将按照缺省编码进行转换");
			str=new String(strBytes).trim();
		}
		return str;

	}
	
	/**
	 * 转换长度为4的byte数组为整型
	 * @param src  长度大于4的byte数组
	 * @param pos  pos+3必须小于数组长度
	 * @return
	 */
	public static int bytesToInt(byte[] src,int pos) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result += ((int) 0xFF & src[pos+i]) << ((3 - i ) * 8);
		}
		return result;
	}
	
	/**
	 * 转换长度为4的byte数组为长整型
	 * @param src  长度大于8的byte数组
	 * @param pos  pos+7必须小于数组长度
	 * @return
	 */
	public static long bytesToLong(byte[] src,int pos) {
		long result =  0;
		for (int i = 0; i < 8; i++) {
			result += ((int) 0xFF & src[pos+i]) << ((7 - i ) * 8);
		}
		return result;
	}
	
	/**
	 * 从byte数组中分离前一段数组，原有数组被缩短
	 * @param src 原数组
	 * @param dest 需要拆分出来的空数组
	 * @return 被分离出来的数组
	 */
	public static byte [] subBytes(byte [] src,int pos,int len) {
		byte [] result=new byte[len];
		if ( len > 0)
			System.arraycopy(src, pos, result, 0, len);
		return result;
	}

	public static byte[] messageFill(byte[] content, byte[] src, int pos) {
		return messageFill(content, src, pos, src.length);
	}

	public static byte[] messageFill(byte[] content, byte[] src, int pos, int len) {
		System.arraycopy(src, 0, content, pos, len);
		return content;
	}

	public static byte [] messageFill(byte[] content, String str, int pos) {
		return messageFill(content, str, pos, -1);
	}

	public static byte [] messageFill(byte[] content, String str, int pos,
			int length) {
		return messageFill(content, str, pos, length, "GBK");
	}

	public static byte [] messageFill(byte[] content, String str, int pos,
			int length, String encode) {
		if (str==null) {
			str="";
		}
		byte[] strBytes = stringToBytes(str, encode);
		if (length == -1) {
			length = strBytes.length;
		}
		if (strBytes.length <= length) {
			content=messageFill(content, strBytes, pos);
		} else {
			content=messageFill(content, strBytes, pos, length);
		}
		return content;
	}

	public static byte [] messageFill(byte[] content, int src, int pos) {
		content[pos + 3] = (byte) (src & 0xff);
		content[pos + 2] = (byte) (src >> 8 & 0xff);
		content[pos + 1] = (byte) (src >> 16 & 0xff);
		content[pos] = (byte) (src >> 24 & 0xff);
		return content;
	}
	
	public static byte [] messageFill(byte[] content, long src, int pos) {
		content[pos + 7] = (byte) (src & 0xff);
		content[pos + 6] = (byte) (src >> 8 & 0xff);
		content[pos + 5] = (byte) (src >> 16 & 0xff);
		content[pos + 4] = (byte) (src >> 24 & 0xff);
		content[pos + 3] = (byte) (src >> 32 & 0xff);
		content[pos + 2] = (byte) (src >> 40 & 0xff);
		content[pos + 1] = (byte) (src >> 48 & 0xff);
		content[pos] = (byte) (src >> 56 & 0xff);
		return content;
	}

	public static byte [] messageFill(byte[] content, BigInteger src, int pos, int len) {
		byte [] srcBytes=src.toByteArray();
		int realLen=(srcBytes.length>len)?len:srcBytes.length;
		int srcPos=srcBytes.length-realLen;
		for ( int i=realLen-1;i>=0;i-- ) {
			content[pos+i]=srcBytes[srcPos+i];
		}
		return content;
	}
	
	public static byte[] mergeBytes(byte[] src1, byte[] src2) {
		if (src1 == null) {
			src1 = new byte[0];
		}
		if (src2 == null) {
			src2 = new byte[0];
		}
		byte[] dest = new byte[src1.length + src2.length];
		messageFill(dest, src1, 0);
		messageFill(dest, src2, src1.length);
		return dest;
	}
	
	public static long getLong(byte[] src, int pos) {
		long result=0;
		for ( int i=7;i>=0;i--) {
			result= result*256 +(src[i] & 0xff) ;
		}

		return result;
	}
}
