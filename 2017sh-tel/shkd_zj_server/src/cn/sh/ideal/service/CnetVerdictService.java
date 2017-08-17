package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.CnetVerdict;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.WorkOrderCdmaSum;

public interface CnetVerdictService {
	/**
	 * 获取C网小结任务派发列表
	 * @param cnetVerdict
	 * @return
	 */
	List<CnetVerdict> getcNetVerdictList(CnetVerdict cnetVerdict);
	/**
	 * 获取C网小结任务派发列表总数
	 * @param cnetVerdict
	 * @return
	 */
	int getcNetVerdictListCount(CnetVerdict cnetVerdict);
	/**
	 * 获取业务类型
	 * @return
	 */
	List<Modeltype> getServiceType();
	/**
	 * 获取小结类型
	 * @return
	 */
	List<Modeltype> getResultType();
	/**
	 * 获取故障来源
	 * @return
	 */
	List<Modeltype> getComplaintSource();
	/**
	 * 检查c网新增任务名称是否重复
	 * @param taskName
	 * @return
	 */
	int checkCnetTaskName(String taskName);
	/**
	 * 新增c网小结任务派发
	 * @param cnetVerdict
	 * @return
	 */
	int addCnetorderTask(CnetVerdict cnetVerdict);
	/**
	 * 修改c网小结任务派发
	 * @param cnetVerdict
	 * @return
	 */
	int updCnetOrderTask(CnetVerdict cnetVerdict);

	/**
	 * 删除C网小结任务
	 * @param map
	 */
	int delCnetorderTask(Map<String, String> map);
	
	
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

	
	
	/***********
	 * 质检员查询可以领取的C网电话小结列表数据 查询随机10条电话小结
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **************/
	public String searchCdma(Map<String, String> mapdata);
	
	
	/**
	 * 查询c网电话小结领取页面
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public List<WorkOrderCdmaSum> searchCdmaSumToGet(Map<String, String> mapdata);
	
	
	 /** 查询c网电话小结领取页面数量
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public int searchCdmaSumToGetCount(Map<String, String> mapdata);
	
	
	/** 质检员c网电话小结一键领取
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public String updateOneKeyGetCdmaSum(Map<String, String> mapdata);
	
	
	
	
	/** 质检员c网电话小结领取
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public String updateGetCdmaSum(Map<String, String> mapdata);
	
	
	
	/*******
	 * 根据小工号查询大工号信息
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *******/
	public String getBigWorkIdBySmail(String smailWorkId);
	
	
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
	public CnetVerdict getCnetorderTaskbyid(int taskId);
	
	
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
	 * 督导以质检员为维度批量释放电话小结
	 * @author niewenqiang
	 * @date 2017-5-26
	 * **********/
	public String releaseCdmaByQcUser(String qcUserItems,String taskId);
	
	
	/*******
	 * 督导以日期为维度批量释放电话小结
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ********/
	public String releaseCdmaByQcDate(String cDates,String taskId,String qcUser,String taskUserId);


	/**********
	 * 根据c网电话小结ID查询在任务表该电话小结任务信息
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ***********/
	public CdmaTaskComplete getTaskInfoByCdmaAutoId(int cdmaPkId);
	
	
	/**********
	 * c网电话小结进行任务评分时，修改对应的状态
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ***********/
	public int updateTaskCdma(Map<String, String> map) ;

}
