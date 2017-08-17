/*
 * @(#)Test.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.sh.ideal.dao.MonitorDao;

public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext(new String [] {
				"D:\\professionalSoft\\myWorkspace\\MonitorForExam\\WebRoot\\WEB-INF\\spring.xml"
				
	});
		MonitorDao dao=(MonitorDao)context.getBean("monitorDao");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(sdf.format(new Date()));
		}
}

