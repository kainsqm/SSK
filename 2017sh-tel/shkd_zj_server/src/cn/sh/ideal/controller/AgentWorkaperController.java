package cn.sh.ideal.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.AgentWorkpaper;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IAgentWorkpaperService;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/agent")
public class AgentWorkaperController {
	private static Logger log = Logger.getLogger(RecordInfoController.class);
	@Resource
	private IAgentWorkpaperService agentService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IQCDetailService QCDetailService;
	@Resource
	private ISysCodeService codeService;
	
	@RequiresPermissions("paper:query")
	@RequestMapping(value="/togetAgentList",method=RequestMethod.GET)
	public String togetAgentList(){		
		return "workPaperList";
	}
	
	@RequestMapping(value="/getAgentList",method = RequestMethod.POST)
	public void getAgentList(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String qcworkid=request.getParameter("qcworkid");
		String agentworkpaperid=request.getParameter("agentworkpaperid");
		String agentworkid=request.getParameter("agentworkid");
		String dealstate=request.getParameter("dealstate");
		String startqcworkpapertime=request.getParameter("startqcworkpapertime");
		String stopqcworkpapertime=request.getParameter("stopqcworkpapertime");
		String groupName=request.getParameter("groupName");
		String qcid=request.getParameter("qc_id");
		if(qcid==null||"".equals(qcid)){
			agent.setQcid(0);
		}else{
			agent.setQcid(Integer.parseInt(qcid));
		}
		String role_flag=request.getAttribute("role_flag").toString();
		String workid=request.getAttribute(Constans.USER_INFO_WORKID).toString();	
			if("role_3".equals(role_flag)){
				agent.setQcworkid(workid);		
			}	
		if(qcworkid!=null&&!"".equals(qcworkid)){
			agent.setQcworkid(qcworkid);
		}
		if(agentworkpaperid!=null&&!"".equals(agentworkpaperid)){		
			agent.setAgentworkpaperid(Integer.parseInt(agentworkpaperid));
		}
		if(agentworkid!=null&&!"".equals(agentworkid)){	
			agent.setAgentworkid(agentworkid);
		}
		agent.setDealstate(dealstate);
		agent.setStartqcworkpapertime(startqcworkpapertime);
		agent.setStopqcworkpapertime(stopqcworkpapertime);	
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<AgentWorkpaper> listagent=agentService.listAgentworkaper(agent);
		int total=agentService.agentcount(agent);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(listagent).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			log.error("获取反馈单列表异常："+e.getMessage());
		}	
		
	}
	
	

	
	@RequestMapping(value="/tzAgent",method = RequestMethod.GET)
	public String tzAgent(HttpServletRequest request,HttpServletResponse response){
		try{
		String workid=request.getParameter("workid");
		UserInfo user =new UserInfo();
		user.setWorkId(workid);
		List<UserInfo> userInfo = userInfoService.getUserInfo(user);
		HttpSession session =request.getSession();
		session.setAttribute(Constans.USER_INFO_WORKID, workid); 
		session.setAttribute(Constans.USER_INFO_USERID, userInfo.get(0).getUserId()); 
		}catch (Exception e) {
			log.error("获取反馈单列表异常："+e.getMessage());
			log.info(e);		
		}
		return "workPaperList_csr";	
	}
	
	@RequiresPermissions("csrpaper:query")
	@RequestMapping(value="/togetAgentzcList",method=RequestMethod.GET)
	public String togetAgentzcList(){		
		return "workPaperList_csr";
	}
	
	
	@RequestMapping(value="/getAgentzcList",method = RequestMethod.POST)
	public void getAgentzcList(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
			String agentworkid=request.getParameter("agentworkid");
			String dealstate=request.getParameter("dealstate");
			String startqcworkpapertime=request.getParameter("startqcworkpapertime");
			String stopqcworkpapertime=request.getParameter("stopqcworkpapertime");	
			String qcid=request.getParameter("qc_id");
			if(qcid==null||"".equals(qcid)){
				agent.setQcid(0);
			}else{
				agent.setQcid(Integer.parseInt(qcid));
			}
			/*if("role_1".equals(role_flag)){
				agent.setAgentworkid(workid);
			}*/
			if(agentworkid!=null&&!"".equals(agentworkid)){		
				agent.setAgentworkid(agentworkid);
			}
			agent.setDealstate(dealstate);
			agent.setStartqcworkpapertime(startqcworkpapertime);
			agent.setStopqcworkpapertime(stopqcworkpapertime);	
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<AgentWorkpaper> listagent=agentService.listzcAgentworkaper(agent);	
			int total=agentService.agentzccount(agent);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(listagent).toString() + "}";
			try {
				System.out.println(json.toString());
				response.getWriter().print(json.toString());	
			} catch (IOException e) {
				log.error("获取反馈单列表异常："+e.getMessage());
				log.info(e);
			}	
		
		
	}

	
	

	@Log(methodname="addAgent",modulename="问题反馈单",funcname="反馈单新增",description="添加反馈单,{0}", code = "ZJ")
	@RequestMapping(value="/addAgent",method = RequestMethod.POST)
	public void addAgent(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String agentuserid=request.getParameter("userid");	
		String qcuserid =request.getAttribute("userid").toString();
		if(qcuserid!=null&&!"".equals(qcuserid)){
			agent.setQc_user_id(Integer.parseInt(qcuserid));
		}
		if(agentuserid!=null&&!"".equals(agentuserid)){	
			agent.setAgentuserid(Integer.parseInt(agentuserid));
		}
		boolean bl=agentService.addagentWorkpaper(agent);
		JSONObject json=new JSONObject();
		json.put("bl", bl);
		json.put("agentid", agent.getAgentworkpaperid());
		try {
			System.out.println(json.toString());
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,编号为:"+agent.getAgentworkid()}));
		} catch (IOException e) {
			log.error("新增反馈单异常："+e.getMessage());
			log.info(e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}	
		
				
	}
	//@RequiresRoles("role_4")
	@RequiresPermissions("paper:del")
	@Log(methodname="delAgent",modulename="问题反馈单",funcname="反馈单删除",description="删除反馈单,{0}", code = "ZJ")
	@RequestMapping(value="/delAgent",method = RequestMethod.POST)
	public void delAgent(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String agentid=request.getParameter("agentid");
		int  aid=0;
		if(agentid!=null&&!"".equals(agentid)){	
			aid=Integer.parseInt(agentid);
		}
		boolean bl=false;
		try {
			bl = agentService.delagentWorkpaper(aid);
		} catch (Exception e1) {
			log.info(e1);
		}
	
		JSONObject json=new JSONObject();
		json.put("blag",bl);
		try {
			System.out.println(json.toString());
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,反馈单编号:"+agentid}));
		} catch (IOException e) {
			log.error("删除反馈单异常："+e.getMessage());
			log.info(e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}	
	}
	@RequiresPermissions("paper:chul")
	@RequestMapping(value="/Agentbyid",method = RequestMethod.POST)
	public String Agentbyid(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String agentid=request.getParameter("agentid");
		int  aid=0;
		if(agentid!=null&&!"".equals(agentid)){	
			aid=Integer.parseInt(agentid);
		}
		agent=agentService.agentByid(aid);
		
		AgentWorkpaper agent2=agentService.qcidbyagent(aid);
		QCDetail qcdetail=  QCDetailService.qcDetailByQcid(agent2.getQcid());
		SysCode code=new SysCode();
		SysCode code3=new  SysCode();
		if("10".equals(qcdetail.getBusinessType())){
			qcdetail.setBusinessType("112");
			code.setPid(124);
			code3.setPid(10);
		}else if("54".equals(qcdetail.getBusinessType())){
			qcdetail.setBusinessType("其他");
			code.setPid(124);
			code3.setPid(54);
		}else if("32".equals(qcdetail.getBusinessType())){
			qcdetail.setBusinessType("c网");
			code.setPid(32);
			code3.setPid(54);
		}else if("76".equals(qcdetail.getBusinessType())){
			qcdetail.setBusinessType("管控");
			code.setPid(172);
			code3.setPid(76);
		}else if("178".equals(qcdetail.getBusinessType())){
			qcdetail.setBusinessType("客调112");
			code.setPid(124);
			code3.setPid(178);
		}else if("200".equals(qcdetail.getBusinessType())){
				qcdetail.setBusinessType("客调c网");
				code.setPid(124);
				code3.setPid(200);
		}else{
			code.setPid(Integer.parseInt(qcdetail.getBusinessType()));
		}
			
		code.setValuees(qcdetail.getCheckcontent());
		code3.setValuees(qcdetail.getDealDis());
		SysCode code2=codeService.selectcodebyId(code);
		code3=codeService.selectcodebyId(code3);
		request.setAttribute("agent", agent);
		request.setAttribute("qcdetail", qcdetail);	
		request.setAttribute("code", code2);	
		request.setAttribute("code2", code3);
		return "workPaperInfo";
	}
	@Log(methodname="updAgent",modulename="问题反馈单",funcname="反馈单详情",description="修改反馈单,{0}", code = "ZJ")
	@RequestMapping(value="/updAgent",method = RequestMethod.POST)
	public void  updAgent(HttpServletRequest request,HttpServletResponse response,AgentWorkpaper agent){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String state=request.getParameter("state");
		String validate=request.getParameter("validate");
		String agentfeedback=request.getParameter("agentfeedback");
		String seatmonitorfeedback=request.getParameter("seatmonitorfeedback");
		String qcfeedback=request.getParameter("qcfeedback");
		String agentworkpaperid=request.getParameter("agentworkpaperid");
		String userid=request.getAttribute("userid").toString();
		if(agentworkpaperid!=null&&!"".equals(agentworkpaperid)){	
			agent.setAgentworkpaperid(Integer.parseInt(agentworkpaperid));
		}
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d= sdf.format(date);
		if("1".equals(state)){
			agent.setAgentfeedback(agentfeedback);
			agent.setAgentvalidate(validate);
			agent.setAgentfeedbacktime(d);
			if("0".equals(validate)){	
				agent.setDealstate("3");
			}
			if("1".equals(validate)){
				agent.setDealstate("2");
			}
			
		}
		if("3".equals(state)){
			agent.setSeatmonitorfeedback(seatmonitorfeedback);
			agent.setAgentvalidate(validate);
			agent.setSeatmonitortime(d);
			if(userid==null||"".equals(userid)){	
			}else{
				agent.setSeatmonitoruserid(Integer.parseInt(userid));
			}
			if("0".equals(validate)){	
				agent.setDealstate("5");
			}
			if("1".equals(validate)){	
				agent.setDealstate("4");
			}			
		}
		if("5".equals(state)){
			agent.setQcfeedback(qcfeedback);
			agent.setAgentvalidate(validate);	
			agent.setDealstate("6");	
			agent.setAgenttime(d);
		}	
	boolean bl=agentService.updateagentWorkpaper(agent);
		JSONObject json=new JSONObject();
		json.put("blag", bl);
		try {
			System.out.println(json.toString());
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,反馈单编号:"+agentworkpaperid}));
		} catch (IOException e) {
			log.error("处理反馈单异常："+e.getMessage());
			log.info(e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}		
	}
	
	
	
	
	
	
}
