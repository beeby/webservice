package com.transfar.messageserver.chinaunicom.service;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.transfar.messageserver.utils.Charset;


public class ChinaUnicomMessageServerContext implements Servlet {
private static Log logger = LogFactory.getLog(ChinaUnicomMessageServerContext.class);
	
	public static ChinaUnicomMessageServerContext localInstance = null;
	ServletConfig servletConfig;
	
	private ApplicationContext context;

	private String [] configLocation= new String[]{
			"spring/resource.xml",
			"spring/kernal.xml",
			"spring/transfation.xml",				
			"spring/dao.xml",
			"spring/timer.xml",
			"spring/service_core.xml"};
	
	public static ChinaUnicomMessageServerContext getInstance(){
		
		return localInstance;
	}
	

	public  ChinaUnicomMessageService getChinaUnicomMessageService(){
		return (ChinaUnicomMessageService)getBean("ChinaUnicomMessageService");
	}
	
	public  ChinaUnicomMessageServer getChinaUnicomMessageServer(){
		return (ChinaUnicomMessageServer)getBean("ChinaUnicomMessageServer");
	}
	
	public Object getBean(String beanName) throws IllegalArgumentException {
		if (!context.containsBean(beanName)) {
			throw new IllegalArgumentException("在容器中没有找到:" + beanName);
		}
		return context.getBean(beanName);
	}
	
	public  JdbcTemplate getJdbcTemplate() {
		return (JdbcTemplate) getBean("jdbcTemplate");
	}
	
	@Override
	public void destroy() {
		getChinaUnicomMessageServer().serviceStop();

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return servletConfig;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return "China Unicom Message Gateway Client";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		this.localInstance=this;
		this.servletConfig=arg0;
		String log4jFile= servletConfig.getServletContext().getRealPath("/")+servletConfig.getInitParameter("log4j");   
 		 if (log4jFile != null)
			 DOMConfigurator.configure(log4jFile);
		try {
			logger.info("[中国联通短信服务接口正在初始化");

			String contextPath="file:"+servletConfig.getServletContext().getRealPath("/")+servletConfig.getInitParameter("spring");
			context = new FileSystemXmlApplicationContext(contextPath);
			JdbcTemplate jt = getJdbcTemplate();
			jt.queryForInt("select 1 from dual");
			logger.info("中国联通短信服务接口 数据库连接初始化完成！");
			String charset=getChinaUnicomMessageService().getDataBaseCharset();
			Charset.setDBCharset(charset);
			logger.info("中国联通短信服务接口 设置数据库字符集为"+charset);
			getChinaUnicomMessageServer().start();
			logger.info("中国联通短信服务接口 初始化成功"); 
		} catch (Exception e) {
			logger.error("中国联通短信服务接口 初始化失败" + e); 
			e.printStackTrace();
		}
		

	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	
	
}
