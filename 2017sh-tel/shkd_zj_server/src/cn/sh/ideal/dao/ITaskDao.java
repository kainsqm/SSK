package cn.sh.ideal.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
/**
 * @author niewq
 * 处理任务Dao
 * 
 * */

public interface ITaskDao {
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
	 * **/
	public int addTask(Task task);
	
	/**
	 * @author niewq
	 * 修改任务信息
	 * @param task  传入参数
	 * **/
	public int updateTask(Task task);
	
	
	
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
	 * 获取自增的主键值
	 * **/
	public int getAutoId();
	
	
	/**
	 * 新增任务员工中间表
	 * **/
	public void addTaskQcUser(Task task);
	
	
	/**
	 * 获取自增的主键值
	 * **/
	public int getTaskQcUserAutoId();
	
	/**
	 * 新增质检员任务完成情况
	 * */
	public void insertToTaskqcuserComplete(Task task);
	
	
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
	 * **/
	public int deleteTask(Map<String, String> mapdata);
	
	/**
	 *  修改t_record_info(录音的任务状态，是，否)
	 * **/
	public int releaseRecordInfo(Map<String, String> mapdata);
	
	/**
	 *  t_qc_taskrecord删除任务录音信息
	 * **/
	public int releaseRecord(Map<String, String> mapdata);
	
	/**
	 *  删除任务表的数据
	 * **/
	public int delTask(int taskId);
	
	/**
	 *  删除任务员工中间表的数据
	 * **/
	public int delTaskQcuser(Map<String, String> mapdata);
	
	
	/**
	 *  删除质检员任务完成情况表的数据
	 * **/
	public int delTaskQcuserComplete(Map<String, String> mapdata);
	

	/**
	 * 根据TASKID查询数据
	 * **/
	public Task getOneTaskInfo(int taskId);
	
	/**
	 * 查询任务完成情况
	 * **/
	public List<Task> queryTaskCompleteInfo(Map<String, String> mapdata);
	
	/**
	 *  获取任务完成情况行数
	 * **/
	public int getCountTaskCompleteInfo(Map<String, String> mapdata);
	
	/**
	 * 修改领取数
	 * **/
	public int updateTaskRecordComplete1(Map<String, String> mapdata);
	
	/**
	 * 修改总领取数
	 * */
	public int updateTaskQcUserInfo1(Map<String, String> mapdata);
	
	
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
	 * 释放录音时，修改质检员任务完成情况
	 * **/
	public int updateTaskRecordComplete(Map<String, String> mapdata);
	
	/**
	 * 释放录音时，修改任务员工中间表
	 * **/
	public int updateTaskQcUserInfo(Map<String, String> mapdata);
	
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
	 * **/
	public String searchReservedthrees (Map<String, String> mapdata);
	
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
	public String oneKeyGetTaskRecord(Map<String, String> mapdata);
	
	/**
	 * 领取录音
	 * **/
	
	public String getRecord(Map<String, String> mapdata);
	
	public int getRecordVerification(Map<String, String> mapdata);
	public int earlierByGet(String cti );
	public int earlierByQc(String cti);
	
	/**
	 * 新增质检任务录音
	 * **/
	public int insertToTaskrecord(Task record);
	
	public int updateRecordTaskFlag(Map<String, String> mapdata);
	public int updateTaskMainStatus(Map<String, String> mapdata);
	
	/**
	 * 查询任务录音
	 * **/
	public List<AgentCallLog> queryRecordList(Map<String, String> mapdata);
	
	/**
	 * 查询任务录音记录数
	 * **/
	public int queryRecordListCount(Map<String, String> mapdata);
	
	public Task queryRecordSJ10(Map<String, String> mapdata);
	
	public String getBigWorkId(int userid);
	
	 
	/**
	 *查询结果中有几个话务员 
	 * **/
	public int getResultHWCount(Map<String, String> mapdata);
    /**
     * 获取录音
     * **/	
 	public List<RecordInfo> getLingQuRecord(Map<String, String> mapdata);
 	
 	/**
 	 * 查询结果中有多少条录音
 	 * **/
 	public int getResultLYCount(Map<String, String> mapdata);
 	
 	/***
 	 * 获取少于10条的录音
 	 * ***/
 	public List<RecordInfo> getRecordUnder10(Map<String, String> mapdata);
 	
 	
 	/***
 	 * 每人抽取一条录音
 	 * ***/
 	public List<RecordInfo> getRecordEveryOne(Map<String, String> mapdata);
 	
 	
 	/***
 	 * 随机抽取剩下的录音
 	 * ***/
 	public List<RecordInfo> randomGetRecord(Map<String, String> mapdata);
 	
 	
 	/***
 	 * 一键领取获取录音数量
 	 * **/
 	public String  recordCount(Map<String, String> mapdata);
 	
 	/**
 	 * 任务录音评分时 ，查询该录音相关的信息
 	 * @author niewenqiang
 	 * **/
 	public Task getRecordTask(int recordId);
 	
 	public int queryRecordListCnt(Map<String, String> mapdata);
 	/**
 	 * 更新当前录音的状态 （是否已质检）
 	 * ***/
 	public int updateTaskRecordInfo(Map<String, String> mapdata);
 	
 	/**
 	 * 更新任务表状态
 	 * **/
	public int updateTaskInfo(Map<String, String> map) ;
	
	/**
	 * 一键领取第一档获取录音数量
	 * ***/
	public int recordsqlcount(Map<String, String> map);
	
	/**
	 * 一键领取第一档获取录音编号
	 * ***/  
	public String  getreservedthree1(Map<String, String> map);
	 
	 /**
	* 一键领取第一档根据编号获取录音记录数
	 * ***/  
	public int  queryTaskRecord1(Map<String, String> map);
	
	/**
	 * 执行SQL 一键领取录音的功能
	 * **/
	public void execSql(Map<String, String> map);
	
	/**
	 * 根据录音编号查询该条录音
	 * ***/
	public RecordInfo selectTaskRecord(Map<String, String> map);
	
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
	public int add112orderTask(Task task);
	
	/**
	 * @author lk 2017/5/03
	 * @param taskId
	 * @return
	 * 删除112工单任务
	 */
	public int del112orderTask(Integer taskId);
	
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
	 * @author lk 2017/5/04
	 * @param task
	 * @return
	 * 新增任务员工中间表
	 */
	public int insert112ordertaskUser(Task task);
	
	/**
	 * @author lk 2017/5/04
	 * @param task
	 * @return
	 * 新增质检员任务完成情况表
	 */
	public int insertToTask112orderuserComplete(Task task);
	
	/**
	 * @author lk 2017/5/9
	 * @param map
	 * @return
	 * 修改关联任务录音状态
	 */
	public int release112Order(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/10
	 * @param task
	 * @return
	 * 质检员查询任务执行列表
	 */
	public List<Task> getimptask112Order(Task task);
	
	public  int getimptask112Ordercount(Task task); 
	
	/**
	 * @author lk 2017/5/12
	 * @param task
	 * @return
	 * 领取112工单
	 */
	public  int update112ordertaskUser(Task task);
	
	/**
	 * @author lk 2017/5/15
	 * @param map
	 * @return
	 * 修改质检员任务领取数量
	 */
	public int upd112ordertaskcomplete(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/16
	 * @param task
	 * @return
	 * 修改112工单状态
	 */
	int upd112orderstatus(Task task);
	
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
	
	/**
	 * @author lk 根据工单id查询任务信息
	 * @param map
	 * @return
	 */
	List<Task> gettaskby112orderId(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/24
	 * @param map
	 * @return
	 * 根据用户id和时间查询当天领取数量
	 */
	 int countusertask(Map<String,String> map);
	
	
	int updateTaskQcUserInfobytaskId(Map<String,String> map);
	
	int updateTaskOrderInfo(Map<String,String> map);
	/**
	 * @author lk 修改任务主表状态
	 * @param map
	 * @return
	 */
	int update112orderTaskInfo(Map<String,String> map);
	/**
	 * @author lk
	 * @return
	 * 查询当天领取数
	 */
	String getcomplecount(Map<String,String> map);
	/**
	 * @author lk
	 * @param map
	 * @return
	 * 修改任务员工完成情况详细表
	 */
	int update112orderTaskcomplete(Map<String,String> map);
	
	List<Task> order112taskqcuserbyuserid(Map<String,String> map);
	
	List<Task> getqctaskqcuser(Map<String,String> map);
	
	int deltaskOrder(Map<String,String> map);
	
	/**
	 * @author lk 2017/5/23
	 * @param task
	 * @return
	 * 查询c网工单任务列表
	 */
	 List<Task> gettaskcwOrder(Task task);
	
	 int gettaskcwOrdercount(Task task);
	 
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
	
    public int checkcwOrdername(String taskName);
    
    public Task getcworderTaskbyid(Integer taskId);
    
    /**
	 * @author lk 2017/5/26
	 * @param taskId
	 * @return
	 * 删除c网工单任务
	 */
	public int delcworderTask(Integer taskId);	
	public int release112orderInfo(Map<String, String> map);
	public int releasecworderInfo(Map<String, String> map);
	
	
	/**
	 * @author lk 2017/5/26
	 * @param task
	 * @return
	 * 质检员查询任务执行列表
	 */
	public List<Task> getimptaskcwOrder(Task task);
	
	public  int getimptaskcwOrdercount(Task task);
	
	public int updcworderstatus(Task task);
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
	 * 修改c网任务工单主表
	 */
	int updatecworderTaskInfo(Map<String,String> map);
	
	/**
	 * @author lk 2017/6/1
	 * @param map
	 * @return
	 * 修改c网工单主表任务状态
	 */
	int releasecwOrder(Map<String,String> map);
	
	/**
	 * @author lk 2017/6/1
	 * @param map
	 * @return
	 * 督导查询c网工单任务任务完成情况
	 */

	
	int getcwordertaskwcqkqccount(Map<String,String> map);
	
	
	List<Task> getcwordertaskwcqkqcList(Map<String,String> map);
	
	
	
	
}
