/*
 * @(#)MonitorDao.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.dao;

import cn.sh.ideal.action.vo.MonitorBean;

public interface MonitorDao {
	public int updateExamforId(String id);
	public int addMonitorExam(MonitorBean mb);
}

