package com.transfar.messageserver.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Charset {
	
	private static Log logger = LogFactory.getLog(Charset.class);
	private static String dataBaseCharset="GBK";
	private static String nomalCharset = "GBK";
	

	public static void setDBCharset(String charset){
		dataBaseCharset=charset;
	}
	


	public  static String toDBCharset(String str){
		String changeStr = null;
		if ( str==null ) {
			changeStr="";
		} else {
			try {
	 		    changeStr = new String(str.getBytes(nomalCharset),dataBaseCharset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error("转换成数据库字符集时出现编码转换异常",e);
				changeStr=str;
			}
		}
		return changeStr;
	}

	public  static String fromDBCharset(String str){
		String changeStr = null;
		if ( str==null ) {
			changeStr="";
		} else {
			try {
	 		    changeStr = new String(str.getBytes(dataBaseCharset),nomalCharset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error("转换成GBK字符集时出现编码转换异常",e);
				changeStr=str;
			}
		}
		return changeStr;
	}

}
