package com.transfar.messageserver.chinaunicom.dao;

import java.util.List;

import com.transfar.messageserver.chinaunicom.mapper.UTSendMsgMapper;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomNotification;
import com.transfar.messageserver.chinaunicom.scheme.ChinaUnicomReport;
import com.transfar.messageserver.chinaunicom.vo.UTSendMsg;
import com.transfar.messageserver.dao.ServiceBaseDao;
import com.transfar.messageserver.utils.MessageServerException;



public class ChinaUnicomMessageDao extends ServiceBaseDao{
	
	/**
	 * 查找出数据库编码信息
	 */
	public String getDataBaseCharset() throws MessageServerException{
		String sql ="select value from nls_database_parameters where parameter=?";
		Object[] params = new Object[] {"NLS_CHARACTERSET"};
		String name  = (String)this.queryForObject(sql,params, String.class);
		
		return name;
	}
	
	/**
	 * 获取待发送的信息列表
	 * @return
	 * @throws MessageServerException
	 */
	public List<UTSendMsg>  getMessages(int limit) throws MessageServerException{
		String sql ="select indexid,dest_terminal,src_id,msg_content,at_time,msg_level,send_flag from UT_SENDMSG where rownum<= ?  and send_flag=0 for update";
		Object [] param=new Object[] { limit };
		List<UTSendMsg> msgList = (List<UTSendMsg>) queryForList(sql,param,new UTSendMsgMapper());
		return msgList;
	}
	
	public void moveMessagetoRecord(UTSendMsg msg) throws MessageServerException {
		String sql ="call record_ut_send_msg('"+msg.getIndexId()+"','','"+msg.getStatus()+"')";
		this.executeSQLUpdate(sql, null);
	}
	
	public void updateRecordMsgSendInfo(UTSendMsg msg) throws MessageServerException {
		String sql ="UPDATE UT_SENDRECORD SET msg_id= ? ,send_flag= ? ,ret_str = ? WHERE indexid= ? ";
		Object [] param=new Object [] {
				msg.getMessageID(),
				msg.getStatus(),
				msg.getStatusMsg(),
				msg.getIndexId()
		};
		this.executeSQLUpdate(sql, param);
	}

	public void updateRecordMsgReportInfo(ChinaUnicomReport msg) {
		String sql = "UPDATE ut_sendrecord SET reported=1  , report_status= ?, report_donetime= ? where msg_id=?";
		Object[] param = new Object[] { msg.getStatusCode(), msg.getTimeStamp(),
				msg.getMessageID() };
		this.executeSQLUpdate(sql, param);
	}
	
	public void insertDeliverMsg(ChinaUnicomNotification msg) {
		String insertStr = "INSERT INTO ut_deliverrecord ( dest_term_id,msg_content,recv_time,src_term_id,extend_number) VALUES ( ?,?,?,?,? )";

			Object[] param = new Object[] { msg.getChannelID(),
				msg.getContent(), msg.getTimeStamp(), msg.getMobileNumber(), msg.getExtendNumber() };
		this.executeSQLUpdate(insertStr, param);

	}
}
