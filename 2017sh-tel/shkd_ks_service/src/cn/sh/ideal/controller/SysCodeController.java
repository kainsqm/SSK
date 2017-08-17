package cn.sh.ideal.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamVo;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExamService;
import cn.sh.ideal.service.IExampaperService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.QuesTionsService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/controller/syscode")
public class SysCodeController {
    private static Logger log = Logger.getLogger(OperateLogController.class);
    @Autowired
    private ISysCodeService iSysCodeService ;
    
    @Autowired
    private IExamService examService;
    
    @Autowired
    private IExampaperService exampaperService;
    
    @Autowired
    private QuesTionsService quesTionsService;
     
    //****测试
    int zuhuId = 1;
    
    /**
     * 查询类别syscode
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequestMapping(value="/getSyscodes",method=RequestMethod.POST)
    public String getSyscodes(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);    
        vSysuser user= (vSysuser) request.getAttribute("sysuser");  
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            String itemFlag =request.getParameter("item_flag");
            log.info("操作用户:["+user.getUserId()+"]"+"查询类别"+itemFlag);
            List<tSysCode> codes = iSysCodeService.getItemFlagList(itemFlag);
            log.info("操作用户:["+user.getUserId()+"]"+"查询类别结束"+codes.size());
            JSONObject result = new JSONObject();
            result.put("codes",JSONArray.fromObject(codes));
            pw.print(result);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询类别失败："+e.getMessage(),e); 
        } finally {
            if (pw != null)
                pw.close();
        }
        return null;
    }
    
    /**
     * 系统参数配置 查询系统参数配置
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequiresPermissions("kscspz:query")
    @RequestMapping(value="/getSysSetting",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getSysSetting(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);    
        vSysuser user= (vSysuser) request.getAttribute("sysuser");  
        JSONObject result = new JSONObject();
        try {
            List<Map<String, Object>> nodes = new ArrayList();
            log.info("操作用户:["+user.getUserId()+"]"+"查询系统参数开始");
            List<tSysCode> list =  iSysCodeService.getSysSettingLists();
            log.info("操作用户:["+user.getUserId()+"]"+"查询系统参数结束");
            for (tSysCode tSysCode : list) {
                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("id", tSysCode.getPkAutoId());
                obj.put("name",tSysCode.getName());
                if(tSysCode.getItemFlag()!=null && tSysCode.getItemFlag().equalsIgnoreCase("SYSCODE")){
                    obj.put("open", true);
                } else if(tSysCode.getItemFlag()!=null && !tSysCode.getItemFlag().equalsIgnoreCase("")){
                    obj.put("open", false);
                }
                obj.put("pId", tSysCode.getPid());
                nodes.add(obj);
            }
            result.put("nodes",JSONArray.fromObject(nodes));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询系统参数失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    @RequiresPermissions("kscspz:add")
    @Log(methodname="addSysCode",modulename="系统参数配置",funcname="新增系统参数",description="新增系统参数,{0}")
    @RequestMapping(value="/addSysCode",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject addSysCode(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");   
        JSONObject result = new JSONObject();
        int insertFlag = 0;
        try {
            String pId =request.getParameter("pId");
            String name =request.getParameter("name");
            
            tSysCode code = new tSysCode();
            code.setPid(Integer.parseInt(pId));
            code.setName(name);
            code.setZuhuId(zuhuId);
            List<tSysCode> list = iSysCodeService.getSysByPidAndName(code);
            if(list.isEmpty()){
                log.info("操作用户:["+user.getUserId()+"]"+"新增系统参数开始："+name);
                insertFlag = iSysCodeService.insertSetting(code);
                log.info("操作用户:["+user.getUserId()+"]"+"新增系统参数结束："+name);
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"+["+name+"]成功"}));
            }else{
                insertFlag = -1;
                result.put("flag", "该名称已存在，请勿重复添加");
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"+["+name+"]失败，该名称已存在，请勿重复添加"}));
            }
            result.put("insertFlag", insertFlag);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"新增系统参数失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"新增系统参数失败"+e.getMessage()}));
        } 
        return null;
    }
    
    @RequiresPermissions("kscspz:update")
    @Log(methodname="updateSysCode",modulename="系统参数配置",funcname="修改系统参数",description="修改系统参数,{0}")
    @RequestMapping(value="/updateSysCode",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject updateSysCode(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);    
        vSysuser user= (vSysuser) request.getAttribute("sysuser");  
        JSONObject result = new JSONObject();
        int updateFlag = 0;
        try {
            String id =request.getParameter("id");
            String pId =request.getParameter("pId");
            String name =request.getParameter("name");
            
            tSysCode code = new tSysCode();
            if(pId!=null && pId.length()>0 && id!=null && id.length()>0){
                code.setPid(Integer.parseInt(pId));
                code.setPkAutoId(Integer.parseInt(id));
                code.setName(name);
                List<tSysCode> list = iSysCodeService.getSysByPidAndName(code);
                if(list.isEmpty()){
                    log.info("操作用户:["+user.getUserId()+"]"+"修改系统参数开始："+name);
                    updateFlag = iSysCodeService.updateSetting(code);
                    log.info("操作用户:["+user.getUserId()+"]"+"修改系统参数结束："+name);
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"修改系统参数+["+name+"]成功"}));
                }else{
                    updateFlag = -1;
                    result.put("flag", "修改失败，该名称已存在");
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"修改系统参数+["+name+"]失败，修改失败，该名称已存在"}));
                }
            }else{
                updateFlag = 3;
            }
            result.put("updateFlag", updateFlag);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"修改系统参数失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"修改系统参数失败"+e.getMessage()}));
        } 
        return null;
    }
    
    @RequiresPermissions("kscspz:delete")
    @Log(methodname="delSysCode",modulename="系统参数配置",funcname="删除系统参数",description="删除系统参数,{0}")
    @RequestMapping(value="/delSysCode",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delSysCode(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");   
        JSONObject result = new JSONObject();
        int delFlag = 0;
        try {
            String id =request.getParameter("id");
            String name =request.getParameter("name");
            
            tSysCode code = new tSysCode();
            code.setPkAutoId(Integer.parseInt(id));
            code.setName(name);
            
            List<tExam> examList = examService.selectByCodeId(Integer.parseInt(id));
            if(examList.isEmpty()){
               List<tExampaper> exampaperList = exampaperService.selectByCodeId(Integer.parseInt(id));
                if(exampaperList.isEmpty()){
                    log.info("操作用户:["+user.getUserId()+"]"+"删除系统参数开始："+name);
                    delFlag = iSysCodeService.delSetting(code);
                    log.info("操作用户:["+user.getUserId()+"]"+"删除系统参数结束："+name);
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除系统参数+["+name+"]成功"}));
                } else{
                    delFlag = -1;
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除系统参数+["+name+"]失败，操作异常"}));
                }
            }else{
                delFlag = -1; 
                LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除系统参数+["+name+"]失败，该参数不存在"}));
            }
            result.put("delFlag", delFlag);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"删除系统参数失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除系统参数失败"+e.getMessage()}));
        } 
        return null;
    }
    
    /**
     * 系统参数配置 查询业务分类配置
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequiresPermissions("kscspz:query")
    @RequestMapping(value="/getSysBusindess",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getSysBusindess(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");   
        JSONObject result = new JSONObject();
        try {
            List<Map<String, Object>> nodes = new ArrayList();
            log.info("操作用户:["+user.getUserId()+"]"+"查询业务分类开始");
            List<tSysCode> list =  iSysCodeService.getSysBusindess();
            log.info("操作用户:["+user.getUserId()+"]"+"查询业务分类结束");
            for (tSysCode tSysCode : list) {
                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("id", tSysCode.getPkAutoId());
                obj.put("name",tSysCode.getName());
                if(tSysCode.getItemFlag()!=null && !tSysCode.getItemFlag().equalsIgnoreCase("")){
                    obj.put("open", true);
                }else{
                    obj.put("open", false);
                }
                obj.put("pId", tSysCode.getPid());
                nodes.add(obj);
            }
            result.put("nodes",JSONArray.fromObject(nodes));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询业务分类失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    @RequiresPermissions("kscspz:delete")
    @RequestMapping(value="/delSysBusiness",method=RequestMethod.POST)
    @Log(methodname="delSysCode",modulename="删除业务分类配置",funcname="删除业务分类",description="删除业务分类,{0}")
    @ResponseBody
    public JSONObject delSysBusiness(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);     
        vSysuser user= (vSysuser) request.getAttribute("sysuser"); 
        JSONObject result = new JSONObject();
        int delFlag = 0;
        try {
            String id =request.getParameter("id");
            String name =request.getParameter("name");
            
            tSysCode code = new tSysCode();
            code.setPkAutoId(Integer.parseInt(id));
            code.setName(name);
            
            //判断是否有子类 返回-2
            List<tSysCode> pidList = iSysCodeService.selectByPid(Integer.parseInt(id));
            if(pidList.isEmpty()){
                List<tQuestions> querList = quesTionsService.selectByCodeId(Integer.parseInt(id));
                if(querList.isEmpty()){
                    log.info("操作用户:["+user.getUserId()+"]"+"删除业务分类开始："+name);
                    delFlag = iSysCodeService.delSetting(code);
                    log.info("操作用户:["+user.getUserId()+"]"+"删除业务分类结束："+name);
                    LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除业务分类+["+name+"]成功"}));
                } else {
                    //在使用返回-1
                    delFlag = -1;
                }
            }else{
                delFlag = -2; 
            }
            result.put("delFlag", delFlag);
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"删除业务分类失败："+e.getMessage(),e); 
            LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"删除业务分类失败"+e.getMessage()}));
        } 
        return null;
    }
    
}

