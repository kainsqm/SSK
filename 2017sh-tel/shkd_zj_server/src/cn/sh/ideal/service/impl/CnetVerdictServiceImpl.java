package cn.sh.ideal.service.impl;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.sh.ideal.dao.CnetVerdictDao;
import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.CnetVerdict;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.service.CnetVerdictService;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.Util;

@Service
public class CnetVerdictServiceImpl implements CnetVerdictService {
	private static Logger log = Logger.getLogger(CnetVerdictServiceImpl.class);
	@Autowired
	private CnetVerdictDao cnetVerdictDao;
	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Override
	public List<CnetVerdict> getcNetVerdictList(CnetVerdict cnetVerdict) {
		return cnetVerdictDao.getcNetVerdictList(cnetVerdict);
	}

	@Override
	public int getcNetVerdictListCount(CnetVerdict cnetVerdict) {
		return cnetVerdictDao.getcNetVerdictListCount(cnetVerdict);
	}

	@Override
	public List<Modeltype> getServiceType() {
		return cnetVerdictDao.getServiceType();
	}

	@Override
	public List<Modeltype> getResultType() {
		return cnetVerdictDao.getResultType();
	}

	@Override
	public List<Modeltype> getComplaintSource() {
		return cnetVerdictDao.getComplaintSource();
	}

	@Override
	public int checkCnetTaskName(String taskName) {
		return cnetVerdictDao.checkCnetTaskName(taskName);
	}

	@Override
	public int addCnetorderTask(CnetVerdict cnetVerdict) {
		cnetVerdictDao.addCnetorderTask(cnetVerdict);
		if(cnetVerdict.getIsPublish().equals("1")){
			
		String taskStartTime = cnetVerdict.getTaskStartTime();
		int taskid = cnetVerdict.getTaskId();
		int countDay = 0;// 任务天数（开始到结束）
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		countDay = DateUtil.getDays(formatter.parse(cnetVerdict
				.getTaskStartTime().substring(0, 10), pos), formatter.parse(
				cnetVerdict.getTaskEndTime().substring(0, 10), pos1));
		String quserid = cnetVerdict.getQcUser();
		String[] qcuserid = quserid.split(",");
		cnetVerdict.setTaskId(cnetVerdict.getTaskId());
		for (String string : qcuserid) {
			//String id=cnetVerdictDao.getuserIdByWorkId(string);
			cnetVerdict = new CnetVerdict();
			cnetVerdict.setTaskId(taskid);
			cnetVerdict.setGetRecordCount("0");
			cnetVerdict.setCompleteCount("0");
			cnetVerdict.setEachQcuserStatus("1");
			cnetVerdict.setTasktype("4");
			cnetVerdict.setQcUser(string);
			cnetVerdictDao.insertCnetordertaskUser(cnetVerdict);
			int userid = cnetVerdict.getTaskuserId();
			String today = taskStartTime.substring(0, 10);// 哪一天的任务
			for (int j = 0; j < countDay + 1; j++) {
				cnetVerdict = new CnetVerdict();
				cnetVerdict.setTaskId(taskid);
				cnetVerdict.setGetRecordCount("0");
				cnetVerdict.setCompleteCount("0");
				cnetVerdict.setTaskuserId(userid);
				cnetVerdict.setcDate(today);
				cnetVerdict.setTasktype("4");
				cnetVerdictDao.insertToTaskCnetorderuserComplete(cnetVerdict);
				today = Util.getSpecifiedDayAfter(today);
			}
		}
		}
		return 1;

	}

	@Override
	public int updCnetOrderTask(CnetVerdict cnetVerdict) {
		Map<String, String> mapdata = new HashMap<String, String>();
		mapdata.put("taskId", "" + cnetVerdict.getTaskId());
		cnetVerdictDao.updCnetOrderTask(cnetVerdict);
		if(cnetVerdict.getIsPublish().equals("1")){
			String taskStartTime = cnetVerdict.getTaskStartTime();
			int taskid = cnetVerdict.getTaskId();
			int countDay = 0;// 任务天数（开始到结束）
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			countDay = DateUtil.getDays(formatter.parse(cnetVerdict
					.getTaskStartTime().substring(0, 10), pos), formatter.parse(
					cnetVerdict.getTaskEndTime().substring(0, 10), pos1));
			String quserid = cnetVerdict.getQcUser();
			String[] qcuserid = quserid.split(",");
			cnetVerdict.setTaskId(cnetVerdict.getTaskId());
			for (String string : qcuserid) {
				String id=cnetVerdictDao.getuserIdByWorkId(string);
				cnetVerdict = new CnetVerdict();
				cnetVerdict.setTaskId(taskid);
				cnetVerdict.setGetRecordCount("0");
				cnetVerdict.setCompleteCount("0");
				cnetVerdict.setEachQcuserStatus("1");
				cnetVerdict.setTasktype("4");
				cnetVerdict.setQcUser(id);
				cnetVerdictDao.insertCnetordertaskUser(cnetVerdict);
				int userid = cnetVerdict.getTaskuserId();
				String today = taskStartTime.substring(0, 10);// 哪一天的任务
				for (int j = 0; j < countDay + 1; j++) {
					cnetVerdict = new CnetVerdict();
					cnetVerdict.setTaskId(taskid);
					cnetVerdict.setGetRecordCount("0");
					cnetVerdict.setCompleteCount("0");
					cnetVerdict.setTaskuserId(userid);
					cnetVerdict.setcDate(today);
					cnetVerdict.setTasktype("4");
					cnetVerdictDao.insertToTaskCnetorderuserComplete(cnetVerdict);
					today = Util.getSpecifiedDayAfter(today);
				}
			}
		}
		return 1;
	}

	@Override
	public int delCnetorderTask(Map<String, String> mapdata) {
		if (!("0".equals(mapdata.get("taskStatus")) || "1".equals(mapdata
				.get("taskStatus")))) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("taskId", mapdata.get("taskId"));
			// 1：修改c网电话小结任务状态(电话小结的任务状态，是，否)
			cnetVerdictDao.releaseCnetver(map1);
			// 2：t_qc_taskrecord删除任务工单信息
			cnetVerdictDao.releaseRecordByCnetver(map1);
		}
		int taskId = Integer.parseInt(mapdata.get("taskId"));
		// 删除C网电话小结任务表中的数据
		cnetVerdictDao.deleteCnetverdict(taskId);
		cnetVerdictDao.delTaskQcuser(mapdata);
		cnetVerdictDao.delTaskQcuserComplete(mapdata);
		return 1;
	}

	/***
	 * 质检员查询任务
	 * 
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **/
	public List<CdmaTaskComplete> queryListForQc(Map<String, String> mapdata) {
		return cnetVerdictDao.queryListForQc(mapdata);
	}

	/***
	 * 质检员查询任务记录总数
	 * 
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **/
	public int queryListForQcCount(Map<String, String> mapdata) {
		return cnetVerdictDao.queryListForQcCount(mapdata);
	}

	/***********
	 * 质检员查询可以领取的C网电话小结列表数据 查询随机10条电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-22
	 * **************/
	public String searchCdma(Map<String, String> mapdata) {
		/** 随机获取10条C网电话小结工单 **/
		String iTaskDate = mapdata.get("taskTime"); // 任务日期(领取哪一天的任务)
		String bussniessType = mapdata.get("bussniessType"); // //业务类型
		String telsumType = mapdata.get("telsumType"); // 小结类型
		String gzlyType = mapdata.get("gzlyType"); // 故障来源
		String serialCdma = ""; // 返回的流水号
		// 设置日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date timeNow = null;
		try {
			timeNow = df.parse(iTaskDate);
		} catch (ParseException e) {
			log.error(e);
		}
		Calendar begin = Calendar.getInstance();
		begin.setTime(timeNow);
		begin.add(Calendar.DAY_OF_MONTH, -1);
		String vStartCTime = df.format(begin.getTime()); // 传入的时间-1天
		vStartCTime = vStartCTime + " 00:00:00";
		String vStopCTime = df.format(begin.getTime()); // 传入的时间-1天
		vStopCTime = vStopCTime + " 23:59:59";
		// 给话单查询的条件限制
		CnetVerdict cnetVer = cnetVerdictDao.queryTaskGetCondition(mapdata);
		String vStrSqlBegin = "select count(1) counts, tcs.op_id,min(tcs.cdma_autoid) cdma_autoid ";
		String vStrSql = "from t_workorder_cdma_sum tcs where "
				+ " tcs.ISTASK='0'"
				+ "AND tcs.result_time BETWEEN DATE_FORMAT('" + vStartCTime
				+ "','%Y-%m-%d %H:%i:%s') AND DATE_FORMAT('" + vStopCTime
				+ "','%Y-%m-%d %H:%i:%s')";

		String vStrSqlEnd = " group by tcs.op_id";
		if (bussniessType != "") {// 业务类型
			vStrSql = vStrSql + " and tcs.service_type in (" + bussniessType
					+ ")";
		} else if (telsumType != "") {
			vStrSql = vStrSql + " and tcs.result_type in (" + telsumType + ")";
		} else if (gzlyType != "") {
			vStrSql = vStrSql + " and tcs.complaint_source in (" + gzlyType
					+ ")";
		}
		String workId = cnetVer.getTelUser().trim();
		String[] workIdList = workId.split(",");
		String bigWorkId = "";
		// 根据userId去查询该员工的小工号
		for (int i = 0; i < workIdList.length; i++) {
			bigWorkId += workIdList[i] + ",";
		}
		if (bigWorkId != "") {
			bigWorkId = bigWorkId.substring(0, bigWorkId.length() - 1);
		}

		List<UserInfo> userList = cnetVerdictDao
				.quertSmainWorkIdByUserid(bigWorkId);
		String smailWork = "";
		for (int i = 0; i < userList.size(); i++) {
			UserInfo user = userList.get(i);
			smailWork += "'" + user.getSmailWorkid() + "',";
		}
		if (cnetVer.getTelUser() != null) { // 约束中话务员ID有效
			smailWork = smailWork.substring(0, smailWork.length() - 1); // 去除最后一个,
			vStrSql = vStrSql + " and SUBSTRING(tcs.op_id,3) in ( " + smailWork
					+ " )";
		}

		// 查出当天最高被质检次数达到饱和状态的CSR工号，在sql中过滤掉
		vStrSql = vStrSql
				+ " and SUBSTRING(tcs.op_id,3) not in (select Smail_workid from t_sys_user where pid in(select T.USER_ID from (select tu.USER_ID, count(1) counts from t_qc_taskrecord tr inner join 	T_SYS_USER  tu on tu.USER_ID=tr.TEL_USER"
				+ "  where tr.task_id=" + cnetVer.getTaskId()
				+ " and tr.ORDER_TYPE='4'"
				+ " and tr.whichdatefortask = DATE_FORMAT('" + iTaskDate
				+ "','%Y-%m-%d') group by tr.tel_user) T "
				+ " where T.counts = " + cnetVer.getCheckCount() + "))";

		/******************** 过滤数据 结束 ********************/
		// 查询结果中有几个话务员
		String sql = "select count(hw.counts) from (" + vStrSqlBegin + vStrSql
				+ vStrSqlEnd + ") hw";
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		System.out.println(sql);
		int vtelUserCount = cnetVerdictDao.getResultHWCount(map);

		/******** 如果符合条件的电话小结的话务员超过10人，则随机10人，每人抽取一条电话小结 ****** 开始 ********/
		int myRandomNum = 0;
		java.util.Random random = new java.util.Random();// 定义随机类
		if (vtelUserCount >= 10) {
			myRandomNum = random.nextInt(vtelUserCount - 10 + 1) + 1;// 返回[0,value)集合中的整数，注意不包括value
			String sqlmore = "select N.cdma_autoid from (select M.* from ("
					+ vStrSqlBegin + vStrSql + vStrSqlEnd + ") M limit "
					+ myRandomNum + "," + (myRandomNum + 10 - 1) + ") N";
			map.put("sqlmore", sqlmore);
			List<WorkOrderCdmaSum> cdmaList = cnetVerdictDao
					.getLingQuCnetVerdict(map);
			for (int i = 0; i < cdmaList.size(); i++) {
				serialCdma += cdmaList.get(i).getCdmaAutoid() + ",";
			}
		}
		/******** 如果符合条件的电话小结的话务员超过10人，则随机10人，每人抽取一条电话小结 ****** 结束 ********/

		/******** 如果符合条件的电话小结的话务员少于10人 ****** 开始 ********/
		if (vtelUserCount < 10) {// 查询结果中有多少条电话小结
			String sqlresult = "select IFNULL(sum(B.counts),0) from ("
					+ vStrSqlBegin + vStrSql + vStrSqlEnd + ") B";
			map.put("sqlresult", sqlresult);
			int vCdmaSumCount = cnetVerdictDao.getResultCdmaSumCount(map);
			/******** 如果符合电话小结的不大于10条，返回全部电话小结 ****** 开始 ********/
			if (vCdmaSumCount <= 10) { // 符合条件的电话小结不大于10条
				serialCdma = "";
				String strsql = "select tcs.cdma_autoid " + vStrSql;
				map.put("sqlmore", strsql);
				List<WorkOrderCdmaSum> cdmaListUnder = cnetVerdictDao
						.getLingQuCnetVerdict(map);
				for (int i = 0; i < cdmaListUnder.size(); i++) {
					serialCdma += cdmaListUnder.get(i).getCdmaAutoid() + ",";
				}
			}
			/******** 如果符合条件的电话小结不大于10条，返回全部电话小结 ****** 结束 ********/

			/******** 如果符合条件的电话小结大于10条，尽量平均抽取10条****** 开始 ********/
			if (vCdmaSumCount > 10) {
				// 每人抽取一条电话小结，然后剩下的从结果集中随机抽取
				serialCdma = "";
				// 每人抽取一条电话小结 开始
				String everyonesql = "select H.cdma_autoid from ("
						+ vStrSqlBegin + vStrSql + vStrSqlEnd + ") H";
				map.put("everyonesql", everyonesql);
				List<WorkOrderCdmaSum> cdmaListUnder = cnetVerdictDao
						.getCdmaSumEveryOne(map);
				for (int i = 0; i < cdmaListUnder.size(); i++) {
					serialCdma += cdmaListUnder.get(i).getCdmaAutoid() + ",";
				}
				// 每人抽取一条电话小结 结束

				// 然后剩下的从结果集中随机抽取 开始
				myRandomNum = random.nextInt(vCdmaSumCount - 10 + 1) + 1;
				serialCdma = serialCdma.substring(0, serialCdma.length() - 1);
				String randomGetCdmasql = "select O.cdma_autoid from (select P.*  from (select tcs.cdma_autoid "
						+ vStrSql
						+ " and tcs.cdma_autoid not in ("
						+ serialCdma
						+ ")) P limit "
						+ myRandomNum
						+ ","
						+ (myRandomNum + 10 - vtelUserCount - 1) + ") O";
				serialCdma = serialCdma + ',';
				map.put("randomGetCdmasql", randomGetCdmasql);
				List<WorkOrderCdmaSum> cdmaListLast = cnetVerdictDao
						.randomGetCdmaSum(map);
				for (int i = 0; i < cdmaListLast.size(); i++) {
					serialCdma += cdmaListLast.get(i).getCdmaAutoid() + ",";
				}
				/******** 如果符合条件的电话小结大于10条，尽量平均抽取10条****** 结束 ********/
			}
		}
		return serialCdma;
	}

	/**
	 * 查询c网电话小结领取页面
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public List<WorkOrderCdmaSum> searchCdmaSumToGet(Map<String, String> mapdata) {
		return cnetVerdictDao.searchCdmaSumToGet(mapdata);
	}

	/**
	 * 查询c网电话小结领取页面数量
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public int searchCdmaSumToGetCount(Map<String, String> mapdata) {
		return cnetVerdictDao.searchCdmaSumToGetCount(mapdata);
	}

	/**
	 * 质检员c网电话小结一键领取
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	String vMessage = "成功";

	public String updateOneKeyGetCdmaSum(Map<String, String> mapdata) {
		String iTaskUserId = mapdata.get("taskUserId");
		String iTaskDate = mapdata.get("taskTime");
		// 设置日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date timeNow = null;
		try {
			timeNow = df.parse(iTaskDate);
		} catch (ParseException e1) {
			log.error(e1);
		}
		Calendar begin = Calendar.getInstance();
		begin.setTime(timeNow);
		begin.add(Calendar.DAY_OF_MONTH, -1);
		String vStartCdmaTime = df.format(begin.getTime()); // 电话小结开始时间
		vStartCdmaTime = vStartCdmaTime + " 00:00:00";
		String vStopCdmaTime = df.format(begin.getTime()); // 电话小结结束时间
		vStopCdmaTime = vStopCdmaTime + " 23:59:59";
		// 查询该质检员该任务 任务规格数
		CnetVerdict cnetVer = cnetVerdictDao.queryTaskGetCondition(mapdata);
		String VgetCdmaCount = cnetVerdictDao.cdmaGetCount(mapdata); // 该天任务已领取数
		String vQcDCount = cnetVer.getCsrTopDCount();
		String VgetAllRCount = cnetVer.getGetRecordCount(); // 该任务总的领取数
		int vQcDCount1 = 0;
		int VgetCdmaCount1 = 0;
		int VgetAllRCount1 = 0;
		// 给当天的任务任务规格赋值
		vQcDCount1 = Integer.parseInt(vQcDCount);
		// 给当天的已领取数赋值
		VgetCdmaCount1 = Integer.parseInt(VgetCdmaCount);
		// 给总领取数赋值
		VgetAllRCount1 = Integer.parseInt(VgetAllRCount);
		// 给话单查询的条件限制

		String vStrSql = " from t_workorder_cdma_sum tcs where 1=1 and tcs.ISTASK='0'"
				+ " AND tcs.result_time BETWEEN DATE_FORMAT('"
				+ vStartCdmaTime
				+ "','%Y-%m-%d %H:%i:%s') AND DATE_FORMAT('"
				+ vStopCdmaTime
				+ "','%Y-%m-%d %H:%i:%s')";
		String bussniessType = cnetVer.getBussniessType();
		String telsumType = cnetVer.getTelsumType();
		String gzlyType = cnetVer.getGzlyType();
		String type = "";
		String teltype = "";
		String gztyle = "";
		// 为查询条件拼接in参数类型
		if (bussniessType != "") {
			String[] typeArray = bussniessType.split(";");
			for (int i = 0; i < typeArray.length; i++) {
				type += "'" + typeArray[i] + "',";
			}
			type = type.substring(0, type.length() - 1); // 去除最后一个,
		}
		if (telsumType != "") {
			String[] typeArray = telsumType.split(";");
			for (int i = 0; i < typeArray.length; i++) {
				teltype += "'" + typeArray[i] + "',";
			}
			teltype = teltype.substring(0, teltype.length() - 1); // 去除最后一个,
		}
		if (gzlyType != "") {
			String[] typeArray = gzlyType.split(";");
			for (int i = 0; i < typeArray.length; i++) {
				gztyle += "'" + typeArray[i] + "',";
			}
			gztyle = gztyle.substring(0, gztyle.length() - 1); // 去除最后一个,
		}
		if (type != "") {// 业务类型
			vStrSql = vStrSql + " and tcs.service_type in (" + type + ")";
		} else if (teltype != "") {
			vStrSql = vStrSql + " and tcs.result_type in (" + teltype + ")";
		} else if (gztyle != "") {
			vStrSql = vStrSql + " and tcs.complaint_source in (" + gztyle + ")";
		}
		String workId = cnetVer.getTelUser().trim(); // 获取受理员UserID
		String[] workIdList = workId.split(",");
		String bigWorkId = "";
		// 根据userId去查询该员工的小工号
		for (int i = 0; i < workIdList.length; i++) {
			bigWorkId += workIdList[i] + ",";
		}
		if (bigWorkId != "") {
			bigWorkId = bigWorkId.substring(0, bigWorkId.length() - 1);
		}

		List<UserInfo> userList = cnetVerdictDao
				.quertSmainWorkIdByUserid(bigWorkId);
		String smailWork = "";
		for (int i = 0; i < userList.size(); i++) {
			UserInfo user = userList.get(i);
			smailWork += "'" + user.getSmailWorkid() + "',";
		}
		if (cnetVer.getTelUser() != null) { // 约束中话务员ID有效
			smailWork = smailWork.substring(0, smailWork.length() - 1); // 去除最后一个,
			vStrSql = vStrSql + " and SUBSTRING(tcs.op_id,3) in ( " + smailWork
					+ " )";
		}

		// 查出当天最高被质检次数达到饱和状态的CSR工号，在sql中过滤掉
		vStrSql = vStrSql
				+ " and SUBSTRING(tcs.op_id,3) not in (select Smail_workid from t_sys_user where pid in(select T.USER_ID from (select tu.USER_ID, count(1) counts from t_qc_taskrecord tr inner join 	T_SYS_USER  tu on tu.USER_ID=tr.TEL_USER"
				+ "  where tr.task_id=" + cnetVer.getTaskId()
				+ " and tr.ORDER_TYPE='4'"
				+ " and tr.whichdatefortask = DATE_FORMAT('" + iTaskDate
				+ "','%Y-%m-%d') group by tr.tel_user) T "
				+ " where T.counts = " + cnetVer.getCheckCount() + "))";

		/******************** 过滤数据 结束 ********************/
		if (vQcDCount1 == VgetCdmaCount1) {
			vMessage = "当天电话小结领取数已满";
			return vMessage;
		}
		updateGetCdmaByMap(iTaskDate, cnetVer, vStrSql, iTaskUserId,
				VgetCdmaCount1, vQcDCount1, VgetAllRCount1);
		return vMessage;
	}

	/**
	 * 一键领取C网电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * @throws Exception
	 * **/
	public void updateGetCdmaByMap(String iTaskDate, CnetVerdict cnetVer,
			String vStrSql, String iTaskUserId, int VgetCdmaCount1,
			int vQcDCount1, int VgetAllRCount1) {
		Map<String, String> map = new HashMap<String, String>();
		/* 一键领取C网电话小结开始 */
		while (VgetCdmaCount1 < vQcDCount1) {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
			try {

				// 重新拼接SQL，过滤数据
				// 查询可以领取的工单数据
				String recordsqlcount1 = "select count(1) " + vStrSql;
				map.put("sql", recordsqlcount1);
				int vCDmaCount = cnetVerdictDao.getResultHWCount(map);
				// 如果没有符合条件的录音
				if (vCDmaCount == 0) {
					vMessage = "数据缺失";
					break;
				}
				String sqlresult = "select bb.cdma_autoid from (select tcs.cdma_autoid "
						+ vStrSql + ") bb limit 0,1";
				map.put("sqlresult", sqlresult);
				int cdmaAutoid = cnetVerdictDao.getResultCdmaSumCount(map); // 获取cdmaid
				int standardCount = 0;// 规范值
				int count = 0;// 实际查出来的值
				// 验证1：每个CSR每天最高被质检次数是否达到饱和
				standardCount = Integer.parseInt(cnetVer.getCheckCount());// 每个CSR每天最高被质检次数
				String smailWorkId = cnetVerdictDao.getWorkIDByCdmaPkId(cdmaAutoid); // 根据C网电话小结表中的受理员工号查询该用户大工号对应的USERID
				String workId=cnetVerdictDao.getBigWorkIdBySmail(smailWorkId);
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("taskId", String.valueOf(cnetVer.getTaskId()));
				map1.put("taskTime", iTaskDate);
				map1.put("telUser", workId);
				count = cnetVerdictDao.getCdmaSumVerification(map1);
				if (count >= standardCount) { // 该受理员抽取已达最大次数
					continue;
				}
				// 验证2：验证此电话小结是否已被领取和领取
				int pkAutoId = cdmaAutoid;
				if (cnetVerdictDao.earlierByGet(pkAutoId) > 0) { // 该电话小结已被领取
					continue;
				}
				if (cnetVerdictDao.earlierByQc(pkAutoId) > 0) {// 该电话小结已被评分
					continue;
				}
				/*** //增加数据：t_qc_taskrecord ***/
				Map<String, String> taskMap = new HashMap<String, String>();
				taskMap.put("qcUser", cnetVer.getQcUser());
				taskMap.put("taskUserId", iTaskUserId);
				taskMap.put("cDate", iTaskDate);
				taskMap.put("telUser", workId);
				taskMap.put("taskId", String.valueOf(cnetVer.getTaskId()));
				taskMap.put("pkAutoId", String.valueOf(cdmaAutoid));
				cnetVerdictDao.insertTaskRecordByCdma(taskMap);
				/*** //修改数据：t_workorder_cdma_sum(工单的任务状态，是，否) ***/
				Map<String, String> map5 = new HashMap<String, String>();
				map5.put("pkAutoId", String.valueOf(cdmaAutoid));
				cnetVerdictDao.updateCdmaIsTask(map5);
				// 已领取数 总数加1
				VgetCdmaCount1 = VgetCdmaCount1 + 1;
				VgetAllRCount1 = VgetAllRCount1 + 1;
				// 全部验证通过，开始操作数据
				/*** //修改数据：t_qc_taskqcuser_complete(领取数，状态) ***/
				Map<String, String> map7 = new HashMap<String, String>();
				map7.put("taskQcUserId", iTaskUserId);
				map7.put("taskTime", iTaskDate);
				map7.put("getRecordCount", String.valueOf(VgetCdmaCount1));
				cnetVerdictDao.updateTaskRecordComplete(map7);
				/*** //更改数据：t_qc_task_qcuser(任务状态) ***/
				Map<String, String> map8 = new HashMap<String, String>();
				map8.put("qcUser", cnetVer.getQcUser());
				map8.put("taskId", String.valueOf(cnetVer.getTaskId()));
				map8.put("getRecordCount", String.valueOf(VgetAllRCount1));
				map8.put("eachQcuserStatus", "2");// 质检员任务状态：进行中
				cnetVerdictDao.updateTaskQcUser(map8);
				/*** //更新数据：t_qc_task(任务状态) ***/
				Map<String, String> map9 = new HashMap<String, String>();
				map9.put("taskStatus", "2");// 更改任务状态为执行中
				map9.put("taskId", String.valueOf(cnetVer.getTaskId()));
				cnetVerdictDao.updateTaskStatus(map9);
				transactionManager.commit(status);
			} catch (Exception e) {
				transactionManager.rollback(status);
				log.error(
						"一键领取电话小结失败，taskQcUserId" + iTaskUserId + "出现异常："
								+ e.getMessage(), e);
			}
		}
	}

	/**
	 * 质检员c网电话小结领取
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * */
	public String updateGetCdmaSum(Map<String, String> map) {
		// 根据任务ID查询该任务领取情况
		CnetVerdict cnetVer = cnetVerdictDao.queryTaskGetCondition(map);
		int standardCount = 0;// 规范值
		int count = 0;// 实际查出来的值
		String message = "通过";
		// 验证1：每个CSR每天最高被质检次数是否达到饱和
		standardCount = Integer.parseInt(cnetVer.getCheckCount());// 每个CSR每天最高被质检次数
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("taskId", map.get("taskId"));
		map1.put("taskTime", map.get("taskTime"));
		map1.put("telUser", map.get("telUser"));
		count = cnetVerdictDao.getCdmaSumVerification(map1);
		if (count >= standardCount) {
			message = "该受理员(" + map.get("telUser") + ")当天("
					+ map.get("taskTime") + ")已达到最高被领取次数，请选择其他受理员";
			return message;
		}

		// 验证3：每个质检员每天质检任务规格中该录音时间档是否达到饱和
		standardCount = Integer.parseInt(cnetVer.getCsrTopDCount());// 每个质检员每天质检任务规格
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("taskId", map.get("taskId"));
		map3.put("taskTime", map.get("taskTime"));
		map3.put("userID", map.get("userID"));
		count = 0;
		count = cnetVerdictDao.getCdmaSumVerification(map3);
		if (count >= standardCount) {
			message = "你该天(" + map.get("taskTime") + ")该电话小结规格已经完成";
			return message;
		}	
		// 验证5：验证此电话小结是否已被领取和领取
		int pkAutoId = Integer.parseInt(map.get("pkAutoId"));
		if (cnetVerdictDao.earlierByGet(pkAutoId) > 0) {
			message = "对不起，该条电话小结刚刚已经被其他质检员提前领取，请选择其他电话小结";
			return message;
		}
		if (cnetVerdictDao.earlierByQc(pkAutoId) > 0) {
			message = "对不起，该条电话小结刚刚已经被其他质检员提前评分，请选择其他电话小结";
			return message;
		}
		int nowdateorder=standardCount-count;//当天任务未领完数量	
		// 如果上述验证都通过，则领取该条电话小结
		/*** //增加数据：t_qc_taskrecord ***/
		Map<String, String> taskMap = new HashMap<String, String>();
		taskMap.put("qcUser", map.get("userID"));
		taskMap.put("taskUserId", map.get("taskUserId"));
		taskMap.put("cDate", map.get("taskTime"));
		taskMap.put("telUser", map.get("telUser"));
		taskMap.put("taskId", map.get("taskId"));
		taskMap.put("pkAutoId", String.valueOf(pkAutoId));
		cnetVerdictDao.insertTaskRecordByCdma(taskMap);
		/*** //修改数据：t_workorder_cdma_sum(工单的任务状态，是，否) ***/
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("pkAutoId", String.valueOf(pkAutoId));
		cnetVerdictDao.updateCdmaIsTask(map5);
		// 修改领取几张表的数据
		String VgetAllRCount = cnetVer.getGetRecordCount(); // 该任务总的领取数
		map.put("lingquCount", "1");
		map.put("VgetAllRCount", VgetAllRCount);
		updateCdmaTable(map);
		if(nowdateorder==1){
			message="通过2"; 
        }
		return message;
	}

	/*******
	 * 根据小工号查询大工号信息
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * *******/
	public String getBigWorkIdBySmail(String smailWorkId) {
		return cnetVerdictDao.getBigWorkIdBySmail(smailWorkId);
	}

	/**********
	 * 领取电话小结时，更新各个表中的数据
	 * 
	 * @author niewenqiang
	 * @date 2017-5-24
	 * **********/
	public void updateCdmaTable(Map<String, String> map) {
		// 全部验证通过，开始操作数据
		/*** //修改数据：t_qc_taskqcuser_complete(领取数，状态) ***/
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("taskQcUserId", map.get("taskUserId"));
		map6.put("startTime", map.get("taskTime"));
		int getRecordCount = cnetVerdictDao.getCdmaComplete(map6);
		int lingquCount = Integer.parseInt(map.get("lingquCount"));// 该次领取电话小结数
		int VgetAllRCount = Integer.parseInt(map.get("VgetAllRCount"));// 该任务总的领取电话小结数
		VgetAllRCount = VgetAllRCount + lingquCount;
		getRecordCount = getRecordCount + lingquCount;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("taskQcUserId", map.get("taskUserId"));
		map7.put("taskTime", map.get("taskTime"));
		map7.put("getRecordCount", String.valueOf(getRecordCount));
		cnetVerdictDao.updateTaskRecordComplete(map7);
		/*** //更改数据：t_qc_task_qcuser(任务状态) ***/
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("qcUser", map.get("userID"));
		map8.put("taskId", map.get("taskId"));
		map8.put("getRecordCount", String.valueOf(VgetAllRCount));
		map8.put("eachQcuserStatus", "2");// 质检员任务状态：进行中
		cnetVerdictDao.updateTaskQcUser(map8);
		/*** //更新数据：t_qc_task(任务状态) ***/
		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("taskStatus", "2");// 更改任务状态为执行中
		map9.put("taskId", map.get("taskId"));
		cnetVerdictDao.updateTaskStatus(map9);
	}

	/******
	 * 执行工作计划时，查询该质检员已领工单列表
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public List<WorkOrderCdmaSum> getCdmaedTaskByUser(
			Map<String, String> mapdata) {
		return cnetVerdictDao.getCdmaedTaskByUser(mapdata);
	}

	/******
	 * 执行工作计划时，查询该质检员已领工单列表数量
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public int getCdmaedTaskByUserCount(Map<String, String> mapdata) {
		return cnetVerdictDao.getCdmaedTaskByUserCount(mapdata);
	}

	/******
	 * 根据TASKID查询C网电话小结任务信息
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * *********/
	public CnetVerdict getCnetorderTaskbyid(int taskId) {
		return cnetVerdictDao.getCnetorderTaskbyid(taskId);
	}

	/******
	 * 质检员完成情况查询
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public List<CdmaTaskComplete> queryTaskCompleteInforc(
			Map<String, String> mapdata) {
		return cnetVerdictDao.queryTaskCompleteInforc(mapdata);
	}

	/******
	 * 质检员完成情况查询 数量
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ********/
	public int queryTaskCompleteInforcCount(Map<String, String> mapdata) {
		return cnetVerdictDao.queryTaskCompleteInforcCount(mapdata);
	}

	/*********
	 * 督导查询该任务完成情况列表
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ************/
	public List<CdmaTaskComplete> queryTaskCompleteInfo(
			Map<String, String> mapdata) {
		return cnetVerdictDao.queryTaskCompleteInfo(mapdata);
	}

	/*********
	 * 督导查询该任务完成情况 列表记录数量
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * ************/
	public int queryTaskCompleteInfoCount(Map<String, String> mapdata) {
		return cnetVerdictDao.queryTaskCompleteInfoCount(mapdata);
	}

	/*******
	 * 督导以质检员为维度批量释放电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-26
	 * **********/
	public String releaseCdmaByQcUser(String qcUserItems, String taskId) {
		String result = "成功";
		String[] qcUser = qcUserItems.split(",");
		Map<String, String> map1 = new HashMap<String, String>();
		for (int i = 0; i < qcUser.length; i++) {
			map1.clear();
			map1.put("taskId", taskId);
			map1.put("qcUser", qcUser[i]);
			map1.put("isqc", "0");
			// 1：修改电话小结表中该电话小结任务标识为0
			cnetVerdictDao.releaseCnetver(map1);
			// 2：t_qc_taskrecord删除任务工单信息
			cnetVerdictDao.releaseRecordByCnetver(map1);
			// 3：t_qc_taskqcuser_complete修改领取数
			cnetVerdictDao.updateTaskCdmaComplete1(map1);
			// 4：t_qc_task_qcuser修改总领取数
			cnetVerdictDao.updateTaskCdmaQcUserInfo1(map1);
		}

		return result;
	}
	
	/*******
	 * 督导以日期为维度批量释放电话小结
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ********/
	public String releaseCdmaByQcDate(String cDates,String taskId,String qcUser,String taskUserId){
		String msg="成功";
		String[] cDateItem = cDates.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < cDateItem.length; i++) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("cDate", cDateItem[i]);
			map1.put("taskId", taskId);
			map1.put("qcUser", qcUser);
			map1.put("isqc", "0");
			// 1：修改电话小结表中该电话小结任务标识为0
			cnetVerdictDao.releaseCnetver(map1);
			// 2：t_qc_taskrecord删除任务工单信息
			cnetVerdictDao.releaseRecordByCnetver(map1);
			// t_qc_taskqcuser_complete修改领取数
			map1.clear();
			map1.put("taskQcUserId", taskUserId);
			map1.put("startTime",cDateItem[i]);
			CdmaTaskComplete cdmaTsk=cnetVerdictDao.queryTaskCompleteInforc(map1).get(0); 
			//获取该日期释放了几条电话小结
			int count=Integer.parseInt(cdmaTsk.getGetRecordCount())-Integer.parseInt(cdmaTsk.getCompleteCount());
			map1.clear();
			map1.put("taskQcUserId", taskUserId);
			map1.put("taskTime", cDateItem[i]);
			map1.put("getRecordCount", cdmaTsk.getCompleteCount());
			map1.put("taskStatus", cdmaTsk.getEachDateStatus());
			cnetVerdictDao.updateTaskRecordComplete(map1);
			// t_qc_task_qcuser修改总领取数
			map1.clear();
			map1.put("qcUser", map.get("qcUser"));
			map1.put("taskId", map.get("taskId"));
			CdmaTaskComplete taskInfoItem = cnetVerdictDao.queryTaskCompleteInfo(map1).get(0);
			int getRecordCount=Integer.parseInt(taskInfoItem.getGetRecordCount())-count; //修改总的领取数
			map1.put("getRecordCount", String.valueOf(getRecordCount));
			cnetVerdictDao.updateTaskQcUser(map1);
		}
		return msg;
	}
	
	
	/**********
	 * 根据c网电话小结ID查询在任务表该电话小结任务信息
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ***********/
	public CdmaTaskComplete getTaskInfoByCdmaAutoId(int cdmaPkId){
		return cnetVerdictDao.getTaskInfoByCdmaAutoId(cdmaPkId);
	}
	
	
	/**********
	 * c网电话小结进行任务评分时，修改对应的状态
	 * @author niewenqiang
	 * @date 2017-5-26
	 * ***********/
	public int updateTaskCdma(Map<String, String> map) {
		String taskId=map.get("taskId");
		String taskuserId=map.get("taskuserId");
		String cDate=map.get("cDate");
		if(cDate!=null && !"".equals(cDate)){
			cDate=cDate.substring(0, 10);
		}
		String cdmaPkId=map.get("cdmaId");
		String sumtaskorder=map.get("sumtaskorder");
		String csrTopDCount=map.get("csrTopDCount");//每天任务数
		//修改该电话小结状态为已质检
		cnetVerdictDao.updateTaskRecordByCdma(Integer.parseInt(cdmaPkId));
		Map<String,String> qcusermap=new HashMap<String, String>();
		qcusermap.put("taskUserId", taskuserId);	
		CnetVerdict cnetVer=cnetVerdictDao.queryTaskGetCondition(qcusermap); //获取任务员工中间表信息
		int complecount=Integer.parseInt(cnetVer.getCompleteCount());//完成的任务工单数
		Map<String,String> taskusermap=new HashMap<String, String>();	
		taskusermap.put("taskuserId", taskuserId);  
		if(Integer.parseInt(sumtaskorder)==complecount+1){
			taskusermap.put("eachQcuserStatus", "3");
			taskusermap.put("completeCount", String.valueOf((complecount+1)));
			Map<String,String> taskmap=new HashMap<String, String>();//更新主表状态
			taskmap.put("taskStatus", "3");
			taskmap.put("taskId", taskId);
			cnetVerdictDao.updateCwxjTask(taskmap); //更新主表的状态
		}else{
			taskusermap.put("eachQcuserStatus", "2");
			taskusermap.put("completeCount", String.valueOf((complecount+1)));
		}
		cnetVerdictDao.updateTaskQcUser(taskusermap);//修改任务员工中间表
		
		Map<String,String> getordermap=new HashMap<String, String>();
		getordermap.put("taskuserId", taskuserId);
		getordermap.put("cDate", cDate);
		String getCount=cnetVerdictDao.getCompleteByDay(getordermap); //获取当天完成数
		
		Map<String,String> completask=new HashMap<String, String>();
		if(Integer.parseInt(csrTopDCount)==(Integer.parseInt(getCount)+1)){
			completask.put("taskStatus", "1");
			completask.put("completeCount",String.valueOf((Integer.parseInt(getCount)+1)));
		}else{
			completask.put("taskStatus", "2");
			completask.put("completeCount", String.valueOf((Integer.parseInt(getCount)+1)));
		}	
		completask.put("taskQcUserId", taskuserId);
		completask.put("taskTime", cDate);
		cnetVerdictDao.updateTaskRecordComplete(completask);	
		return 1;
	}
}
