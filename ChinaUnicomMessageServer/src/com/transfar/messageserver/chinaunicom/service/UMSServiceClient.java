package com.transfar.messageserver.chinaunicom.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.chinaunicom.scheme.AsisInfoSend;
import com.transfar.messageserver.chinaunicom.scheme.AsisInfoSendResponse;
import com.transfar.messageserver.chinaunicom.scheme.UMSRequest;
import com.transfar.messageserver.chinaunicom.scheme.UmsSmsRequest;
import com.transfar.messageserver.chinaunicom.scheme.UmsSmsResponse;
import com.transfar.messageserver.utils.MessageServerException;

public class UMSServiceClient {
	private String targetEPR = "https://smsapi.ums86.com:9600/sms_hb/services/Sms";
	private String nameSpace = "http://ws.flaginfo.com.cn";
	private String nameSpaceShort = "";
	private String spId = "222839";
	private String spUsername="hn_hndx";
	private String spPassword = "hndx2016";
	private Log logger = LogFactory.getLog(getClass());
	private SOAPFactory fac;
	private OMNamespace omNs;
	
	public String getTargetEPR() {
		return targetEPR;
	}

	public void setTargetEPR(String targetEPR) {
		this.targetEPR = targetEPR;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getNameSpaceShort() {
		return nameSpaceShort;
	}

	public void setNameSpaceShort(String nameSpaceShort) {
		this.nameSpaceShort = nameSpaceShort;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
	
	public String getSpUsername() {
		return spUsername;
	}

	public void setSpUsername(String spUsername) {
		this.spUsername = spUsername;
	}
	
	public String getSpPassword() {
		return spPassword;
	}

	public void setSpPassword(String spPassword) {
		this.spPassword = spPassword;
	}

	private UMSServiceClient() {
		init();
	}
	
	public void init() {
		fac = OMAbstractFactory.getSOAP11Factory();
		omNs = fac.createOMNamespace(nameSpace, nameSpaceShort);
	}
	
	public OMNamespace getOMNameSpace() {
		return omNs;
	}

	public OMFactory getOMFactory() {
		return fac;
	}

	private OMElement transport(UMSRequest request) {
		request.setSpId(getSpId());
		request.setSpUsername(getSpUsername());
		request.setSpPassword(getSpPassword());
		OMElement element=request.getOMElement(omNs, fac);
		logger.debug("开始进行数据传输：" + element);
		System.out.println(element);
		OMElement result = null;
		try {
			

			Options options = new Options();
			options.setTo(new EndpointReference(targetEPR));
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setProperty(HTTPConstants.CHUNKED, false);
			options.setAction(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			result = sender.sendReceive(element);
			result.build();
			result.detach();
			sender.cleanupTransport();
			sender.cleanup();
			logger.debug("数据传输完成" + result);
		} catch (Exception e) { // (XMLStreamException e) {
			logger.error("数据传输发生错误");

			e.printStackTrace();
		}
		return result;
	}

	public UmsSmsResponse smsRequest (UmsSmsRequest request) throws MessageServerException {
		request.setIgnoreErrorNumber(true);
		OMElement element=transport(request);
		if ( element==null) {
			throw new MessageServerException(MessageServerException.INTERACTION_RESPONSE_ERROR);
		}
		return new UmsSmsResponse(element);
	
	}
	
	
	public static void main(String[] args) {
		int time = -3;
		UMSServiceClient client = new UMSServiceClient();
		Calendar can = Calendar.getInstance();
		can.add(Calendar.MINUTE, time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date operatingTimestamp = can.getTime();
		System.out.print(operatingTimestamp);
		UmsSmsRequest parm = new UmsSmsRequest();
		parm.setMsgId("10201208271538000001");
		parm.setMobileNumber("15576637224");

		parm.setContent("test");
		parm.setScheduledTime(Calendar.getInstance().getTime());
		try {
			UmsSmsResponse respon=client.smsRequest(parm);
		} catch (MessageServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}


}
