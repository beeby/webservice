package com.transfar.messageserver.chinaunicom.service;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomNotification;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomReport;



public class ChinaUnicomWebService {
	private String nameSpace = "http://smsmonitor.asiainfo.com";
	private String nameSpaceShort = "ns";
	private Log logger = LogFactory.getLog(getClass());
	private SOAPFactory fac;
	private OMNamespace omNs;
	private String serviceCode="";
	private ChinaUnicomMessageService service;
	private ChinaUnicomWebService() {
		init();
	}
	
	public void init() {
		fac = OMAbstractFactory.getSOAP11Factory();
		omNs = fac.createOMNamespace(nameSpace, nameSpaceShort);
	}
	
	public ChinaUnicomMessageService getService() {
		return service;
	}

	public void setService(ChinaUnicomMessageService service) {
		this.service = service;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public OMElement notification(OMElement parameter) {
		/*
		MessageContext msgContext = MessageContext.getCurrentMessageContext();
		OMElement header=msgContext.getEnvelope().getHeader().getFirstElement();
		*/
		OMElement response = fac.createOMElement("notificationResponse", omNs);
		try {
			ChinaUnicomNotification deliverMsg=new ChinaUnicomNotification(parameter);
			if ( deliverMsg.getChannelID() != null && deliverMsg.getChannelID().startsWith(serviceCode)) {
				deliverMsg.setExtendNumber(deliverMsg.getChannelID().substring(serviceCode.length()));
			}
			service.addDeliverMsg(deliverMsg);
			logger.debug("接收联通下发信息：SRC="+deliverMsg.getMobileNumber()+" DST="+deliverMsg.getChannelID()+" CONTENT="+deliverMsg.getContent()+" TIMESTAMP="+deliverMsg.getTimeStamp());
		} catch (Exception e) {
			logger.error("接收联通下发信息时出现异常："+e.getMessage(),e);
		}
		return response;
	}

	public OMElement report(OMElement parameter) {
		/*
		 MessageContext msgContext = MessageContext.getCurrentMessageContext();
		 OMElement header=msgContext.getEnvelope().getHeader().getFirstElement();
		*/
		OMElement response = fac.createOMElement("reportResponse", omNs);
		try {
			ChinaUnicomReport report=new ChinaUnicomReport(parameter);
			service.updateMessageReport(report);
			logger.debug("接收联通报告信息：DST="+report.getMobileNumber()+" MSG_ID="+report.getMessageID()+" STATUS="+report.getStatusCode()+" TIMESTAMP="+report.getTimeStamp());

		} catch (Exception e) {
			logger.error("接收联通报告信息时出现异常："+e.getMessage(),e);
		}
		return response;
	}

	private OMElement getResponse(OMElement child) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		String methodName = trace[2].getMethodName();
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace(nameSpace, nameSpaceShort);
		OMElement response = fac.createOMElement(methodName + "Response", omNs);
		if (child != null)
			response.addChild(child);
		return response;
	}

	private OMElement getResponse() {
		return getResponse(null);
	}
}
