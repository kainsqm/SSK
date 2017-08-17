package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.CnetVerdict;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrderCdmaSum;

public interface CnetVerdictDao {

	List<CnetVerdict> getcNetVerdictList(CnetVerdict CnetVerdict);

	int getcNetVerdictListCount(CnetVerdict CnetVerdict);

	List<Modeltype> getServiceType();

	List<Modeltype> getResultType();

	List<Modeltype> getComplaintSource();

	int checkCnetTaskName(String taskName);

	

	int updCnetOrderTask(CnetVerdict cnetVerdict);

	int insertCnetordertaskUser(CnetVerdict cnetVerdict);

	int insertToTaskCnetorderuserComplete(CnetVerdict cnetVerdict);

	int addCnetorderTask(CnetVerdict cnetVerdict);

	/********
	 * 删除C网工单数据 将任务状态该为否
	 * @author niewenqiang
	 * @date 2017-5-22
	 * *******/
	void releaseCnetver(Map<String, String> mapdata);
	
	/*****
	 * 删除任务派发时，C网电话小结与录音中间表的数据
	 * @author niewenqiang
	 * @date 2017-5-22
	 * *****/
	void releaseRecordByCnetver(Map<String, String> mapdata);
	
	
	/******
	 * 删除C网电话小结任务派发
	 * @author niewenqiang
	 * @date 2017-5-22
	 * *****/
	void deleteCnetverdict(@Param("taskId") int taskId);
	
	/******
	 * 删除C网电话小结任务与质检员关联表中的数据
	 * @author niewenqiang
	 * @date 2017-5-22
	 * *****/
	int delTaskQcuser(Map<String, String> mapdata);

	/******
	 * 删除C网电话小结任务与质检员完成情况关联表中的数据
	 * @author niewenqiang
	 * @date 2017-5-22
	 * *****/
	int delTaskQcuserComplete(Map<String, String> mapdata);
	
	
	/***
	 * 质检员查询任务
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **/
	public List<CdmaTaskComplete> queryListForQc (Map<String, String> mapdata);
	
	/***
	 * 质检员查询任务记录总数
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **/
	public int queryListForQcCount (Map<String, String> mapdata);
	
	
	
	/******* 
	 * 查询该员工该条任务领取完成情况
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **********/
	public CnetVerdict queryTaskGetCondition(Map<String, String> mapdata);
	
	/*****
	 * 根据userID查询对应的小工号信息
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ****/
	public List<UserInfo> quertSmainWorkIdByUserid(@Param("userId") String userId);
	
	/******
	 * 获取查询结果中有几个话务员
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ********/
	public int getResultHWCount(Map<String, String> mapdata);
	
	
	 /**
     * 获取C网电话小结任务工单
     * @author niewenqiang
     * @date 2017-5-23
     * **/	
 	public List<WorkOrderCdmaSum> getLingQuCnetVerdict(Map<String, String> mapdata);
 	
 	/**
 	 * 查询结果中有多少条C网电话小结
 	 * @author niewenqiang
 	 * @date 2017-5-23
 	 * **/
 	public int getResultCdmaSumCount(Map<String, String> mapdata);
 	
 	
	/***
 	 * 每人抽取一条电话小结
 	 * @author niewenqiang
 	 * @date 2017-5-23
 	 * ***/
 	public List<WorkOrderCdmaSum> getCdmaSumEveryOne(Map<String, String> mapdata);
 	
 	
 	/***
 	 * 随机抽取剩下的电话小结
 	 * ***/
 	public List<WorkOrderCdmaSum> randomGetCdmaSum(Map<String, String> mapdata);
 	
 	
 	
 	/**
	 * 查询c网电话小结领取页面
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public List<WorkOrderCdmaSum> searchCdmaSumToGet(Map<String, String> mapdata);
	
	/**
	 * 查询c网电话小结领取页面数量
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public int searchCdmaSumToGetCount(Map<String, String> mapdata);
	

	/*****
	 * 查询该质检员当天任务已领取数
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *****/
	public String cdmaGetCount(Map<String, String> mapdata);
	
	
	/*******
	 * 获取该受理员当天被质检次数
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ******/
	public Integer getCdmaSumVerification(Map<String, String> mapdata);
	
	
	/*******
	 * 根据小工号查询大工号信息
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *******/
	public String getBigWorkIdBySmail(@Param("smailWorkId") String smailWorkId);
	
	
	/********
	 * 质检员领取时，判断该电话小结是否已被其他质检员领取
	 * @author  niewenqiang
	 * @date 2017-5-23
	 * *******/
	public int earlierByGet(@Param("pkAutoId") int pkAutoId);
	
	
	
	/********
	 * 质检员领取并评分时，判断该电话小结是否已被其他质检员评分
	 * @author  niewenqiang
	 * @date 2017-5-23
	 * *******/
	public int earlierByQc(@Param("pkAutoId") int pkAutoId);
	
	
	/***********
	 * 新增任务录音关联表中的记录
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *************/
	public void insertTaskRecordByCdma(Map<String, String> mapdata);
	
	
	/******
	 * 更新C网工单电话小结任务状态
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ******/
	public void updateCdmaIsTask(Map<String, String> mapdata);
	
	
	/******
	 * 获取该质检员该任务已领电话小结数量
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *****/
	public int getCdmaComplete(Map<String, String> mapdata);
	
	
	
	/*******
	 * 更新该质检员已领工单数量表
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ******/
	public void updateTaskRecordComplete(Map<String, String> mapdata);
	
	
	
	/********
	 * 修改质检员任务质检中间表数据
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ******/
	public void updateTaskQcUser(Map<String, String> mapdata);
	
	
	/*******
	 * 更改任务状态
	 * @author neiwenqiang
	 * @daet 2017-5-23
	 * *********/
	public void updateTaskStatus(Map<String, String> mapdata);
	
	
	/*******
	 * 根据C网电话小结ID获取该电话小结小工号
	 * @author niewenqiang
	 * @date 2017-5-23
	 * ******/
	public String getWorkIDByCdmaPkId(@Param("cdmaPkId") int cdmaPkId);
	
	/******
	 * 执行工作计划时，查询该质检员已领工单列表
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public List<WorkOrderCdmaSum> getCdmaedTaskByUser(Map<String, String> mapdata);
	
	    
	/******
	 * 执行工作计划时，查询该质检员已领工单列表数量
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public int getCdmaedTaskByUserCount(Map<String, String> mapdata);
	
	/******
	 * 根据TASKID查询C网电话小结任务信息
	 * @author niewenqiang
	 * @date 2017-5-25
	 * *********/
	public CnetVerdict getCnetorderTaskbyid(@Param("taskId") int taskId);
	
	
	/******
	 * 质检员完成情况查询
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public List<CdmaTaskComplete> queryTaskCompleteInforc(Map<String, String> mapdata);
	
	
	/******
	 * 质检员完成情况查询 数量
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public int queryTaskCompleteInforcCount(Map<String, String> mapdata);
	
	
	
	/*********
	 * 督导查询该任务完成情况列表
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ************/
	public List<CdmaTaskComplete> queryTaskCompleteInfo(Map<String, String> mapdata);
	
	
	/*********
	 * 督导查询该任务完成情况 列表记录数量
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ************/
	public int queryTaskCompleteInfoCount(Map<String, String> mapdata);
	
	
	/*******
	 * 释放电话小结时，修改领取数
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ********/
	public void updateTaskCdmaComplete1(Map<String, String> mapdata);
	
	
	/*******
	 * 释放电话小结时，修改总的领取数
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ********/
	public void updateTaskCdmaQcUserInfo1(Map<String, String> mapdata);
	
	
	
	/**********
	 * 根据c网电话小结ID查询在任务表该电话小结任务信息
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ***********/
	public CdmaTaskComplete getTaskInfoByCdmaAutoId(@Param("cdmaPkId") int cdmaPkId);
	
	
	/********
	 * 修改C网电话小结对应的小结ID为已质检
	 * @author niewenqiang
	 * @date 2017-5-26
	 * *********/
	public void updateTaskRecordByCdma(@Param("cdmaPkId") int cdmaPkId);
	
	
	/*******
	 * 修改TASK任务状态
	 * @author niewenqiang
	 * @date 2017-5-26
	 * *******/
	public void updateCwxjTask(Map<String, String> mapdata);
	
	
	/*******
	 * 获取当天的完成数
	 * @author niewenqiang
	 * @date 2017-5-26
	 * *******/
	public String getCompleteByDay(Map<String, String> mapdata);
	
	
	/******
	 * 根据大工号查询userId
	 * @author niewenqiang
	 * @date 2017-6-5
	 * ******/
	public String getuserIdByWorkId(@Param("workId") String workId);
}
