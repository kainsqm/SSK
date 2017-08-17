package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.TaskChapter;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ITask112ChapterService;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 112电话小结
 * @author chendi
 *
 */
@Controller
@RequestMapping("/controller/chapter112")
public class Chapter112TaskController {
    private static Logger log = Logger.getLogger(Chapter112TaskController.class);
    @Resource
    private ITaskService taskService;
    
    @Resource
    private ITask112ChapterService chapter112Service;
    
    /*vSysuser user= new vSysuser(44,"AA005","小朋友","","","");
    vSysuser userzj = new vSysuser(40,"AA001","小朋友","","","");*/
    /**
     * @author cd 跳转112小结任务派发界面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/show112Chapter", method = RequestMethod.GET)
    public String show112Chapter(HttpServletRequest request,HttpServletResponse response){
        return "task/112chapter/112task_chapter_List";
    }
    
    /**
     * @author cd 跳转112小结执行任务界面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/showExecute112Chapter", method = RequestMethod.GET)
    public String toExecuteChapterList(HttpServletRequest request,HttpServletResponse response){
        return "task/112chapter/execute_chapter112List";
    }
    
    /**
     * 112小结任务查询
     * @param request
     * @param response
     * @param task
     */
    @RequiresPermissions("task112:query")
    @RequestMapping(value="/gettaskOrder112Chapter", method = RequestMethod.POST)
    public void gettaskOrder112Chapter(HttpServletRequest request,HttpServletResponse response ,TaskChapter taskChapter){
        try {
            vSysuser user= (vSysuser) request.getAttribute("User");
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String taskid=request.getParameter("taskid");
            if(taskid==null||"".equals(taskid)){
                taskChapter.setTaskId(0);
            }else{
                taskChapter.setTaskId(Integer.parseInt(taskid));
            }
            int currentPage = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pagesize"));
            PageHelper.startPage(currentPage, pageSize);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务查询开始");
            List<TaskChapter> list=chapter112Service.gettask112Chapter(taskChapter);
            int total=chapter112Service.gettask112Chaptercount(taskChapter);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务查询开始,结果["+list.size()+"]");
            String json = "{\"Total\":" + total + " , \"Rows\":" + JSONArray.fromObject(list).toString() + "}";  
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("112小结任务查询失败:"+e.getMessage());
        }
    }
    
    /**
     * 跳转至新增界面
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("task112:add")
    @RequestMapping(value = "/to112ChapterAdd")
    public String to112OrderTaskInfo(HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try{
            List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
            request.setAttribute("qcUserList", qcUserList);
        }catch(Exception e){
            log.error("查询质检员异常："+e.getMessage());
        }
        return "task/112chapter/edit_112taskInfo";
    }
    
    /**
     * 新增任务时，判断任务名称是否存在
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("task112:add")
    @RequestMapping(value = "/check112ChapterName", method = RequestMethod.POST)
    public void check112TaskName(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        String taskName =request.getParameter("taskName");
        String oldName=request.getParameter("oldName");
        int count=0;
        log.info("操作用户:["+user.getUserId()+"]"+"检测是否存在112小结任务名称:["+taskName+"]");
        if(oldName==null || "".equals(oldName)){
            count=chapter112Service.check112Chaptername(taskName);
        }else{
            if(oldName.equals(taskName)){
                count=0;
            }else{
                count=chapter112Service.check112Chaptername(taskName);
            }
        }
        JSONObject json = new JSONObject();
        json.put("data", count);
        response.getWriter().print(json);   
    }
    
    /**
     * 新增112小结任务
     * @param request
     * @param response
     * @param task
     */
    @RequiresPermissions("task112:add")
    @Log(methodname = "add112ChapterTask", modulename = "112小结任务派发", funcname = "112小结任务派发新增", description = "112小结任务派发新增,{0}", code = "ZJ")
    @RequestMapping(value = "/add112ChapterTask", method = RequestMethod.POST)
    public void add112ChapterTask(HttpServletRequest request,
            HttpServletResponse response,TaskChapter taskChapter){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        vSysuser user= (vSysuser) request.getAttribute("User");
        try{
            taskChapter.setTaskStatus(taskChapter.getIsPublish()); 
            taskChapter.setCreateUserId(user.getUserId()+"");
            log.info("操作用户:["+user.getUserId()+"]"+"新增112小结任务名称:["+taskChapter.getTaskName()+"]");
            chapter112Service.insert112ChapterTask(taskChapter);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "新增112小结任务名称操作成功,任务名称:"+taskChapter.getTaskName() }));
            json.put("result", "1");
            response.getWriter().print(json.toString());
        }catch(Exception e){        
            try {
                json.put("result", "0");
                response.getWriter().print(json.toString());
            } catch (IOException e1) {
                log.error("112小结任务派发新增异常：" + e1.getMessage());
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务派发新增异常："+e1.getMessage() }));
            }
            log.error("112小结任务派发新增异常："+e.getMessage());
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务派发新增异常："+e.getMessage() }));
        }
    }
    /**
     * 跳转至修改界面
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("task112:upd")
    @RequestMapping(value="/to112ChapterUpdate")
    public String to112ChapterUpdate(HttpServletRequest request,
            HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=null;
        String taskId=null;
        try {
            taskId=request.getParameter("taskId");
            String type=request.getParameter("type");
            if(taskId==null||"".equals(taskId)){
                taskId="0";
            }
            TaskChapter taskChapter = chapter112Service.get112ChapterTaskbyid(Integer.parseInt(taskId));
            List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
            
            String papersvalue = "";
            if ("".equals(taskChapter.getTelUser())
                    || null == taskChapter.getTelUser()) {

            } else {
                String Userid = taskChapter.getTelUser() + ",";
                String Customer = taskChapter.getTelUserName() + ",";
                String[] checkedUserid = Userid.split(",");
                String[] checkedCustomer = Customer.split(",");
                for (int i = 0; i < checkedUserid.length; i++) {
                    papersvalue += checkedUserid[i] + "-" + checkedCustomer[i]
                            + ",";

                }
            }
            taskChapter.setPapers_hid(papersvalue);
            request.setAttribute("qcUserList", qcUserList);
            request.setAttribute("type", type);
            request.setAttribute("task", taskChapter); 
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("查看112小结任务："+e.getMessage());
        }
        return "task/112chapter/edit_112taskInfo";
    }
    
    /**
     * 修改提交
     * @param request
     * @param response
     * @param task
     */
    @RequiresPermissions("task112:upd")
    @Log(methodname = "upd112ChapterTask", modulename = "112小结任务派发", funcname = "112小结任务派发修改", description = "112小结任务派发修改,{0}", code = "ZJ")
    @RequestMapping(value="/upd112ChapterTask",  method=RequestMethod.POST)
    public void upd112ChapterTask(HttpServletRequest request,
            HttpServletResponse response,TaskChapter taskChapter){
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        JSONObject json=null;
        try{
            taskChapter.setQcUser(taskChapter.getQcUserWorkId());
            taskChapter.setTaskStatus(taskChapter.getIsPublish()); 
            taskChapter.setCreateUserId(user.getUserId()+"");
            log.info("操作用户:["+user.getUserId()+"]"+"修改112小结任务:["+taskChapter.getTaskName()+"]");
            chapter112Service.update112ChapterTask(taskChapter);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "修改112小结任务名称操作成功,任务名称:"+taskChapter.getTaskName() }));
            json=new JSONObject();
            json.put("result", "1");
            response.getWriter().print(json.toString());
        }catch(Exception e){        
            try {
                json=new JSONObject();
                json.put("result", "0");
                response.getWriter().print(json.toString());
            } catch (IOException e1) {          
                log.error("112小结任务派发修改异常：" + e1.getMessage());
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务派发修改异常："+e1.getMessage() }));
            }
            log.error("112小结任务派发修改异常："+e.getMessage());
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务派发修改异常："+e.getMessage() }));
        }
    }
    
    /**
     * 删除任务派发
     * @param request
     * @param response
     */
    @RequiresPermissions("task112:del")
    @Log(methodname = "del112ChapterTask", modulename = "112小结任务派发", funcname = "112小结任务派发删除", description = "112小结任务派发删除,{0}", code = "ZJ")
    @RequestMapping(value="/del112ChapterTask",  method=RequestMethod.POST)
    public void del112ChapterTask(HttpServletRequest request,
            HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject json=null;
        String taskId=null;
        vSysuser user= (vSysuser) request.getAttribute("User");
        try {
            taskId=request.getParameter("taskId");
            if(taskId==null||"".equals(taskId)){
                taskId="0";
            }
            String status=request.getParameter("status");
            Map<String,String> map=new HashMap<>();
            map.put("taskStatus", status);
            map.put("taskId", taskId);
            log.info("操作用户:["+user.getUserId()+"]"+"删除112小结任务编号:["+taskId+"]");
            chapter112Service.del112ChapterTask(map);
            json=new JSONObject();
            json.put("result", "1");
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "删除成功，任务名称:["+taskId+"]成功" }));
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("删除112小结任务："+e.getMessage());
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除失败，任务名称:【"+taskId+"】"+e.getMessage()}));
        }
    } 
    
    
    //-------------任务执行
    /**
     * @author lk  2017/5/10
     * @param request
     * @param response
     * @param task
     * 质检员查询任务执行列表
     */
    @RequiresPermissions("ztask112:query")
    @RequestMapping(value="/getExecuteChapterList" ,method=RequestMethod.POST)
    public void getExecuteChapterList(HttpServletRequest request,HttpServletResponse response ,TaskChapter taskChapter){
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String taskid=request.getParameter("taskid");
            vSysuser user= (vSysuser) request.getAttribute("User");
            if(taskid==null||"".equals(taskid)){
                taskChapter.setTaskId(0);
            }else{
                taskChapter.setTaskId(Integer.parseInt(taskid));
            }
            int currentPage = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pagesize"));
            PageHelper.startPage(currentPage, pageSize);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行查询开始:");
            List<TaskChapter> list=chapter112Service.getExecuteChapterList(taskChapter);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行查询结束:["+list.size()+"]");
            int total=chapter112Service.getExecuteChapterListCount(taskChapter);
            String json = "{\"Total\":" + total + " , \"Rows\":" + JSONArray.fromObject(list).toString() + "}";  
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("112小结任务列表查询失败:"+e.getMessage());
        }
    }
    
    /**
     * 获取任务工单列表
     * @param request
     * @param response
     * @param workOrder112
     */
    @RequestMapping(value="/getReviceChapterList",method = RequestMethod.POST)
    public void getReviceChapterList(HttpServletRequest request,HttpServletResponse response,WorkOrder112 workOrder112){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        try {
            String taskId=request.getParameter("taskId");
            String type=request.getParameter("type");
            Map<String,String> map=new HashMap<String,String>();
            map.put("taskId", taskId);
            TaskChapter taskChapter = chapter112Service.get112ChapterTaskbyid(Integer.parseInt(taskId));
            String  businessType = taskChapter.getBussniessType();
            String  telsumType = taskChapter.getTelsumType();
            String  gzlyType = taskChapter.getGzlyType();
            if(businessType!=null && !"".equals(businessType)){
                map.put("businessType", businessType);
            }else{
                map.put("businessType", "");
            }
            if(telsumType!=null &&!"".equals(telsumType)){
                map.put("telsumType", telsumType);
            }else{
                map.put("telsumType", "");
            }
            if(gzlyType!=null &&!"".equals(gzlyType)){
                map.put("errorSource", gzlyType);
            }else{
                map.put("errorSource", "");
            }
            if(!"default".equals(type)){     
                if(!request.getParameter("businessType").equalsIgnoreCase("")){
                    map.put("businessType",request.getParameter("businessType"));
                }
                if(!request.getParameter("telsumType").equalsIgnoreCase("")){
                    map.put("telsumType",request.getParameter("telsumType"));
                }
                if(!request.getParameter("errorSource").equalsIgnoreCase("")){
                    map.put("errorSource",request.getParameter("errorSource"));
                }
            }
            log.info("操作用户:["+user.getUserId()+"]"+"获取任务功能列表开始:");
            Map<String,String> maps= DateUtil.getNowDateFront(new Date()); 
            map.put("startdate",maps.get("startdate"));
            map.put("stopdate",maps.get("stopdate"));
            List<WorkTelSum112> wkList=chapter112Service.getReviceChapterList(map);
            log.info("操作用户:["+user.getUserId()+"]"+"获取任务功能列表结束:"+wkList.size());
            String json = "{\"Rows\":"+ JSONArray.fromObject(wkList).toString() + "}";    
            System.out.println(json.toString());
            response.getWriter().print(json.toString());        
        } catch (Exception e) {
            log.error("获取工单列表异常："+e.getMessage());      
        }       
    }
    
    /**
     * 获取112小结信息查询多选下拉框
     * @author chendi
     * **/
   @RequestMapping(value = "/get112ChapterTypeByID", method = RequestMethod.POST)
    public void get112ChapterTypeByID( HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            vSysuser user= (vSysuser) request.getAttribute("User");
            log.info("操作用户:["+user.getUserId()+"]"+"获取112小结信息查询多选下拉框:");
            JSONObject object=new JSONObject();
            String taskId=request.getParameter("taskId");
            TaskChapter taskChapter = chapter112Service.get112ChapterTaskbyid(Integer.parseInt(taskId));
            List<Modeltype> businessType= new ArrayList<>(); //获取申告大类
            List<Modeltype> telsumType= new ArrayList<>();  //获取结案方式
            List<Modeltype> errorSource= new ArrayList<>();  // 获取受理来源
            
            if(taskChapter.getBussniessType()!=null){
                for (String string : taskChapter.getBussniessType().split(";")) {
                    Modeltype mtype=new Modeltype();
                    mtype.setId(string);
                    mtype.setText(string);
                    businessType.add(mtype);
                }
            }   
            
            if(taskChapter.getTelsumType()!=null){
                for (String string : taskChapter.getTelsumType().split(";")) {
                    Modeltype mtype=new Modeltype();
                    mtype.setId(string);
                    mtype.setText(string);
                    telsumType.add(mtype);
                }
            }   
            
            if(taskChapter.getGzlyType()!=null){
                for (String string : taskChapter.getGzlyType().split(";")) {
                    Modeltype mtype=new Modeltype();
                    mtype.setId(string);
                    mtype.setText(string);
                    errorSource.add(mtype);
                }
            }   
            object.put("businessType", JSONArray.fromObject(businessType));
            object.put("telsumType", JSONArray.fromObject(telsumType));
            object.put("errorSource", JSONArray.fromObject(errorSource));
            System.out.println(object.toString());
            response.getWriter().print(object);
        } catch (Exception e) {
            log.error("获取工单信息查询下拉框异常：" + e.getMessage());       
        }
    }
    
    
    /**
     * 任务单条领取112小结
     * @param request
     * @param response
     */ 
    @Log(methodname = "getReciveTask", modulename = "112小结任务执行", funcname = "112小结任务执行", description = "单条领取112小结,{0}", code = "ZJ")
    @RequestMapping(value="/getReciveTask",method = RequestMethod.POST)
    public void getReciveTask(HttpServletRequest request,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        try{
            String qcUserId = user.getUserId()+"";
            String taskId=request.getParameter("taskId");
            String telSumId=request.getParameter("telSumId");
            String teluser=request.getParameter("teluser");
            TaskChapter taskChapter = chapter112Service.get112ChapterTaskbyid(Integer.parseInt(taskId));
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("taskId", taskId);//任务id
            map.put("telSumId", telSumId);//工单id
            map.put("taskChapter", taskChapter);
            map.put("qcUserId", qcUserId);//质检员
            map.put("teluser", teluser);//受理员
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，单条领取112小结:["+taskId+"]");
            String vmsg=chapter112Service.updReviceTask(map);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，单条领取112小结成功");
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "单条领取112小结，工单id:["+telSumId+"]成功" }));
            JSONObject json=new JSONObject();
            json.put("vmsg", vmsg);
            response.getWriter().print(json.toString());
        }catch (Exception e) {
            log.error("单条领取112小结异常：" + e.getMessage());  
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "单条领取112小结失败"+e.getMessage() }));
        }
    }
    
    /**
     * 质检已领
     * @param request
     * @param response
     */
    @RequestMapping(value="/getylChapterList",method = RequestMethod.POST)
    public void  getylChapterList(HttpServletRequest request,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pagesize"));
        String type=request.getParameter("type");
        String userid=request.getAttribute((Constans.USER_INFO_USERID)).toString();
        PageHelper.startPage(currentPage, pageSize);
        String taskId=request.getParameter("taskId");
        String isqc=request.getParameter("isqc");
        String cDate=request.getParameter("cDate");
        if("null".equals(taskId)){
            taskId=null;
        }
        if("null".equals(cDate)){
            cDate=null;
        }
        Map<String,String> map=new HashMap<String, String>();
        map.put("taskId", taskId);
        map.put("isqc", isqc);  
        map.put("userid", userid);
        Set<String> list= chapter112Service.getyl112TelSumtaskidList(map); //获取已领任务工单
        List<WorkTelSum112> orderlist = new ArrayList<>();
        int total = 0 ;
        if(!list.isEmpty()){
            orderlist= chapter112Service.getYlChapterList(list);
            total=  chapter112Service.getYlChapterListCount(list);
        } 
        String json = "{\"Total\":" + total + " , \"Rows\":"+ JSONArray.fromObject(orderlist).toString() + "}"; 
        try {
            System.out.println(json.toString());
            response.getWriter().print(json.toString());
        } catch (IOException e) {
            log.error("获取工单列表异常："+e.getMessage());
        }   
    }
    
    /**
     * @author cd 2017/5/17
     * @param request
     * @param response
     * 质检员查看自己完成情况
     */
    @RequestMapping(value="/getChapter112Finish",  method=RequestMethod.POST)
    public void getChapter112Finish(HttpServletRequest request,
            HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pagesize"));
        try{
        PageHelper.startPage(currentPage, pageSize);
        String taskId=request.getParameter("taskId");
        vSysuser user= (vSysuser) request.getAttribute("User");
        String userid=user.getUserId()+"";
        String eachDateStatus=request.getParameter("eachDateStatus");
        Map<String,String> map=new HashMap<String, String>();
        map.put("taskId", taskId);
        map.put("qcUser", userid);
        map.put("taskType", "3");
        map.put("eachDateStatus", eachDateStatus);
        List<Task> list=chapter112Service.getFinishChapterList(map);
        int total=chapter112Service.getFinishChapterListCount(map);
        String json = "{\"Total\":" + total + " , \"Rows\":"+ JSONArray.fromObject(list).toString() + "}";
        response.getWriter().print(json.toString());
        }catch (Exception e) {
            log.error("质检员查看自己完成情况异常："+e.getMessage());
        }
    }
    
    /**
     * 督导查询任务完成情况
     * @author niewenqiang
     * @date 2017-5-25
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/queryTaskCompleteInfo", method = RequestMethod.POST)
    public void queryTaskCompleteInfo(HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Map<String, String> map = new HashMap<String, String>(); // 日期
            String taskId = request.getParameter("taskId");
            String taskStatus = request.getParameter("taskStatus");
            map.put("taskId", taskId);
            map.put("taskStatus", taskStatus);
            int currentPage = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pagesize"));
            PageHelper.startPage(currentPage, pageSize);
            List<CdmaTaskComplete> taskInfoList = chapter112Service.queryTaskCompleteInfo(map);
            int counts=chapter112Service.queryTaskCompleteInfoCount(map);
            JSONObject object = new JSONObject();
            object.put("Rows", taskInfoList);
            object.put("Total", counts);
            response.getWriter().print(object.toString());
        } catch (Exception e) {
            log.error("督导查询112电话小结任务完成情况出现异常" + e.getMessage(),e);
        }
    }
    
    
    /**
     * 112小结一键领取
     * @author chendi
     * @return
     * @throws Exception 
     * @throws UnsupportedEncodingException
     */
    @Log(methodname = "oneKeyReciveAll", modulename = "质检员一键领取112电话小结任务", funcname = "质检员一键领取112电话小结任务", description = "质检员一键领取112电话小结任务,{0}", code = "ZJ")
    @RequestMapping(value = "/oneKeyReciveAll", method = RequestMethod.POST)
    public void oneKeyReciveAll(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String taskId=request.getParameter("taskId");       
        TaskChapter taskChapter = chapter112Service.get112ChapterTaskbyid(Integer.parseInt(taskId));
        String teluserId=request.getAttribute(Constans.USER_INFO_USERID).toString();
        Map<String,String> map=new HashMap<String,String>();
        vSysuser user= (vSysuser) request.getAttribute("User");
        try{
            if(taskChapter!=null){
                map.put("qcUser", teluserId);
                map.put("taskId", taskId);
                log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，开始一键领取112小结");
                Map<String,String> msg=chapter112Service.updateOneKeyReciveAll(map);
                String lingquAllId = msg.get("lingquAllId");
                log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，一键领取112小结成功：["+lingquAllId+"]");
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务执行，一键领取112小结成功：["+lingquAllId+"]" }));
                JSONObject json=new JSONObject();
                json.put("vmsg", msg.get("vmsg"));
                response.getWriter().print(json.toString());
            }
        }catch (Exception e) {
            log.error("112小结一键领取异常："+e.getMessage());
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "112小结任务执行，一键领取112小结成功失败"+e.getMessage() }));
        }
    }
    
    /**
     * @author lk 2017/5/19
     * @param request
     * @param response
     * 督导查看个人完成情况
     */
    @RequestMapping(value="/getChapterFinishDetails",  method=RequestMethod.POST)
    public void getChapterFinishDetails(HttpServletRequest request,
            HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pagesize"));
        try{
        PageHelper.startPage(currentPage, pageSize);
        String taskId=request.getParameter("taskId");
        String taskuserId=request.getParameter("taskuserId");
        String eachDateStatus=request.getParameter("eachDateStatus");
        Map<String,String> map=new HashMap<String, String>();
        map.put("taskId", taskId);
        map.put("eachDateStatus", eachDateStatus);
        map.put("taskuserId", taskuserId);
        List<Task> list=chapter112Service.getChapterFinishDetails(map);
        int total=chapter112Service.getChapterFinishDetailsCount(map);
        String json = "{\"Total\":" + total + " , \"Rows\":"
                + JSONArray.fromObject(list).toString() + "}";
        response.getWriter().print(json.toString());
        }catch (Exception e) {
            log.error("质检员查看自己完成情况异常："+e.getMessage());
        }
    }
    
    /**
     * @author lk 2017/5/22
     * 一键释放质检员领取工单
     */
    @Log(methodname = "release112ChapterByUser", modulename = "112小结任务执行", funcname = "112小结任务执行", description = "一键释放质检员领取工单,{0}", code = "ZJ")
    @RequestMapping(value="/release112ChapterByUser",  method=RequestMethod.POST)
    public void release112ChapterByUser(HttpServletRequest request,
            HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        try {
            String qcUserItem=request.getParameter("qcUserItem"); //质检员userid
            String taskId=request.getParameter("taskId");//任务id
            Map<String,String> map=new HashMap<String, String>();
            map.put("qcUserItem", qcUserItem);
            map.put("taskId", taskId);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，一键释放质检员领取工单:["+taskId+"]");
            String releaseList = chapter112Service.updrelease112ChapterByUser(map);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，一键释放质检员领取工单成功:"+releaseList);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "一键释放质检员成功：["+releaseList+"]" }));
            JSONObject object=new JSONObject();
            object.put("result", "成功");
            response.getWriter().print(object);
        } catch (Exception e) {
            log.error("一件释放112小结异常：" + e.getMessage());  
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "一键释放质检员领取工单失败"+e.getMessage() }));
        }
    }
    
    /**
     * @author
     * @param request
     * @param response
     * 一键释放112小结 以天为维度
     */
    @Log(methodname = "release112ChapterBydate", modulename = "112小结任务执行", funcname = "112小结任务执行", description = "一键释放112小结 以天为维度,{0}", code = "ZJ")
    @RequestMapping(value="/release112ChapterBydate",  method=RequestMethod.POST)
    public void release112ChapterBydate(HttpServletRequest request,
            HttpServletResponse response){  
        response.setContentType("text/html;charset=UTF-8");
        vSysuser user= (vSysuser) request.getAttribute("User");
        try {
            String cDateItem=request.getParameter("cDateItem"); //质检员userid 
            String taskId=request.getParameter("taskId");//任务id
            String taskUserId=request.getParameter("taskUserId");//任务员工中间表id
            Map<String,String> map=new HashMap<String, String>();
            map.put("cDateItem", cDateItem);
            map.put("taskId", taskId);
            map.put("taskUserId", taskUserId);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，一键释放112小结 以天为维度:["+taskId+"]");
            String releaseList = chapter112Service.updrelease112ChapterBycDate(map);
            log.info("操作用户:["+user.getUserId()+"]"+"112小结任务执行，一键释放112小结 以天为维度成功:"+releaseList);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "一键释放112小结 以天为维度成功：["+releaseList+"]" }));
            JSONObject object=new JSONObject();
            object.put("result", "成功");
            response.getWriter().print(object);
        } catch (Exception e) {
            log.error("一键领取112小结异常：" + e.getMessage());  
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "一键释放112小结 以天为维度"+e.getMessage() }));
        }
    }
}
