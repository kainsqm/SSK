package cn.sh.ideal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/controller/page")
public class PageController {
    
    /**
     * 考试管理-考试维护管理
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequestMapping(value="/manageView",method=RequestMethod.GET)
    public String manageView(HttpServletRequest request,HttpServletResponse response){
       return "examination/exam_manage";
    }  
    
    /**
     * 考试管理-考试维护管理-增加考试
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequiresPermissions("kswhgl:add")
    @RequestMapping(value="/addView",method=RequestMethod.GET)
    public String addView(HttpServletRequest request,HttpServletResponse response){
       return "examination/addexam_manage";
    } 
    
    /**
     * 考试管理-重考设置
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequestMapping(value="/examResetView",method=RequestMethod.GET)
    public String examResetView(HttpServletRequest request,HttpServletResponse response){
       return "examination/resetexam_set";
    } 
    
    /**
     * 阅卷管理-试卷评分
     * @param request
     * @param response
     * @author chendi
     * @return
     */
    @RequestMapping(value="/scoreView",method=RequestMethod.GET)
    public String scoreView(HttpServletRequest request,HttpServletResponse response){
       return "marking/testpaper_score";
    } 
    
    /**
     * 考试情况查询-考试查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/examQuery",method=RequestMethod.GET)
    public String examQuery(HttpServletRequest request,HttpServletResponse response){
       return "examsquery/exam_query";
    } 
    
    /**
     * 考试情况查询-个人考试查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/examPersonQuery",method=RequestMethod.GET)
    public String examPersonQuery(HttpServletRequest request,HttpServletResponse response){
       return "examsquery/exam_query_person";
    } 
   
    /**
     * 考试情况查询-考生查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="userQuery",method=RequestMethod.GET)
    public String userQuery(HttpServletRequest request,HttpServletResponse response){
       return "examsquery/user_query";
    }
    
    
    /**
     * 系统配置-业务参数配置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="systemsQuery",method=RequestMethod.GET)
    public String systemsQuery(HttpServletRequest request,HttpServletResponse response){
        return "system/sys_systems"; 
    } 
}
