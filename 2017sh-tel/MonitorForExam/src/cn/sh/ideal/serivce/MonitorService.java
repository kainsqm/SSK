/*
 * @(#)MonitorService.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.serivce;

import cn.sh.ideal.action.vo.MonitorBean;

public interface MonitorService {
	public boolean updateExamforId(String id);
	public boolean addMonitorExam(MonitorBean mb);
}

