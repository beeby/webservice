package com.transfar.messageserver.chinaunicom.tester;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
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
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomNotification;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomReport;
import com.transfar.messageserver.chinaunicom.service.AsisinfoServiceClient;
import com.transfar.messageserver.utils.MessageServerException;

public class SMServiceTestClient {
	private String targetEPR = "http://localhost:8080/ChinaUnicomMessageServer/services/SMService";
	private String nameSpace = "http://smsmonitor.asiainfo.com";
	private String nameSpaceShort = "ns";
	private String spId = "yutkyx";
	private String spPassword = "a2jm08";
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

	public String getSpPassword() {
		return spPassword;
	}

	public void setSpPassword(String spPassword) {
		this.spPassword = spPassword;
	}

	private SMServiceTestClient() {
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

	private OMElement transport(OMElement element) {
		logger.debug("开始进行数据传输：" + element);
		OMElement result = null;
		try {

			SOAPEnvelope envelope = fac.getDefaultEnvelope();

			OMElement headElement = fac.createOMElement("SpInfo", omNs);
			headElement.addAttribute("SpID", spId, omNs);
			headElement.addAttribute("SpPassword", spPassword, omNs);
			Options options = new Options();
			options.setTo(new EndpointReference(targetEPR));
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setProperty(HTTPConstants.CHUNKED, false);
			options.setAction(targetEPR);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			OperationClient oc = sender.createClient(ServiceClient.ANON_OUT_IN_OP);
			MessageContext context = new MessageContext();
			context.setEnvelope(envelope);
			sender.addHeader(headElement);
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

	public AsisInfoSendResponse messageTransmit(AsisInfoSend msg) throws MessageServerException {
		OMElement element=transport(msg.getOMElement(omNs, fac));
		if ( element==null) {
			throw new MessageServerException(MessageServerException.INTERACTION_RESPONSE_ERROR);
		}
		return new AsisInfoSendResponse(element);
	
	}
	
	
	public static void main(String[] args) {
		int time = -3;
		SMServiceTestClient client = new SMServiceTestClient();
		Calendar can = Calendar.getInstance();
		can.add(Calendar.MINUTE, time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date operatingTimestamp = can.getTime();
		System.out.print(operatingTimestamp);
		/*
		ChinaUnicomReport parm = new ChinaUnicomReport();
		parm.setMessageID("00000134785489302204");
		parm.setMobileNumber("15576637224");
		parm.setStatusCode("0");
		*/
		ChinaUnicomNotification parm=new ChinaUnicomNotification();
		parm.setChannelID("10657213213");
		parm.setMobileNumber("13607447799");
		parm.setContent("测量一下");

		parm.setTimeStamp(df.format(operatingTimestamp));
		OMElement e = parm.getOMElement(client.getOMNameSpace(),
				client.getOMFactory());
		OMElement result = client.transport(e);
		
		return;
	}
}
