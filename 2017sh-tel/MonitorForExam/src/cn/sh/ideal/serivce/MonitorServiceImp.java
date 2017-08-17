/*
 * @(#)MonitorServiceImp.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.serivce;

import cn.sh.ideal.action.vo.MonitorBean;
import cn.sh.ideal.dao.MonitorDao;

public class MonitorServiceImp implements MonitorService{
	private MonitorDao monitorDao;

	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}
	
	public boolean updateExamforId(String id){
		int i=monitorDao.updateExamforId(id);
		if(i>0)return true;
		else return false;
	}
	
	public boolean addMonitorExam(MonitorBean mb){
		int i=monitorDao.addMonitorExam(mb);
		if(i>0)return true;
		else return false;
	}
	
}

