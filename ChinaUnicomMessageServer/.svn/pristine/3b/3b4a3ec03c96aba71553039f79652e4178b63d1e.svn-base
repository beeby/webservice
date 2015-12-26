package com.transfar.messageserver.chinaunicom.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.transfar.messageserver.chinaunicom.dao.ChinaUnicomMessageDao;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomNotification;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomReport;
import com.transfar.messageserver.chinaunicom.vo.UTSendMsg;
import com.transfar.messageserver.utils.MessageServerException;

public class ChinaUnicomMessageService {
	public ChinaUnicomMessageDao serviceDao;

	private Log logger = LogFactory.getLog(getClass());

	public ChinaUnicomMessageDao getServiceDao() {
		return serviceDao;
	}

	public void setServiceDao(ChinaUnicomMessageDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	public String getDataBaseCharset() {
		String charset = "GBK";
		try {
			String DBCharset = serviceDao.getDataBaseCharset();
			if (DBCharset.equalsIgnoreCase("US7ASCII")) {
				charset = "iso-8859-1";
			}
		} catch (MessageServerException e) {
			logger.error("获取数据库字符集出现异常,将字符集设置为缺省值GBK", e);
		}
		return charset;
	}

	public List<UTSendMsg> getMessages(int limit) throws MessageServerException {

		List<UTSendMsg> msgs = serviceDao.getMessages(limit);
		if (msgs == null || msgs.size() == 0) {
			return null;
		}
		for (UTSendMsg msg : msgs) {
			serviceDao.moveMessagetoRecord(msg);
		}
		return msgs;

	}

	public void updateMessageSendInfo(UTSendMsg msg)
			throws MessageServerException {
		serviceDao.updateRecordMsgSendInfo(msg);
	}

	public void updateMessageReport(ChinaUnicomReport msg)
			throws MessageServerException {
		if (msg != null) {

			serviceDao.updateRecordMsgReportInfo(msg);

		} else {
			throw new MessageServerException(MessageServerException.PARAM_NULL,
					"ChinaUnicomReport对象为空");
		}
	}

	public void addDeliverMsg(ChinaUnicomNotification msg)
			throws MessageServerException {
		if (msg != null) {
			serviceDao.insertDeliverMsg(msg);
		} else {
			throw new MessageServerException(MessageServerException.PARAM_NULL,
					"ChinaUnicomNotification对象为空");
		}
	}
}
