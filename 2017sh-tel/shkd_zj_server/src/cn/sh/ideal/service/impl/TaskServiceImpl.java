/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sh.ideal.controller.RecordInfoController;
import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.Util;

/**
 * 任务业务类
 * 
 * **/

@Service("taskService")
public class TaskServiceImpl implements ITaskService {
	private static Logger log = Logger.getLogger(TaskServiceImpl.class);
	@Resource
	private ITaskDao taskDao;
	@Resource
	private IUserInfoDao userInfoDao;
	
	/**
	 * 获取工作任务派发列表
	 * 
	 * **/
	@Override
	public List<Task> getTaskDistribut(Map<String, String> mapdata) {
		return taskDao.getTaskDistribut(mapdata);
	}

	/**
	 * @author niewq 新增任务信息
	 * @param task
	 *            传入参数
	 * **/
	public int addTask() throws SQLException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();// 获取请求
		String taskName = request.getParameter("TASK_NAME");
		String taskStartTime = request.getParameter("STARTTIME");
		String taskEndTime = request.getParameter("ENDTIME");
		String telUserWorkId = request.getParameter("TEL_USER_WORKID");
		String telUser = (String) request.getParameter("TEL_USER");// 包含的话务员ID
		if (telUser.trim() == "") {
			telUser = null;
		}
		String telUserName = (String) request.getParameter("TEL_USER_NAME");// 包含的话务员名字
		if (telUserName.trim() == "") {
			telUserName = null;
		}
		String QC_USER = (String) request.getParameter("QC_USER");// 质检员ID
		String QC_USER_WORKID = (String) request.getParameter("QC_USER_WORKID");// 质检员ID
		String qcUserName = (String) request.getParameter("QC_USER_NAME");// 质检员名字
		String csrTopDCount = (String) request.getParameter("CSR_TOP_D_COUNT");// 每个CSR每天最高被质检次数
		String csrTopWCount = (String) request.getParameter("oneCsrWCount1")
				+ "," + (String) request.getParameter("oneCsrWCount2") + ","
				+ (String) request.getParameter("oneCsrWCount3") + ","
				+ (String) request.getParameter("oneCsrWCount4") + ","
				+ (String) request.getParameter("oneCsrWCount5") + ","
				+ (String) request.getParameter("oneCsrWCount6");// 每个CSR每周最高被质检次数(六个挡)
		String qcDCount = (String) request.getParameter("oneQcDCount1") + ","
				+ (String) request.getParameter("oneQcDCount2") + ","
				+ (String) request.getParameter("oneQcDCount3") + ","
				+ (String) request.getParameter("oneQcDCount4") + ","
				+ (String) request.getParameter("oneQcDCount5") + ","
				+ (String) request.getParameter("oneQcDCount6");// 每个质检员每天质检任务规格(六个挡)
		String IS_PUBLISH = (String) request.getParameter("IS_PUBLISH");// 是否发布

		String createUser = String.valueOf(request.getAttribute(
				Constans.USER_INFO_USERID));
		Task task = new Task();
		if (IS_PUBLISH.equals("1")) { // 当选择发布时 设置状态为待执行
			task.setTaskStatus("1");
		} else {
			task.setTaskStatus("0");
		}
		task.setIsPublish(IS_PUBLISH); // 是否发布
		task.setTaskName(taskName);// 任务名称
		task.setCreateUserId(Integer.parseInt(createUser));// 任务创建者
		task.setQcUser(QC_USER);// 质检员id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		task.setTaskStartTime(taskStartTime);// 任务开始时间
		task.setTaskEndTime(taskEndTime);// 任务结束时间;
		task.setTelUser(telUser);
		task.setTelUserWorkId(telUserWorkId);
		task.setTelUserName(telUserName);
		task.setQcUserWorkId(QC_USER_WORKID);
		task.setCsrTopDCount(csrTopDCount);
		task.setCsrTopWCount(csrTopWCount);
		task.setQcDCount(qcDCount);
		task.setQcUserName(qcUserName);
		taskDao.addTask(task);
		int taskId = task.getTaskId();
		int countDay = 0;// 任务天数（开始到结束）
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			countDay = DateUtil.getDays(formatter.parse(taskStartTime
					.substring(0, 10), pos), formatter.parse(taskEndTime
					.substring(0, 10), pos1));
		} catch (Exception e) {
			System.out.println("exception" + e.toString());
			throw e;
		}
		String[] qcUsersItem = QC_USER.split(",");

		for (int i = 0; i < qcUsersItem.length; i++) {
			// 保存任务员工中间表信息
			task = new Task();
			task.setTaskId(taskId);
			task.setQcUser(qcUsersItem[i].trim());// 质检员ID
			task.setGetRecordCount("0,0,0,0,0,0");// 领取录音数(六个挡，中间逗号隔开)
			task.setCompleteCount("0,0,0,0,0,0");// 完成录音数(六个挡，中间逗号隔开)
			if (IS_PUBLISH.trim().equals("1")) {
				task.setEachQcuserStatus("1");
			} else {
				task.setEachQcuserStatus(null);
			}
			// }
			taskDao.addTaskQcUser(task);
			int taskuserId = task.getTaskuserId();
			// 保存任务完成情况表信息
			String today = taskStartTime.substring(0, 10);// 哪一天的任务
			for (int j = 0; j < countDay + 1; j++) {
				task = new Task();

				task.setcDate(today);// 哪天的任务(日期)
				task.setTaskuserId(taskuserId);// 任务质检员表ID
				task.setTaskId(taskId);
				task.setGetRecordCount("0,0,0,0,0,0");
				task.setCompleteCount("0,0,0,0,0,0");
				taskDao.insertToTaskqcuserComplete(task);
				today = Util.getSpecifiedDayAfter(today);
			}

		}
		return 1;
	}

	/**
	 * @author niewq 查询质检员
	 * */
	public List<UserInfo> getQcUser() {
		return taskDao.getQcUser();
	}

	/**
	 * @author niewq 查询受理员
	 * */
	public List<UserInfo> getManager(Map<String, String> map) {
		return taskDao.getManager(map);
	}

	/**
	 * 获取任务派发列表数据行数
	 * **/
	public int getCountTaskDistribut(Map<String, String> mapdata) {
		return taskDao.getCountTaskDistribut(mapdata);
	}

	/**
	 * 获取受理员 总数
	 * **/
	public int getCountManager(Map<String, String> mapdata) {
		return taskDao.getCountManager(mapdata);
	}

	/**
	 * 删除任务
	 * **/
	public int deleteTask(Map<String, String> mapdata) throws SQLException {
		mapdata.put("taskType", "5");
		if (!("0".equals(mapdata.get("taskStatus")) || "1".equals(mapdata
				.get("taskStatus")))) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", mapdata.get("taskId"));
			// 1：修改t_record_info(录音的任务状态，是，否)
			taskDao.releaseRecordInfo(map1);
			// 2：t_qc_taskrecord删除任务录音信息
			taskDao.releaseRecord(map1);
		} else {
		}
		int taskId = Integer.parseInt(mapdata.get("taskId"));
		taskDao.delTask(taskId);
		taskDao.delTaskQcuser(mapdata);
		taskDao.delTaskQcuserComplete(mapdata);

		return 1;
	}

	/**
	 * 根据TASKID查询数据
	 * **/
	public Task getOneTaskInfo(int taskId) {
		return taskDao.getOneTaskInfo(taskId);
	}

	@Override
	public int updateTask() throws SQLException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();// 获取请求
		String oldtaskId = request.getParameter("TASK_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskStartTime = request.getParameter("taskStartTime");
		String taskEndTime = request.getParameter("taskEndTime");
		String telUserWorkId = request.getParameter("TEL_USER_WORKID");
		String telUser = (String) request.getParameter("TEL_USER");// 包含的话务员ID
		if (telUser.trim() == "") {
			telUser = null;
		}
		String telUserName = (String) request.getParameter("TEL_USER_NAME");// 包含的话务员名字
		if (telUserName.trim() == "") {
			telUserName = null;
		}
		String QC_USER = (String) request.getParameter("QC_USER");// 质检员ID
		String QC_USER_WORKID = (String) request.getParameter("QC_USER_WORKID");// 质检员ID
		String qcUserName = (String) request.getParameter("QC_USER_NAME");// 质检员名字
		String csrTopDCount = (String) request.getParameter("CSR_TOP_D_COUNT");// 每个CSR每天最高被质检次数
		String csrTopWCount = (String) request.getParameter("oneCsrWCount1")
				+ "," + (String) request.getParameter("oneCsrWCount2") + ","
				+ (String) request.getParameter("oneCsrWCount3") + ","
				+ (String) request.getParameter("oneCsrWCount4") + ","
				+ (String) request.getParameter("oneCsrWCount5") + ","
				+ (String) request.getParameter("oneCsrWCount6");// 每个CSR每周最高被质检次数(六个挡)
		String qcDCount = (String) request.getParameter("oneQcDCount1") + ","
				+ (String) request.getParameter("oneQcDCount2") + ","
				+ (String) request.getParameter("oneQcDCount3") + ","
				+ (String) request.getParameter("oneQcDCount4") + ","
				+ (String) request.getParameter("oneQcDCount5") + ","
				+ (String) request.getParameter("oneQcDCount6");// 每个质检员每天质检任务规格(六个挡)
		String IS_PUBLISH = (String) request.getParameter("IS_PUBLISH");// 是否发布

		String createUser = String.valueOf(request.getAttribute(
				Constans.USER_INFO_USERID));
		Task task = new Task();
		if (IS_PUBLISH.equals("1")) { // 当选择发布时 设置状态为待执行
			task.setTaskStatus("1");
		} else {
			task.setTaskStatus("0");
		}
		task.setIsPublish(IS_PUBLISH); // 是否发布
		task.setTaskName(taskName);// 任务名称
		task.setCreateUserId(Integer.parseInt(createUser));// 任务创建者
		task.setQcUser(QC_USER);// 质检员id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		task.setTaskStartTime(taskStartTime);// 任务开始时间
		task.setTaskEndTime(taskEndTime);// 任务结束时间
		task.setTelUser(telUser);
		task.setTelUserName(telUserName);
		task.setQcUserWorkId(QC_USER_WORKID);
		task.setCsrTopDCount(csrTopDCount);
		task.setTelUserWorkId(telUserWorkId);
		task.setCsrTopWCount(csrTopWCount);
		task.setQcDCount(qcDCount);
		task.setQcUserName(qcUserName);
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskId", oldtaskId);
		map.put("taskStatus", "0");

		int success = deleteTask(map);
		if (1 == success) {
			taskDao.addTask(task);
			int taskId = task.getTaskId();
			int countDay = 0;// 任务天数（开始到结束）
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				ParsePosition pos = new ParsePosition(0);
				ParsePosition pos1 = new ParsePosition(0);
				countDay = DateUtil.getDays(formatter.parse(taskStartTime
						.substring(0, 10), pos), formatter.parse(taskEndTime
						.substring(0, 10), pos1));
			} catch (Exception e) {
				log.error("exception" + e.toString());
				throw e;
			}
			String[] qcUsersItem = QC_USER.split(",");

			for (int i = 0; i < qcUsersItem.length; i++) {
				// 保存任务员工中间表信息
				task = new Task();
				task.setTaskId(taskId);
				task.setQcUser(qcUsersItem[i].trim());// 质检员ID
				task.setGetRecordCount("0,0,0,0,0,0");// 领取录音数(六个挡，中间逗号隔开)
				task.setCompleteCount("0,0,0,0,0,0");// 完成录音数(六个挡，中间逗号隔开)
				if (IS_PUBLISH.trim().equals("1")) {
					task.setEachQcuserStatus("1");
				} else {
					task.setEachQcuserStatus(null);
				}
				// }
				taskDao.addTaskQcUser(task);
				int taskuserId = task.getTaskuserId();
				// 保存任务完成情况表信息
				String today = taskStartTime.substring(0, 10);// 哪一天的任务
				for (int j = 0; j < countDay + 1; j++) {
					task = new Task();

					task.setcDate(today);// 哪天的任务(日期)
					task.setTaskuserId(taskuserId);// 任务质检员表ID
					task.setTaskId(taskId);
					task.setGetRecordCount("0,0,0,0,0,0");
					task.setCompleteCount("0,0,0,0,0,0");
					taskDao.insertToTaskqcuserComplete(task);
					today = Util.getSpecifiedDayAfter(today);
				}

			}
			return 1;
		}
		return 0;
	}

	/**
	 * 查询任务完成情况
	 * **/
	public List<Task> queryTaskCompleteInfo(Map<String, String> mapdata) {
		mapdata.put("taskType", "5");
		return taskDao.queryTaskCompleteInfo(mapdata);
	}

	/**
	 * 获取任务完成情况行数
	 * **/
	public int getCountTaskCompleteInfo(Map<String, String> mapdata) {
			mapdata.put("taskType", "5");
		return taskDao.getCountTaskCompleteInfo(mapdata);
	}

	/**
	 * 批量释放录音
	 * **/
	public String releaseRecordByQcUser(Map<String, String> map)
			throws SQLException {
		String result = "成功";
		try {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", map.get("taskId"));
			map1.put("qcUser", map.get("qcUser"));
			map1.put("isqc", "0");
			map1.put("taskType", "5");
			// 1：修改t_record_info(录音的任务状态，是，否)
			taskDao.releaseRecordInfo(map1);
			// 2：t_qc_taskrecord删除任务录音信息
			taskDao.releaseRecord(map1);
			// 3：t_qc_taskqcuser_complete修改领取数
			taskDao.updateTaskRecordComplete1(map1);
			// 4：t_qc_task_qcuser修改总领取数
			taskDao.updateTaskQcUserInfo1(map1);
		} catch (Exception e) {
			result = "失败";
			throw e;
		}
		return result;
	}

	/**
	 * 
	 * 任务完成情况详情
	 * */
	public List<Task> queryTaskCompleteInforc(Map<String, String> mapdata) {
		mapdata.put("taskType", "5");
		return taskDao.queryTaskCompleteInforc(mapdata);
	}

	/**
	 * 获取任务完成情况详情总数
	 * */
	public int getCountTaskCompleteInforc(Map<String, String> mapdata) {
		mapdata.put("taskType", "5");
		return taskDao.getCountTaskCompleteInforc(mapdata);
	}

	/**
	 * 质检班长批量释放录音(以天为维度)
	 * **/
	public String releaseRecord(Map<String, String> map) throws SQLException {
		String result = "成功";
		try {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("cDate", map.get("cDate"));
			map1.put("taskId", map.get("taskId"));
			map1.put("qcUser", map.get("qcUser"));
			map1.put("isqc", "0");
			// 修改数据：t_record_info(录音的任务状态，是，否)
			taskDao.releaseRecordInfo(map1);
			// t_qc_taskrecord删除任务录音信息
			taskDao.releaseRecord(map1);
			// t_qc_taskqcuser_complete修改领取数
			map1.clear();
			map1.put("taskQcUserId", map.get("taskUserId"));
			map1.put("startTime", map.get("cDate"));
			map1.put("ednTime", map.get("cDate"));
			map1.put("taskType", "5");
			Task taskInfoItem = taskDao.queryTaskCompleteInforc(map1).get(0);
			String getRecords = taskInfoItem.getGetRecordCount();
			String completeCount = taskInfoItem.getCompleteCount();
			taskInfoItem.setGetRecordCountItem(getRecords.split(","));// 领取数
			taskInfoItem.setCompleteCountItem(completeCount.split(","));// 完成数
			String[] countItem = new String[6];// 相差
			for (int i = 0; i < taskInfoItem.getGetRecordCountItem().length; i++) {
				countItem[i] = String
						.valueOf(Integer.parseInt(taskInfoItem
								.getGetRecordCountItem()[i])
								- Integer.parseInt(taskInfoItem
										.getCompleteCountItem()[i]));
			}
			map1.clear();
			map1.put("taskQcUserId", map.get("taskUserId"));
			map1.put("taskTime", map.get("cDate"));
			map1.put("getRecordCount", taskInfoItem.getCompleteCount());
			map1.put("taskStatus", taskInfoItem.getEachDateStatus());
			map1.put("taskType", "5");
			taskDao.updateTaskRecordComplete(map1);
			// t_qc_task_qcuser修改总领取数
			map1.clear();
			map1.put("qcUser", map.get("qcUser"));
			map1.put("taskId", map.get("taskId"));
			map1.put("taskType", "5");
			taskInfoItem = (Task) taskDao.queryTaskCompleteInfo(map1).get(0);
			taskInfoItem.setGetRecordCountItem(taskInfoItem.getGetRecordCount()
					.split(","));
			// 领取数还剩
			for (int i = 0; i < taskInfoItem.getGetRecordCountItem().length; i++) {
				taskInfoItem.getGetRecordCountItem()[i] = String
						.valueOf(Integer.parseInt(taskInfoItem
								.getGetRecordCountItem()[i])
								- Integer.parseInt(countItem[i]));
			}
			String getRecordTemp = "";
			for (int i = 0; i < taskInfoItem.getGetRecordCountItem().length; i++) {
				getRecordTemp = getRecordTemp
						+ taskInfoItem.getGetRecordCountItem()[i] + ",";
			}
			getRecordTemp = getRecordTemp.substring(0,
					getRecordTemp.length() - 1);
			map1.put("getRecordCount", getRecordTemp);
			map1.put("taskType", "5");
			taskDao.updateTaskQcUserInfo(map1);
		} catch (Exception e) {
			result = "失败";
			throw e;
		}
		return result;
	}

	/***
	 * 质检员查询任务
	 * **/
	public List<Task> queryListForQc(Map<String, String> mapdata) {
		List<Task> list = taskDao.queryListForQc(mapdata);
		return list;
	}

	/***
	 * 质检员查询任务记录总数
	 * **/
	public int queryListForQcCount(Map<String, String> mapdata) {
		return taskDao.queryListForQcCount(mapdata);
	}

	/**
	 * 查询随机10条录音
	 * **/
	public String searchReservedthrees(Map<String, String> mapdata)
		 {
		/** 随机获取10条录音 **/
	
			String iTaskUserId = mapdata.get("taskUserId"); // 任务质检员相关表主键
			String iTaskDate = mapdata.get("taskTime"); // 任务日期(领取哪一天的任务)
			String iTimeGroup = mapdata.get("recordLength");// 时间档(1-6)
			String iTelUserID = mapdata.get("telUserID"); // 话务员ID
			String vReservedthrees = ""; // 返回的流水号
			// 设置日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date timeNow=null;
			try {
				timeNow = df.parse(iTaskDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			Calendar begin = Calendar.getInstance();
			begin.setTime(timeNow);
			begin.add(Calendar.DAY_OF_MONTH, -1);
			String vStartRecordTime = df.format(begin.getTime()); // 传入的时间-1天
			vStartRecordTime = vStartRecordTime + " 00:00:00";
			String vStopRecordTime = df.format(begin.getTime()); // 传入的时间-1天
			vStopRecordTime = vStopRecordTime + " 23:59:59";
			// 给话单查询的条件限制，赋值 DATE_FORMAT(ti.CREATE_DATE,'%Y-%m-%d %h:%i:%s')
			Task taskList = taskDao.queryRecordSJ10(mapdata);
			// 根据传入的USErID获取大工号
			String iTelUserIDWorkId = "";
			if (iTelUserID != null) {
				/*iTelUserIDWorkId = taskDao.getBigWorkId(Integer
						.parseInt(iTelUserID));*/
				iTelUserIDWorkId=iTelUserID;
			}
			String vStrSqlBegin = "select count(1) counts, tri.BIG_WORKID,min(tri.RESERVED_THREE) reservedthree ";
			String vStrSql = "from t_record_info tri where tri.RECORD_ID not in (select QC.RECORD_ID from T_QC QC where QC.ENABLED='1' AND RECORD_ID IS NOT NULL) "
					+ "and tri.RESERVED_THREE <> '' "
					+ "and tri.IS_TASK='0'"
					+ "and tri.RESERVED_THREE is not null "
					+ "AND tri.START_TIME BETWEEN DATE_FORMAT('"
					+ vStartRecordTime
					+ "','%Y-%m-%d %H:%i:%s') AND DATE_FORMAT('"
					+ vStopRecordTime + "','%Y-%m-%d %H:%i:%s')";

			String vStrSqlEnd = " group by tri.BIG_WORKID";
			if (iTimeGroup.equals("1")) {// 时间档
				vStrSql = vStrSql + " and tri.RECORD_LENGTH between 0 and 60";
			} else if (iTimeGroup.equals("2")) {
				vStrSql = vStrSql + " and tri.RECORD_LENGTH between 61 and 120";
			} else if (iTimeGroup.equals("3")) {
				vStrSql = vStrSql
						+ " and tri.RECORD_LENGTH between 121 and 180";
			} else if (iTimeGroup.equals("4")) {
				vStrSql = vStrSql
						+ " and tri.RECORD_LENGTH between 181 and 300";
			} else if (iTimeGroup.equals("5")) {
				vStrSql = vStrSql
						+ " and tri.RECORD_LENGTH between 301 and 600";
			} else if (iTimeGroup.equals("6")) {
				vStrSql = vStrSql
						+ " and tri.RECORD_LENGTH between 601 and 360000";
			}
			if (iTelUserID != null) { // 传过来的话务员ID
				vStrSql = vStrSql + " and tri.BIG_WORKID ='" + iTelUserIDWorkId
						+ "'";
			}
			String workId = taskList.getTelUserWorkId().trim();
			String[] workIdList = workId.split(",");
			String bigWorkId = "";
			for (int i = 0; i < workIdList.length; i++) {
				bigWorkId = bigWorkId + "'" + workIdList[i] + "',";
			}
			if (bigWorkId != "") {
				bigWorkId = bigWorkId.substring(0, bigWorkId.length() - 1);
			}

			if (taskList.getTelUser() != null) { // 约束中话务员ID有效
				vStrSql = vStrSql + " and tri.BIG_WORKID in ( " + bigWorkId
						+ " )";
			}

			// 查出当天最高被质检次数达到饱和状态的CSR工号，在sql中过滤掉
			vStrSql = vStrSql
					+ " and tri.BIG_WORKID not in (select T.WORK_ID from (select tu.WORK_ID, count(1) counts from t_qc_taskrecord tr inner join 	T_SYS_USER  tu on tu.USER_ID=tr.TEL_USER"
					+ "  where tr.task_id=" + taskList.getTaskId()
					+ " and tr.whichdatefortask = DATE_FORMAT('" + iTaskDate
					+ "','%Y-%m-%d') group by tr.tel_user) T "
					+ " where T.counts = " + taskList.getCsrTopDCount() + ")";
			// 查询出每周最高被质检次数达到饱和状态的CSR工号，在sql中过滤掉
			String vToday = iTaskDate; // 根据传入的日期 获取该日期所在周的周一和周日
			String vweekBegin = DateUtil.dayForWeekBegin(vToday);
			String vWeekEnd = DateUtil.dayForWeekEnd(vToday);
			String[] csrTop = taskList.getCsrTopWCount().split(",");// 转换为数组
			int index = Integer.parseInt(iTimeGroup) - 1;
			vStrSql = vStrSql
					+ " and tri.BIG_WORKID not in (select aa.WORK_ID from (select tu.WORK_ID, count(1) counts from t_qc_taskrecord rr inner join 	T_SYS_USER  tu on tu.USER_ID=rr.TEL_USER where rr.RECORDLENGTH = "
					+ iTimeGroup
					+ " and rr.whichdatefortask between DATE_FORMAT('"
					+ vweekBegin + "','%Y-%m-%d') and DATE_FORMAT('" + vWeekEnd
					+ "','%Y-%m-%d')"
					+ " group by rr.tel_user) aa where aa.counts >="
					+ csrTop[index] + ")";

			/******************** 过滤数据 结束 ********************/
			// 查询结果中有几个话务员
			String sql = "select count(hw.counts) from (" + vStrSqlBegin
					+ vStrSql + vStrSqlEnd + ") hw";
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql", sql);
			int vtelUserCount = taskDao.getResultHWCount(map);

			/******** 如果符合条件的录音的话务员超过10人，则随机10人，每人抽取一条录音 ****** 开始 ********/
			int myRandomNum = 0;
			java.util.Random random = new java.util.Random();// 定义随机类
			if (vtelUserCount >= 10) {
				myRandomNum = random.nextInt(vtelUserCount - 10 + 1) + 1;// 返回[0,value)集合中的整数，注意不包括value
				String sqlmore = "select N.reservedthree from (select M.* from ("
						+ vStrSqlBegin
						+ vStrSql
						+ vStrSqlEnd
						+ ") M limit "
						+ myRandomNum + "," + (myRandomNum + 10 - 1) + ") N";
				map.put("sqlmore", sqlmore);
				List<RecordInfo> recordList = taskDao.getLingQuRecord(map);
				for (int i = 0; i < recordList.size(); i++) {
					vReservedthrees = vReservedthrees + "'"
							+ recordList.get(i).getReservedThree() + "',";
				}
			}
			/******** 如果符合条件的录音的话务员超过10人，则随机10人，每人抽取一条录音 ****** 结束 ********/

			/******** 如果符合条件的录音的话务员少于10人 ****** 开始 ********/
			if (vtelUserCount < 10) {// 查询结果中有多少条录音
				String sqlresult = "select IFNULL(sum(B.counts),0) from ("
						+ vStrSqlBegin + vStrSql + vStrSqlEnd + ") B";
				map.put("sqlresult", sqlresult);
				int vRecordCount = taskDao.getResultLYCount(map);
				/******** 如果符合条件的录音不大于10条，返回全部录音 ****** 开始 ********/
				if (vRecordCount <= 10) { // 符合条件的录音不大于10条
					vReservedthrees = "";
					String strsql = "select tri.RESERVED_THREE " + vStrSql;
					map.put("sqlmore", strsql);
					List<RecordInfo> recordListUnder = taskDao
							.getLingQuRecord(map);
					for (int i = 0; i < recordListUnder.size(); i++) {
						vReservedthrees = vReservedthrees + "'"
								+ recordListUnder.get(i).getReservedThree()
								+ "',";
					}
				}
				/******** 如果符合条件的录音不大于10条，返回全部录音 ****** 结束 ********/

				/******** 如果符合条件的录音大于10条，尽量平均抽取10条****** 开始 ********/
				if (vRecordCount > 10) {
					// 每人抽取一条录音，然后剩下的从结果集中随机抽取
					vReservedthrees = "";
					// 每人抽取一条录音 开始
					String everyonesql = "select H.reservedthree from ("
							+ vStrSqlBegin + vStrSql + vStrSqlEnd + ") H";
					map.put("everyonesql", everyonesql);
					List<RecordInfo> recordListUnder = taskDao
							.getRecordEveryOne(map);
					for (int i = 0; i < recordListUnder.size(); i++) {
						vReservedthrees = vReservedthrees + "'"
								+ recordListUnder.get(i).getReservedThree()
								+ "',";
					}
					// 每人抽取一条录音 结束

					// 然后剩下的从结果集中随机抽取 开始
					// vRecordCount：=10-vtelUserCount;--计算还余几条待抽取
					myRandomNum = random.nextInt(vRecordCount - 10 + 1) + 1;
					vReservedthrees = vReservedthrees.substring(0,
							vReservedthrees.length() - 1);
					String randomGetRecordsql = "select O.RESERVED_THREE from (select P.*  from (select tri.RESERVED_THREE "
							+ vStrSql
							+ " and tri.RESERVED_THREE not in ("
							+ vReservedthrees
							+ ")) P limit "
							+ myRandomNum
							+ ","
							+ (myRandomNum + 10 - vtelUserCount - 1)
							+ ") O";
					vReservedthrees = vReservedthrees + ',';
					map.put("randomGetRecordsql", randomGetRecordsql);
					List<RecordInfo> recordListLast = taskDao
							.randomGetRecord(map);
					for (int i = 0; i < recordListLast.size(); i++) {
						vReservedthrees = vReservedthrees + "'"
								+ recordListLast.get(i).getReservedThree()
								+ "',";
					}
					/******** 如果符合条件的录音大于10条，尽量平均抽取10条****** 结束 ********/
				}
			}

			return vReservedthrees;
		
	}

	/**
	 * 查询任务领取
	 * */
	public List<AgentCallLog> searchRecordToGet(Map<String, String> mapdata) {
		return taskDao.searchRecordToGet(mapdata);
	}

	/***
	 * 查询任务领取总数
	 * **/
	public int searchRecordToGetCount(Map<String, String> mapdata) {
		return taskDao.searchRecordToGetCount(mapdata);
	}

	/**
	 * 一键领取录音
	 * 
	 * **/
	// 设置全局变量 记录获取的录音数和领取数
	 int vQcDCount1;
	 int vQcDCount2;
	 int vQcDCount3;
	 int vQcDCount4;
	 int vQcDCount5;
	 int vQcDCount6;
	 int VgetRecordCount1;
	 int VgetRecordCount2;
	 int VgetRecordCount3;
	 int VgetRecordCount4;
	 int VgetRecordCount5;
	 int VgetRecordCount6;
	 int VgetAllRCount1;
	 int VgetAllRCount2;
	 int VgetAllRCount3;
	 int VgetAllRCount4;
	 int VgetAllRCount5;
	 int VgetAllRCount6;
	String vMessage = "成功";
	public String updoneKeyGetRecord(Map<String, String> mapdata){
			String iTaskUserId = mapdata.get("taskUserId");
			String iTaskDate = mapdata.get("taskTime");
			// 设置日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date timeNow=null;
			try {
				timeNow = df.parse(iTaskDate);
			} catch (ParseException e1) {
			log.error(e1);
			}
			Calendar begin = Calendar.getInstance();
			begin.setTime(timeNow);
			begin.add(Calendar.DAY_OF_MONTH, -1);
			String vStartRecordTime = df.format(begin.getTime()); // 录音开始时间
			vStartRecordTime = vStartRecordTime + " 00:00:00";
			String vStopRecordTime = df.format(begin.getTime()); // 录音结束时间
			vStopRecordTime = vStopRecordTime + " 23:59:59";
			// 给话单查询的条件限制，赋值 DATE_FORMAT(ti.CREATE_DATE,'%Y-%m-%d %h:%i:%s')
			Task taskList = taskDao.queryRecordSJ10(mapdata);
			String VgetRecordCount = taskDao.recordCount(mapdata);
			String vQcDCount = taskList.getQcDCount();
			String VgetAllRCount = taskList.getGetRecordCount();
			String[] VgetRecordCountList = VgetRecordCount.split(",");
			String[] vQcDCountList = vQcDCount.split(",");
			String[] VgetAllRCountList = VgetAllRCount.split(",");
			// 给当天的任务任务规格赋值(六个挡)
			vQcDCount1 = Integer.parseInt(vQcDCountList[0]);
			vQcDCount2 = Integer.parseInt(vQcDCountList[1]);
			vQcDCount3 = Integer.parseInt(vQcDCountList[2]);
			vQcDCount4 = Integer.parseInt(vQcDCountList[3]);
			vQcDCount5 = Integer.parseInt(vQcDCountList[4]);
			vQcDCount6 = Integer.parseInt(vQcDCountList[5]);
			// 给当天的已领取数赋值(六个挡)
			VgetRecordCount1 = Integer.parseInt(VgetRecordCountList[0]);
			VgetRecordCount2 = Integer.parseInt(VgetRecordCountList[1]);
			VgetRecordCount3 = Integer.parseInt(VgetRecordCountList[2]);
			VgetRecordCount4 = Integer.parseInt(VgetRecordCountList[3]);
			VgetRecordCount5 = Integer.parseInt(VgetRecordCountList[4]);
			VgetRecordCount6 = Integer.parseInt(VgetRecordCountList[5]);
			// 给总领取数赋值
			VgetAllRCount1 = Integer.parseInt(VgetAllRCountList[0]);
			VgetAllRCount2 = Integer.parseInt(VgetAllRCountList[1]);
			VgetAllRCount3 = Integer.parseInt(VgetAllRCountList[2]);
			VgetAllRCount4 = Integer.parseInt(VgetAllRCountList[3]);
			VgetAllRCount5 = Integer.parseInt(VgetAllRCountList[4]);
			VgetAllRCount6 = Integer.parseInt(VgetAllRCountList[5]);

			String vtelUser = taskList.getTelUser();
			String vStrSql = " from t_record_info tri left join T_SYS_USER tsu on tri.BIG_WORKID=tsu.WORK_ID  where 1=1 and tri.RESERVED_THREE <> '' and tri.IS_TASK='0' and tri.RESERVED_THREE is not null "
					+ "AND tri.START_TIME BETWEEN DATE_FORMAT('"
					+ vStartRecordTime
					+ "','%Y-%m-%d %H:%i:%s') AND DATE_FORMAT('"
					+ vStopRecordTime + "','%Y-%m-%d %H:%i:%s')";
			if (vtelUser != "") {
				// 约束中话务员ID有效
				vStrSql = vStrSql + " and tsu.USER_ID in (" + vtelUser + ")";
			}
			if(vQcDCount1==VgetAllRCount1 &&vQcDCount2==VgetAllRCount2&&vQcDCount3==VgetAllRCount3
					&&vQcDCount4==VgetAllRCount4&&vQcDCount5==VgetAllRCount5&&vQcDCount6==VgetAllRCount6){
				vMessage="没有领取的录音";
				return vMessage;
			}
			
			
			// 调用相关的分配录音的方法
			try {
				updateoneKeyGetRecordBy1(iTaskDate, taskList, vStrSql, iTaskUserId);
				updateoneKeyGetRecordBy2(iTaskDate, taskList, vStrSql, iTaskUserId);
				updateoneKeyGetRecordBy3(iTaskDate, taskList, vStrSql, iTaskUserId);
				updateoneKeyGetRecordBy4(iTaskDate, taskList, vStrSql, iTaskUserId);
				updateoneKeyGetRecordBy5(iTaskDate, taskList, vStrSql, iTaskUserId);
				updateoneKeyGetRecordBy6(iTaskDate, taskList, vStrSql, iTaskUserId);
			} catch (Exception e) {
			log.error(e);
			}
			
	
		 
		return vMessage;
	}

	/**
	 * 一键领取获取第一档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy1(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		//int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为1的录音开始 */
		while (VgetRecordCount1 < vQcDCount1) { // 0>=1VgetRecordCount1
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "1",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 0 and 60 " + vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
			    vMessage="数据缺失";
				break;
			}
			//myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 0 and 60 "
					+ vSqlTemp + ") bb limit 0,1";// into
			// vReservedthree;
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);

			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','1',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ (VgetRecordCount1 + 1)
					+ ","
					+ VgetRecordCount2
					+ ","
					+ VgetRecordCount3
					+ ","
					+ VgetRecordCount4
					+ ","
					+ VgetRecordCount5
					+ ","
					+ VgetRecordCount6
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ (VgetAllRCount1 + 1)
					+ ","
					+ VgetAllRCount2
					+ ","
					+ VgetAllRCount3
					+ ","
					+ VgetAllRCount4
					+ ","
					+ VgetAllRCount5
					+ ","
					+ VgetAllRCount6
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			// taskDao.execSql(map);
			VgetRecordCount1 = VgetRecordCount1 + 1;
			VgetAllRCount1 = VgetAllRCount1 + 1;
		}
		;
	}

	/**
	 * 一键领取获取第二档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy2(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		//int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为2的录音开始 */
		while (VgetRecordCount2 < vQcDCount2) {
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "2",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 61 and 120 " + vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
				vMessage="数据缺失";
				break;
			}
			// 直接将map删除了
			// map.clear();

			//myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 61 and 120 "
					+ vSqlTemp + ") bb limit 0,1";// into
			// vReservedthree;
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
	
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);
			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','2',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ VgetRecordCount1
					+ ","
					+ (VgetRecordCount2 + 1)
					+ ","
					+ VgetRecordCount3
					+ ","
					+ VgetRecordCount4
					+ ","
					+ VgetRecordCount5
					+ ","
					+ VgetRecordCount6
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ VgetAllRCount1
					+ ","
					+ (VgetAllRCount2 + 1)
					+ ","
					+ VgetAllRCount3
					+ ","
					+ VgetAllRCount4
					+ ","
					+ VgetAllRCount5
					+ ","
					+ VgetAllRCount6
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			taskDao.execSql(map);
			VgetRecordCount2 = VgetRecordCount2 + 1;
			VgetAllRCount2 = VgetAllRCount2 + 1;
		}
		;
	}

	/**
	 * 一键领取获取第三档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy3(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		//int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为3的录音开始 */
		while (VgetRecordCount3 < vQcDCount3) {
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "3",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 121 and 180 " + vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
				vMessage="数据缺失";
				break;
			}
			// 直接将map删除了
			// map.clear();

			//myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 121 and 180 "
					+ vSqlTemp + ") bb limit 0,1";// into
			// vReservedthree;
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);
			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','3',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ VgetRecordCount1
					+ ","
					+ VgetRecordCount2
					+ ","
					+ (VgetRecordCount3 + 1)
					+ ","
					+ VgetRecordCount4
					+ ","
					+ VgetRecordCount5
					+ ","
					+ VgetRecordCount6
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ VgetAllRCount1
					+ ","
					+ VgetAllRCount2
					+ ","
					+ (VgetAllRCount3 + 1)
					+ ","
					+ VgetAllRCount4
					+ ","
					+ VgetAllRCount5
					+ ","
					+ VgetAllRCount6
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			taskDao.execSql(map);
			VgetRecordCount3 = VgetRecordCount3 + 1;
			VgetAllRCount3 = VgetAllRCount3 + 1;
		}
		;
	}

	/**
	 * 一键领取获取第四档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy4(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		//int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为4的录音开始 */
		while (VgetRecordCount4 < vQcDCount4) {
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "4",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 181 and 300 " + vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
				vMessage="数据缺失";
				break;
			}
			// 直接将map删除了
			// map.clear();

			//myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 181 and 300 "
					+ vSqlTemp + ") bb limit 0,1";// into
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);
			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','4',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ VgetRecordCount1
					+ ","
					+ VgetRecordCount2
					+ ","
					+ VgetRecordCount3
					+ ","
					+ (VgetRecordCount4 + 1)
					+ ","
					+ VgetRecordCount5
					+ ","
					+ VgetRecordCount6
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ VgetAllRCount1
					+ ","
					+ VgetAllRCount2
					+ ","
					+ VgetAllRCount3
					+ ","
					+ (VgetAllRCount4 + 1)
					+ ","
					+ VgetAllRCount5
					+ ","
					+ VgetAllRCount6
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			taskDao.execSql(map);
			VgetRecordCount4 = VgetRecordCount4 + 1;
			VgetAllRCount4 = VgetAllRCount4 + 1;
		}
		;
	}

	/**
	 * 一键领取获取第五档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy5(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		//int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为5的录音开始 */
		while (VgetRecordCount5 < vQcDCount5) {
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "5",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 301 and 600 " + vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
				vMessage="数据缺失";
				break;
			}
			// 直接将map删除了
			// map.clear();

			//myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 301 and 600 "
					+ vSqlTemp + ") bb limit 0,1";// into
			// vReservedthree;
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);
			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','5',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ VgetRecordCount1
					+ ","
					+ VgetRecordCount2
					+ ","
					+ VgetRecordCount3
					+ ","
					+ VgetRecordCount4
					+ ","
					+ (VgetRecordCount5 + 1)
					+ ","
					+ VgetRecordCount6
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ VgetAllRCount1
					+ ","
					+ VgetAllRCount2
					+ ","
					+ VgetAllRCount3
					+ ","
					+ VgetAllRCount4
					+ ","
					+ (VgetAllRCount5 + 1)
					+ ","
					+ VgetAllRCount6
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			taskDao.execSql(map);
			VgetRecordCount5 = VgetRecordCount5 + 1;
			VgetAllRCount5 = VgetAllRCount5 + 1;
		}
		;
	}

	/**
	 * 一键领取获取第六档的录音
	 * 
	 * @throws Exception
	 * **/
	public void updateoneKeyGetRecordBy6(String iTaskDate, Task taskList,
			String vStrSql, String iTaskUserId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vSqlTemp = "";
		int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		/* 一键领取时间档为6的录音开始 */
		while (VgetRecordCount6 < vQcDCount6) {
			vSqlTemp = Util
					.sp_1w_onekeysqladd(iTaskDate, taskList.getTaskId(), "6",
							taskList.getCsrTopDCount(), taskList
									.getCsrTopWCount());
			// 重新拼接SQL，过滤数据
			String recordsqlcount1 = "select count(1) " + vStrSql
					+ " and tri.RECORD_LENGTH between 601 and 360000 "
					+ vSqlTemp;
			map.put("recordsqlcount", recordsqlcount1);
			int vRecordCount = taskDao.recordsqlcount(map);
			// 如果没有符合条件的录音
			if (vRecordCount == 0) {
				vMessage="数据缺失";
				break;
			}
			// 直接将map删除了
			// map.clear();

			myRandomNum = random.nextInt(vRecordCount + 1)+1; // 随机函数
			String getreservedthree1 = "select bb.RESERVED_THREE from (select tri.RESERVED_THREE "
					+ vStrSql
					+ " and tri.RECORD_LENGTH between 601 and 360000 "
					+ vSqlTemp + ") bb limit 0," + myRandomNum;// into
			// vReservedthree;
			map.put("getreservedthree1", getreservedthree1);
			String vReservedthree = taskDao.getreservedthree1(map);
			// 开始更改相关数据.....
			/* 再次过滤数据 */
			String queryTaskRecord1 = "select count(*)  from t_qc_taskrecord qt where qt.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("queryTaskRecord1", queryTaskRecord1);
			int vTemp = taskDao.queryTaskRecord1(map);
			if (vTemp >= 1) {
				continue;
			}
			/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
			String updaterecordInfo = "update t_record_info ri set ri.IS_TASK='1' where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("execSql", updaterecordInfo);
			taskDao.execSql(map);
			/*** //增加数据：t_qc_taskrecord ***/
			String selectTaskRecord = "select tsu.USER_ID as BIG_WORKID,ri.RESERVED_THREE,RECORD_ID from t_record_info ri inner join t_sys_user tsu on tsu.WORK_ID=ri.BIG_WORKID where ri.RESERVED_THREE='"
					+ vReservedthree + "'";
			map.put("selectTaskRecord", selectTaskRecord);
			RecordInfo rdinfo = taskDao.selectTaskRecord(map);
			String inserttr1 = "insert into T_QC_TASKRECORD(TASKUSER_ID,ISQC,RECORDLENGTH,WHICHDATEFORTASK,CREATE_DATE,TEL_USER,RESERVED_THREE,RECORD_ID,TASK_ID) values"
					+ "('"
					+ iTaskUserId
					+ "','0','6',DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d'),now(),'"
					+ rdinfo.getBigWorkid()
					+ "','"
					+ rdinfo.getReservedThree()
					+ "',"
					+ rdinfo.getRecordId()
					+ ",'"
					+ taskList.getTaskId()
					+ "')";
			map.put("execSql", inserttr1);
			taskDao.execSql(map);

			/*** //修改数据：t_qc_taskqcuser_complete(领取数) ***/
			String updateqcSql = "update t_qc_taskqcuser_complete t set t.get_record_count='"
					+ VgetRecordCount1
					+ ","
					+ VgetRecordCount2
					+ ","
					+ VgetRecordCount3
					+ ","
					+ VgetRecordCount4
					+ ","
					+ VgetRecordCount5
					+ ","
					+ (VgetRecordCount6 + 1)
					+ "' where t.taskuser_id='"
					+ iTaskUserId
					+ "' and t.cdate= DATE_FORMAT('"
					+ iTaskDate
					+ "','%Y-%m-%d')";
			map.put("execSql", updateqcSql);
			taskDao.execSql(map);
			/*** //更改数据：t_qc_task_qcuser(领取数，状态) ***/
			String utqtqSql = "update t_qc_task_qcuser tq set tq.get_record_count='"
					+ VgetAllRCount1
					+ ","
					+ VgetAllRCount2
					+ ","
					+ VgetAllRCount3
					+ ","
					+ VgetAllRCount4
					+ ","
					+ VgetAllRCount5
					+ ","
					+ (VgetAllRCount6 + 1)
					+ "',tq.each_qcuser_status='2'"
					+ "where tq.taskuser_id="
					+ iTaskUserId;
			map.put("execSql", utqtqSql);
			taskDao.execSql(map);
			/*** //更新数据：t_qc_task(任务状态) ***/
			String tqtStatusSql = "UPDATE T_QC_TASK TQT SET TQT.TASK_STATUS = '2' WHERE TQT.TASK_ID = "
					+ taskList.getTaskId();
			map.put("execSql", tqtStatusSql);
			taskDao.execSql(map);
			VgetRecordCount6 = VgetRecordCount6 + 1;
			VgetAllRCount6 = VgetAllRCount6 + 1;
		}
		;
	}

	public String updateRecord(Map<String, String> map){
		Task taskInfo = taskDao.getOneTaskInfo(Integer.parseInt(map
				.get("taskId")));
		int standardCount = 0;// 规范值
		int count = 0;// 实际查出来的值
		String message = "通过";

		// 验证1：每个CSR每天最高被质检次数是否达到饱和
		standardCount = Integer.parseInt(taskInfo.getCsrTopDCount());// 每个CSR每天最高被质检次数
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("taskId", map.get("taskId"));
		map1.put("taskTime", map.get("taskTime"));
		map1.put("telUser", map.get("telUser"));
		count = taskDao.getRecordVerification(map1);
		if (count >= standardCount) {
			message = "该受理员(" + map.get("telUser") + ")当天("
					+ map.get("taskTime") + ")已达到最高被领取次数，请选择其他受理员";
			return message;
		}

		// 验证2：每个CSR每周最高被质检次数是否达到饱和
		String csrTopWCountAll = taskInfo.getCsrTopWCount();// 每个CSR每周最高被质检次数(六个挡，中间逗号隔开)
		standardCount = Integer.parseInt(csrTopWCountAll.split(",")[Integer
				.parseInt(map.get("recordLength").toString()) - 1].toString());
		String taskTime = map.get("taskTime");
		String taskTimeWBegin = taskTime;
		String taskTimeWEnd = taskTime;
		try {
			Date date = new SimpleDateFormat("yy-MM-dd").parse(taskTime);
			int temp = date.getDay();
			if (temp == 0) {
				temp = 7;
			}
			int before = temp - 1;
			int after = 7 - temp;
			// 获取本周开始时间
			for (int i = 0; i < before; i++) {
				taskTimeWBegin = Util.getSpecifiedDayBefore(taskTimeWBegin);
			}
			// 获取本周结束时间
			for (int i = 0; i < after; i++) {
				taskTimeWEnd = Util.getSpecifiedDayAfter(taskTimeWEnd);
			}
		} catch (Exception e) {
			log.error(e);
		}
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("taskId", map.get("taskId"));
		map2.put("telUser", map.get("telUser"));
		map2.put("taskTimeWBegin", taskTimeWBegin);
		map2.put("taskTimeWEnd", taskTimeWEnd);
		map2.put("recordLength", map.get("recordLength"));
		count = 0;
		count = taskDao.getRecordVerification(map2);
		if (count >= standardCount) {
			message = "该受理员(" + map.get("telUser")
					+ ")本周该录音时间档已达到最高被领取次数，请选择其他时间档";
			return message;
		}

		// 验证3：每个质检员每天质检任务规格中该录音时间档是否达到饱和
		String qcDCountAll = taskInfo.getQcDCount();// 每个质检员每天质检任务规格(六个挡，中间逗号隔开)
		standardCount = Integer.parseInt(qcDCountAll.split(",")[Integer
				.parseInt(map.get("recordLength").toString()) - 1].toString());
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("taskId", map.get("taskId"));
		map3.put("taskTime", map.get("taskTime"));
		map3.put("userID", map.get("userID"));
		map3.put("recordLength", map.get("recordLength"));
		count = 0;
		count = taskDao.getRecordVerification(map3);
		if (count >= standardCount) {
			message = "你该天(" + map.get("taskTime") + ")该录音时间档规格已经完成，请选择其他时间档";
			return message;
		}

		// 验证5：验证此录音是否已被领取和领取
		if (taskDao.earlierByGet(map.get("reservedThree")) <= 0) {
			message = "对不起，该条录音刚刚已经被其他质检员提前领取，请选择其他录音";
			return message;
		}
		if (taskDao.earlierByQc(map.get("reservedThree")) > 0) {
			message = "对不起，该条录音刚刚已经被其他质检员提前评分，请选择其他录音";
			return message;
		}

		// 全部验证通过，开始操作数据
		/*** //增加数据：t_qc_taskrecord ***/
		Task record = new Task();
		record.setQcUser(map.get("userID"));
		record.setTaskuserId(Integer.parseInt(map.get("taskUserId")));
		record.setIsQc("0");
		record.setRecordLengthPart(map.get("recordLength"));
		record.setcDate(map.get("taskTime"));
		record.setTelUser(map.get("telUser"));
		record.setRecordId(map.get("recordId"));
		record.setCallId(map.get("reservedThree"));
		record.setTaskId(Integer.parseInt(map.get("taskId")));
		record.setTasktype("5");
		taskDao.insertToTaskrecord(record);
		/*** //修改数据：t_record_info(录音的任务状态，是，否) ***/
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("taskFlag", "1");
		map5.put("reservedthree", map.get("reservedThree"));
		taskDao.updateRecordTaskFlag(map5);
		/*** //修改数据：t_qc_taskqcuser_complete(领取数，状态) ***/
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("taskQcUserId", map.get("taskUserId"));
		map6.put("startTime", map.get("taskTime"));
		map6.put("ednTime", map.get("taskTime"));
		Task taskInfoItem = taskDao.queryTaskCompleteInforc(map6).get(0);
		String getRecords = taskInfoItem.getGetRecordCount();
		taskInfoItem.setGetRecordCountItem(getRecords.split(","));
		int countTemp = Integer
				.parseInt((taskInfoItem.getGetRecordCountItem())[Integer
						.parseInt(map.get("recordLength").toString()) - 1]);
		countTemp++;
		(taskInfoItem.getGetRecordCountItem())[Integer.parseInt(map.get(
				"recordLength").toString()) - 1] = String.valueOf(countTemp);
		String getRecordCount = "";
		for (int i = 0; i < taskInfoItem.getGetRecordCountItem().length; i++) {
			getRecordCount = getRecordCount
					+ taskInfoItem.getGetRecordCountItem()[i] + ",";
		}
		getRecordCount = getRecordCount.substring(0,
				getRecordCount.length() - 1);

		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("taskQcUserId", map.get("taskUserId"));
		map7.put("taskTime", map.get("taskTime"));
		map7.put("getRecordCount", getRecordCount);
		map7.put("taskType", "5");
		taskDao.updateTaskRecordComplete(map7);
		/*** //更改数据：t_qc_task_qcuser(任务状态) ***/
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("qcUser", map.get("userID"));
		map8.put("taskId", map.get("taskId"));
		taskInfoItem = (Task) taskDao.queryTaskCompleteInfo(map8).get(0);
		getRecords = taskInfoItem.getGetRecordCount();
		taskInfoItem.setGetRecordCountItem(getRecords.split(","));
		countTemp = Integer
				.parseInt((taskInfoItem.getGetRecordCountItem())[Integer
						.parseInt(map.get("recordLength").toString()) - 1]);
		countTemp++;
		(taskInfoItem.getGetRecordCountItem())[Integer.parseInt(map.get(
				"recordLength").toString()) - 1] = String.valueOf(countTemp);
		getRecordCount = "";
		for (int i = 0; i < taskInfoItem.getGetRecordCountItem().length; i++) {
			getRecordCount = getRecordCount
					+ taskInfoItem.getGetRecordCountItem()[i] + ",";
		}
		getRecordCount = getRecordCount.substring(0,
				getRecordCount.length() - 1);
		map8.put("getRecordCount", getRecordCount);
		map8.put("eachQcuserStatus", "2");// 质检员任务状态：进行中
		map8.put("taskType", "5");
		taskDao.updateTaskQcUserInfo(map8);
		/*** //更新数据：t_qc_task(任务状态) ***/
		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("taskStatus", "2");// 更改任务状态为执行中
		map9.put("taskId", map.get("taskId"));
		taskDao.updateTaskMainStatus(map9);

		return message;
	}

	/**
	 * 查询任务录音
	 * 
	 * @return
	 */
	public List<AgentCallLog> queryRecordList(Map<String, String> map) {
		List<AgentCallLog> list = taskDao.queryRecordList(map);
		return list;
	}

	/**
	 * 查询任务录音记录数
	 * **/
	public int queryRecordListCount(Map<String, String> mapdata) {
		return taskDao.queryRecordListCount(mapdata);
	}

	/**
	 * 任务录音评分时，修改任务相关表的状态
	 * **/

	public String updateTaskRecInfo(Map<String, String> map) {
		if (map.get("taskId") != null) {
			Map<String, String> map0 = new HashMap<String, String>();
			map0.put("reservedThree", map.get("callId"));
			int rowCount = taskDao.queryRecordListCnt(map0);
			if (rowCount != 0) {// 避免正在评分时候，任务录音被释放
				if ("Qc".equals(map.get("QcOrDel"))) {
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("isQc", map.get("isQc"));
					map1.put("qcDate", "temp");
					map1.put("callId", map.get("callId"));
					taskDao.updateTaskRecordInfo(map1);// 更新当前录音的状态 （是否已质检）
					// 更改当天任务的完成数，当天的任务状态 t_qc_taskqcuser_complete
					map1.clear();
					map1.put("taskQcUserId", map.get("taskuserId"));
					map1.put("startTime", map.get("cDate"));
					map1.put("ednTime", map.get("cDate"));
					map1.put("taskType", "5");
					Task taskInfoItem = taskDao.queryTaskCompleteInforc(map1)
							.get(0);
					String completeCount = taskInfoItem.getCompleteCount();
					taskInfoItem.setCompleteCountItem(completeCount.split(","));
					int countTemp = Integer.parseInt((taskInfoItem
							.getCompleteCountItem())[Integer.parseInt(map.get(
							"recordLengthPart").toString()) - 1]);
					countTemp++;
					(taskInfoItem.getCompleteCountItem())[Integer.parseInt(map
							.get("recordLengthPart").toString()) - 1] = String
							.valueOf(countTemp);
					completeCount = "";
					for (int i = 0; i < taskInfoItem.getCompleteCountItem().length; i++) {
						completeCount = completeCount
								+ taskInfoItem.getCompleteCountItem()[i] + ",";
					}
					completeCount = completeCount.substring(0, completeCount
							.length() - 1);// 拼接后，为该天最新的完成数
					String taskStatus = "";
					if (completeCount.equals(map.get("qcDCount"))) {
						taskStatus = "1";// 完成情况信息：该天的状态为已完成
					} else {
						taskStatus = "2";// 完成情况信息：该天的状态为未完成
					}
					map1.clear();
					map1.put("taskQcUserId", map.get("taskuserId"));
					map1.put("taskTime", map.get("cDate"));
					map1.put("completeCount", completeCount);
					map1.put("taskStatus", taskStatus);
					map1.put("taskType", "5");
					taskDao.updateTaskRecordComplete(map1);
					map.put("taskType", "5");
					updateTaskQcUserInfo(map);
				} else if ("Del".equals(map.get("QcOrDel"))) {
					// 删除打分时，更新任务录音的逻辑
					map.put("isQc", "0");
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("isQc", map.get("isQc"));
					map1.put("qcDate", null);
					map1.put("callId", map.get("callId"));
					taskDao.updateTaskRecordInfo(map1);// 更新当前录音的状态
					// 更改当天任务的完成数，当天的任务状态 t_qc_taskqcuser_complete
					map1.clear();
					map1.put("taskQcUserId", map.get("taskuserId"));
					map1.put("startTime", map.get("cDate"));
					map1.put("ednTime", map.get("cDate"));
					map1.put("taskType", "5");
					Task taskInfoItem = taskDao.queryTaskCompleteInforc(map1)
							.get(0);
					String completeCount = taskInfoItem.getCompleteCount();
					taskInfoItem.setCompleteCountItem(completeCount.split(","));
					int countTemp = Integer.parseInt((taskInfoItem
							.getCompleteCountItem())[Integer.parseInt(map.get(
							"recordLengthPart").toString()) - 1]);
					countTemp--;
					(taskInfoItem.getCompleteCountItem())[Integer.parseInt(map
							.get("recordLengthPart").toString()) - 1] = String
							.valueOf(countTemp);
					completeCount = "";
					for (int i = 0; i < taskInfoItem.getCompleteCountItem().length; i++) {
						completeCount = completeCount
								+ taskInfoItem.getCompleteCountItem()[i] + ",";
					}
					completeCount = completeCount.substring(0, completeCount
							.length() - 1);// 拼接后，为该天最新的完成数

					String taskStatus = "2";// 完成情况信息：该天的状态为未完成
					map1.clear();
					map1.put("taskQcUserId", map.get("taskuserId"));
					map1.put("taskTime", map.get("cDate"));
					map1.put("completeCount", completeCount);
					map1.put("taskStatus", taskStatus);
					map1.put("taskType", "5");
					taskDao.updateTaskRecordComplete(map1);
					map.put("taskType", "5");
					updateTaskQcUserInfo(map);
				}
			}
		}
		return "";
	}

	/**
	 * 更新任务相关信息（T_QC_TASKQCUSER 如：状态，完成数...）
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@Override
	public String updateTaskQcUserInfo(Map<String, String> map) {
		Map<String, String> map0 = new HashMap<String, String>();
		map0.put("reservedThree", map.get("callId"));
		int rowCount = taskDao.queryRecordListCnt(map0);
		if (rowCount != 0) {// 避免正在评分时候，任务录音被释放
			if ("Qc".equals(map.get("QcOrDel"))) {
				//  用来查找和修改T_QC_TASK 状态
				Map<String, String> taskQcMap = new HashMap<String, String>();
				taskQcMap.put("taskId", map.get("taskId"));
				taskQcMap.put("eachQcuserStatus", "1,2,4");
				Map<String, String> taskMap = new HashMap<String, String>();
				taskMap.put("taskId", map.get("taskId"));
				taskMap.put("qcUser", map.get("qcUser"));
				taskMap.put("isQc", "0");
				SimpleDateFormat dateformat1 = new SimpleDateFormat(
						"yyyy-MM-dd");
				String today = dateformat1.format(new Date());
				String taskEndTime = map.get("taskEndTime").substring(0, 10);
				Date todayD = null;
				Date taskEndTimeD = null;
				try {
					todayD = dateformat1.parse(today);
					taskEndTimeD = dateformat1.parse(taskEndTime);
				} catch (Exception e) {
					log.error(e);
				}
				// 查询
				List<Task> list = null;
				Map<String, String> taskQcUserMap = new HashMap<String, String>();
				taskQcUserMap.put("taskId", map.get("taskId"));
				taskQcUserMap.put("qcUser", map.get("qcUser"));
				taskQcUserMap.put("unStatus1", "0");
				taskQcUserMap.put("unStatus2", "5");

				Map<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("taskQcUserId", map.get("taskuserId"));
				mapTemp.put("eachDateStatus", "2");// mapTemp.put("eachDateStatus",
				// "1,3");
				if (!"".equals(taskQcUserMap.get("taskId"))
						&& !"".equals(taskQcUserMap.get("qcUser"))
						) {

					// 任务完成数T_QC_TASK_QCUSER
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("qcUser", map.get("qcUser"));
					map1.put("taskId", map.get("taskId"));
					Task taskInfoItem = taskDao.queryTaskCompleteInfo(map1)
							.get(0);
					String completeCount = taskInfoItem.getCompleteCount();
					String[] completeCountItem = completeCount.split(",");
					int countTemp = Integer
							.parseInt(completeCountItem[Integer.parseInt(map
									.get("recordLengthPart").toString()) - 1]);
					countTemp++;
					completeCountItem[Integer.parseInt(map.get(
							"recordLengthPart").toString()) - 1] = String
							.valueOf(countTemp);
					completeCount = "";
					for (int i = 0; i < completeCountItem.length; i++) {
						completeCount = completeCount + completeCountItem[i]
								+ ",";
					}
					completeCount = completeCount.substring(0, completeCount
							.length() - 1);// 拼接后，为该天最新的完成数
					taskQcUserMap.put("completeCount", completeCount);

					if (today.equals(taskEndTime)) {
						// 任务结束当天，如果任务完成详细表里面，某质检员每天的任务状态都为已完成，更新任务质检员相关表中的某任务某质检员的任务状态为'3已完成'
						mapTemp.put("taskType", "5");
						list = taskDao.queryTaskCompleteInforc(mapTemp);
						if (list.size() == 0) {
							taskQcUserMap.put("eachQcuserStatus", "3");
							taskQcUserMap.put("taskType", "5");
							taskDao.updateTaskQcUserInfo(taskQcUserMap);
						} else {
							taskQcUserMap.put("eachQcuserStatus", "2");
							taskQcUserMap.put("taskType", "5");
							taskDao.updateTaskQcUserInfo(taskQcUserMap);
						}

						// lfj 2012-08-14 add
						// 任务结束当天，如果该任务下的所有质检员完成状态都为已完成，更新任务质检员相关表中的某任务某的任务状态为'3已完成'
						list = taskDao.queryTaskCompleteInfo(taskQcMap);
						if (list.size() == 0) {
							taskQcMap.put("taskStatus", "3");
							taskDao.updateTaskInfo(taskQcMap);
						} else {
							taskQcMap.put("taskStatus", "2");
							taskDao.updateTaskInfo(taskQcMap);
						}

					} else if (todayD != null && taskEndTimeD != null
							&& todayD.after(taskEndTimeD)) {
						// 任务结束后某天，如果任务录音表中某任务某质检员的录音都质检，更新任务质检员相关表中的某任务某质检员的任务状态为'3已完成'，否则'4未完成'
						mapTemp.put("taskType", "5");
						list = taskDao.queryTaskCompleteInforc(mapTemp);
						if (list.size() == 0) {
							taskQcUserMap.put("eachQcuserStatus", "3");
							taskQcUserMap.put("taskType", "5");
							taskDao.updateTaskQcUserInfo(taskQcUserMap);
						} else {
							taskQcUserMap.put("eachQcuserStatus", "4");
							taskQcUserMap.put("taskType", "5");
							taskDao.updateTaskQcUserInfo(taskQcUserMap);
						}
						// 任务结束后某天，无论怎样，该任务始终为'未完成'
						taskQcMap.put("taskStatus", "4");
						taskDao.updateTaskInfo(taskQcMap);

					} else if (todayD != null && taskEndTimeD != null
							&& todayD.before(taskEndTimeD)) {
						// 离任务结束多于1天的任务，更新其状态为'2执行中'
						taskQcUserMap.put("eachQcuserStatus", "2");
						taskQcUserMap.put("taskType", "5");
						taskDao.updateTaskQcUserInfo(taskQcUserMap);
						// 离任务结束多于1天的任务，更新其状态为'2执行中'
						taskQcMap.put("taskStatus", "2");
						taskDao.updateTaskInfo(taskQcMap);

					}
				}
			} else if ("Del".equals(map.get("QcOrDel"))) {
				// 更新质检员任务相关表完成数和完成状态
				String aCompleteCount = map.get("aCompleteCount");
				String[] aCompleteCountItem = new String[6];
				aCompleteCountItem = aCompleteCount.split(",");
				aCompleteCountItem[Integer
						.parseInt(map.get("recordLengthPart")) - 1] = String
						.valueOf((Integer.parseInt(aCompleteCountItem[Integer
								.parseInt(map.get("recordLengthPart")) - 1]) - 1));
				aCompleteCount = "";
				for (int i = 0; i < aCompleteCountItem.length; i++) {
					aCompleteCount = aCompleteCount + aCompleteCountItem[i]
							+ ",";
				}
				aCompleteCount = aCompleteCount.substring(0, aCompleteCount
						.length() - 1);
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("completeCount", aCompleteCount);
				if ("3".equals(map.get("eachQcuerStatus"))) {
					map1.put("eachQcuserStatus", "4");
				}
				map1.put("taskId", map.get("taskId"));
				map1.put("qcUser", map.get("qcUser"));
				map1.put("taskType", "5");
				taskDao.updateTaskQcUserInfo(map1);
				// 更新总任务的状态
				if ("3".equals(map.get("eachQcuerStatus"))) {
					Map<String, String> taskQcMap = new HashMap<String, String>();
					taskQcMap.put("taskId", map.get("taskId"));
					taskQcMap.put("taskStatus", "4");
					taskDao.updateTaskInfo(taskQcMap);
				}
			}
			return "1";
		}
		return "0";
	}

	/**
	 * 任务录音评分时 ，查询该录音相关的信息
	 * 
	 * @author niewenqiang
	 * **/
	public Task getRecordTask(int recordId) {
		return taskDao.getRecordTask(recordId);
	}
	
	
	/**
	 *  判断任务名称是否存在
	 * **/
	public int checkTaskName(Map<String, String> mapdata){
		return taskDao.checkTaskName(mapdata);
	}

	@Override
	public List<Task> gettask112Order(Task task) {
		return taskDao.gettask112Order(task);
	}

	@Override
	public int gettask112Ordercount(Task task) {
		return taskDao.gettask112Ordercount(task);
	}

	
	@Override
	public List<Task> gettaskcwOrder(Task task) {
		return taskDao.gettaskcwOrder(task);
	}

	@Override
	public int gettaskcwOrdercount(Task task) {
		return taskDao.gettaskcwOrdercount(task);
	}
	
	
	
	@Override
	public int add112orderTask(Task task){	
			taskDao.add112orderTask(task);
			String taskStartTime =task.getTaskStartTime();
			int taskid=task.getTaskId();
			int countDay = 0;// 任务天数（开始到结束）
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			countDay = DateUtil.getDays(formatter.parse(task.getTaskStartTime()
					.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
					.substring(0, 10), pos1));
			String quserid=task.getQcUser();
			String[] qcuserid=quserid.split(",");
			task.setTaskId(task.getTaskId());
			if("1".equals(task.getIsPublish())){
			for (String string : qcuserid) {
				task=new Task();
				task.setTaskId(taskid);
				task.setGetRecordCount("0");
				task.setCompleteCount("0");
				task.setEachQcuserStatus("1");	
				task.setTasktype("1");
				task.setQcUser(string);
				taskDao.insert112ordertaskUser(task);	
				int userid=task.getTaskuserId();
				String today = taskStartTime.substring(0, 10);// 哪一天的任务
				for (int j = 0; j < countDay + 1; j++) {		
				task=new Task();
				task.setTaskId(taskid);
				task.setGetRecordCount("0");
				task.setCompleteCount("0");
				task.setTaskuserId(userid);
				task.setcDate(today);
				task.setTasktype("1");
				taskDao.insertToTask112orderuserComplete(task);
				today = Util.getSpecifiedDayAfter(today);
				}			
			}
			}
		return 1;
				
	}

	@Override
	public int del112orderTask(Map<String, String> mapdata) {	
		if (!("0".equals(mapdata.get("taskStatus")) || "1".equals(mapdata
				.get("taskStatus")))) {
			Map<String, String> map1 = new HashMap<String, String>();
			mapdata.put("taskType", "1");
			map1.put("taskId", mapdata.get("taskId"));
			// 1：修改112工单任务状态(录音的任务状态，是，否)
			taskDao.release112Order(map1);
			// 2：t_qc_taskrecord删除任务工单信息
			taskDao.release112orderInfo(map1);
		} else {
		}
		int taskId = Integer.parseInt(mapdata.get("taskId"));
		taskDao.del112orderTask(taskId);
		taskDao.delTaskQcuser(mapdata);
		taskDao.delTaskQcuserComplete(mapdata);
		return 1;
	}

	@Override
	public Task get112orderTaskbyid(Integer taskId) {
		return taskDao.get112orderTaskbyid(taskId);
	}

	@Override
	public Task getcworderTaskbyid(Integer taskId) {
		return taskDao.getcworderTaskbyid(taskId);
	}
	
	@Override
	public int check112Ordername(String taskName) {
		return taskDao.check112Ordername(taskName);
	}

	@Override
	public int upd112OrderTask(Task task) {	
		taskDao.upd112OrderTask(task);	
		int taskid=task.getTaskId();
		String taskStartTime =task.getTaskStartTime();
		int countDay = 0;// 任务天数（开始到结束）
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		countDay = DateUtil.getDays(formatter.parse(task.getTaskStartTime()
				.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
				.substring(0, 10), pos1));
		String workid=task.getQcUserWorkId();
		String[] qcworkid=workid.split(",");
		if("1".equals(task.getIsPublish())){
		for (String string : qcworkid) {
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setEachQcuserStatus("1");	
			task.setTasktype("1");
			String userids=userInfoDao.userIdbyworkId(string);
			task.setQcUser(userids);
			taskDao.insert112ordertaskUser(task);	
			int userid=task.getTaskuserId();
			String today = taskStartTime.substring(0, 10);// 哪一天的任务
			for (int j = 0; j < countDay + 1; j++) {		
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setTaskuserId(userid);
			task.setcDate(today);
			task.setTasktype("1");
			taskDao.insertToTask112orderuserComplete(task);
			today = Util.getSpecifiedDayAfter(today);
			}			
		}
		}
		return 1;
	}

	@Override
	public List<Task> getimptask112Order(Task task) {
		return taskDao.getimptask112Order(task);
	}

	@Override
	public int getimptask112Ordercount(Task task) {
		return taskDao.getimptask112Ordercount(task);
	}

	@Override
	public Set<String> getyl112ordertaskidList(Map<String, String> map) {
		return taskDao.getyl112ordertaskidList(map);
	}

	@Override
	public List<Task> get112ordertaskwcqkList(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkList(map);
	}

	@Override
	public int get112ordertaskwcqkcount(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkcount(map);
	}

	@Override
	public List<Task> get112ordertaskwcqkallList(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkallList(map);
	}

	@Override
	public int get112ordertaskwcqkallcount(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkallcount(map);
	}

	@Override
	public List<Task> get112ordertaskwcqkqcList(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkqcList(map);
	}

	@Override
	public int get112ordertaskwcqkqccount(Map<String, String> map) {
		return taskDao.get112ordertaskwcqkqccount(map);
	}

	@Override
	public List<Task> gettaskby112orderId(Map<String, String> map) {
		return taskDao.gettaskby112orderId(map);
	}

	@Override
	public int updateTaskorder(Map<String, String> map) {
		String taskId=map.get("taskId");
		String taskuserId=map.get("taskuserId");
		String cDate=map.get("cDate");
		if(cDate!=null&&!"".equals(cDate)){
			cDate=cDate.substring(0, 10);
		}
		String orderId=map.get("orderId");
		String sumtaskorder=map.get("sumtaskorder");
		String csrTopDCount=map.get("csrTopDCount");//每天任务数
		Map<String,String> taskordermap=new HashMap<String, String>();
		taskordermap.put("orderId", orderId);
		taskordermap.put("isQc", "1");
		taskordermap.put("type", "1");
		taskDao.updateTaskOrderInfo(taskordermap);
		Map<String,String> qcusermap=new HashMap<String, String>();
		qcusermap.put("taskId", taskId);
		qcusermap.put("taskuserId", taskuserId);	
		qcusermap.put("taskType", "1");
		List<Task> qcusertask =	taskDao.order112taskqcuserbyuserid(qcusermap); //获取任务员工中间表信息
		int linqusum=Integer.parseInt(qcusertask.get(0).getGetRecordCount());//领取的任务工单数
		int complecount=Integer.parseInt(qcusertask.get(0).getCompleteCount());//完成的任务工单数
		Map<String,String> taskusermap=new HashMap<String, String>();	
		taskusermap.put("taskuserId", taskuserId);  
		if(Integer.parseInt(sumtaskorder)>complecount+1){
			taskusermap.put("eachQcuserStatus", "2");
			taskusermap.put("completeCount", (complecount+1)+"");
		}else{
			taskusermap.put("eachQcuserStatus", "3");
			taskusermap.put("completeCount", (complecount+1)+"");
			Map<String,String> taskmap=new HashMap<String, String>();//更新主表状态
			taskmap.put("taskStatus", "3");
			taskmap.put("taskId", taskId);
			taskmap.put("taskType", "3");
			taskDao.update112orderTaskInfo(taskmap);
		}
		taskusermap.put("taskType", "1");
		taskDao.updateTaskQcUserInfobytaskId(taskusermap);//修改任务员工中间表
		
		Map<String,String> getordermap=new HashMap<String, String>();
		getordermap.put("taskuserId", taskuserId);
		getordermap.put("cDate", cDate);
		String getordercount=taskDao.getcomplecount(getordermap); //获取当天完成数
		
		Map<String,String> completask=new HashMap<String, String>();
		if(Integer.parseInt(csrTopDCount)>(Integer.parseInt(getordercount)+1)){
			completask.put("eachDateStatus", "2");
			completask.put("completeCount", (Integer.parseInt(getordercount)+1)+"");
		}else{
			completask.put("eachDateStatus", "1");
			completask.put("completeCount",(Integer.parseInt(getordercount)+1)+"");
		}	
		completask.put("taskuserId", taskuserId);
		completask.put("cDate", cDate);
		completask.put("taskType", "1");
		taskDao.update112orderTaskcomplete(completask);	
		return 1;
	}

	
	public int updatecwTaskorder(Map<String, String> map) {
		String taskId=map.get("taskId");
		String taskuserId=map.get("taskuserId");
		String cDate=map.get("cDate");
		if(cDate!=null&&!"".equals(cDate)){
			cDate=cDate.substring(0, 10);
		}
		String orderId=map.get("orderId");
		String sumtaskorder=map.get("sumtaskorder");
		String csrTopDCount=map.get("csrTopDCount");//每天任务数
		Map<String,String> taskordermap=new HashMap<String, String>();
		taskordermap.put("orderId", orderId);
		taskordermap.put("isQc", "1");
		taskordermap.put("type", "2");	
		taskDao.updateTaskOrderInfo(taskordermap);
		Map<String,String> qcusermap=new HashMap<String, String>();
		qcusermap.put("taskId", taskId);
		qcusermap.put("taskuserId", taskuserId);
		qcusermap.put("taskType", "2");
		List<Task> qcusertask =	taskDao.order112taskqcuserbyuserid(qcusermap); //获取任务员工中间表信息
		int linqusum=Integer.parseInt(qcusertask.get(0).getGetRecordCount());//领取的任务工单数
		int complecount=Integer.parseInt(qcusertask.get(0).getCompleteCount());//完成的任务工单数
		Map<String,String> taskusermap=new HashMap<String, String>();	
		taskusermap.put("taskuserId", taskuserId);  
		if(Integer.parseInt(sumtaskorder)>complecount+1){
			taskusermap.put("eachQcuserStatus", "2");
			taskusermap.put("completeCount", (complecount+1)+"");
		}else{
			taskusermap.put("eachQcuserStatus", "3");
			taskusermap.put("completeCount", (complecount+1)+"");
			Map<String,String> taskmap=new HashMap<String, String>();//更新主表状态
			taskmap.put("taskStatus", "3");
			taskmap.put("taskId", taskId);
			taskDao.updatecworderTaskInfo(taskmap);
		}
		taskusermap.put("taskType", "2");
		taskDao.updateTaskQcUserInfobytaskId(taskusermap);//修改任务员工中间表
		
		Map<String,String> getordermap=new HashMap<String, String>();
		getordermap.put("taskuserId", taskuserId);
		getordermap.put("cDate", cDate);
		String getordercount=taskDao.getcomplecount(getordermap); //获取当天完成数
		
		Map<String,String> completask=new HashMap<String, String>();
		if(Integer.parseInt(csrTopDCount)>(Integer.parseInt(getordercount)+1)){
			completask.put("eachDateStatus", "2");
			completask.put("completeCount", (Integer.parseInt(getordercount)+1)+"");
		}else{
			completask.put("eachDateStatus", "1");
			completask.put("completeCount",(Integer.parseInt(getordercount)+1)+"");
		}	
		completask.put("taskuserId", taskuserId);
		completask.put("cDate", cDate);
		completask.put("taskType", "2");	
		taskDao.update112orderTaskcomplete(completask);	
		return 1;
	}
	
	@Override
	public String updrelease112OrderByUser(Map<String, String> map) {
		String qcUserItem=map.get("qcUserItem");
		String taskId=map.get("taskId");
		String[] qcUser=qcUserItem.split(",");
		String	orderid="";
		Set<String> orderlist=new HashSet<String>();
		for (String string : qcUser) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", taskId);
			map1.put("qcUser", string);
			map1.put("ordertype", "1");
			// 1：修改112工单任务状态(录音的任务状态，是，否)
			taskDao.release112Order(map1);		
			Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
			ordermap.put("qcUser", string); 
			ordermap.put("taskId", taskId);
			ordermap.put("isqc", "0");
			ordermap.put("ordertype", "1");
			Map<String,String> ordercwmap2=new HashMap<String, String>();//查询任务领取工单表
			ordercwmap2.put("userid", string); 
			ordercwmap2.put("taskId", taskId);
			ordercwmap2.put("isqc", "0");
			ordercwmap2.put("ordertype", "1");	
			orderlist=taskDao.getyl112ordertaskidList(ordercwmap2);	
			for (String sorder : orderlist) {
				orderid=orderid+sorder+",";
			}	
			taskDao.releaseRecord(ordermap);
			Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
			qcusermap.put("taskId", taskId);
			qcusermap.put("qcUser", string);
			qcusermap.put("taskType", "1");
			List<Task> task=taskDao.order112taskqcuserbyuserid(qcusermap);
			Map<String,String> updqcusermap=new HashMap<String, String>();//用户修改任务员工中间表信息
			if("0".equals(task.get(0).getCompleteCount())){
				updqcusermap.put("eachQcuserStatus", "1");
			}
			updqcusermap.put("getRecordCount", task.get(0).getCompleteCount()+"");
			updqcusermap.put("taskuserId", task.get(0).getTaskuserId()+"");
			updqcusermap.put("taskType", "1");
			taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
			Map<String,String> complemap=new HashMap<String, String>();//查询任务员工详细表信息
			complemap.put("taskId", taskId);
			complemap.put("taskuserId", task.get(0).getTaskuserId()+"");
			complemap.put("tasktype", "1");
			List<Task> completask =taskDao.get112ordertaskwcqkqcList(complemap);
			for (Task task2 : completask) {
				Map<String,String> updcomplemap=new HashMap<String, String>();	
				updcomplemap.put("getRecordCount", task2.getCompleteCount()+"");
				updcomplemap.put("taskuserId", task.get(0).getTaskuserId()+"");
				updcomplemap.put("cDate", task2.getcDate());
				updcomplemap.put("taskType", "1");
				taskDao.update112orderTaskcomplete(updcomplemap);
			}
			
		}		
		if(!orderid.equals("")){
			orderid = orderid.substring(0, orderid.length()-1);
		}
			return orderid;
	}

	@Override
	public String updrelease112OrderBycDate(Map<String, String> map) {
		String cDateItem=map.get("cDateItem");
		String taskId=map.get("taskId");
		String taskUserId=map.get("taskUserId");
		String[] cDate=cDateItem.split(",");
		int sfcount=0;
		String orderId="";
		for (String cdate : cDate) {
			Map<String,String> complemap=new HashMap<String, String>();//用于查询任务员工中间表
			complemap.put("taskId", taskId);
			complemap.put("cDate", cdate);
			complemap.put("taskuserId", taskUserId);	
			List<Task> task=taskDao.get112ordertaskwcqkqcList(complemap);
			Map<String, String> map1 = new HashMap<String, String>();//修改112工单主表状态
			map1.put("cDate", cdate);
			map1.put("taskuserId", taskUserId);
			// 1：修改112工单任务状态(112工单的任务状态，是，否)
			taskDao.release112Order(map1);
			Map<String,String> updcomplemap=new HashMap<String, String>();	
			updcomplemap.put("getRecordCount", task.get(0).getCompleteCount()+"");
			updcomplemap.put("taskuserId", taskUserId);
			updcomplemap.put("cDate", cdate);
			updcomplemap.put("taskType", "1");
			taskDao.update112orderTaskcomplete(updcomplemap);//修改任务员工详细表
			Set<String> orderidList=new HashSet<String>();
			Map<String,String> orderidmap=new HashMap<String, String>();
			orderidmap.put("taskId", taskId);
			orderidmap.put("ctodate", cdate);
			orderidmap.put("isqc", "0");
			orderidmap.put("ordertype", "1");
			orderidmap.put("taskuserId", taskUserId);
			orderidList=taskDao.getyl112ordertaskidList(orderidmap);
			for (String string : orderidList) {
				orderId=orderId+string+",";	
			}
			sfcount=sfcount+(Integer.parseInt(task.get(0).getGetRecordCount())-Integer.parseInt(task.get(0).getCompleteCount()));//释放的任务数	
			Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
			//ordermap.put("qcUser", string); 
			ordermap.put("taskuserId", taskUserId);
			ordermap.put("cDate",cdate);
			taskDao.deltaskOrder(ordermap);//修改任务工单中间表				
		
		}	
		Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
		qcusermap.put("taskuserId", taskUserId);
		List<Task> usertask=taskDao.order112taskqcuserbyuserid(qcusermap);	
		Map<String,String> updqcusermap=new HashMap<String, String>();//用户修改任务员工中间表信息
		if(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount>Integer.parseInt(usertask.get(0).getCompleteCount())){
			updqcusermap.put("getRecordCount",(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount)+"");		
		}else{
			updqcusermap.put("getRecordCount",usertask.get(0).getCompleteCount()+"");
		}
		updqcusermap.put("taskuserId", usertask.get(0).getTaskuserId()+"");
		taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
		if(!orderId.equals("")){
			orderId=orderId.substring(0, orderId.length()-1);
		}
		return orderId;
	}

	@Override
	public int addcworderTask(Task task) {
		taskDao.addcworderTask(task);
		String taskStartTime =task.getTaskStartTime();
		int taskid=task.getTaskId();
		int countDay = 0;// 任务天数（开始到结束）
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		countDay = DateUtil.getDays(formatter.parse(task.getTaskStartTime()
				.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
				.substring(0, 10), pos1));
		String quserid=task.getQcUser();
		String[] qcuserid=quserid.split(",");
		task.setTaskId(task.getTaskId());
		if("1".equals(task.getIsPublish())){
		for (String string : qcuserid) {
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setEachQcuserStatus("1");	
			task.setTasktype("2");
			task.setQcUser(string);
			taskDao.insert112ordertaskUser(task);	
			int userid=task.getTaskuserId();
			String today = taskStartTime.substring(0, 10);// 哪一天的任务
			for (int j = 0; j < countDay + 1; j++) {		
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setTaskuserId(userid);
			task.setcDate(today);
			task.setTasktype("2");
			taskDao.insertToTask112orderuserComplete(task);
			today = Util.getSpecifiedDayAfter(today);
			}			
		}	
		}
	return 1;
	}
	
	
	@Override
	public int updcwOrderTask(Task task) {	
		Map<String,String> mapdata=new HashMap<String, String>();
		mapdata.put("taskId", ""+task.getTaskId());
		mapdata.put("taskType", "2");	
		taskDao.delTaskQcuser(mapdata);
		taskDao.delTaskQcuserComplete(mapdata);
		taskDao.updcwOrderTask(task);	
		int taskid=task.getTaskId();
		String taskStartTime =task.getTaskStartTime();
		int countDay = 0;// 任务天数（开始到结束）
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		countDay = DateUtil.getDays(formatter.parse(task.getTaskStartTime()
				.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
				.substring(0, 10), pos1));
		String workid=task.getQcUserWorkId();
		String[] qcworkid=workid.split(",");
		if("1".equals(task.getIsPublish())){
		for (String string : qcworkid) {
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setEachQcuserStatus("1");	
			task.setTasktype("2");
			String userids=userInfoDao.userIdbyworkId(string);
			task.setQcUser(userids);
			taskDao.insert112ordertaskUser(task);	
			int userid=task.getTaskuserId();
			String today = taskStartTime.substring(0, 10);// 哪一天的任务
			for (int j = 0; j < countDay + 1; j++) {		
			task=new Task();
			task.setTaskId(taskid);
			task.setGetRecordCount("0");
			task.setCompleteCount("0");
			task.setTaskuserId(userid);
			task.setcDate(today);
			task.setTasktype("2");
			taskDao.insertToTask112orderuserComplete(task);
			today = Util.getSpecifiedDayAfter(today);
			}			
		}
		}
		return 1;
	}

	@Override
	public int checkcwOrdername(String taskName) {
		return taskDao.checkcwOrdername(taskName);
	}

	@Override
	public int delcworderTask(Map<String, String> mapdata) {
		if (!("0".equals(mapdata.get("taskStatus")) || "1".equals(mapdata
				.get("taskStatus")))) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", mapdata.get("taskId"));
			mapdata.put("taskType", "2");
			// 1：修改112工单任务状态(录音的任务状态，是，否)
			taskDao.release112Order(map1);
			// 2：t_qc_taskrecord删除任务工单信息
			taskDao.releasecworderInfo(map1);
		} else {
		}
		int taskId = Integer.parseInt(mapdata.get("taskId"));
		taskDao.delcworderTask(taskId);
		taskDao.delTaskQcuser(mapdata);
		taskDao.delTaskQcuserComplete(mapdata);
		return 1;
	}

	@Override
	public List<Task> getimptaskcwOrder(Task task) {
		return taskDao.getimptaskcwOrder(task);
	}

	@Override
	public int getimptaskcwOrdercount(Task task) {
		return taskDao.getimptaskcwOrdercount(task);
	}

	@Override
	public Set<String> getyl112ordertaskList(Map<String,String> map) {
		return taskDao.getyl112ordertaskList(map);
	}

	@Override
	public List<Task> getcwordertaskwcqkList(Map<String, String> map) {
		return taskDao.getcwordertaskwcqkList(map);
	}

	@Override
	public int getcwordertaskwcqkcount(Map<String, String> map) {
		return taskDao.getcwordertaskwcqkcount(map);
	}

	@Override
	public List<Task> gettaskbycworderId(Map<String,String> map) {
		return taskDao.gettaskbycworderId(map);
	}
	
	
	@Override
	public String updreleasecwOrderByUser(Map<String, String> map) {
		String qcUserItem=map.get("qcUserItem");
		String taskId=map.get("taskId");
		String[] qcUser=qcUserItem.split(",");
		String	orderid="";
		Set<String> orderlist=new HashSet<String>();
		for (String string : qcUser) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", taskId);
			map1.put("qcUser", string);
			map1.put("ordertype", "2");
			// 1：修改c网工单任务状态(录音的任务状态，是，否)
			taskDao.releasecwOrder(map1);		
			Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
			ordermap.put("qcUser", string); 
			ordermap.put("taskId", taskId);
			ordermap.put("isqc", "0");
			ordermap.put("ordertype", "2");		
			Map<String,String> ordercwmap2=new HashMap<String, String>();//查询任务领取工单表
			ordercwmap2.put("userid", string); 
			ordercwmap2.put("taskId", taskId);
			ordercwmap2.put("isqc", "0");
			ordercwmap2.put("ordertype", "2");	
			orderlist=taskDao.getyl112ordertaskidList(ordercwmap2);	
			for (String sorder : orderlist) {
				orderid=orderid+sorder+",";
			}	
			taskDao.releaseRecord(ordermap);	
			Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
			qcusermap.put("taskId", taskId);
			qcusermap.put("qcUser", string);
			qcusermap.put("taskType", "2");
			List<Task> task=taskDao.order112taskqcuserbyuserid(qcusermap);
			Map<String,String> updqcusermap=new HashMap<String, String>();//用于修改任务员工中间表信息
			if("0".equals(task.get(0).getCompleteCount())){
				updqcusermap.put("eachQcuserStatus", "1");
			}
			updqcusermap.put("getRecordCount", task.get(0).getCompleteCount()+"");
			updqcusermap.put("taskuserId", task.get(0).getTaskuserId()+"");
			updqcusermap.put("taskType", "2");		
			taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
			Map<String,String> complemap=new HashMap<String, String>();//查询任务员工详细表信息
			complemap.put("taskId", taskId);
			complemap.put("taskuserId", task.get(0).getTaskuserId()+"");
			complemap.put("tasktype", "2");
			List<Task> completask =taskDao.getcwordertaskwcqkqcList(complemap);
			for (Task task2 : completask) {
				Map<String,String> updcomplemap=new HashMap<String, String>();	
				updcomplemap.put("getRecordCount", task2.getCompleteCount()+"");
				updcomplemap.put("taskuserId", task.get(0).getTaskuserId()+"");
				updcomplemap.put("cDate", task2.getcDate());
				updcomplemap.put("taskType", "2");
				taskDao.update112orderTaskcomplete(updcomplemap);
			}
			
		}
	if(!orderid.equals("")){
		orderid= orderid.substring(0, orderid.length()-1);
	}
		return orderid;
	}
	
	
	@Override
	public String updreleasecwOrderBycDate(Map<String, String> map) {
		String cDateItem=map.get("cDateItem");
		String taskId=map.get("taskId");
		String taskUserId=map.get("taskUserId");
		String[] cDate=cDateItem.split(",");
		int sfcount=0;
		String orderId="";
		for (String cdate : cDate) {
			Map<String,String> complemap=new HashMap<String, String>();//用于查询任务员工中间表
			complemap.put("taskId", taskId);
			complemap.put("cDate", cdate);
			complemap.put("taskuserId", taskUserId);	
			List<Task> task=taskDao.getcwordertaskwcqkqcList(complemap);
			Map<String, String> map1 = new HashMap<String, String>();//修改112工单主表状态
			map1.put("cDate", cdate);
			map1.put("taskuserId", taskUserId);
			// 1：修改112工单任务状态(112工单的任务状态，是，否)
			taskDao.releasecwOrder(map1);
			Map<String,String> updcomplemap=new HashMap<String, String>();	
			updcomplemap.put("getRecordCount", task.get(0).getCompleteCount()+"");
			updcomplemap.put("taskuserId", taskUserId);
			updcomplemap.put("cDate", cdate);
			updcomplemap.put("taskType", "2");
			taskDao.update112orderTaskcomplete(updcomplemap);//修改任务员工详细表
			Set<String> orderidList=new HashSet<String>();
			Map<String,String> orderidmap=new HashMap<String, String>();
			orderidmap.put("taskId", taskId);
			orderidmap.put("ctodate", cdate);
			orderidmap.put("isqc", "0");
			orderidmap.put("ordertype", "2");
			orderidmap.put("taskuserId", taskUserId);
			orderidList=taskDao.getyl112ordertaskidList(orderidmap);
			for (String string : orderidList) {
				orderId=orderId+string+",";	
			}
			sfcount=sfcount+(Integer.parseInt(task.get(0).getGetRecordCount())-Integer.parseInt(task.get(0).getCompleteCount()));//释放的任务数	
			Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
			//ordermap.put("qcUser", string); 
			ordermap.put("taskuserId", taskUserId);
			ordermap.put("cDate",cdate);
			ordermap.put("taskType","2");
			taskDao.deltaskOrder(ordermap);//修改任务工单中间表				
		}	
		Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
		qcusermap.put("taskuserId", taskUserId);
		qcusermap.put("taskType", "2");
		List<Task> usertask=taskDao.order112taskqcuserbyuserid(qcusermap);	
		Map<String,String> updqcusermap=new HashMap<String, String>();//用户修改任务员工中间表信息
		if(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount>Integer.parseInt(usertask.get(0).getCompleteCount())){
			updqcusermap.put("getRecordCount",(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount)+"");		
		}else{
			updqcusermap.put("getRecordCount",usertask.get(0).getCompleteCount()+"");
		}
		updqcusermap.put("taskuserId", usertask.get(0).getTaskuserId()+"");
		updqcusermap.put("taskType", "2");
		taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
		if(!orderId.equals("")){
			orderId=orderId.substring(0, orderId.length()-1);
		}
		return orderId;
	}

	@Override
	public List<Task> getcwordertaskwcqkqcList(Map<String, String> map) {
		return taskDao.getcwordertaskwcqkqcList(map);
	}

	@Override
	public int getcwordertaskwcqkqccount(Map<String, String> map) {
		return taskDao.getcwordertaskwcqkqccount(map);
	}
}