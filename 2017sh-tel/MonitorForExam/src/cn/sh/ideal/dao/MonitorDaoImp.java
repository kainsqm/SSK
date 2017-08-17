/*
 * @(#)MonitorDaoImp.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.sh.ideal.action.vo.MonitorBean;


public class MonitorDaoImp implements MonitorDao{
	private DataSource dataSource;
	private static Logger log = Logger.getLogger(MonitorDaoImp.class);
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int updateExamforId(String id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int result=0;
		try {
			String sql="update t_exam_exampaper_examine ee set ee.isnormal=0  where ee.pk_auto_id='"+id+"'";
			result= jdbcTemplate.update(sql);
		} catch (Exception e) {
			log.error("更新考生考试状态出现异常:"+e.getMessage(),e);
		}
		return result;
		
	}
	
	public int addMonitorExam(MonitorBean mb){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int result=0;
		try {
		String sql="insert into t_monitor_exam(FK_E_EP_EXAMINEE_ID,FK_USER_ID,ANSWERSTR,IPADDR,STATE,RAMAININGTIME,INSERT_TIME,FK_EXAM_ID,ZUHU_ID) " +
				"values("+mb.getExampaper_exam_user_id()+","+mb.getUserId()+",'"+mb.getAnswerStr()+"','"
				+mb.getIpAddr()+"','"+mb.getState()+"',"+mb.getRemainingTime()+", str_to_date('"+sdf.format(new Date())
				+"', '%Y-%m-%d %H:%i:%s'),"+mb.getExamId()+",1)";
		result=  jdbcTemplate.update(sql);
		} catch (Exception e) {
			log.error("新增考生考试状态出现异常:"+e.getMessage(),e);
		}
		
		return result;
	}
	
	public void setValueForExam(){
		
	}
	
	
}

