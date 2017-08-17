/*
 * @(#)MonitorBean.java 1.0 2011-3-10
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.action.vo;

public class MonitorBean {
	private String exampaper_exam_user_id;     //考试关联ID
	private String userId;          //座席工号
	private String answerStr;       //考题答案字符串
	private String ipAddr;          //当前考试的客户端IP地址
	private String state;           //状态   0考试中， 1，正常提交 2，提交异常 3,超时无响应
	private String remainingTime;   //剩余时间
	private String examId;
	private String examName;
	private int accumulator;        //累加器
	
	public String getAnswerStr() {
		return answerStr;
	}
	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
	public String getExampaper_exam_user_id() {
		return exampaper_exam_user_id;
	}
	public void setExampaper_exam_user_id(String exampaper_exam_user_id) {
		this.exampaper_exam_user_id = exampaper_exam_user_id;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getAccumulator() {
		return accumulator;
	}
	public void setAccumulator(int accumulator) {
		this.accumulator = accumulator;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	
	

}

