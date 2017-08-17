package cn.sh.ideal.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;

public interface ITaskService{
	/**
	 * @author niewq
	 * 获取任务派发列表数据
	 * @param task  传入参数
	 * **/
	public List<Task> getTaskDistribut(Map<String, String> mapdata);
	
	/**
	 * @author niewq
	 * 新增任务信息
	 * @param task  传入参数
	 * @throws Exception 
	 * **/
	public int addTask() throws SQLException;
	
	/**
	 * @author niewq
	 *  查询质检员
	 * */
	public List<UserInfo> getQcUser();
	
	/**
	 * @author niewq
	 *  查询受理员
	 * */
	public List<UserInfo> getManager(Map<String, String> map);
	
	/**
	 *  获取任务派发列表数据行数
	 * **/
	public int getCountTaskDistribut(Map<String, String> mapdata);
	
	/**
	 *  获取受理员 总数
	 * **/
	public int getCountManager(Map<String, String> mapdata);
	
	
	/**
	 *  删除任务
	 * @throws Exception 
	 * **/
	public int deleteTask(Map<String, String> mapdata) throws SQLException;
	
	/**
	 * 根据TASKID查询数据
	 * **/
	public Task getOneTaskInfo(int taskId);
	

	/**
	 * @author niewq
	 * 修改任务信息
	 * @param task  传入参数
	 * @throws Exception 
	 * **/
	public int updateTask() throws SQLException;
	
	/**
	 * 查询任务完成情况
	 * **/
	public List<Task> queryTaskCompleteInfo(Map<String, String> mapdata);
	
	/**
	 *  获取任务完成情况行数
	 * **/
	public int getCountTaskCompleteInfo(Map<String, String> mapdata);
	
	/**
	 * 批量释放录音
	 * @throws Exception 
	 * **/
	public String releaseRecordByQcUser(Map<String, String> mapdata) throws SQLException;
	
	/**
	 * 
	 * 任务完成情况详情
	 * */
	public List<Task> queryTaskCompleteInforc(Map<String, String> mapdata);
	
	/**
	 *  获取任务完成情况详情总数
	 * */
	public int getCountTaskCompleteInforc(Map<String, String> mapdata);
	
	/**
	 * 质检班长批量释放录音(以天为维度)
	 * @throws Exception 
	 * **/ 
	public String releaseRecord(Map<String, String> mapdata) throws SQLException;
	
	/***
	 * 质检员查询任务
	 * **/
	public List<Task> queryListForQc (Map<String, String> mapdata);
	
	/***
	 * 质检员查询任务记录总数
	 * **/
	public int queryListForQcCount (Map<String, String> mapdata);
	
	/**
	 * 查询编号
	 * @throws Exception 
	 * **/
	public String searchReservedthrees (Map<String, String> mapdata) throws SQLException;
	
	/**
	 * 查询任务领取
	 * */
	public List<AgentCallLog> searchRecordToGet(Map<String, String> mapdata);
	
	/***
	 * 查询任务领取总数
	 * **/
	public int searchRecordToGetCount (Map<String, String> mapdata);
	
	
	/**
	 * 一键领取录音
	 * 
	 * **/
	public String updoneKeyGetRecord(Map<String, String> mapdata) throws SQLException;
	
	/**
	 * 领取录音
	 * **/
	
	public String updateRecord(Map<String, String> mapdata) throws SQLException;
	
	
	/**
	 * 查询任务录音
	 * **/
	public List<AgentCallLog> queryRecordList(Map<String, String> mapdata);
	
	/**
	 * 查询任务录音记录数
	 * **/
	public int queryRecordListCount(Map<String, String> mapdata);
	
	/**
	 * 任务录音评分时，修改任务相关表的状态
	 * **/
	
	public String updateTaskRecInfo(Map<String, String> map);
	
	/**
	 * 更新任务相关信息（T_QC_TASKQCUSER 如：状态，完成数...）
	 * @param request
	 * @param map
	 * @return
	 */
	public String updateTaskQcUserInfo(Map<String, String> map) ;
	
	/**
 	 * 任务录音评分时 ，查询该录音相关的信息
 	 * @author niewenqiang
 	 * **/
 	public Task getRecordTask(int recordId);
 	
 	
 	/**
	 *  判断任务名称是否存在
	 * **/
	public int checkTaskName(Map<String, String> mapdata);
	
	/**
	 * @author lk 2017/4/26
	 * @param task
	 * @return
	 * 查询112工单任务列表
	 */
	public List<Task> gettask112Order(Task task);
	
	public  int gettask112Ordercount(Task task);
	
	
	
	/**
	 * @author lk 2017/5/03
	 * @param task
	 * @return
	 * 新增112工单任务
	 */
	public int add112orderTask(Task task)throws SQLException;
	
	/**
	 * @author lk 2017/5/03
	 * @param taskId
	 * @return
	 * 删除112工单任务
	 */
	public int del112orderTask(Map<String, String> mapdata);
	
	/**
	 * @author lk 2017/5/03
	 * @param taskId
	 * @return
	 * 查询112工单任务详细
	 */
	public Task get112orderTaskbyid(Integer taskId);
	
	/**
	 * @author lk 2017/5/04
	 * @param taskName
	 * @return
	 * 判断112工单任务名称是否重复
	 */
	public int check112Ordername(String taskName);
	
	/**
	 * @author lk 2017/5/04
	 * @param task
	 * @return
	 * 修改112工单任务
	 */
	public int upd112OrderTask(Task task);
	
	/**
	 * @author lk 2017/5/10
	 * @param task
	 * @return
	 * 质检员查询任务执行列表
	 */
	public List<Task> getimptask112Order(Task task);
	
	public  int getimptask112Ordercount(Task task); 
	
	/**
	 * @author lk 获取已领任务工单id
	 * @param map
	 * @return
	 */
	Set<String> getyl112ordertaskidList(Map<String,String> map);
	
	/**
	 * @author lk 查询质检员112工单任务完成情况
	 * @param map
	 * @return
	 */
	List<Task> get112ordertaskwcqkList(Map<String,String> map);
	
	int get112ordertaskwcqkcount(Map<String,String> map);
	
	/**
	 * @author lk 查询所有质检员112工单任务完成情况
	 * @param map
	 * @return
	 */
	List<Task> get112ordertaskwcqkallList(Map<String,String> map);
	
	int get112ordertaskwcqkallcount(Map<String,String> map);
	
	/**
	 * @author lk 督导查询个人112工单
	 * @param map
	 * @return
	 */
	List<Task> get112ordertaskwcqkqcList(Map<String,String> map);
	
	int get112ordertaskwcqkqccount(Map<String,String> map);
	
	List<Task> getcwordertaskwcqkqcList(Map<String,String> map);
	
	int getcwordertaskwcqkqccount(Map<String,String> map);
	
	/**
	 * @author lk 根据工单id查询任务信息
	 * @param map
	 * @return
	 */
	List<Task> gettaskby112orderId(Map<String,String> map);
 	
	int updateTaskorder(Map<String,String> map);
	
	/**
	 * @author lk 根据用户id批量释放录音
	 * @param map
	 * @return
	 */
	String updrelease112OrderByUser(Map<String,String> map);
	
	/**
	 * @author lk 根据时间释放录音
	 * @param map
	 * @return
	 */
	String updrelease112OrderBycDate(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/23
	 * @param task
	 * @return
	 * 查询c网工单任务列表
	 */
	public List<Task> gettaskcwOrder(Task task);
	
	public int gettaskcwOrdercount(Task task);
	
	/**
	 * @author lk 2017/5/24
	 * @param task
	 * @return
	 * 新增c网工单任务
	 */
	public int addcworderTask(Task task);
	
	/**
	 * @author lk 2017/5/24
	 * @param task
	 * @return
	 */
	public int updcwOrderTask(Task task);
	
	/**
	 * @author lk 2017/5/04
	 * @param taskName
	 * @return
	 * 判断112工单任务名称是否重复
	 */
	public int checkcwOrdername(String taskName);
	
	public Task getcworderTaskbyid(Integer taskId);
	
	/**
	 * @author lk 2017/5/03
	 * @param taskId
	 * @return
	 * 删除c网工单任务
	 */
	public int delcworderTask(Map<String, String> mapdata);
	
	/**
	 * @author lk 2017/5/26
	 * @param task
	 * @return
	 * 质检员查询任务执行列表
	 */
	public List<Task> getimptaskcwOrder(Task task);
	
	public  int getimptaskcwOrdercount(Task task);
	
	/**
	 * @author lk 2017/5/31
	 * @return
	 * 查询已领工单号
	 */
	public  Set<String> getyl112ordertaskList(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/31
	 * @param map
	 * @return
	 * 查询c网任务完成情况
	 */
	List<Task> getcwordertaskwcqkList(Map<String,String> map);
	
	int getcwordertaskwcqkcount(Map<String,String> map);
	
	/**
	 * 根据c网工单id查询任务信息
	 * @return
	 */
	List<Task> gettaskbycworderId(Map<String,String> map);
	/**
	 * @author lk 2017/5/31
	 * @param map
	 * @return
	 * 评分后操作
	 */
	public int updatecwTaskorder(Map<String, String> map);
	
	/**
	 * @author lk 2017/6/1
	 * @param map
	 * @return
	 * 释放c网任务工单
	 */
	public String updreleasecwOrderByUser(Map<String, String> map);
	
	/**
	 * @author lk 2017/6/1
	 * @param map
	 * @return
	 */
	public String updreleasecwOrderBycDate(Map<String, String> map);
}
