package cn.sh.ideal.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.dao.IWorkOrder112Dao;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.OrderType;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.util.CommXmlService;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.ICollDataToMap;
import cn.sh.ideal.util.Util;

@Service("workOrder112Service")
public class WorkOrder112ServiceImpl implements IWorkOrder112Service {
	private static Logger log = Logger.getLogger(WorkOrder112ServiceImpl.class);
	@Resource
	private IWorkOrder112Dao workOrder112Dao;
	@Resource
	private IUserInfoDao   userInfoDao;
	@Resource
	private ITaskDao taskDao;
	@Autowired
	private DataSourceTransactionManager txManager;
	
	@Override
	public int deleteByPrimaryKey(Integer workorderId) {
		return workOrder112Dao.deleteByPrimaryKey(workorderId);
	}

	@Override
	public int insert(WorkOrder112 workorder) {
		return workOrder112Dao.insert(workorder);
	}

	@Override
	public int insertSelective(WorkOrder112 workorder) {
		return workOrder112Dao.insertSelective(workorder);
	}

	@Override
	public WorkOrder112 selectByPrimaryKey(Integer workorderId) {
		return workOrder112Dao.selectByPrimaryKey(workorderId);
	}

	@Override
	public List<WorkOrder112> selectWorkOrder112(WorkOrder112 workorder) {
		return workOrder112Dao.selectWorkOrder112(workorder);
	}

	@Override
	public int selectCount(WorkOrder112 workorder) {
		return workOrder112Dao.selectCount(workorder);
	}

	 /**
     * 查询112工单
     * @author niewenqiang
     * **/
   public List<WorkOrder112> selectOrder112(Map<String, String> mapdata){
    	return workOrder112Dao.selectOrder112(mapdata);
    }
    
    
    /**
     * 查询112工单数量
     * @author niewenqiang
     * **/
    public int selectOrder112Count(Map<String, String> mapdata){
    	return workOrder112Dao.selectOrder112Count(mapdata);
    }
    
    
    /**
     * 查询112工单电话小结
     * @author niewenqiang
     * **/
   public  List<WorkTelSum112> select112Xj(WorkTelSum112 worksum112){
    	return workOrder112Dao.select112Xj(worksum112);
    }
    
    /**
     * 查询112工单电话小结数量
     * @author niewenqiang
     * **/
    public int select112XjCount(WorkTelSum112 worksum112){
    	return workOrder112Dao.select112XjCount(worksum112);
    }
    
    /***
     * 查询未关联的录音信息
     * **/
    public List<RecordInfo> getNoGLRecord(RecordInfo recordinfo){
    	return workOrder112Dao.getNoGLRecord(recordinfo);
    }
    
    
    /***
     * 查询未关联的录音信息数量
     * **/
   public  int getNoGLRecordCount(RecordInfo recordinfo){
    	return workOrder112Dao.getNoGLRecordCount(recordinfo);
    }
   

   /***
    * 获取录音播放地址
 * @throws Exception 
    * **/
   public String getRecordFileUrl(String recordFilename,String xmlfilepath){
		try {
			return getRecodeFileUrl(recordFilename,xmlfilepath);
		} catch (Exception e) {
			log.error(e);
		}
		return "";
   }
   

	/**
	 * 获取录音路径 
	 * @author taoyang
	 * @date 2010-09-02
	 * @param recordSerialNumber
	 * @param xmlfilepath
	 * @return
	 * @throws DocumentException 
	 * @throws Exception
	 */
	public String getRecodeFileUrl(String recordSerialNumber,String xmlfilepath) throws DocumentException{

		final String vid = recordSerialNumber.substring(1, 3);
		String channel = recordSerialNumber.substring(3, 6);
		String date = recordSerialNumber.substring(6, 14);
		StringBuffer fileURL = new StringBuffer();
		
		CommXmlService cxService = new CommXmlService();
		Map fptMap = cxService.getMapByXmlFilePath(xmlfilepath, new ICollDataToMap(){

			public Map collDataToMap(Element root) {
				Map map = new HashMap();
				List list = root.selectNodes("/servers/server/voiceID");
				Iterator iterator_c  = list.iterator();
				while(iterator_c.hasNext()){
					Element ele = (Element)iterator_c.next();
					if(ele.getTextTrim().equals(vid)){
						Element ele_p = ele.getParent();
						Iterator iterator_p = ele_p.elementIterator();
						while(iterator_p.hasNext()){
							Element ele_c = (Element)iterator_p.next();
							map.put(ele_c.getName(), ele_c.getTextTrim());
						}
						break;
					}
				}
				return map;
			}
		});
		
		if(fptMap.size()!=0){
			String remotefilename =  recordSerialNumber + ".wav";
			String ip = fptMap.get("ip").toString();
			int port = Integer.parseInt(fptMap.get("port").toString());
			String user = fptMap.get("username").toString();
			String password = fptMap.get("password").toString();
			String path = fptMap.get("dir").toString() +"/" + date +"/"+vid +"/" + channel ;
			/*ftp://lxxz:rec123abc@10.7.241.10\vox\lxxz03\030\e0303020100818101045.wav
			fileURL.append(user).append(":").append(password)
			.append("@").append(ip).append("/").append(path)
			.append("/").append(remotefilename);*/
			fileURL.append("/").append(path).append("/").append(remotefilename);
		}
		
		return fileURL.toString();
	}

	@Override
	public int insert112sum(WorkTelSum112 record) {
		return workOrder112Dao.insert112sum(record);
	}

	@Override
	public List<String> getDeclarationBigType() {
		return workOrder112Dao.getDeclarationBigType();
	}
	
	/**
     * 获取获取结案方式
     * */
     public List<String> getCloseedWay(){
    	 return workOrder112Dao.getCloseedWay();
     }
     
     /**
      * 获取受理来源
      * */
    public List<String> getAcceptedSource(){
    	 return workOrder112Dao.getAcceptedSource();
     }
     
     /**
      * 获取派修方向
      * */
     public List<String> getrRepairDirection(){
    	 return workOrder112Dao.getrRepairDirection();
     }
     
     /**
      * 获取三级故障大类
      * */
    public List<String> getThreeErrorType(){
    	 return workOrder112Dao.getThreeErrorType();
     }

	@Override
	public WorkTelSum112 get112jbyid(Integer autoId) {
		return workOrder112Dao.get112jbyid(autoId);
	}

	@Override
	public List<String> get112xjtype() {
		return workOrder112Dao.get112xjtype();
	}

	@Override
	public List<String> getxjbstype() {
		return workOrder112Dao.getxjbstype();
	}

	@Override
	public List<Modeltype> getqjtype() {
		return workOrder112Dao.getqjtype();
	}

	@Override
	public List<Modeltype> getsgxxtype() {	
		return workOrder112Dao.getsgxxtype();
	}

	@Override
	public List<Modeltype> getcsdmtype() {
		return workOrder112Dao.getcsdmtype();
	}

	@Override
	public List<Modeltype> getsgbztype() {
		return workOrder112Dao.getsgbztype();
	}

	@Override
	public List<Modeltype> getlxinfotype() {
		return workOrder112Dao.getlxinfotype();
	}

	@Override
	public List<Modeltype> getgzxftype() {
		return workOrder112Dao.getgzxftype();
	}

	@Override
	public List<Modeltype> getDeclarationBigType2() {
		return workOrder112Dao.getDeclarationBigType2();
	}

	@Override
	public List<Modeltype> getCloseedWay2() {
		return workOrder112Dao.getCloseedWay2();
	}

	@Override
	public List<Modeltype> getAcceptedSource2() {
		return workOrder112Dao.getAcceptedSource2();
	}

	@Override
	public List<Modeltype> getrRepairDirection2() {
		return workOrder112Dao.getrRepairDirection2();
	}

	@Override
	public List<Modeltype> getThreeErrorType2() {
		return workOrder112Dao.getThreeErrorType2();
	}

	@Override
	public List<WorkOrder112> selecttaskWorkOrder112(Map<String,Object> map) {	
		Map<String,String> sqlmap=new HashMap<String, String>();	
		String sql="select"+
		" t.WORKORDER_ID, t.ERROR_NO, t.IS_GW, t.RC, t.BRANCH, t.SITES,"+
		" date_format(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') DECLARATION_TIME,"+
		" t.BUSINESS_TYPE,"+
		" t.DECLARATION_BIG_TYPE, t.DECLARATION_DESCRIPTION, t.DECLARATION_REMARK, t.TEST_CODE, t.TEST_RESULT,"+
		" t.DECLARATION_LINKINFO, t.IS_SECOND_REDEAL, t.CLOSEED_WAY,"+
		" t.CLOSEED_TIMELENGTH, t.ACCEPTED_SOURCE,"+
		" t.FIRST_AGENT_USERID, t.FIRST_REDEAL_WORKID, t.FIRST_REDEAL_SUGGEST, t.FIRST_REDEAL_CLOSEDCODE,"+
		" t.SECOND_REDEAL_CLOSEDCODE, t.SECOND_REDEAL_REMARK, t.SECOND_WORKID,"+
		" date_format(t.SECOND_CLOSETIME,'%Y-%m-%d %H:%i:%s') SECOND_CLOSETIME,"+
		" date_format(t.REPAIR_TIME,'%Y-%m-%d %H:%i:%s') REPAIR_TIME,"+
		" t.REPAIR_WORKID, t.REPAIR_DIRECTION, t.REPAIR_CENTER, t.THREE_ERROR_TYPE,"+
		 " t.THREE_ERROR_REPAIR_CODE,"+
		 " t.THREE_WORKID,"+
		" date_format(t.THREE_REPAIR_TIME,'%Y-%m-%d %H:%i:%s') THREE_REPAIR_TIME"+
		" from t_workorder_info_112  t"+
		" where 1=1"+
		" and t.WORKORDER_ID not in (select WORKORDER_ID from t_qc where"+
		" ENABLED='1' and WORKORDER_ID is not null and (QC_TYPE=2 or QC_TYPE=7))"+
		" and t.ISTASK='0'";
		String startdate=map.get("startdate").toString();
		String stopdate=map.get("stopdate").toString();
		String sqldate=" and DATE_FORMAT(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+startdate+"','%Y-%m-%d %H:%i:%s')"+ 
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
			woridsql=" and substring(FIRST_AGENT_USERID,3) in (";
			//woridsql=" and FIRST_AGENT_USERID in (";			
			String workidinsql="";
			for (String string : worklist) {
				qcusermap.put("teluser", string);	
				int	qcusercont=taskDao.countusertask(qcusermap);
				if(qcusercont>=Integer.parseInt(checkcount)){
					notuserset.add(string);
				}	
				workidinsql=workidinsql+"'"+string+"'"+",";
			}		
			if(worklist.size()>=1){
				woridsql=woridsql+workidinsql.substring(0, workidinsql.length()-1)+")";
			}else{
				woridsql=woridsql+workidinsql+")";
			}
			if(notuserset.size()>=1){//存在领满的受理员
				workidnosql=" and substring(FIRST_AGENT_USERID,3) not in (";
				//workidnosql="and FIRST_AGENT_USERID not in (";
				String workidnotinsql="";
			for (String string : notuserset) {
				workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
			}
			workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
			}				
		}
		 String order112sql=get112ordersql(map);//112工单sql	
		String topsql=" limit 10";
		sqlmap.put("order112sql", sql+sqldate+woridsql+workidnosql+order112sql+topsql);
		System.out.println(sql+sqldate+woridsql+workidnosql+order112sql+topsql);
		return workOrder112Dao.selecttaskWorkOrder112(sqlmap);
	}
	
/*	*//**
	 * 一键领取112工单
	 *//*
	@Override
	public Map<String,String> updalllinqu112order(Map<String,Object> map) {
		String teluserId=map.get("teluserId").toString();//质检员id
		String countDay=map.get("countDay").toString();//任务天数
		String taskId=map.get("taskId").toString(); //任务id
		String csrTopDCount=map.get("csrTopDCount").toString();//质检员任务规格
		String vmsg="描述信息";
		String blag="";//1.成功2.失败
		String orderid="";//存工单id逗号隔开
		int sumtask112order=(Integer.parseInt(countDay)+1)*Integer.parseInt(csrTopDCount);//总任务数
		Task task=new Task();
		Map<String,String> sqlmap=new HashMap<String, String>();
		Map<String,String> countsqlmap=new HashMap<String, String>();
		String countsql="select count(1) "+
				" from t_workorder_info_112  t"+
				" where 1=1"+
				" and t.WORKORDER_ID not in (select WORKORDER_ID from t_qc where"+
				" ENABLED='1' and WORKORDER_ID is not null)"+
				" and t.ISTASK='0'";
		String sql="select"+
				" t.WORKORDER_ID,FIRST_AGENT_USERID,"+
				" date_format(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') DECLARATION_TIME"+
				" from t_workorder_info_112  t"+
				" where 1=1"+
				" and t.WORKORDER_ID not in (select WORKORDER_ID from t_qc where"+
				" ENABLED='1' and WORKORDER_ID is not null)"+
				" and t.ISTASK='0'";
				String startdate=map.get("startdate").toString();//任务开始时间
				String stopdate=map.get("stopdate").toString();//任务结束时间	
				String	cstartdate=Util.getSpecifiedDayBefore(startdate)+" 00:00:00";
				String	cstopdate=Util.getSpecifiedDayBefore(stopdate)+" 23:59:59";
				String countsqldate=" and DATE_FORMAT(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+cstartdate+"','%Y-%m-%d %H:%i:%s')"+ 
					" and DATE_FORMAT('"+cstopdate+"','%Y-%m-%d %H:%i:%s')";			
				String userid=map.get("userid").toString();
				String[] us=userid.split(",");
				Set<String> userset=new HashSet<String>();
				for (String string : us) {
					userset.add(string);
				}
				List<String> worklist=userInfoDao.getsmailWorkidbyuserid(userset);		
				String woridsql="";
				if(worklist!=null){ //拼小工号sql
					//woridsql=" and substring(FIRST_AGENT_USERID,3) in (";
					woridsql=" and FIRST_AGENT_USERID in (";
					String workidinsql="";
					for (String string : worklist) {
						workidinsql="'"+string+"'"+",";
					}		
					if(worklist.size()>1){
						woridsql=woridsql+workidinsql.substring(0, workidinsql.length()-1)+")";
					}else{
						woridsql=woridsql+workidinsql+")";
					}		
				}
			    String order112sql=get112ordersql(map);//112工单sql		
				countsqlmap.put("order112countsql", countsql+countsqldate+woridsql+order112sql);
				int count=workOrder112Dao.selecttaskWorkOrder112count(countsqlmap);
				Map<String,String> qcusermap=new HashMap<String, String>();
				qcusermap.put("taskId", taskId);
				qcusermap.put("qcUser", teluserId);
				List<Task> qcuserList =	taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息		
				int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
				int linqusum=Integer.parseInt(qcuserList.get(0).getGetRecordCount());//领取的任务工单数
				if(count>0){
				if(sumtask112order-linqusum>0){ //判断任务是否已领完
					if(count>(sumtask112order-linqusum)){//判断任务数是否大于剩余未领取数
						task.setGetRecordCount(sumtask112order-linqusum+"");	
						}else{
						task.setGetRecordCount(count+"");
						}	
					//task.setGetRecordCount(sumtask112order-linqusum+"");
					task.setTaskId(Integer.parseInt(taskId));
					task.setQcUser(teluserId);
					task.setEachQcuserStatus("2");
					taskDao.update112ordertaskUser(task); //修改任务员工中间表
					Task updTask=new Task();
					updTask.setTaskStatus("2");
					updTask.setTaskId(Integer.parseInt(taskId));
					taskDao.upd112orderstatus(updTask); //修改任务主表状态为执行中
					Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数 	
					getordermap.put("taskUserId",taskuserid+"");
					Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况 	
					String tostartdate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天开始时间
					String toenddate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天结束时间
					String sqldate="";
					String ctodate=startdate;
					for (int j = 0; j < Integer.parseInt(countDay) + 1; j++) {	//修改质检员任务完成情况表领取录音数
						tostartdate=tostartdate+"  00:00:00";
						toenddate= toenddate+" 23:59:59";
						getordermap.put("taskTime", ctodate);	
						String getordercount=taskDao.recordCount(getordermap); //获取当天领取数
						int nowdateorder=Integer.parseInt(csrTopDCount)-Integer.parseInt(getordercount);//当天任务未领完数量
						if(nowdateorder>0){//判断当天任务是否已领满
							completemap.put("cDate", ctodate);	
							completemap.put("taskId", taskId);
							completemap.put("taskuserId", taskuserid+"");
							sqldate=" and DATE_FORMAT(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+tostartdate+"','%Y-%m-%d %H:%i:%s')"+ 
									" and DATE_FORMAT('"+toenddate+"','%Y-%m-%d %H:%i:%s')";//获取指定时间任务录音sql
							sqlmap.put("order112sql", sql+sqldate+woridsql+order112sql);				
							//int counts=workOrder112Dao.selecttaskWorkOrder112count(countsqlmap);//任务对应天数领取的录音数
							System.out.println(sql+sqldate+woridsql+order112sql);
							List<WorkOrder112> ordertasklist=workOrder112Dao.selecttaskWorkOrder112(sqlmap);//获取任务工单列表
							if(ordertasklist!=null){
							int counts=ordertasklist.size();
							if(counts>nowdateorder){
								completemap.put("getRecordCount", nowdateorder+"");
							}else{
								completemap.put("getRecordCount", counts+"");
							}	
							taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表
							sqlmap.put("order112sql", sql+sqldate+woridsql+order112sql);			
						
							for (int k = 0; k <ordertasklist.size(); k++) { //新增任务工单中间表
								Task tasks=new Task();
								tasks.setTaskId(Integer.parseInt(taskId));
								tasks.setTaskuserId(taskuserid);
								tasks.setIsQc("0");
								tasks.setcDate(ctodate);
								tasks.setTelUser(ordertasklist.get(k).getFirstAgentUserid());
								tasks.setOrderid(ordertasklist.get(k).getWorkorderId()+"");
								tasks.setTasktype("1");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
								taskDao.insertToTaskrecord(tasks);
								Map<String,String> statusmap=new HashMap<String, String>();
								statusmap.put("workorderId", ordertasklist.get(k).getWorkorderId()+"");
								workOrder112Dao.updorder112status(statusmap);//修改112工单主表为任务工单
								orderid=orderid+ordertasklist.get(k).getWorkorderId()+",";
							}
							}else{
								vmsg=ctodate+"当天没有任务录音";
								blag="1";
							}
							ctodate = Util.getSpecifiedDayAfter(ctodate);
							tostartdate=Util.getSpecifiedDayAfter(tostartdate);
							toenddate=Util.getSpecifiedDayAfter(toenddate);			
							
						}else{
							vmsg=ctodate+"当天任务已领满!";
							blag="1";
						}	
						vmsg="领取成功";
						blag="1";
					}						
				}else{			
					vmsg="任务工单已领满,无法继续领取";
					blag="1";
				}	
				}else{
					vmsg="没有任务工单";
					blag="2";
				}
		Map<String,String> msgmap=new HashMap<String, String>();
		msgmap.put("vmsg", vmsg);
		msgmap.put("blag", blag);
		msgmap.put("orderid", orderid);
		return msgmap;
	}
	*/
	
	/**
	 * 一键领取112工单
	 */
	@Override
	public Map<String,String> updalllinqu112order(Map<String,Object> map) {
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
				" from t_workorder_info_112  t"+
				" where 1=1"+
				" and t.WORKORDER_ID not in (select WORKORDER_ID from t_qc where"+
				" ENABLED='1' and WORKORDER_ID is not null)"+
				" and t.ISTASK='0'";
		String sql="select"+
				" t.WORKORDER_ID,FIRST_AGENT_USERID,"+
				" date_format(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') DECLARATION_TIME"+
				" from t_workorder_info_112  t"+
				" where 1=1"+
				" and t.WORKORDER_ID not in (select WORKORDER_ID from t_qc where"+
				" ENABLED='1' and WORKORDER_ID is not null)"+
				" and t.ISTASK='0'";
				//String startdate=map.get("startdate").toString();//任务开始时间
				//String stopdate=map.get("stopdate").toString();//任务结束时间	
				String startdate=DateUtil.getDateStr(new Date()); 
				String	cstartdate=Util.getSpecifiedDayBefore(startdate)+" 00:00:00";
				String	cstopdate=Util.getSpecifiedDayBefore(startdate)+" 23:59:59";
				String countsqldate=" and DATE_FORMAT(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+cstartdate+"','%Y-%m-%d %H:%i:%s')"+ 
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
					woridsql=" and substring(FIRST_AGENT_USERID,3) in (";
					//woridsql=" and FIRST_AGENT_USERID in (";
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
						workidnosql=" and substring(FIRST_AGENT_USERID,3) not in (";
						//workidnosql="and FIRST_AGENT_USERID not in (";
						String workidnotinsql="";
					for (String string : notuserset) {
						workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
					}
					workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
					}			
				}
			    String order112sql=get112ordersql(map);//112工单sql		    
				countsqlmap.put("order112countsql", countsql+countsqldate+woridsql+order112sql);
				int count=workOrder112Dao.selecttaskWorkOrder112count(countsqlmap);
				Map<String,String> qcusermap=new HashMap<String, String>();
				qcusermap.put("taskId", taskId);
				qcusermap.put("qcUser", teluserId);
				List<Task> qcuserList =	taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息		
				int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
				int linqusum=Integer.parseInt(qcuserList.get(0).getGetRecordCount());//领取的任务工单数	
				if(sumtask112order>linqusum){
				if(count>0){
					//task.setGetRecordCount(sumtask112order-linqusum+"");
					task.setTaskId(Integer.parseInt(taskId));
					task.setQcUser(teluserId);
					task.setEachQcuserStatus("2");	
					Task updTask=new Task();
					updTask.setTaskStatus("2");
					updTask.setTaskId(Integer.parseInt(taskId));		
					Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数 	
					getordermap.put("taskUserId",taskuserid+"");
					Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况 	
					String tostartdate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天开始时间
					String toenddate=Util.getSpecifiedDayBefore(startdate);//存任务的具体那一天结束时间
					String sqldate="";
					String ctodate=startdate;	//修改质检员任务完成情况表领取录音数		
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
								sqldate=" and DATE_FORMAT(t.DECLARATION_TIME,'%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT('"+tostartdate+"','%Y-%m-%d %H:%i:%s')"+ 
										" and DATE_FORMAT('"+toenddate+"','%Y-%m-%d %H:%i:%s')";//获取指定时间任务录音sql
								sqlmap.put("order112sql", sql+sqldate+notinusersql+woridsql+order112sql);				
								System.out.println(sql+sqldate+notinusersql+woridsql+order112sql);
								List<WorkOrder112> ordertasklist=workOrder112Dao.selecttaskWorkOrder112(sqlmap);//获取任务工单列表
								if(!ordertasklist.isEmpty()){
										//for (int k = 0; k <ordertasklist.size(); k++) { //新增任务工单中间表
									try{			
											Task tasks=new Task();
											tasks.setTaskId(Integer.parseInt(taskId));
											tasks.setTaskuserId(taskuserid);
											tasks.setIsQc("0");
											tasks.setcDate(ctodate);
											tasks.setTelUser(ordertasklist.get(0).getFirstAgentUserid());
											tasks.setOrderid(ordertasklist.get(0).getWorkorderId()+"");
											tasks.setTasktype("1");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
											taskDao.insertToTaskrecord(tasks);
											Map<String,String> statusmap=new HashMap<String, String>();
											statusmap.put("workorderId", ordertasklist.get(0).getWorkorderId()+"");
											workOrder112Dao.updorder112status(statusmap);//修改112工单主表为任务工单
											orderid=orderid+ordertasklist.get(0).getWorkorderId()+",";			
											String getordercount2=taskDao.recordCount(getordermap); //获取当天领取数
											completemap.put("getRecordCount", 1+Integer.parseInt(getordercount2)+"");//当天领取的总录音数
											taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表
											List<Task> qcuserList2 =	taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息		
											int linqusum2=Integer.parseInt(qcuserList2.get(0).getGetRecordCount());//领取的任务工单数
											task.setGetRecordCount(1+linqusum2+"");	
											taskDao.update112ordertaskUser(task); //修改任务员工中间表
											taskDao.upd112orderstatus(updTask); //修改任务主表状态为执行中	
											DefaultTransactionDefinition def=new DefaultTransactionDefinition();
											def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务	
											status=txManager.getTransaction(def);
											txManager.commit(status);
									}catch(Exception e){
											txManager.rollback(status);
										vmsg="领取成功,部分数据缺失";
										throw e;
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
				int	qcusercont=taskDao.countusertask(telqcusermap);
				if(qcusercont>=Integer.parseInt(checkcount)){
					notuserset.add(string);
				}	
			}		
			if(notuserset.size()>=1){//存在领满的受理员
				workidnosql=" and substring(FIRST_AGENT_USERID,3) not in (";
				//workidnosql="and FIRST_AGENT_USERID not in (";
				String workidnotinsql="";
			for (String string : notuserset) {
				workidnotinsql=workidnotinsql+"'"+string+"'"+",";		
			}
			workidnosql=workidnosql+workidnotinsql.substring(0, workidnotinsql.length()-1)+")";
			}			
		}
		return workidnosql;
	}
	
	
	
	
	/**
	 *获取112工单sql
	 * @param map
	 * @return
	 */
	public String get112ordersql(Map<String,Object> map){
		String order112sql="";	
		String qjtypesql=""; //区局
		String[] qjtype=(String[])map.get("qjtype");
		if(qjtype!=null){
			qjtypesql=" and RC in (";
			String qjlist="";
			for (String string : qjtype) {
				qjlist=qjlist+"'"+string+"'"+",";
			}
				qjtypesql=qjtypesql+qjlist.substring(0, qjlist.length()-1)+")";		
		}
		String sgdltypesql=""; //申告大类
		String[] sgdltype=(String[])map.get("sgdltype");
		if(sgdltype!=null){
			sgdltypesql=" and DECLARATION_BIG_TYPE in (";
			String sgdllist="";
			for (String string : sgdltype) {
				sgdllist=sgdllist+"'"+string+"'"+",";
			}
				sgdltypesql=sgdltypesql+sgdllist.substring(0, sgdllist.length()-1)+")";	
		}
		String sgxxtypesql=""; //申告现象
		String[] sgxxtype=(String[])map.get("sgxxtype");
		if(sgxxtype!=null){
			sgxxtypesql=" and DECLARATION_DESCRIPTION in (";
			String sgxxlist="";
			for (String string : sgxxtype) {
				sgxxlist=sgxxlist+"'"+string+"'"+",";
			}		
				sgxxtypesql=sgxxtypesql+sgxxlist.substring(0, sgxxlist.length()-1)+")";		
		}
		String csdmtypesql=""; //测试代码
		String[] csdmtype=(String[])map.get("csdmtype");
		if(csdmtype!=null){
			csdmtypesql=" and TEST_CODE in (";
			String csdmlist="";
			for (String string : csdmtype) {
				csdmlist=csdmlist+"'"+string+"'"+",";
			}		
				csdmtypesql=csdmtypesql+csdmlist.substring(0, csdmlist.length()-1)+")";
		}
		
		String sgbztypesql=""; //申告备注
		String[] sgbztype=(String[])map.get("sgbztype");
		if(sgbztype!=null){
			sgbztypesql=" and DECLARATION_REMARK in (";
			String sgbzlist="";
			for (String string : sgbztype) {
				sgbzlist=sgbzlist+"'"+string+"'"+",";
			}
				sgbztypesql=sgbztypesql+sgbzlist.substring(0, sgbzlist.length()-1)+")";		
		}
		
		String sglxtypesql=""; //申告类型
		String[] sglxtype=(String[])map.get("sglxtype");
		if(sglxtype!=null){
			sglxtypesql=" and DECLARATION_LINKINFO in (";
			String sglxlist="";
			for (String string : sglxtype) {
				sglxlist=sglxlist+"'"+string+"'"+",";
			}
				sglxtypesql=sglxtypesql+sglxlist.substring(0, sglxlist.length()-1)+")";
		}
		
		String jafstypesql=""; //结案方式
		String[] jafstype=(String[])map.get("jafstype");
		if(jafstype!=null){
			jafstypesql=" and CLOSEED_WAY in (";
			String jafslist="";
			for (String string : jafstype) {
				jafslist=jafslist+"'"+string+"'"+",";
			}
				jafstypesql=jafstypesql+jafslist.substring(0, jafslist.length()-1)+")";		
		}
		
		String sllytypesql=""; //受理来源
		String[] sllytype=(String[])map.get("sllytype");
		if(sllytype!=null){
			sllytypesql=" and ACCEPTED_SOURCE in (";
			String sllylist="";
			for (String string : sllytype) {
				sllylist=sllylist+"'"+string+"'"+",";
			}		
				sllytypesql=sllytypesql+sllylist.substring(0, sllylist.length()-1)+")";
		}
		String pxfxtypesql=""; //派修方向
		String[] pxfxtype=(String[])map.get("pxfxtype");
		if(pxfxtype!=null){
			pxfxtypesql=" and REPAIR_DIRECTION in (";
			String pxfxlist="";
			for (String string : pxfxtype) {
				pxfxlist=pxfxlist+"'"+string+"'"+",";
			}
				pxfxtypesql=pxfxtypesql+pxfxlist.substring(0, pxfxlist.length()-1)+")";		
		}
		
		String sjgztypesql=""; //派修三级故障大类
		String[] sjgztype=(String[])map.get("sjgztype");
		if(sjgztype!=null){
			sjgztypesql=" and THREE_ERROR_TYPE in (";
			String sjgzlist="";
			for (String string : sjgztype) {
				sjgzlist=sjgzlist+"'"+string+"'"+",";
			}
				sjgztypesql=sjgztypesql+sjgzlist.substring(0, sjgzlist.length()-1)+")";			
		}		
		String gzxftypesql=""; //三级故障修复代码
		String[] gzxftype=(String[])map.get("gzxftype");
		if(gzxftype!=null){
			gzxftypesql=" and THREE_ERROR_REPAIR_CODE in (";
			String gzxflist="";
			for (String string : gzxftype) {
				gzxflist=gzxflist+"'"+string+"'"+",";
			}		
			gzxftypesql=gzxftypesql+gzxflist.substring(0, gzxflist.length()-1)+")";
		}	
		
		order112sql=qjtypesql+sgdltypesql+sgxxtypesql+csdmtypesql+sgbztypesql+sglxtypesql+jafstypesql+sllytypesql+pxfxtypesql+sjgztypesql+gzxftypesql;
	return order112sql;
	}

	@Override
	public String updlinqu112order(Map<String, Object> map) {
		String teluserId=map.get("teluserId").toString();//质检员id
		String taskId=map.get("taskId").toString();//任务id
		String orderId=map.get("orderId").toString();//工单id
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
		qcusermap.put("taskType", "1");//c网工单
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
				updTask.setTasktype("1");
				updTask.setTaskId(Integer.parseInt(taskId));
				taskDao.upd112orderstatus(updTask); //修改任务主表状态为执行中
				ustask.setGetRecordCount(linqusum+1+"");
				ustask.setTaskId(Integer.parseInt(taskId));
				ustask.setQcUser(teluserId);
				ustask.setEachQcuserStatus("2");
				taskDao.update112ordertaskUser(ustask); //修改任务员工中间表
				completemap.put("cDate", todate);	
				completemap.put("taskId", taskId);
				completemap.put("tasktype", "1");//c网工单
				completemap.put("taskuserId", taskuserid+"");
				completemap.put("getRecordCount", (Integer.parseInt(getordercount)+1)+"");
				taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表
				Task tasks=new Task();
				tasks.setTaskId(Integer.parseInt(taskId));
				tasks.setTaskuserId(taskuserid);
				tasks.setIsQc("0");
				tasks.setcDate(todate);
				tasks.setTelUser(teluser);
				tasks.setOrderid(orderId);
				tasks.setTasktype("1");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
				taskDao.insertToTaskrecord(tasks);
				Map<String,String> statusmap=new HashMap<String, String>();
				statusmap.put("workorderId", orderId);
				workOrder112Dao.updorder112status(statusmap);//修改112工单主表为任务工单			
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
	public List<WorkOrder112> getyl112ordertaskList(Set<String> orderlist) {
		return workOrder112Dao.getyl112ordertaskList(orderlist);
	}

	@Override
	public int getyl112ordertaskListcount(Set<String> orderlist) {	
		return workOrder112Dao.getyl112ordertaskListcount(orderlist);
	}
	
	@Override
    public List<Modeltype> getBusinessType(){
        return workOrder112Dao.getBusinessType();
    }
    
    @Override
    public List<Modeltype> getTelsumType(){
        return workOrder112Dao.getTelsumType();
        
    }
    @Override
    public List<Modeltype> getErrorSource(){
        return workOrder112Dao.getErrorSource();
    }
}
