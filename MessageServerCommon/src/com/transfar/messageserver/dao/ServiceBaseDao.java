package com.transfar.messageserver.dao;


import java.util.List;
import java.util.Random;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.transfar.messageserver.utils.MessageServerException;


;

public  class ServiceBaseDao  extends JdbcDaoSupport {
	
	protected final int sqlRetriesLimit=3;
	protected final int sqlRetryInterval=1000;
	private Log logger=LogFactory.getLog(getClass());
	/**
	 * 根据序列名称取关键字
	 * @param seqName  序列的名称
	 * @return
	 */
	public Long getSeq(String seqName){ 
		String querySql = "SELECT "+ seqName +".NEXTVAL as seq FROM DUAL";
		return getJdbcTemplate().queryForLong(querySql);

	}

	/**
	 *   执行查询语句，并捕获资源忙异常，并重新尝试三次
	 * @throws MessageServerException 
	 * @throws Exception 
	 *   
	 */
	public Object tryQuery(String sqlName, Object[] args, RowMapper rowMapper) throws MessageServerException  {
		return tryQuery(sqlName,args,rowMapper,1);
	}
	public Object tryQuery(String sqlName, Object[] args, RowMapper rowMapper, int limit) throws MessageServerException  {
		Object result=null;
		int tryCount=0;
		Exception sqlErr=null;
		Random rand=new Random();
		while ( tryCount < sqlRetriesLimit && result == null ) {
			try {
				result=getJdbcTemplate().query(sqlName, args, new RowMapperResultSetExtractor(rowMapper, limit));
			} catch (Exception e) {
				if ( e.getLocalizedMessage().contains("ORA-00054")) {
					sqlErr=e;
					tryCount++;
					try {
						Thread.sleep(rand.nextInt(sqlRetryInterval));	
					} catch (InterruptedException ie ) {
						
					}
				} else {
					MessageServerException.handleException(e);
				}
			}
		}
		if ( result == null ) {
			throw new MessageServerException(MessageServerException.DATABASE_RESOURCE_BUSY_LOCK);
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 查询,返回一个对象
	 * 
	 * @param sql
	 *            SQL语句在sqlxml配置文件中得名称
	 * @param args =
	 *            new Object[] { roleCode,new Long(100)};
	 * @param rowMapper =
	 *            new RowMapper(){
	 * 
	 * public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	 * String temp = rs.getString("city_name"); return temp; }
	 *  }
	 * @return
	 * @throws MessageServerException 
	 * @throws Exception 
	 * @throws DAOException
	 * @throws DataAccessException
	 */
	public Object queryForObject(String sqlName, Object[] args, RowMapper rowMapper) throws MessageServerException  {
		List results;

		results = (List) tryQuery(sqlName, args, rowMapper);
		int size = (results != null ? results.size() : 0);
		if (size != 0) {
			return results.iterator().next();
		}

		return null;
	}
	
	
	public Object queryForObject(String sqlName, Object[] args, Class requiredType) throws MessageServerException  {
		RowMapper singleColumnRowMapper = new SingleColumnRowMapper(requiredType);
		return queryForObject(sqlName,args,singleColumnRowMapper);
	}
	

	public Object queryForObject(String sqlName, Object[] args, RowMapper rowMapper, Class requiredType) throws MessageServerException {
		List results;

		results = (List) tryQuery(sqlName, args, rowMapper);
		int size = (results != null ? results.size() : 0);
		if (size != 0) {
			return results.iterator().next();
		}

		return null;
	}

	
	
	

	/**
	 * 查询,返回list列表
	 * 
	 * @param sqlName
	 *            SQL语句在sqlxml配置文件中得名称
	 * @param args
	 * @param rowMapper
	 * @return
	 * @throws MessageServerException 
	 * @throws Exception 
	 * @throws DAOException
	 */
	public List queryForList(String sqlName, Object[] args, RowMapper rowMapper) throws MessageServerException  {
		
		List result=null;
		int tryCount=0;
		Exception sqlErr=null;
		Random rand=new Random();
		while ( tryCount < sqlRetriesLimit && result == null ) {
			try {
				result = (List) getJdbcTemplate().query(sqlName, args, new RowMapperResultSetExtractor(rowMapper));
			} catch (Exception e) {
				if ( e.getMessage().contains("ORA-00054") || e.getLocalizedMessage().contains("ORA-00054")) {
					sqlErr=e;
					tryCount++;
					try {
						Thread.sleep(rand.nextInt(sqlRetryInterval));	
					} catch (InterruptedException ie ) {
						
					}
				} else {
					MessageServerException.handleException(e);
				}
			}
		}
		if ( result == null ) {
			throw new MessageServerException(MessageServerException.DATABASE_RESOURCE_BUSY_LOCK);
		}
		return result;

	}
	
	
	/**
	 * 查询,返回list列表
	 * 
	 * @param sqlName
	 *            SQL语句在sqlxml配置文件中得名称
	 * @param args
	 * @param rowMapper
	 * @return
	 * @throws MessageServerException 
	 * @throws Exception 
	 * @throws DAOException
	 */
	
	public List queryForList(String sqlName, PreparedStatementSetter pss, RowMapper rowMapper) throws MessageServerException  {
		
		List result=null;
		int tryCount=0;
		Exception sqlErr=null;
		Random rand=new Random();
		while ( tryCount < sqlRetriesLimit && result == null ) {
			try {
				result=(List) getJdbcTemplate().query(sqlName, pss, new RowMapperResultSetExtractor(rowMapper));
			} catch (Exception e) {
				if ( e.getLocalizedMessage().contains("ORA-00054")) {
					sqlErr=e;
					tryCount++;
					try {
						Thread.sleep(rand.nextInt(sqlRetryInterval));	
					} catch (InterruptedException ie ) {
						
					}
				} else {
					MessageServerException.handleException(e);
				}
			}
		}
		if ( result == null ) {
			throw new MessageServerException(MessageServerException.DATABASE_RESOURCE_BUSY_LOCK);
		}
		return result;
		
	}
	

	/**
	 * 查询,返回list列表.不带入参
	 * 
	 * @param sqlName
	 *            SQL语句在sqlxml配置文件中得名称
	 * @param rowMapper
	 * @return
	 * @throws MessageServerException 
	 * @throws Exception 
	 * @throws DAOException
	 */
	public List queryForList(String sqlName, RowMapper rowMapper) throws MessageServerException  {
		
		List result=null;
		int tryCount=0;
		Exception sqlErr=null;
		Random rand=new Random();
		while ( tryCount < sqlRetriesLimit && result == null ) {
			try {
				result=(List)getJdbcTemplate().query(sqlName, new RowMapperResultSetExtractor(rowMapper));;
			} catch (Exception e) {
				if ( e.getLocalizedMessage().contains("ORA-00054")) {
					sqlErr=e;
					tryCount++;
					try {
						Thread.sleep(rand.nextInt(sqlRetryInterval));	
					} catch (InterruptedException ie ) {
						
					}
				} else {
					MessageServerException.handleException(e);
				}
			}
		}
		if ( result == null ) {
			throw new MessageServerException(MessageServerException.DATABASE_RESOURCE_BUSY_LOCK);
		}
		return result;
		
	}
	
	

	
	
	/**
	 * 直接执行查询SQL语句
	 * @param SQL 合法得SQL语句 rowMapper
	 * @param args 参数 为空不带条件
	 * @return
	 */
	public List executeSQLSelect(String SQL, Object[] args, RowMapper rowMapper) {
		if (args == null){
			return (List) getJdbcTemplate().query(SQL, new RowMapperResultSetExtractor(rowMapper));
		}
		return (List) getJdbcTemplate().query(SQL, args, new RowMapperResultSetExtractor(rowMapper));
	}
	
	
	/**
	 * 直接执行SQL语句
	 * @param SQL 合法得SQL语句
	 * @param args 参数
	 * @return
	 */
	public int executeSQLUpdate(String SQL, Object[] args) {
		int rows;
		rows = getJdbcTemplate().update(SQL, args);
		return rows;
	}

	

}
