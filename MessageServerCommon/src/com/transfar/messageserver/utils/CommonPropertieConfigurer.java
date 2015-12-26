package com.transfar.messageserver.utils;

import java.io.File;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletContext;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

public class CommonPropertieConfigurer extends PropertyPlaceholderConfigurer
		implements ServletContextAware {

	private ServletContext servletContext;
	private String tomcatHome = "";
	private String appHome = "";
	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String rawKey = "2f3102c7018c29e5";

	private static boolean isLoadFile = false;

	private Resource runtimeParamFile;

	public void setLocation(Resource location) {
		super.setLocation(convertLocal(location));
	}

	public void setLocations(Resource[] locations) {
		super.setLocations(convertLocal(locations));
	}

	protected void convertProperties(Properties props) {
		Enumeration propertyNames = props.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			if (propertyName.equalsIgnoreCase("password")) {
				String propertyValue = props.getProperty(propertyName);
				String convertedValue = convertPropertyValue(propertyValue);
				if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
					props.setProperty(propertyName, convertedValue);
				}
			}
		}
	}

	protected String convertPropertyValue(String originalValue) {
		if (isLoadFile) {
			byte[] encryptedData = originalValue.getBytes();
			Base64 base64encode = new Base64();
			encryptedData = base64encode.decode(encryptedData);
			byte rawKeyData[] = null;
			rawKeyData = rawKey.getBytes();
			try {
				originalValue = decrypt(rawKeyData, encryptedData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return originalValue;
	}

	private Resource[] convertLocal(Resource[] locations) {

		if (runtimeParamFile.exists()) {
			locations[0] = runtimeParamFile;
			isLoadFile = true;
		}
		return locations;
	}

	private Resource convertLocal(Resource location) {
	
		if (runtimeParamFile.exists()) {
			location = runtimeParamFile;
			isLoadFile = true;
		}

		return location;
	}

	public Resource getruntimeParamFile() {
		return runtimeParamFile;
	}

	public void setruntimeParamFile(Resource paramFile) {
		this.runtimeParamFile = paramFile;
	}

	/**
	 * ���ܷ���
	 * 
	 * @param rawKeyData
	 * @param encryptedData
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static String decrypt(byte rawKeyData[], byte[] encryptedData)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException {
		// DES�㷨Ҫ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance("DES");
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		// ��ʽִ�н��ܲ���
		byte decryptedData[] = cipher.doFinal(encryptedData);
		// System.out.println("���ܺ�===>" + new String(decryptedData));
		return new String(decryptedData);
	}

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
		this.appHome = servletContext.getContextPath();
		this.tomcatHome = StringUtils.delete(this.appHome, "/webapps/"
				+ servletContext.getServletContextName());

	}

}
