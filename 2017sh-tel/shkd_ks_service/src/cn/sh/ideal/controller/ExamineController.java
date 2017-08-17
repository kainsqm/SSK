package cn.sh.ideal.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
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
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExamExampaperExamineService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.json.JsonDateValueProcessor;
import cn.sh.ideal.util.json.JsonTimeValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping(value = "/controller/examine")
public class ExamineController {

    private static Logger log = Logger.getLogger(ExamineController.class);
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
     * 个人考试情况考生查询数据 查询
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kskssjck:query")
    @RequestMapping(value = "/getPersonExams", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getPersonExams(@ModelAttribute("queryExamine") tExamExampaperExamine examine, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
            if(examine.getEnabled()==null || examine.getEnabled().equalsIgnoreCase("")){
                examine.setFkUserId(user.getUserId());
            }
            examine.setZuhuId(zuhuId);
            log.info("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据开始");
            List<tExamExampaperExamine> tlist = examExampaperExamineService.selectPersonExams(examine);
            log.info("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据结束["+tlist.size()+"]");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonConfig.registerJsonValueProcessor(Time.class, new JsonTimeValueProcessor());
            result.put("Rows", JSONArray.fromObject(tlist, jsonConfig));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据查询失败："+e.getMessage(),e); 
        }
        return null;
    }

    
    
    /**
     * 个人考试情况考生查询数据 查询  从首页进入
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequestMapping(value = "/getPersonExamsByMain", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getPersonExamsByMain(@ModelAttribute("queryExamine") tExamExampaperExamine examine, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
            if(examine.getEnabled()==null || examine.getEnabled().equalsIgnoreCase("")){
                examine.setFkUserId(user.getUserId());
            }
            examine.setZuhuId(zuhuId);
            log.info("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据开始");
            List<tExamExampaperExamine> tlist = examExampaperExamineService.selectPersonExams(examine);
            log.info("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据结束["+tlist.size()+"]");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonConfig.registerJsonValueProcessor(Time.class, new JsonTimeValueProcessor());
            result.put("Rows", JSONArray.fromObject(tlist, jsonConfig));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"个人考试情况考生查询数据查询失败："+e.getMessage(),e); 
        }
        return null;
    }

    /**
     * 重考设置 查询
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kscksz:query")
    @RequestMapping(value = "/getResetExams", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getResetExams(@ModelAttribute("resetExamine") tExamExampaperExamine examine, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
            examine.setFkUserId(user.getUserId());
            examine.setZuhuId(zuhuId);
            log.info("操作用户:["+user.getUserId()+"]"+"重考设置查询数据开始");
            List<tExamExampaperExamine> tlist = examExampaperExamineService.selectResetExam(examine);
            log.info("操作用户:["+user.getUserId()+"]"+"重考设置查询数据结束");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonConfig.registerJsonValueProcessor(Time.class, new JsonTimeValueProcessor());
            result.put("Rows", JSONArray.fromObject(tlist, jsonConfig));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"重考设置数据查询失败："+e.getMessage(),e); 
        }
        return null;
    }

    @RequiresPermissions("kscksz:ck")
    @Log(methodname="restartExam",modulename="重考设置",funcname="重置考试提交",description="重置考试提交,{0}")
    @RequestMapping(value = "/restartExam", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject restartExam(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
            String param[] = request.getParameter("params").split(",");
            log.info("操作用户:["+user.getUserId()+"]"+"重考设置循环提交开始");
            for (String string : param) { // examineid_examid_userId_isvert
                String arr[] = string.split("_");
                tExamExampaperExamine examExampaperExamine = new tExamExampaperExamine();
                if(arr.length>3){
                    examExampaperExamine.setIsrevert("1");
                    log.info("操作用户:["+user.getUserId()+"]"+"查询考试剩余时间");
                    examExampaperExamine.setSurplustime(examExampaperExamineService.getsltMeByEId(Integer.parseInt(arr[0])));
                }else{
                    examExampaperExamine.setIsrevert("0");
                }
                examExampaperExamine.setPkAutoId(Integer.parseInt(arr[0]));
                examExampaperExamine.setExamId(Integer.parseInt(arr[1]));
                examExampaperExamine.setFkUserId(Integer.parseInt(arr[2]));
                examExampaperExamine.setExamineeStatus("0");
                examExampaperExamine.setExampaperScore(0);
                examExampaperExamine.setBeginTime(null);
                examExampaperExamine.setSubmitTime(null);
                examExampaperExamine.setFkScoreuserId(0);
                examExampaperExamine.setIsnormal("1");
                examExampaperExamineService.updateExamReset(examExampaperExamine);
            }
            log.info("操作用户:["+user.getUserId()+"]"+"重考设置循环提交结束");
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"提交成功"}));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"重考设置循环提交失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
        }
        return null;
    }
    
    /**
     * 手动评分 获取可手动评分的数据
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kssjpf:query")
    @RequestMapping(value = "/getScoreManual", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getScoreManual(@ModelAttribute("scoreManual") ExamManualScore score, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
            log.info("操作用户:["+user.getUserId()+"]"+"获取可手动评分的数据开始");   
            List<ExamManualScore> tlist = examExampaperExamineService.selectScoreManual(score);
            log.info("操作用户:["+user.getUserId()+"]"+"获取可手动评分的数据结束");   
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonConfig.registerJsonValueProcessor(Time.class, new JsonTimeValueProcessor());
            result.put("Rows", JSONArray.fromObject(tlist, jsonConfig));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"获取可手动评分的数据失败："+e.getMessage(),e); 
        }
        return null;
    }
    
    /**
     * 手动评分 判断是否已经在评分
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kswhgl:sdpf")
    @RequestMapping(value = "/checkScoreManual", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkScoreManual(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
          int pkAutoId = Integer.parseInt(request.getParameter("id"));
          String canJoinNow = ""; // 是否可以进入评分，1代表可以进入评分，0代表不可以
          String status="";
          tExamExampaperExamine examine = examExampaperExamineService.selectByPrimaryKey(pkAutoId);
          if(examine==null){
              canJoinNow = "0";
              result.put("canJoinNow", canJoinNow);
          }else{
        	  status= examine.getExamineeStatus();
          }
          
          if ("9".equals(status)&&examine!=null) { // 正在手工评分中的试卷，只有原评分人才可强行进入继续评分
              log.info("操作用户:["+user.getUserId()+"]"+"正在手工评分中的试卷，只有原评分人才可强行进入继续评分");   
              if (user.getUserId() != null && user.getUserId().equals(examine.getFkScoreuserId())) {
                  canJoinNow = "1";
              } else {
                  canJoinNow = "0";
              }
          } else { // 其他状态可以直接进入评分，同时更新试卷状态以及评分人
              tExamExampaperExamine texamExamine = new tExamExampaperExamine();
              texamExamine.setPkAutoId(pkAutoId);
              texamExamine.setExamineeStatus("9");
              texamExamine.setFkScoreuserId(user.getUserId()); //userId
              examExampaperExamineService.updateByPrimaryKeySelective(texamExamine);
              canJoinNow = "1";
          }
          result.put("canJoinNow", canJoinNow);
          return result;
      } catch (Exception e) {
          log.error("操作用户:["+user.getUserId()+"]"+"判断是否已经在评分失败："+e.getMessage(),e); 
      }
      return null;
    }

    /**
     * 手动评分 开始手动评分
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kswhgl:sdpf")
    @RequestMapping(value="/openUpdateView",method=RequestMethod.GET)
    public String openUpdateView(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        int pkAutoId = Integer.parseInt(request.getParameter("id"));
        String queryflag = request.getParameter("queryflag");   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            //自动评分测试
            //examExampaperExamineService.updateAutoSetMark(pkAutoId); 
            int typeOneScoreTotal = 0;// 判断题模块总分
            int typeTwoScoreTotal = 0;// 判断改错题模块总分
            int typeThreeScoreTotal = 0;// 单选题模块总分
            int typeFourScoreTotal = 0;// 多选题模块总分
            int typeFiveScoreTotal = 0;// 不定项选择题模块总分
            int typeSixScoreTotal = 0;// 填空题模块总分
            int typeSevenScoreTotal = 0;// 问答题模块总分
            int typeEightScoreTotal = 0;// 案例分析题模块总分
            List list = null;
            
            // 先判断是否为随机模板试卷
            ExamManualScore t = examExampaperExamineService.selectExampaperQuestionIFRandom(pkAutoId);
            if(t!=null){ 
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,t.getUserId(),t.getExamId()+"");
            }else{
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,"","");
            }

            if (list != null && list.size() > 0) {
                String[][] array  = new String[list.size()][4];
                for (int i = 0; i < list.size(); i++) {
                    ExamManualScore manualScore = (ExamManualScore) list.get(i);
                    array[i][1] = manualScore.getQuesContent().replace("\n", "<br>");
                    String [] aa = array[i][1].split("</DIV>");
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < aa.length; j++) {
                        if (!"".equals(aa[j])) {
                            buffer.append(aa[j]+"</DIV>");
                        }
                    }
                    array[i][1] = buffer.toString().replace("<DIV", "<SPAN").replace("</DIV>", "</SPAN>");
                    manualScore.setQuesContent(array[i][1].trim());
                    if(manualScore.getResultsScore()==null){
                        manualScore.setResultsScore("0");
                    }
                    if(manualScore.getResultsCorrectAnswer()==null){
                        manualScore.setResultsCorrectAnswer("");
                    }
                    //根据考题类型累加模块总分
                    if ("1".equals(manualScore.getQuesType())) {
                        typeOneScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("2".equals(manualScore.getQuesType())){
                        typeTwoScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("3".equals(manualScore.getQuesType())){
                        typeThreeScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("4".equals(manualScore.getQuesType())){
                        typeFourScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("5".equals(manualScore.getQuesType())){
                        typeFiveScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("6".equals(manualScore.getQuesType())){
                        typeSixScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("7".equals(manualScore.getQuesType())){
                        typeSevenScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("8".equals(manualScore.getQuesType())){
                        typeEightScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }
                }
            }
            if(queryflag==null) { 
                log.info("操作用户:["+user.getUserId()+"]"+"更新考试考卷考生状态(修改手动评分中)"); 
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"更新考试考卷考生状态(修改手动评分中)"}));
            }else { 
                log.info("操作用户:["+user.getUserId()+"]"+"考试试卷查询"); 
            }
            modelMap.put("artificialList", list);
            modelMap.put("exampaperName", ((ExamManualScore)list.get(0)).getExamPaperName());
            modelMap.put("workId", ((ExamManualScore)list.get(0)).getWorkId());
            modelMap.put("userId", ((ExamManualScore)list.get(0)).getUserId());
            modelMap.put("examExampaperExamineId", ((ExamManualScore)list.get(0)).getPkAutoId());

            String statuDesc = "";
            String intStatus =  ((ExamManualScore)list.get(0)).getExamineeStatus();
            if(intStatus.equalsIgnoreCase("0")) statuDesc = "待考中";
            if(intStatus.equalsIgnoreCase("1")) statuDesc = "考试中";
            if(intStatus.equalsIgnoreCase("2")) statuDesc = "已考完";
            if(intStatus.equalsIgnoreCase("3")) statuDesc = "取消考试资格 ";
            if(intStatus.equalsIgnoreCase("4")) statuDesc = "缺考";
            if(intStatus.equalsIgnoreCase("5")) statuDesc = "已自动评分";
            if(intStatus.equalsIgnoreCase("6")) statuDesc = "已手动评分";
            if(intStatus.equalsIgnoreCase("9")) statuDesc = "手动评分中";
            modelMap.put("examineeStatus", statuDesc);
            //实现在主观阅卷的时候能够查看之前客观题目的功能
            modelMap.put("exampaperScore", ((ExamManualScore)list.get(0)).getExampaperScore());//考生得分
            modelMap.put("typeOneScoreTotal", String.valueOf(typeOneScoreTotal));
            modelMap.put("typeTwoScoreTotal", String.valueOf(typeTwoScoreTotal));
            modelMap.put("typeThreeScoreTotal", String.valueOf(typeThreeScoreTotal));
            modelMap.put("typeFourScoreTotal", String.valueOf(typeFourScoreTotal));
            modelMap.put("typeFiveScoreTotal", String.valueOf(typeFiveScoreTotal));
            modelMap.put("typeSixScoreTotal", String.valueOf(typeSixScoreTotal));
            modelMap.put("typeSevenScoreTotal", String.valueOf(typeSevenScoreTotal));
            modelMap.put("typeEightScoreTotal", String.valueOf(typeEightScoreTotal));
            modelMap.put("scoreTotal", String.valueOf(typeOneScoreTotal+typeTwoScoreTotal+typeThreeScoreTotal+typeFourScoreTotal+typeFiveScoreTotal+typeFiveScoreTotal+typeSixScoreTotal+typeSevenScoreTotal+typeEightScoreTotal));
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"查询成功["+list.size()+"]"}));
        } catch (Exception e) {
            if(queryflag==null) { 
                log.error("操作用户:["+user.getUserId()+"]"+"打开手动评分失败："+e.getMessage(),e); 
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
            }
            else {
                log.error("操作用户:["+user.getUserId()+"]"+"试卷查询失败："+e.getMessage(),e); 
            }
        } 
        if(queryflag==null){
            return "marking/showArtificial";
        }else{
            return "publicdialog/showExampaper";
        }
    }
    
    
    
    /**
     * 首页 个人考试查询
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequestMapping(value="/openUpdateViewByMain",method=RequestMethod.GET)
    public String openUpdateViewByMain(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        int pkAutoId = Integer.parseInt(request.getParameter("id"));
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            //自动评分测试
            //examExampaperExamineService.updateAutoSetMark(pkAutoId); 
            int typeOneScoreTotal = 0;// 判断题模块总分
            int typeTwoScoreTotal = 0;// 判断改错题模块总分
            int typeThreeScoreTotal = 0;// 单选题模块总分
            int typeFourScoreTotal = 0;// 多选题模块总分
            int typeFiveScoreTotal = 0;// 不定项选择题模块总分
            int typeSixScoreTotal = 0;// 填空题模块总分
            int typeSevenScoreTotal = 0;// 问答题模块总分
            int typeEightScoreTotal = 0;// 案例分析题模块总分
            List list = null;
            
            // 先判断是否为随机模板试卷
            ExamManualScore t = examExampaperExamineService.selectExampaperQuestionIFRandom(pkAutoId);
            if(t!=null){ 
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,t.getUserId(),t.getExamId()+"");
            }else{
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,"","");
            }

            if (list != null && list.size() > 0) {
                String[][] array  = new String[list.size()][4];
                for (int i = 0; i < list.size(); i++) {
                    ExamManualScore manualScore = (ExamManualScore) list.get(i);
                    array[i][1] = manualScore.getQuesContent().replace("\n", "<br>");
                    String [] aa = array[i][1].split("</DIV>");
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < aa.length; j++) {
                        if (!"".equals(aa[j])) {
                            buffer.append(aa[j]+"</DIV>");
                        }
                    }
                    array[i][1] = buffer.toString().replace("<DIV", "<SPAN").replace("</DIV>", "</SPAN>");
                    manualScore.setQuesContent(array[i][1].trim());
                    if(manualScore.getResultsScore()==null){
                        manualScore.setResultsScore("0");
                    }
                    if(manualScore.getResultsCorrectAnswer()==null){
                        manualScore.setResultsCorrectAnswer("");
                    }
                    //根据考题类型累加模块总分
                    if ("1".equals(manualScore.getQuesType())) {
                        typeOneScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("2".equals(manualScore.getQuesType())){
                        typeTwoScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("3".equals(manualScore.getQuesType())){
                        typeThreeScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("4".equals(manualScore.getQuesType())){
                        typeFourScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("5".equals(manualScore.getQuesType())){
                        typeFiveScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("6".equals(manualScore.getQuesType())){
                        typeSixScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("7".equals(manualScore.getQuesType())){
                        typeSevenScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("8".equals(manualScore.getQuesType())){
                        typeEightScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }
                }
            } 
            log.info("操作用户:["+user.getUserId()+"]"+"考试试卷查询"); 
            modelMap.put("artificialList", list);
            modelMap.put("exampaperName", ((ExamManualScore)list.get(0)).getExamPaperName());
            modelMap.put("workId", ((ExamManualScore)list.get(0)).getWorkId());
            modelMap.put("userId", ((ExamManualScore)list.get(0)).getUserId());
            modelMap.put("examExampaperExamineId", ((ExamManualScore)list.get(0)).getPkAutoId());
           
            String statuDesc = "";
            String intStatus =  ((ExamManualScore)list.get(0)).getExamineeStatus();
            if(intStatus.equalsIgnoreCase("0")) statuDesc = "待考中";
            if(intStatus.equalsIgnoreCase("1")) statuDesc = "考试中";
            if(intStatus.equalsIgnoreCase("2")) statuDesc = "已考完";
            if(intStatus.equalsIgnoreCase("3")) statuDesc = "取消考试资格 ";
            if(intStatus.equalsIgnoreCase("4")) statuDesc = "缺考";
            if(intStatus.equalsIgnoreCase("5")) statuDesc = "已自动评分";
            if(intStatus.equalsIgnoreCase("6")) statuDesc = "已手动评分";
            if(intStatus.equalsIgnoreCase("9")) statuDesc = "手动评分中";
            modelMap.put("examineeStatus", statuDesc);
            
            //实现在主观阅卷的时候能够查看之前客观题目的功能
            modelMap.put("exampaperScore", ((ExamManualScore)list.get(0)).getExampaperScore());//考生得分
            modelMap.put("typeOneScoreTotal", String.valueOf(typeOneScoreTotal));
            modelMap.put("typeTwoScoreTotal", String.valueOf(typeTwoScoreTotal));
            modelMap.put("typeThreeScoreTotal", String.valueOf(typeThreeScoreTotal));
            modelMap.put("typeFourScoreTotal", String.valueOf(typeFourScoreTotal));
            modelMap.put("typeFiveScoreTotal", String.valueOf(typeFiveScoreTotal));
            modelMap.put("typeSixScoreTotal", String.valueOf(typeSixScoreTotal));
            modelMap.put("typeSevenScoreTotal", String.valueOf(typeSevenScoreTotal));
            modelMap.put("typeEightScoreTotal", String.valueOf(typeEightScoreTotal));
            modelMap.put("scoreTotal", String.valueOf(typeOneScoreTotal+typeTwoScoreTotal+typeThreeScoreTotal+typeFourScoreTotal+typeFiveScoreTotal+typeFiveScoreTotal+typeSixScoreTotal+typeSevenScoreTotal+typeEightScoreTotal));
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"查询成功["+list.size()+"]"}));
        } catch (Exception e) {
                log.error("操作用户:["+user.getUserId()+"]"+"试卷查询失败："+e.getMessage(),e); 
        } 
            return "publicdialog/showExampaper";
    }
    
    
    
    
    /**
     * 首页查询已考试卷详情
     * @author niewenqiang
     * @date 2017-5-22
     */
    @RequestMapping(value="/showExampaperByMain",method=RequestMethod.GET)
    public String showExampaperByMain(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        int pkAutoId = Integer.parseInt(request.getParameter("id")); 
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            int typeOneScoreTotal = 0;// 判断题模块总分
            int typeTwoScoreTotal = 0;// 判断改错题模块总分
            int typeThreeScoreTotal = 0;// 单选题模块总分
            int typeFourScoreTotal = 0;// 多选题模块总分
            int typeFiveScoreTotal = 0;// 不定项选择题模块总分
            int typeSixScoreTotal = 0;// 填空题模块总分
            int typeSevenScoreTotal = 0;// 问答题模块总分
            int typeEightScoreTotal = 0;// 案例分析题模块总分
            List list = null;
            
            // 先判断是否为随机模板试卷
            ExamManualScore t = examExampaperExamineService.selectExampaperQuestionIFRandom(pkAutoId);
            if(t!=null){ 
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,t.getUserId(),t.getExamId()+"");
            }else{
                list = examExampaperExamineService.queryExampaperForScore(pkAutoId,"","");
            }

            if (list != null && list.size() > 0) {
                String[][] array  = new String[list.size()][4];
                for (int i = 0; i < list.size(); i++) {
                    ExamManualScore manualScore = (ExamManualScore) list.get(i);
                    array[i][1] = manualScore.getQuesContent().replace("\n", "<br>");
                    String [] aa = array[i][1].split("</DIV>");
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < aa.length; j++) {
                        if (!"".equals(aa[j])) {
                            buffer.append(aa[j]+"</DIV>");
                        }
                    }
                    array[i][1] = buffer.toString().replace("<DIV", "<SPAN").replace("</DIV>", "</SPAN>");
                    manualScore.setQuesContent(array[i][1].trim());
                    if(manualScore.getResultsScore()==null){
                        manualScore.setResultsScore("0");
                    }
                    if(manualScore.getResultsCorrectAnswer()==null){
                        manualScore.setResultsCorrectAnswer("");
                    }
                    //根据考题类型累加模块总分
                    if ("1".equals(manualScore.getQuesType())) {
                        typeOneScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("2".equals(manualScore.getQuesType())){
                        typeTwoScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("3".equals(manualScore.getQuesType())){
                        typeThreeScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("4".equals(manualScore.getQuesType())){
                        typeFourScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("5".equals(manualScore.getQuesType())){
                        typeFiveScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("6".equals(manualScore.getQuesType())){
                        typeSixScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("7".equals(manualScore.getQuesType())){
                        typeSevenScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }else if("8".equals(manualScore.getQuesType())){
                        typeEightScoreTotal += Integer.parseInt(manualScore.getQuesScore());
                    }
                }
            }
            log.info("操作用户:["+user.getUserId()+"]"+"考试试卷查询");
            modelMap.put("artificialList", list);
            modelMap.put("exampaperName", ((ExamManualScore)list.get(0)).getExamPaperName());
            modelMap.put("workId", ((ExamManualScore)list.get(0)).getWorkId());
            modelMap.put("userId", ((ExamManualScore)list.get(0)).getUserId());
            modelMap.put("examExampaperExamineId", ((ExamManualScore)list.get(0)).getPkAutoId());

            String statuDesc = "";
            String intStatus =  ((ExamManualScore)list.get(0)).getExamineeStatus();
            if(intStatus.equalsIgnoreCase("0")) statuDesc = "待考中";
            if(intStatus.equalsIgnoreCase("1")) statuDesc = "考试中";
            if(intStatus.equalsIgnoreCase("2")) statuDesc = "已考完";
            if(intStatus.equalsIgnoreCase("3")) statuDesc = "取消考试资格 ";
            if(intStatus.equalsIgnoreCase("4")) statuDesc = "缺考";
            if(intStatus.equalsIgnoreCase("5")) statuDesc = "已自动评分";
            if(intStatus.equalsIgnoreCase("6")) statuDesc = "已手动评分";
            if(intStatus.equalsIgnoreCase("9")) statuDesc = "手动评分中";
            modelMap.put("examineeStatus", statuDesc);
            
            //实现在主观阅卷的时候能够查看之前客观题目的功能
            modelMap.put("exampaperScore", ((ExamManualScore)list.get(0)).getExampaperScore());//考生得分
            modelMap.put("typeOneScoreTotal", String.valueOf(typeOneScoreTotal));
            modelMap.put("typeTwoScoreTotal", String.valueOf(typeTwoScoreTotal));
            modelMap.put("typeThreeScoreTotal", String.valueOf(typeThreeScoreTotal));
            modelMap.put("typeFourScoreTotal", String.valueOf(typeFourScoreTotal));
            modelMap.put("typeFiveScoreTotal", String.valueOf(typeFiveScoreTotal));
            modelMap.put("typeSixScoreTotal", String.valueOf(typeSixScoreTotal));
            modelMap.put("typeSevenScoreTotal", String.valueOf(typeSevenScoreTotal));
            modelMap.put("typeEightScoreTotal", String.valueOf(typeEightScoreTotal));
            modelMap.put("scoreTotal", String.valueOf(typeOneScoreTotal+typeTwoScoreTotal+typeThreeScoreTotal+typeFourScoreTotal+typeFiveScoreTotal+typeFiveScoreTotal+typeSixScoreTotal+typeSevenScoreTotal+typeEightScoreTotal));
        } catch (Exception e) {
             log.error("操作用户:["+user.getUserId()+"]"+"试卷查询失败："+e.getMessage(),e); 
        } 
           return "publicdialog/showExampaperByMain";
    }
    
    
    
    @RequiresPermissions("kswhgl:tj")
    @Log(methodname="updateResultScore",modulename="手动评分",funcname="手动评分提交",description="手动评分提交,{0}")
    @RequestMapping(value = "/updateResultScore", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateResultScore(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        String examineId = "";
        try {
            String params = request.getParameter("params");
            examineId = request.getParameter("examineId");
            String userId = request.getParameter("userId");
            log.info("操作用户:["+user.getUserId()+"]"+"手动评分提交开始");
            String totalScore = examExampaperExamineService.updateResultScore(params, examineId, Integer.parseInt(userId));
            log.info("操作用户:["+user.getUserId()+"]"+"手动评分提交结束");
            result.put("totalScore", totalScore);
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"手动评分提交成功,id：["+examineId+"],分数["+totalScore+"]"}));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"手动评分失败：,id：["+examineId+"],"+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
        }
        return null;
    }
    
}
