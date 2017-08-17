package cn.sh.ideal.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.tExamVo;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.VSysuserService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.TreeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.reflect.generics.tree.Tree;

@Controller
@RequestMapping(value="/controller/sysuser")
public class SysuserController {
    
    private static Logger log = Logger.getLogger(OperateLogController.class);
    @Autowired
    private VSysuserService sysuserService;
     
  //****测试
    int zuhuId = 1;
    
    /**
     * 查询用户信息
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequestMapping(value="/getUsers",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getUsers(@ModelAttribute("queryUser") vSysuser userVo, HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);      
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        try {
//            log.info("操作用户:["+user.getUserId()+"]"+"查询用户信息开始");
            List<vSysuser> users = sysuserService.selectSysusers(userVo);
//            log.info("操作用户:["+user.getUserId()+"]"+"查询用户信息结束,["+users.size()+"]");
            result.put("Rows",JSONArray.fromObject(users));
            return result;
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询用户信息失败："+e.getMessage(),e); 
        } 
        return null;
    }
    
    /**
     * 查询所有组室
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequestMapping(value="/getGroups",method=RequestMethod.POST)
    public String getGroups(HttpServletRequest request,HttpServletResponse response){
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);      
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        JSONObject result = new JSONObject();
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
//            log.info("操作用户:["+user.getUserId()+"]"+"查询所有组室开始");
            List<vSysuser> groups = sysuserService.getGroups();
//            log.info("操作用户:["+user.getUserId()+"]"+"查询所有组室结束,["+groups.size()+"]");
            result.put("groups",JSONArray.fromObject(groups));
            pw.print(result);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error("操作用户:["+user.getUserId()+"]"+"查询所有组室失败："+e.getMessage(),e); 
        } finally {
            if (pw != null)
                pw.close();
        }
        return null;
    }
    
    /**
     * 查询角色
     * @param vo
     * @author chendi
     * @param response
     * @return
     */
    @RequestMapping(value="/getRoles",method=RequestMethod.POST)
    public void getRoles(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Constans.CONTENTCHA);
        response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
        try {
            List<SysRole> roles = sysuserService.getRoles();
            TreeUtil util = new TreeUtil(roles);
            SysRole roleSys = util.generateSysRole(34);
            SysRole rolezj = util.generateSysRole(35);
            SysRole roleks = util.generateSysRole(36);
            JSONObject jsonObjZj = JSONObject.fromObject(rolezj);  
            JSONObject jsonObjKs = JSONObject.fromObject(roleks);
            JSONObject jsonObjSys = JSONObject.fromObject(roleSys); 
            String strSys = jsonObjSys.toString().replace("\"children\":[],", "");    
            strSys = strSys.replace("roleName", "text");
            strSys = strSys.replace("roleId", "id");
            String strzj = jsonObjZj.toString().replace("\"children\":[],", "");    
            strzj = strzj.replace("roleName", "text");
            strzj = strzj.replace("roleId", "id");
            String strks = jsonObjKs.toString().replace("\"children\":[],", "");   
            strks = strks.replace("roleName", "text");
            strks = strks.replace("roleId", "id");
            response.getWriter().print("["+strSys+","+strzj+","+strks+"]");
        } catch (Exception e) {
            log.error("角色列表据获取异常:"+e.getMessage(),e);
        }
    }
    
    
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}
}
