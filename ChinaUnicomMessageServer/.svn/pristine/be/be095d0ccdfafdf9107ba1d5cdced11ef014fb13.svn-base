package com.transfar.messageserver.chinaunicom.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.transfar.messageserver.utils.Charset;
import com.transfar.messageserver.chinaunicom.vo.UTSendMsg;

public class UTSendMsgMapper implements RowMapper{

	@Override
	public UTSendMsg mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub

		UTSendMsg vo = new UTSendMsg();
		vo.setIndexId(rs.getString("indexid"));
		vo.setMobileNumber(rs.getString("dest_terminal"));
		vo.setChannelID(rs.getString("src_id"));
		vo.setContent(Charset.fromDBCharset(rs.getString("msg_content")));
		vo.setScheduledTime(rs.getString("at_time"));
		vo.setPriority(rs.getInt("msg_level"));
		vo.setStatus(rs.getInt("send_flag"));
		return vo;
	}
	

}
