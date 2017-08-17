package cn.sh.ideal.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExamVo;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExamExampaperExamineService;
import cn.sh.ideal.service.IExamService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.json.JsonDateValueProcessor;
import cn.sh.ideal.util.json.JsonTimeValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping(value="/controller/exam")
public class ExamController {
    
	private static Logger log = Logger.getLogger(ExamController.class);
	@Autowired
	private IExamService examService ;
	@Autowired
    private ISysCodeService iSysCodeService;
	@Autowired
    private IExamExampaperExamineService examExampaperExamineService;
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
	 
	//****测试
    int zuhuId = 1;
    
    /**
     * 考试管理-考试维护管理--查询
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequiresPermissions("kswhgl:query")
    @RequestMapping(value="/getExamsManage",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getExamsManage(@ModelAttribute("queryExamForm") tExamVo vo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            vo.setZuhuId(zuhuId);
            log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理查询数据开始");
            List<tExamVo> codes = examService.selectExamVos(vo);
            log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理查询数据结束,["+codes.size()+"]");
            JSONObject result = new JSONObject();
            result.put("Rows",JSONArray.fromObject(codes));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"考试维护管理查询失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    @RequiresPermissions("kswhgl:tj")
	@Log(methodname="addExam",modulename="考试维护管理",funcname="新增考试",description="新增考试,{0}")
	@RequestMapping(value="/addExam",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject addExam(@ModelAttribute("addExamForm") tExamVo vo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        String resultStr = "-1";
        try {
            vo.setZuhuId(zuhuId);
            vo.setFkInsertUserId(user.getUserId());
            log.info("操作用户:["+user.getUserId()+"]"+vo.getExamName()+"查询考试是否存在");
            int flag = examService.selectByExamName(vo.getExamName());
            if(flag>0){
                resultStr = "-1";
            }else{
                log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理新增考试开始");
                resultStr = examService.addExam(vo);
                log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理新增考试结束,["+resultStr+"]");
            }
            result.put("resultStr",resultStr);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"新增考试+["+vo.getExamName()+"]成功"}));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"考试维护管理新增考试失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"新增考试+["+vo.getExamName()+"]失败"+e.getMessage()}));
        } 
        return null;
    }
	
	/**
    * 考试维护管理，打开修改考试界面
    * @param request
    * @param response
    * @author chendi
    * @return
    */
    @RequiresPermissions("kswhgl:update")
	@RequestMapping(value="/openUpdateView",method=RequestMethod.GET)
    public String openUpdateView(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);      
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            int pkAutoId = Integer.parseInt(request.getParameter("pkAutoId"));
            log.info("操作用户:["+user.getUserId()+"]"+"修改界面查询考试信息开始");
            tExamVo tExamVo = examService.selectByPKId(pkAutoId);
            log.info("操作用户:["+user.getUserId()+"]"+"修改界面查询考试信息结束,["+tExamVo.getExamName()+"]");
            List<tSysCode> objectList = iSysCodeService.getItemFlagList("SYSEXAMOBJECT");
            List<tSysCode> typeList = iSysCodeService.getItemFlagList("SYSEXAMTYPE");
            modelMap.put("objectList", objectList);
            modelMap.put("typeList", typeList);
            modelMap.put("tExamVo", tExamVo);
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"修改界面查询考试信息失败："+e.getMessage(),e); 
        } 
        return "examination/updateexam_manage";
    }
	
   @RequiresPermissions("kswhgl:tj")
   @Log(methodname="updateExam",modulename="考试维护管理",funcname="修改考试提交",description="修改考试提交,{0}")
   @RequestMapping(value="/updateExam",method=RequestMethod.POST)
   @ResponseBody
   public JSONObject updateExam(@ModelAttribute("updateExamForm") tExamVo vo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        JSONObject result = new JSONObject();   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            vo.setZuhuId(zuhuId);
            vo.setFkUpdateUserId(user.getUserId());
            log.info("操作用户:["+user.getUserId()+"]"+"修改考试信息开始");
            String resultStr = examService.updateExam(vo);
            log.info("操作用户:["+user.getUserId()+"]"+"修改考试信息结束,["+resultStr+"]");
            result.put("resultStr",resultStr);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"修改考试ID+["+vo.getPkAutoId()+"]成功"}));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"修改考试ID"+vo.getPkAutoId()+"信息失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"修改考试ID+["+vo.getPkAutoId()+"]失败"+e.getMessage()}));
        } 
        return null;
    }
   
   @RequiresPermissions("kswhgl:delete")
   @Log(methodname="deleteExam",modulename="考试维护管理",funcname="删除考试",description="删除考试,{0}")
   @RequestMapping(value="/deleteExam",method=RequestMethod.POST)
   @ResponseBody
   public JSONObject deleteExam(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");   
        JSONObject result = new JSONObject();
        String resultStr = "";
        int pkAutoId = 0;
        try {
            pkAutoId = Integer.parseInt(request.getParameter("pkAutoId"));
            log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息开始,["+pkAutoId+"]");
            resultStr = examService.deleteExam(pkAutoId);
            if(resultStr.equalsIgnoreCase("-1")){
                log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息失败,["+resultStr+"]已有人考试不能删除");
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+pkAutoId+"]失败，已有人考试不能删除"}));
            }else{
                log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息成功,["+resultStr+"]");
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+pkAutoId+"]成功"}));
            }
            result.put("resultStr",resultStr);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"删除考试信息失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+pkAutoId+"]失败"+e.getMessage()}));
        } 
        return result;
    }
   
   @RequiresPermissions("kswhgl:delete")
   @Log(methodname="deleteExamLists",modulename="考试维护管理",funcname="删除多场考试",description="删除多长考试,{0}")
   @RequestMapping(value="/deleteExamLists",method=RequestMethod.POST)
   @ResponseBody
   public JSONObject deleteExamLists(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");   
        JSONObject result = new JSONObject();
        String resultStr = "";
        int pkAutoId = 0;
        try {
            String ids[] = request.getParameter("ids").split(",");
            log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息循环开始,["+request.getParameter("ids")+"]");
            for (String id : ids) {
                pkAutoId = Integer.parseInt(id);
                resultStr = examService.deleteExam(pkAutoId);
                if(resultStr.equalsIgnoreCase("-1")){
                    log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息失败,["+pkAutoId+"]已有人考试不能删除");
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+pkAutoId+"]失败，已有人考试不能删除"}));
                    break;
                }
            }
            if(!resultStr.equalsIgnoreCase("-1")){
                log.info("操作用户:["+user.getUserId()+"]"+"删除考试信息成功,["+request.getParameter("ids")+"]");
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+request.getParameter("ids")+"]成功"}));
            }
            result.put("resultStr",resultStr);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"删除考试信息失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除考试+["+pkAutoId+"]失败"+e.getMessage()}));
        } 
        return result;
    }
	
   /**
    * 考试情况查询--考试查询--查看参考人员
    * @param request
    * @param response
    * @author chendi
    * @return
    */
    @RequiresPermissions("kskssjck:showuserinfo")
    @RequestMapping(value="/testStudentsView",method=RequestMethod.GET)
    public String openTestStudentsView(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);      
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            int examId = Integer.parseInt(request.getParameter("examId"));
            int listPaperID = Integer.parseInt(request.getParameter("listPaperID"));
            String url=ConfigProperties.getProperty("url"); //加载心跳程序访问地址
            modelMap.put("examId", examId);
            modelMap.put("listPaperID", listPaperID);
            modelMap.put("url", url);
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"打开参考人员窗口："+e.getMessage(),e); 
        } 
        return "publicdialog/query_test_student";
    }
    
    /**
     * 考试情况查询--考试查询
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequiresPermissions("kskssjck:query")
    @RequestMapping(value="/getExamsQuery",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getExamsQuery(@ModelAttribute("queryExamForm") tExamVo vo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);      
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            vo.setZuhuId(zuhuId);
            log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理查询数据开始");
            List<tExamVo> codes = examService.selectExamVos(vo);
            log.info("操作用户:["+user.getUserId()+"]"+"考试维护管理查询数据结束,["+codes.size()+"]");
            JSONObject result = new JSONObject();
            result.put("Rows",JSONArray.fromObject(codes));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"考试维护管理查询失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    /**
     * 考试情况查询--考生查询--查询参考人员
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kskssjck:showuserinfo")
    @RequestMapping(value="/getTestStudents",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getTestStudents(@ModelAttribute("examUserForm") ExamManualScore vo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);     
        vSysuser user= (vSysuser) request.getAttribute("sysuser"); 
        try {
            log.info("操作用户:["+user.getUserId()+"]"+"查询参考人员开始");
            List<ExamManualScore> scoreList = examExampaperExamineService.selectListByUser(vo);
            log.info("操作用户:["+user.getUserId()+"]"+"查询参考人员结束,["+scoreList.size()+"]");
            for (ExamManualScore examManualScore : scoreList) {
                if(examManualScore.getUserExamLength()!=null){
                    if(Integer.parseInt(examManualScore.getUserExamLength())<60){
                        examManualScore.setUserExamLength(examManualScore.getUserExamLength()+"秒");
                    }else{
                        examManualScore.setUserExamLength(String.valueOf(Integer.parseInt(examManualScore.getUserExamLength())/60)+"分");
                    }
                }
            }
            JSONObject result = new JSONObject();
            JsonConfig jsonConfig = new JsonConfig();
            // 这里Date.class 需要根据你用的日期格式进行修改 比如Timestamp.class、Date.class、Time.class
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonConfig.registerJsonValueProcessor(Time.class, new JsonTimeValueProcessor());
            result.put("Rows", JSONArray.fromObject(scoreList, jsonConfig));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询参考人员信息失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    /*****
     * 为统一登录首页查询待考试以及已考试卷提供数据
     * @date 2017-05-04
     * @author niewenqiang
     * ******/
	@RequestMapping(value="/getExamByMainPage",method=RequestMethod.GET)
    public void getExamByMainPage(HttpServletRequest request,HttpServletResponse response){
    	try {
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		//显示当前登录角色待考试的考试信息  默认显示5条
    		List examList=examService.queryExam(userId);
    		//显示当前登录角色已考试卷信息  默认显示5条
    		List examoverList=examService.queryExamOver(userId);
    		JSONObject object=new JSONObject();
    		object.put("examList", examList);
    		object.put("examoverList", examoverList);
    		String output = request.getParameter("callback")+"("+object.toString()+");";
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(output);
		} catch (Exception e) {
			log.error("统一登录首页查询待考试以及已考试卷异常"+e.getMessage(),e);
		}
    }  
    
}
