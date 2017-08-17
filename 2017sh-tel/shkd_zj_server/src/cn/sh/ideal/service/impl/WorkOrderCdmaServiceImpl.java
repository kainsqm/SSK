package cn.sh.ideal.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.dao.IWorkOrderCdmaDao;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.service.IWorkOrderCdmaService;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.Util;

@Service("workOrderCdmaService")
public class WorkOrderCdmaServiceImpl implements IWorkOrderCdmaService {
	@Resource
	private IWorkOrderCdmaDao workOrderCdmaDao;
	@Resource
	private ITaskDao taskDao;
	@Resource
	private IUserInfoDao   userInfoDao;
	@Autowired
	private DataSourceTransactionManager txManager;

	@Override
	public int deleteByPrimaryKey(Integer serialCdma) {
		return workOrderCdmaDao.deleteByPrimaryKey(serialCdma);
	}

	@Override
	public int insert(WorkOrderCdma record) {
		return workOrderCdmaDao.insert(record);
	}

	@Override
	public int insertSelective(WorkOrderCdma record) {
		return workOrderCdmaDao.insertSelective(record);
	}

	@Override
	public   List<WorkOrderCdma>  selectByPrimaryKey(WorkOrderCdma record) {
		return workOrderCdmaDao.selectByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WorkOrderCdma record) {
		return workOrderCdmaDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WorkOrderCdma record) {
		return workOrderCdmaDao.updateByPrimaryKey(record);
	}

	@Override
	public int countselectByPrimaryKey(WorkOrderCdma record) {
		return workOrderCdmaDao.countselectByPrimaryKey(record);
	}

	@Override
	public WorkOrderCdma getOrderCdma(Integer serialCdma) {
		return workOrderCdmaDao.getOrderCdma(serialCdma);
	}

	@Override
	public List<String> getbstype() {
		return workOrderCdmaDao.getbstype();
	}

	@Override
	public List<Modeltype> getywxltype() {
		return workOrderCdmaDao.getywxltype();
	}

	@Override
	public List<Modeltype> getsgxxtype() {
		return workOrderCdmaDao.getsgxxtype();
	}

	@Override
	public List<Modeltype> getclfstype() {
		return workOrderCdmaDao.getclfstype();
	}

	@Override
	public List<Modeltype> getjadmtype() {
		return workOrderCdmaDao.getjadmtype();
	}

	@Override
	public List<Modeltype> getejsgxxtype() {
		return workOrderCdmaDao.getejsgxxtype();
	}

	@Override
	public List<Modeltype> getzejtype() {
		return workOrderCdmaDao.getzejtype();
	}

	@Override
	public List<Modeltype> getycltype() {
		return workOrderCdmaDao.getycltype();
	}

	@Override
	public List<Modeltype> getywdltype() {
		return workOrderCdmaDao.getywdltype();
	}

	
	@Override
	public List<WorkOrderCdma> selecttaskWorkOrdercw(Map<String,Object> map) {	
		Map<String,String> sqlmap=new HashMap<String, String>();	
		String sql="select serial_cdma, appeal_status, dir_num, wx_serial, date_format(complaint_time,'%Y-%m-%d %H:%i:%s')  complaintTime, complaint_type, complaint_service,"+
			    " complaint_reason, appealing_num, receipt_op_id, pretreat_type, pretreat_result1,"+
			    " date_format(pretreat_time,'%Y-%m-%d %H:%i:%s') pretreatTime, pretreat_remark, tl_op_id, date_format(tl_time,'%Y-%m-%d %H:%i:%s') tlTime, complaint_type2, complaint_service2,"+ 
			    " complaint_reason2, is_zhuan, pretreat_remark2, deal_, pretreat_type2, pretreat_op_id,"+ 
			    " pretreat_dir, date_format(dispatch_time,'%Y-%m-%d %H:%i:%s')dispatchTime , pretreat_result2, date_format(repair_time,'%Y-%m-%d %H:%i:%s') repairTime, repair_op_id, satisfy,"+ 
			    " is_english, complaint_addr, dispach_op"+
			    " from t_workorder_cdma tw"+
			    " where  1=1"+			   
		" and serial_cdma not in (select WORKORDER_ID from t_qc where"+
		" ENABLED='1' and WORKORDER_ID is not null and (QC_TYPE=3 or QC_TYPE=8))"+
		" and ISTASK='0'";
		String startdate=map.get("startdate").toString();
		String stopdate=map.get("stopdate").toString();
		String sqldate=" and DATE_FORMAT(complaint_time,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+startdate+"','%Y-%m-%d %H:%i:%s')"+ 
			" and DATE_FORMAT('"+stopdate+"','%Y-%m-%d %H:%i:%s')";		
		String checkcount=map.get("checkcount").toString();	//受理员每天被领取限制次数
		String taskId=map.get("taskId").toString();
		Map<String,String> qcusermap=new HashMap<String, String>();
		String ndate=DateUtil.getDateStr(new Date());
		qcusermap.put("cDate", ndate);
		qcusermap.put("taskId", taskId);
		String userid=map.get("userid").toString();
		String[] us=userid.split(",");
		Set<String> userset=new HashSet<String>();
		Set<String> notuserset=new HashSet<String>();
		for (String string : us) {	
			userset.add(string);
		}	
		String workidnosql="";//去除受理员领满的工号	
		List<String> worklist=userInfoDao.getsmailWorkidbyuserid(userset);		
		String woridsql="";
		if(worklist!=null){ //拼小工号sql
			woridsql=" and substring(receipt_op_id,3) in (";
			//woridsql=" and receipt_op_id in (";			
			String workidinsql="";
			for (String string : worklist) {
				qcusermap.put("teluser", string);	
				qcusermap.put("ordertype", "2");
				int	qcusercont=taskDao.countusertask(qcusermap);
				if(qcusercont>=Integer.parseInt(checkcount)){
					notuserset.add(string);
				}	
				workidinsql=workidinsql+"'"+string+"'"+",";
			}		
			if(worklist.size()>1){
				woridsql=woridsql+workidinsql.substring(0, workidinsql.length()-1)+")";
			}else{
				woridsql=woridsql+workidinsql+")";
			}
			if(notuserset.size()>=1){//存在领满的受理员
				workidnosql=" and substring(receipt_op_id,3) not in (";
				//workidnosql="and receipt_op_id not in (";
				String workidnotinsql="";
			for (String string : notuserset) {
				workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
			}
			workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
			}				
		}
		 String order112sql=get112ordersql(map);//112工单sql	
		String topsql=" limit 10";
		sqlmap.put("ordercwsql", sql+sqldate+woridsql+workidnosql+order112sql+topsql);
		System.out.println(sql+sqldate+woridsql+workidnosql+order112sql+topsql);
		return workOrderCdmaDao.selecttaskWorkOrdercw(sqlmap);
	}
	/**
	 *获取c网工单sql
	 * @param map
	 * @return
	 */
	public String get112ordersql(Map<String,Object> map){
		String order112sql="";	
		String sgdltypesql=""; //申告业务大类
		String[] sgdltype=(String[])map.get("sgdltype");
		if(sgdltype!=null){
			sgdltypesql=" and complaint_type in (";
			String sgdltypelist="";
			for (String string : sgdltype) {
				sgdltypelist=sgdltypelist+"'"+string+"'"+",";
			}
			sgdltypesql=sgdltypesql+sgdltypelist.substring(0, sgdltypelist.length()-1)+")";		
		}
		String sgxltypesql=""; //申告业务小类
		String[] sgxltype=(String[])map.get("sgxltype");
		if(sgxltype!=null){
			sgxltypesql=" and complaint_service in (";
			String sgxltypelist="";
			for (String string : sgxltype) {
				sgxltypelist=sgxltypelist+"'"+string+"'"+",";
			}
			sgxltypesql=sgxltypesql+sgxltypelist.substring(0, sgxltypelist.length()-1)+")";	
		}
		
		String sgxxtypesql=""; //申告现象
		String[] sgxxtype=(String[])map.get("sgxxtype");
		if(sgxxtype!=null){
			sgxxtypesql=" and complaint_reason in (";
			String sgxxlist="";
			for (String string : sgxxtype) {
				sgxxlist=sgxxlist+"'"+string+"'"+",";
			}		
				sgxxtypesql=sgxxtypesql+sgxxlist.substring(0, sgxxlist.length()-1)+")";		
		}
		String clfstypesql=""; //一级处理方式
		String[] clfstype=(String[])map.get("clfstype");
		if(clfstype!=null){
			clfstypesql=" and pretreat_type in (";
			String clfstypelist="";
			for (String string : clfstype) {
				clfstypelist=clfstypelist+"'"+string+"'"+",";
			}		
			clfstypesql=clfstypesql+clfstypelist.substring(0, clfstypelist.length()-1)+")";
		}
		
		String jadmtypesql=""; //一级结案代码
		String[] jadmtype=(String[])map.get("jadmtype");
		if(jadmtype!=null){
			jadmtypesql=" and pretreat_result1 in (";
			String jadmtypelist="";
			for (String string : jadmtype) {
				jadmtypelist=jadmtypelist+"'"+string+"'"+",";
			}
			jadmtypesql=jadmtypesql+jadmtypelist.substring(0, jadmtypelist.length()-1)+")";		
		}
		
		String secsgxxtypesql=""; //二级申告现象
		String[] secsgxxtype=(String[])map.get("secsgxxtype");
		if(secsgxxtype!=null){
			secsgxxtypesql=" and complaint_reason2 in (";
			String secsgxxtypelist="";
			for (String string : secsgxxtype) {
				secsgxxtypelist=secsgxxtypelist+"'"+string+"'"+",";
			}
			secsgxxtypesql=secsgxxtypesql+secsgxxtypelist.substring(0, secsgxxtypelist.length()-1)+")";
		}
		
		String zejtypesql=""; //是否应该转二级
		String[] zejtype=(String[])map.get("zejtype");
		if(zejtype!=null){
			zejtypesql=" and is_zhuan in (";
			String zejtypelist="";
			for (String string : zejtype) {
				zejtypelist=zejtypelist+"'"+string+"'"+",";
			}
			zejtypesql=zejtypesql+zejtypelist.substring(0, zejtypelist.length()-1)+")";		
		}
		
		String ycltypesql=""; //二级预处理结案代码
		String[] ycltype=(String[])map.get("ycltype");
		if(ycltype!=null){
			ycltypesql=" and pretreat_result2 in (";
			String ycltypelist="";
			for (String string : ycltype) {
				ycltypelist=ycltypelist+"'"+string+"'"+",";
			}		
			ycltypesql=ycltypesql+ycltypelist.substring(0, ycltypelist.length()-1)+")";
		}
		
	
		
		order112sql=sgdltypesql+sgxltypesql+sgxxtypesql+clfstypesql+jadmtypesql+secsgxxtypesql+zejtypesql+ycltypesql;
		return order112sql;
	}

	@Override
	public String updlinqucworder(Map<String, Object> map) {
		String teluserId=map.get("teluserId").toString();//质检员id
		String taskId=map.get("taskId").toString();//任务id
		String serial_cdma=map.get("serial_cdma").toString();//工单id
		String teluser=map.get("teluser").toString();//受理员工号
		Task task=(Task)map.get("task");
		String vmsg="描述信息";
		Map<String,String> telqcusermap=new HashMap<String, String>();
		String ndate=DateUtil.getDateStr(new Date());
		telqcusermap.put("cDate", ndate);
		telqcusermap.put("taskId", taskId);
		String checkcount=task.getCheckcount();//受理员每天被领取限制次数	
		telqcusermap.put("teluser", teluser);	
		int	qcusercont=taskDao.countusertask(telqcusermap);//获取受理员被领取数
		if(qcusercont<Integer.parseInt(checkcount)){		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		int countDay= DateUtil.getDays(formatter.parse(task.getTaskStartTime()
				.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
				.substring(0, 10), pos1));	
		String csrTopDCount=task.getCsrTopDCount();//质检员任务规格
		
		int sumtask112order=(countDay+1)*Integer.parseInt(csrTopDCount);//总任务数
		Map<String,String> qcusermap=new HashMap<String, String>();
		qcusermap.put("taskId", taskId);
		qcusermap.put("qcUser", teluserId);
		qcusermap.put("taskType", "2");//c网工单
		List<Task> qcuserList =	taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息
		int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
		int linqusum=Integer.parseInt(qcuserList.get(0).getGetRecordCount());//领取的任务工单数
		Task ustask=new Task();
		if(sumtask112order-linqusum>0){ //判断任务是否已领完		
			Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数 		
			Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况 	
			getordermap.put("taskUserId",taskuserid+"");
			String todate=DateUtil.getDateStr(new Date());
			getordermap.put("taskTime", todate);	
			String getordercount=taskDao.recordCount(getordermap); //获取当天领取数
			if("".equals(getordercount)||getordercount==null){
				getordercount="0";
			}
			int nowdateorder=Integer.parseInt(csrTopDCount)-Integer.parseInt(getordercount);//当天任务未领完数量
			if(nowdateorder>0){//判断当天任务是否已领满
				Task updTask=new Task();
				updTask.setTaskStatus("2");	
				updTask.setTaskId(Integer.parseInt(taskId));
				taskDao.updcworderstatus(updTask); //修改任务主表状态为执行中
				ustask.setGetRecordCount(linqusum+1+"");
				ustask.setTaskId(Integer.parseInt(taskId));
				ustask.setQcUser(teluserId);
				ustask.setEachQcuserStatus("2");
				ustask.setTasktype("2");//c网工单
				taskDao.update112ordertaskUser(ustask); //修改任务员工中间表
				completemap.put("cDate", todate);	
				completemap.put("taskId", taskId);
				completemap.put("tasktype", "2");//c网工单
				completemap.put("taskuserId", taskuserid+"");
				completemap.put("getRecordCount", (Integer.parseInt(getordercount)+1)+"");
				taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表
				Task tasks=new Task();
				tasks.setTaskId(Integer.parseInt(taskId));
				tasks.setTaskuserId(taskuserid);
				tasks.setIsQc("0");
				tasks.setcDate(todate);//serial_cdma
				tasks.setTelUser(teluser);
				tasks.setOrderid(serial_cdma);
				tasks.setTasktype("2");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
				taskDao.insertToTaskrecord(tasks);
				Map<String,String> statusmap=new HashMap<String, String>();
				statusmap.put("serial_cdma", serial_cdma);
				workOrderCdmaDao.updordercwstatus(statusmap);//修改c网工单主表为任务工单			
				vmsg="领取成功";
				if(nowdateorder==1){
                    vmsg="领取成功,今日任务已领满"; 
                }
			}else{
				vmsg=todate+"任务已领满,无法继续领取";	
			}
		}else{
			vmsg="任务已领满,无法继续领取";	
		}	
		}else{
			vmsg="该受理员当前被领取数已满,无法继续领取!";
		}
		return vmsg;
	}

	@Override
	public Map<String, String> updalllinqucworder(Map<String, Object> map) {
		String teluserId=map.get("teluserId").toString();//质检员id
		String countDay=map.get("countDay").toString();//任务天数
		String taskId=map.get("taskId").toString(); //任务id
		String csrTopDCount=map.get("csrTopDCount").toString();//质检员任务规格
		String checkcount=map.get("checkcount").toString();	//受理员每天被领取限制次数
		TransactionStatus status=null;
	
		String vmsg="描述信息";
		String orderid="";//存工单id逗号隔开
		int sumtask112order=(Integer.parseInt(countDay)+1)*Integer.parseInt(csrTopDCount);//总任务数
		Task task=new Task();
		Map<String,String> sqlmap=new HashMap<String, String>();
		Map<String,String> countsqlmap=new HashMap<String, String>();
		String countsql="select count(1) "+
				 " from t_workorder_cdma tw"+
				 " where  1=1"+			   
				 " and serial_cdma not in (select WORKORDER_ID from t_qc where"+
				 " ENABLED='1' and WORKORDER_ID is not null and (QC_TYPE=3 or QC_TYPE=8))"+
				 " and ISTASK='0'";
		String sql="select"+
				" tw.serial_cdma,receipt_op_id,"+
				" date_format(tw.complaint_time,'%Y-%m-%d %H:%i:%s') complaintTime"+
				" from t_workorder_cdma tw"+
				" where  1=1"+			   
				" and serial_cdma not in (select WORKORDER_ID from t_qc where"+
				" ENABLED='1' and WORKORDER_ID is not null and (QC_TYPE=3 or QC_TYPE=8))"+
				" and ISTASK='0'";
				String startdate=DateUtil.getDateStr(new Date()); 	
				String	cstartdate=Util.getSpecifiedDayBefore(startdate)+" 00:00:00";
				String	cstopdate=Util.getSpecifiedDayBefore(startdate)+" 23:59:59";
				String countsqldate=" and DATE_FORMAT(tw.complaint_time,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+cstartdate+"','%Y-%m-%d %H:%i:%s')"+ 
					" and DATE_FORMAT('"+cstopdate+"','%Y-%m-%d %H:%i:%s')";
				Map<String,String> telqcusermap=new HashMap<String, String>();
				telqcusermap.put("taskId", taskId);
				String userid=map.get("userid").toString();
				String[] us=userid.split(",");
				Set<String> userset=new HashSet<String>();
				Set<String> notuserset=new HashSet<String>();
				for (String string : us) {
					userset.add(string);
				}
				String workidnosql="";//去除受理员领满的工号	
				List<String> worklist=userInfoDao.getsmailWorkidbyuserid(userset);		
				String woridsql="";
				if(worklist!=null){ //拼小工号sql
					woridsql=" and substring(receipt_op_id,3) in (";
					//woridsql=" and receipt_op_id in (";
					String workidinsql="";
					for (String string : worklist) {
						telqcusermap.put("teluser", string);	
						int	qcusercont=taskDao.countusertask(telqcusermap);
						if(qcusercont>=Integer.parseInt(checkcount)){
							notuserset.add(string);
						}	
						workidinsql=workidinsql+"'"+string+"'"+",";
					}		
					if(worklist.size()>1){
						woridsql=woridsql+workidinsql.substring(0, workidinsql.length()-1)+")";
					}else{
						woridsql=woridsql+workidinsql+")";
					}
					if(notuserset.size()>=1){//存在领满的受理员
						workidnosql=" and substring(receipt_op_id,3) not in (";
					//	workidnosql="and receipt_op_id not in (";
						String workidnotinsql="";
					for (String string : notuserset) {
						workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
					}
					workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
					}			
				}
			    String order112sql=get112ordersql(map);//112工单sql		    
				countsqlmap.put("ordercwcountsql", countsql+countsqldate+woridsql+order112sql);
				int count=workOrderCdmaDao.selecttaskWorkOrdercwcount(countsqlmap);
				Map<String,String> qcusermap=new HashMap<String, String>();
				qcusermap.put("taskId", taskId);
				qcusermap.put("qcUser", teluserId);
				qcusermap.put("taskType", "2");//c网工单
				List<Task> qcuserList =	taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息		
				int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
				int linqusum=Integer.parseInt(qcuserList.get(0).getGetRecordCount());//领取的任务工单数
				if(sumtask112order>linqusum){
				if(count>0){
					//task.setGetRecordCount(sumtask112order-linqusum+"");
					task.setTaskId(Integer.parseInt(taskId));
					task.setQcUser(teluserId);
					task.setEachQcuserStatus("2");
					task.setTasktype("2");
					Task updTask=new Task();
					updTask.setTaskStatus("2");
					updTask.setTasktype("2");
					updTask.setTaskId(Integer.parseInt(taskId));		
					Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数 	
					getordermap.put("taskUserId",taskuserid+"");
					Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况 	
					String tostartdate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天开始时间
					String toenddate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天结束时间
					String sqldate="";
					String ctodate=startdate;
					System.out.println("循环次数:"+(Integer.parseInt(countDay) + 1));
						getordermap.put("taskTime", ctodate);	
						String getordercount=taskDao.recordCount(getordermap); //获取当天领取数
						int nowdateorder=Integer.parseInt(csrTopDCount)-Integer.parseInt(getordercount);//当天任务未领完数量
						if(nowdateorder>0){//判断当天任务是否已领满
							completemap.put("cDate", ctodate);	
							completemap.put("taskId", taskId);
							completemap.put("taskuserId", taskuserid+"");
							for (int i = 0; i < nowdateorder; i++) { //循环一条一条领取		
								tostartdate=tostartdate+"  00:00:00";
								toenddate= toenddate+" 23:59:59";
								Map<String,Object> mapsql=new HashMap<String, Object>();
								mapsql.put("taskId", taskId);
								mapsql.put("ndate", ctodate);
								mapsql.put("userid", userid);
								mapsql.put("checkcount", checkcount);
								String notinusersql=getcountuserlinqu(mapsql);					
								sqldate=" and DATE_FORMAT(tw.complaint_time,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+tostartdate+"','%Y-%m-%d %H:%i:%s')"+ 
										" and DATE_FORMAT('"+toenddate+"','%Y-%m-%d %H:%i:%s')";//获取指定时间任务录音sql
								sqlmap.put("ordercwsql", sql+sqldate+notinusersql+woridsql+order112sql);				
								System.out.println(sql+sqldate+notinusersql+woridsql+order112sql);
								List<WorkOrderCdma> ordertasklist=workOrderCdmaDao.selecttaskWorkOrdercw(sqlmap);//获取任务工单列表
								if(!ordertasklist.isEmpty()){
										//for (int k = 0; k <ordertasklist.size(); k++) { //新增任务工单中间表
									try{
											Task tasks=new Task();
											tasks.setTaskId(Integer.parseInt(taskId));
											tasks.setTaskuserId(taskuserid);
											tasks.setIsQc("0");
											tasks.setcDate(ctodate);
											tasks.setTelUser(ordertasklist.get(0).getReceiptOpId());
											tasks.setOrderid(ordertasklist.get(0).getSerialCdma()+"");
											tasks.setTasktype("2");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
											taskDao.insertToTaskrecord(tasks);
											Map<String,String> statusmap=new HashMap<String, String>();
											statusmap.put("serial_cdma", ordertasklist.get(0).getSerialCdma()+"");
											workOrderCdmaDao.updordercwstatus(statusmap);//修改c网工单主表为任务工单			
											orderid=orderid+ordertasklist.get(0).getSerialCdma()+",";
											String getordercount2=taskDao.recordCount(getordermap); //获取当天领取数
											completemap.put("getRecordCount", 1+Integer.parseInt(getordercount2)+"");//当天领取的总录音数
											taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表
											List<Task> qcuserList2 =taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息		
											int linqusum2=Integer.parseInt(qcuserList2.get(0).getGetRecordCount());//领取的任务工单数
											task.setGetRecordCount(1+linqusum2+"");			
											taskDao.update112ordertaskUser(task); //修改任务员工中间表
											taskDao.updcworderstatus(updTask); //修改任务主表状态为执行中	
											DefaultTransactionDefinition def=new DefaultTransactionDefinition();
											def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务	
											status=txManager.getTransaction(def);
											txManager.commit(status);
									}catch(Exception e){
											vmsg="领取成功,部分数据缺失";
											txManager.rollback(status);			
									}	
																
								}else{
									vmsg=ctodate+"当天任务录音工单已领完";
									break;
								}		
							}			
						}else{
							vmsg=ctodate+"当天任务已领满!";
						}				
						vmsg="领取成功";													
				}else{
					vmsg="没有任务工单";			
				}
				}else{
					vmsg="任务工单已领满,无法继续领取";		
				}
		Map<String,String> msgmap=new HashMap<String, String>();
		msgmap.put("vmsg", vmsg);
		msgmap.put("orderid", orderid);
		return msgmap;
	}
	
	/**
	 * 获取受理员已被领满sql
	 * @param map
	 * @return
	 */
	public String getcountuserlinqu(Map<String,Object> map){
		String taskId=map.get("taskId").toString();
		String ndate=map.get("ndate").toString();
		String userid=map.get("userid").toString();
		String checkcount=map.get("checkcount").toString();	//受理员每天被领取限制次数
		Map<String,String> telqcusermap=new HashMap<String, String>();
		telqcusermap.put("taskId", taskId);
		telqcusermap.put("ndate", ndate);	
		String[] us=userid.split(",");
		Set<String> userset=new HashSet<String>();
		Set<String> notuserset=new HashSet<String>();
		for (String string : us) {
			userset.add(string);
		}
		String workidnosql="";//去除受理员领满的工号	
		List<String> worklist=userInfoDao.getsmailWorkidbyuserid(userset);		
		if(worklist!=null){ //拼小工号sql
			for (String string : worklist) {
				telqcusermap.put("teluser", string);
				telqcusermap.put("ordertype", "2");
				int	qcusercont=taskDao.countusertask(telqcusermap);
				if(qcusercont>=Integer.parseInt(checkcount)){
					notuserset.add(string);
				}	
			}		
			if(notuserset.size()>=1){//存在领满的受理员
				workidnosql=" and substring(receipt_op_id,3) not in (";
			//	workidnosql="and receipt_op_id not in (";
				String workidnotinsql="";
			for (String string : notuserset) {
				workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
			}
			workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
			}			
		}
		return workidnosql;
	}

	@Override
	public List<WorkOrderCdma> getylcwordertaskList(Set<String> orderlist) {
		return workOrderCdmaDao.getylcwordertaskList(orderlist);
	}

	@Override
	public int getylcwordertaskListcount(Set<String> orderlist) {
		return workOrderCdmaDao.getylcwordertaskListcount(orderlist);
	}
	
}
