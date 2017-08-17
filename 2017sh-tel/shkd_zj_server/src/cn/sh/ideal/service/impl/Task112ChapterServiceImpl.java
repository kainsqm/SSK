/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.dao.ItQc112xjtaskDao;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.TaskChapter;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.service.ITask112ChapterService;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.ToolString;
import cn.sh.ideal.util.Util;

/**
 * 任务业务类
 * 
 * **/
@Service("chapter112Service")
public class Task112ChapterServiceImpl implements ITask112ChapterService {
    
    private static Logger log = Logger.getLogger(TaskServiceImpl.class);
    
    @Resource
    private ItQc112xjtaskDao qc112xjtaskDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IUserInfoDao userInfoDao;
    @Autowired
    private DataSourceTransactionManager txManager;
    
    public List<TaskChapter> gettask112Chapter(TaskChapter task){
        return qc112xjtaskDao.getTask112Chapter(task);
    }
    
    public  int gettask112Chaptercount(TaskChapter task){
        return qc112xjtaskDao.getTask112ChapterCount(task);
    }
    
    public int check112Chaptername(String taskName){
        return qc112xjtaskDao.check112ChapterName(taskName);
    }
    
    public int insert112ChapterTask(TaskChapter task){ 
        if(task.getIsPublish().equalsIgnoreCase("0")){
            qc112xjtaskDao.add112ChapterTask(task); //添加主表
        } else {
            qc112xjtaskDao.add112ChapterTask(task); //添加主表
            String taskStartTime =task.getTaskStartTime();
            int taskid=task.getTaskId();
            int countDay = 0;// 任务天数（开始到结束）
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            countDay = DateUtil.getDays(formatter.parse(task.getTaskStartTime().substring(0, 10), pos),
                    formatter.parse(task.getTaskEndTime().substring(0, 10), pos1));
            String quserid=task.getQcUser();
            String[] qcuserid=quserid.split(",");
            task.setTaskId(task.getTaskId());
            for (String string : qcuserid) {
                task=new TaskChapter();
                task.setTaskId(taskid);
                task.setGetRecordCount("0");
                task.setCompleteCount("0");
                task.setEachQcuserStatus("1");   
                task.setTasktype("3");
                task.setQcUser(string);
                qc112xjtaskDao.insertChapter112User(task);
                int userid=task.getTaskuserId();
                String today = taskStartTime.substring(0, 10);// 哪一天的任务
                for (int j = 0; j < countDay + 1; j++) {        
                    task=new TaskChapter();
                    task.setTaskId(taskid);
                    task.setGetRecordCount("0");
                    task.setCompleteCount("0");
                    task.setTaskuserId(userid);
                    task.setcDate(today);
                    task.setTasktype("3");
                    qc112xjtaskDao.insertToChapter112UserComplete(task);
                    today = Util.getSpecifiedDayAfter(today);
                }           
            }
        }
        return 1;
    }
    
    public int update112ChapterTask(TaskChapter task){
        if(task.getIsPublish().equalsIgnoreCase("0")){
            qc112xjtaskDao.update112ChapterTask(task);
        } else {
            qc112xjtaskDao.update112ChapterTask(task);
            Map<String,String> mapdata=new HashMap<String, String>();
            mapdata.put("taskId", ""+task.getTaskId());
            mapdata.put("taskType", "3");
/*          taskDao.delTaskQcuser(mapdata);
            taskDao.delTaskQcuserComplete(mapdata);*/
            int taskid=task.getTaskId();
            String taskStartTime =task.getTaskStartTime();
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
            for (String string : qcuserid) {
                task=new TaskChapter();
                task.setTaskId(taskid);
                task.setGetRecordCount("0");
                task.setCompleteCount("0");
                task.setEachQcuserStatus("1");   
                task.setTasktype("3");
                String userids=userInfoDao.userIdbyworkId(string);
                task.setQcUser(userids);
                qc112xjtaskDao.insertChapter112User(task);    
                int userid=task.getTaskuserId();
                String today = taskStartTime.substring(0, 10);// 哪一天的任务
                for (int j = 0; j < countDay + 1; j++) {        
                    task=new TaskChapter();
                    task.setTaskId(taskid);
                    task.setGetRecordCount("0");
                    task.setCompleteCount("0");
                    task.setTaskuserId(userid);
                    task.setcDate(today);
                    task.setTasktype("3");
                    qc112xjtaskDao.insertToChapter112UserComplete(task);
                    today = Util.getSpecifiedDayAfter(today);
                }
            }
        }
        return 1;
    }
    
    public int del112ChapterTask(Map<String,String> mapdata){   
        if (!("0".equals(mapdata.get("taskStatus")) || "1".equals(mapdata
            .get("taskStatus")))) {
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("taskId", mapdata.get("taskId"));
            map1.put("ordertype", "3");
            map1.put("taskType", "3");
            // 1：修改112工单任务状态(录音的任务状态，是，否)
            qc112xjtaskDao.release112Telsum(map1);
            // 2：t_qc_taskrecord删除任务工单信息
            taskDao.releaseRecord(map1);
        } else {
            
        }
        int taskId = Integer.parseInt(mapdata.get("taskId"));
        mapdata.put("ordertype", "3");
        mapdata.put("taskType", "3");
        qc112xjtaskDao.del112ChapterTask(taskId);
        taskDao.delTaskQcuser(mapdata);
        taskDao.delTaskQcuserComplete(mapdata);
        return 1;
    }
    
    public TaskChapter get112ChapterTaskbyid(Integer taskId){
        return qc112xjtaskDao.get112ChapterTaskbyid(taskId);
    }
    
    public List<TaskChapter> getExecuteChapterList(TaskChapter taskChapter){
        return qc112xjtaskDao.getExecuteChapterList(taskChapter);
    }
    
    public int getExecuteChapterListCount(TaskChapter taskChapter){
        return qc112xjtaskDao.getExecuteChapterListCount(taskChapter);
    }
    
    public List<TaskChapter> gettaskChapterBytelsum(Map<String,String> map){
        return qc112xjtaskDao.gettaskChapterBytelsum(map);
    }
    
    /**
     * 获取112小结工单
     */
    public List<WorkTelSum112> getReviceChapterList(Map<String,String> map){
        List<WorkTelSum112> telsumList = new ArrayList<>();
        WorkTelSum112 telSum = new WorkTelSum112();
        String taskId = map.get("taskId");
        telSum.setStartdate(map.get("startdate"));
        telSum.setStopdate(map.get("stopdate"));
        String curDate = map.get("curDate");
        //查询对应任务数据
        TaskChapter chapter = qc112xjtaskDao.get112ChapterTaskbyid(Integer.parseInt(taskId));
        String userid=chapter.getTelUser();
        String checkcount = chapter.getCheckCount();
        String[] us=userid.split(",");
        
        //获取受理员的小号
        Set<String> userset=new HashSet<String>();
        for (String string : us) {
            userset.add(string);
        }
        List<String> userIds=userInfoDao.getsmailWorkidbyuserid(userset);  
        telSum.setUserIds(userIds); 
        
        //判断工号是否被领满、 去除工号被领满的工号
        Map<String,String> qcusermap=new HashMap<String, String>();
        String ndate=DateUtil.getDateStr(new Date());
        if(curDate!=null){
            ndate = Util.getSpecifiedDayBefore(curDate);
        }
        qcusermap.put("cDate", ndate);
        qcusermap.put("taskId", taskId);
        List<String> notUserIds = new ArrayList<>(); //超了条数
        for (String string : userIds) {
            qcusermap.put("teluser", string);   
            int qcusercont=taskDao.countusertask(qcusermap);
            if(qcusercont>=Integer.parseInt(checkcount)){
                notUserIds.add(string);
            }
        }
        telSum.setNotUserIds(notUserIds);
        
        String businessType = map.get("businessType"); //业务类型
        String telsumType = map.get("telsumType");//小结类型
        String errorSource = map.get("errorSource");//故障来源
        
        if(businessType!=null && businessType!="" && businessType.length()>0){
            List<String> lists = new ArrayList<>();
            String arr[] = businessType.split(";");
            for (String a : arr) {
                lists.add(a);
            }
            telSum.setBusinessTypeList(lists);
        }
        
        if(telsumType!=null && telsumType!="" && telsumType.length()>0){
            List<String> lists = new ArrayList<>();
            String arr[] = telsumType.split(";");
            for (String a : arr) {
                lists.add(a);
            }
            telSum.setTelsumTypeList(lists);
        }
        
        if(errorSource!=null && errorSource!="" && errorSource.length()>0){
            List<String> lists = new ArrayList<>();
            String arr[] = errorSource.split(";");
            for (String a : arr) {
                lists.add(a);
            }
            telSum.setErrorSourceList(lists);
        }
        telsumList = qc112xjtaskDao.getReviceChapterList(telSum);
        return telsumList;
    }
    
    public String updReviceTask(Map<String,Object> map){
        String vmsg="描述信息";
        String todate=DateUtil.getDateStr(new Date());
        String qcUserId=map.get("qcUserId").toString();//质检员id
        String taskId=map.get("taskId").toString();//任务id
        String telSumId=map.get("telSumId").toString();//工单id
        String teluser=map.get("teluser").toString();//受理员工号
        TaskChapter taskChapter=(TaskChapter)map.get("taskChapter");
        
        //判断受理员是否领取完
        Map<String,String> telUsermap=new HashMap<String, String>();
        String ndate=DateUtil.getDateStr(new Date());
        telUsermap.put("cDate", ndate);
        telUsermap.put("taskId", taskId);
        telUsermap.put("teluser", ToolString.subNumber(teluser));   
        int telUsercount=taskDao.countusertask(telUsermap);
        if(telUsercount>=Integer.parseInt(taskChapter.getCheckCount())){
            vmsg=todate+"该受理员被领取次数已满,无法继续领取"; 
            return vmsg;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        int countDay= DateUtil.getDays(formatter.parse(taskChapter.getTaskStartTime()
                .substring(0, 10), pos), formatter.parse(taskChapter.getTaskEndTime()
                .substring(0, 10), pos1));  
        String csrTopDCount=taskChapter.getCsrTopDCount();//质检员任务规格
        int sumtask112order=(countDay+1)*Integer.parseInt(csrTopDCount);//总任务数
        Map<String,String> qcusermap=new HashMap<String, String>();
        qcusermap.put("taskId", taskId);
        qcusermap.put("qcUser", qcUserId);
        qcusermap.put("taskType", "3");
        List<Task> qcuserList = taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息 
        int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
        int linqusum=Integer.parseInt(qcuserList.get(0).getGetRecordCount());//领取的任务工单数
        Task ustask=new Task();
        if(sumtask112order-linqusum>0){ //判断任务是否已领完
            Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数         
            Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况    
            getordermap.put("taskUserId",taskuserid+"");
            getordermap.put("taskTime", todate);    
            String getordercount=taskDao.recordCount(getordermap); //获取当天领取数
            if("".equals(getordercount)||getordercount==null){
                getordercount="0";
            }
            int nowdateorder=Integer.parseInt(csrTopDCount)-Integer.parseInt(getordercount);//当天任务未领完数量
            if(nowdateorder>0){//判断当天任务是否已领满
                TaskChapter chapter=new TaskChapter();
                chapter.setTaskStatus("2");
                chapter.setTaskId(Integer.parseInt(taskId));
                qc112xjtaskDao.upd112Chapterstatus(chapter); //修改任务主表状态为执行中
                
                ustask.setGetRecordCount(linqusum+1+"");
                ustask.setTaskId(Integer.parseInt(taskId));
                ustask.setQcUser(qcUserId);
                ustask.setEachQcuserStatus("2");
                ustask.setTasktype("3");
                taskDao.update112ordertaskUser(ustask); 
                
                completemap.put("cDate", todate);   
                completemap.put("taskId", taskId);
                completemap.put("taskuserId", taskuserid+"");
                completemap.put("tasktype", "3");
                completemap.put("getRecordCount", (Integer.parseInt(getordercount)+1)+"");
                taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表  
                Task tasks=new Task();
                tasks.setTaskId(Integer.parseInt(taskId));
                tasks.setTaskuserId(taskuserid);
                tasks.setIsQc("0");
                tasks.setcDate(todate);
                tasks.setTelUser(teluser);
                tasks.setTelsum(telSumId);
                tasks.setTasktype("3");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
                //新增一条领取记录
                taskDao.insertToTaskrecord(tasks);
                qc112xjtaskDao.updTelsum112status(telSumId);//修改112小结主表为任务工单-----
                
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
        return vmsg;
    }
    
    /**
     * 一键领取
     */
    @Override
    public Map<String, String> updateOneKeyReciveAll(Map<String,String> map) {
        String vmsg="描述信息";
        String lingquAllId = "";
        Map<String, String> resultMap = new HashMap<>();
        Map<String, String> searchCountMap = new HashMap<>();
        String taskId = map.get("taskId");
        String qcUser = map.get("qcUser");
        TransactionStatus status=null;
        TaskChapter taskChapter = get112ChapterTaskbyid(Integer.parseInt(taskId));
        
        Map<String,String> maps= DateUtil.getNowDateFront(new Date()); 
        String startdate=  maps.get("startdate");
        String stopdate=  maps.get("stopdate");
        searchCountMap.put("taskId", taskId);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String  businessType = taskChapter.getBussniessType();
        String  telsumType = taskChapter.getTelsumType();
        String  gzlyType = taskChapter.getGzlyType();
        if(businessType!=null && !"".equals(businessType)){
            searchCountMap.put("businessType", businessType);
        }else{
            searchCountMap.put("businessType", "");
        }
        if(telsumType!=null &&!"".equals(telsumType)){
            searchCountMap.put("telsumType", telsumType);
        }else{
            searchCountMap.put("telsumType", "");
        }
        if(gzlyType!=null &&!"".equals(gzlyType)){
            searchCountMap.put("gzlyType", gzlyType);
        }else{
            searchCountMap.put("gzlyType", "");
        }
        searchCountMap.put("startdate", startdate);
        searchCountMap.put("stopdate", stopdate);
        List<WorkTelSum112> telSumList = getReviceChapterList(searchCountMap);
        int count = telSumList.size();
        
        Map<String,String> qcusermap=new HashMap<String, String>();
        qcusermap.put("taskId", taskId);
        qcusermap.put("qcUser", qcUser);
        qcusermap.put("taskType", "3");
        List<Task> qcuserList = taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息     
        int taskuserid=qcuserList.get(0).getTaskuserId();//任务员工中间表主键
        
        String ctodate= formatter.format(new Date());
        
        Map<String,String> getordermap=new HashMap<String, String>();//质检员当天领取数     
        getordermap.put("taskUserId",taskuserid+"");
        getordermap.put("taskTime", ctodate);   
        String getordercount=taskDao.recordCount(getordermap); //获取当天领取数
        int nowdateorder=Integer.parseInt(taskChapter.getCsrTopDCount())-Integer.parseInt(getordercount);//当天任务未领完数量
        
        TaskChapter chapter = new TaskChapter();
        if(nowdateorder>0 && count>0){
            Task updTask=new Task();
            updTask.setTaskStatus("2");
            updTask.setTaskId(Integer.parseInt(taskId));        
            
            Map<String,String> completemap=new HashMap<String, String>();//质检员任务完成情况    
            
            completemap.put("cDate", ctodate);  
            completemap.put("taskId", taskId);
            completemap.put("taskuserId", taskuserid+"");
            for (int i = 0; i < nowdateorder; i++) { //循环一条一条领取     
                searchCountMap.put("startdate", startdate);
                searchCountMap.put("stopdate", stopdate);
                searchCountMap.put("curDate", ctodate);
                List<WorkTelSum112> telSumOnedayList = getReviceChapterList(searchCountMap);
                
                if(telSumOnedayList!=null && !telSumOnedayList.isEmpty()){
                    try{
                        Task tasks=new Task();
                        tasks.setTaskId(Integer.parseInt(taskId));
                        tasks.setTaskuserId(taskuserid);
                        tasks.setIsQc("0");
                        tasks.setcDate(ctodate);
                        tasks.setTelUser(telSumOnedayList.get(0).getTelsumWorkid());
                        tasks.setTelsum(telSumOnedayList.get(0).getAutoId()+"");
                        tasks.setTasktype("3");//任务类型1.112工单2.c网工单3.112小结，c网小结4.录音)
                        taskDao.insertToTaskrecord(tasks);
                        
                        qc112xjtaskDao.updTelsum112status(telSumOnedayList.get(0).getAutoId()+"");//修改112小结主表为任务工单
                        
                        completemap.put("cDate", ctodate);   
                        completemap.put("taskId", taskId);
                        completemap.put("taskuserId", taskuserid+"");
                        completemap.put("tasktype", "3");
                        getordermap.put("taskTime", ctodate);   
                        String getordercount2=taskDao.recordCount(getordermap); //获取当天领取数
                        completemap.put("getRecordCount", (Integer.parseInt(getordercount2)+1)+"");
                        taskDao.upd112ordertaskcomplete(completemap);//修改质检员任务完成情况表  
                        List<Task> qcuserList2 = taskDao.queryTaskCompleteInfo(qcusermap); //获取任务员工中间表信息     
                        int linqusum2=Integer.parseInt(qcuserList2.get(0).getGetRecordCount());//领取的任务工单数
                        Task ustask=new Task();
                        ustask.setGetRecordCount(linqusum2+1+"");
                        ustask.setTaskId(Integer.parseInt(taskId));
                        ustask.setQcUser(qcUser);
                        ustask.setEachQcuserStatus("2");
                        ustask.setTasktype("3");
                        taskDao.update112ordertaskUser(ustask);  //修改任务员工中间表
                        
                        chapter.setTaskId(Integer.parseInt(taskId));
                        chapter.setQcUser(qcUser);
                        chapter.setTaskStatus("2");  
                        qc112xjtaskDao.upd112Chapterstatus(chapter); //修改任务主表状态为执行中
                        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
                        status=txManager.getTransaction(def);
                        txManager.commit(status);
                        
                        lingquAllId = lingquAllId + telSumOnedayList.get(0).getAutoId() +",";
                    }catch(Exception e){
                        txManager.rollback(status);
                        vmsg="领取成功,部分数据缺失";
                        throw e;
                    }   
                }else{
                    vmsg=ctodate+"当天任务录音工单已领完";
                }                   
            } 
            vmsg="领取成功";
        }else {
            if(nowdateorder<=0){
                vmsg=ctodate+"当天任务已领满!";
            }else{
                vmsg = "领取失败，没有可领取的112电话小结";
            }
        }
        resultMap.put("vmsg", vmsg);
        resultMap.put("lingquAllId", lingquAllId);
        return resultMap;
    }
    
    public List<WorkTelSum112> getYlChapterList(Set<String> orderlist){
        return qc112xjtaskDao.getYlChapterList(orderlist);
    }
    
    public Set<String> getyl112TelSumtaskidList(Map<String, String> map){
        return qc112xjtaskDao.getyl112TelSumtaskidList(map);
    }
    
    public int getYlChapterListCount(Set<String> orderlist){
        return qc112xjtaskDao.getYlChapterListCount(orderlist);
    }
    
    public List<Task> getFinishChapterList(Map<String,String> map){
        return qc112xjtaskDao.getFinishChapterList(map);
    }
    public int getFinishChapterListCount(Map<String,String> map){
        return qc112xjtaskDao.getFinishChapterListCount(map);
    }
    
    public List<CdmaTaskComplete> queryTaskCompleteInfo( Map<String, String> mapdata) {
        return qc112xjtaskDao.queryTaskCompleteInfo(mapdata);
    }
    public int queryTaskCompleteInfoCount(Map<String, String> mapdata) {
        return qc112xjtaskDao.queryTaskCompleteInfoCount(mapdata);
    }
    
    public List<Task> getChapterFinishDetails(Map<String,String> map){
        return qc112xjtaskDao.getChapterFinishDetails(map);   
    }
    public int getChapterFinishDetailsCount(Map<String,String> map){
        return qc112xjtaskDao.getChapterFinishDetailsCount(map);
    }
    
    @Override
    public String updrelease112ChapterByUser(Map<String, String> map) {
        String qcUserItem=map.get("qcUserItem");
        String taskId=map.get("taskId");
        String[] qcUser=qcUserItem.split(",");
        String releaseList = "";
        for (String string : qcUser) {
            releaseList +=  string+ ":[";
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("taskId", taskId);
            map1.put("qcUser", string);
            // 1：修改112小结任务状态(录音的任务状态，是，否)
            qc112xjtaskDao.release112Chapter(map1);      
            Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
            ordermap.put("qcUser", string); 
            ordermap.put("taskId", taskId);
            ordermap.put("isqc", "0");
            List<String> getReleaseList = qc112xjtaskDao.selectRecordForLog(ordermap);
            for (String re : getReleaseList) {
                releaseList += re +",";
            }
            qc112xjtaskDao.releaseRecord(ordermap);
            Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
            qcusermap.put("taskId", taskId);
            qcusermap.put("qcUser", string);
            qcusermap.put("taskType", "3");
            List<Task> task=taskDao.order112taskqcuserbyuserid(qcusermap);
            Map<String,String> updqcusermap=new HashMap<String, String>();//用户修改任务员工中间表信息
            if("0".equals(task.get(0).getCompleteCount())){
                updqcusermap.put("eachQcuserStatus", "1");
            }
            updqcusermap.put("getRecordCount", task.get(0).getCompleteCount()+"");
            updqcusermap.put("taskuserId", task.get(0).getTaskuserId()+"");
            updqcusermap.put("taskType", "3");
            taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
            Map<String,String> complemap=new HashMap<String, String>();//查询任务员工详细表信息
            complemap.put("taskId", taskId);
            complemap.put("taskuserId", task.get(0).getTaskuserId()+"");
            List<Task> completask =qc112xjtaskDao.getChapterFinishDetails(complemap);
            for (Task task2 : completask) {
                Map<String,String> updcomplemap=new HashMap<String, String>();  
                updcomplemap.put("getRecordCount", task2.getCompleteCount()+"");
                updcomplemap.put("taskuserId", task.get(0).getTaskuserId()+"");
                updcomplemap.put("cDate", task2.getcDate());
                updcomplemap.put("taskType","3");
                taskDao.update112orderTaskcomplete(updcomplemap);
            }
            releaseList += "]";
        }
        return releaseList;
    }

    @Override
    public String updrelease112ChapterBycDate(Map<String, String> map) {
        String releaseList = "";
        String cDateItem=map.get("cDateItem");
        String taskId=map.get("taskId");
        String taskUserId=map.get("taskUserId");
        String[] cDate=cDateItem.split(",");
        int sfcount=0;
        for (String cdate : cDate) {
            releaseList +=  cdate+ ":[";
            Map<String,String> complemap=new HashMap<String, String>();//用于查询任务员工中间表
            complemap.put("taskId", taskId);
            complemap.put("cDate", cdate);
            complemap.put("taskuserId", taskUserId);    
            List<Task> task=qc112xjtaskDao.getChapterFinishDetails(complemap);
            
            Map<String, String> map1 = new HashMap<String, String>();//修改112主表状态
            map1.put("cDate", cdate);
            map1.put("taskuserId", taskUserId);
            // 1：修改112工单任务状态(112工单的任务状态，是，否)
            qc112xjtaskDao.release112Chapter(map1);  
            
            Map<String,String> ordermap=new HashMap<String, String>();//修改任务工单中间表
            ordermap.put("taskuserId", taskUserId);
            ordermap.put("cDate",cdate);
            ordermap.put("taskType","3");
            List<String> getReleaseList = qc112xjtaskDao.selectRecordDateForLog(ordermap);
            for (String re : getReleaseList) {
                releaseList += re +",";
            }
            taskDao.deltaskOrder(ordermap);//修改任务工单中间表              
               
            Map<String,String> updcomplemap=new HashMap<String, String>();  
            updcomplemap.put("getRecordCount", task.get(0).getCompleteCount()+"");
            updcomplemap.put("taskuserId", taskUserId);
            updcomplemap.put("cDate", cdate);
            updcomplemap.put("taskType", "3");
            taskDao.update112orderTaskcomplete(updcomplemap);//修改任务员工详细表
            sfcount=sfcount+(Integer.parseInt(task.get(0).getGetRecordCount())-Integer.parseInt(task.get(0).getCompleteCount()));//释放的任务数   
            releaseList += "]";
        }   
        Map<String,String> qcusermap=new HashMap<String, String>();//用于查询任务员工中间表
        qcusermap.put("taskuserId", taskUserId);
        qcusermap.put("taskType","3");
        List<Task> usertask=taskDao.order112taskqcuserbyuserid(qcusermap);  
        Map<String,String> updqcusermap=new HashMap<String, String>();//用户修改任务员工中间表信息
        if(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount>Integer.parseInt(usertask.get(0).getCompleteCount())){
            updqcusermap.put("getRecordCount",(Integer.parseInt(usertask.get(0).getGetRecordCount())-sfcount)+"");      
        }else{
            updqcusermap.put("getRecordCount",usertask.get(0).getCompleteCount()+"");
        }
        updqcusermap.put("taskuserId", usertask.get(0).getTaskuserId()+"");
        updqcusermap.put("taskType", "3");
        taskDao.updateTaskQcUserInfobytaskId(updqcusermap);//修改任务员工中间表
        
        return releaseList;
    }
    
    /**
     * 评分
     */
    public int updateTaskChapter(Map<String,String> map){
        String taskId=map.get("taskId");
        String taskuserId=map.get("taskuserId");
        String cDate=map.get("cDate");
        if(cDate!=null&&!"".equals(cDate)){
            cDate=cDate.substring(0, 10);
        }
        String telsumId=map.get("telsumId");
        String sumtaskorder=map.get("sumtaskorder");
        String csrTopDCount=map.get("csrTopDCount");//每天任务数
        Map<String,String> taskordermap=new HashMap<String, String>();
        taskordermap.put("telsumId", telsumId);
        taskordermap.put("isQc", "1");
        taskordermap.put("type", "3");
        taskDao.updateTaskOrderInfo(taskordermap);
        Map<String,String> qcusermap=new HashMap<String, String>();
        qcusermap.put("taskId", taskId);
        qcusermap.put("taskuserId", taskuserId);    
        qcusermap.put("taskType", "3");    
        List<Task> qcusertask =   taskDao.order112taskqcuserbyuserid(qcusermap); //获取任务员工中间表信息
        int linqusum=Integer.parseInt(qcusertask.get(0).getGetRecordCount());//领取的任务工单数
        int complecount=Integer.parseInt(qcusertask.get(0).getCompleteCount());//完成的任务工单数
        Map<String,String> taskusermap=new HashMap<String, String>();   
        taskusermap.put("taskuserId", taskuserId);  
        taskusermap.put("taskType","3");  
        if(Integer.parseInt(sumtaskorder)>complecount+1){
            taskusermap.put("eachQcuserStatus", "2");
            taskusermap.put("completeCount", (complecount+1)+"");
        }else{
            taskusermap.put("eachQcuserStatus", "3");
            taskusermap.put("completeCount",  (complecount+1)+"");
            Map<String,String> taskmap=new HashMap<String, String>();//更新主表状态
            taskmap.put("taskStatus", "3");
            taskmap.put("taskId", taskId);
            qc112xjtaskDao.update112ChapterTaskInfo(taskmap);
        }
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
        completask.put("taskType", "3");
        taskDao.update112orderTaskcomplete(completask); 
        return 1;
    }
}