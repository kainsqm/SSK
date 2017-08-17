package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.sh.ideal.model.CoachInfo;
import cn.sh.ideal.model.CoachMain;
import cn.sh.ideal.model.CoachServer;
import cn.sh.ideal.service.ICoachService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.SearchBean;
import cn.sh.ideal.util.SearchResultBean;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/coach")
public class CoachController {
	private static Logger log = Logger.getLogger(FunctionController.class);
	@Resource
	private ICoachService coachService;
	
	@RequiresPermissions("coach:query")
	@RequestMapping(value="/togetCoachMonth",method=RequestMethod.GET)
	public String togetCoachMonth(HttpServletRequest request,
			HttpServletResponse response){	
		String role=request.getAttribute("role_flag").toString();
		String userworkid=request.getAttribute("userworkid").toString();
		request.setAttribute("roleflag", role);
		request.setAttribute("userworkid", userworkid);
		return "coach_program/coachInfoList_qc";
	}
	
	
	@RequestMapping(value="/tocoachInfoListDudao",method=RequestMethod.GET)
	public String tocoachInfoListDudao(){		
		return "coach_program/coachInfoList_dudao";
	}
	
	@RequestMapping(value="/tocoachInfoList",method=RequestMethod.GET)
	public String tocoachInfoList(HttpServletRequest request,
			HttpServletResponse response){	
		String role=request.getAttribute("role_flag").toString();
		String userworkid=request.getAttribute("userworkid").toString();
		request.setAttribute("roleflag", role);
		request.setAttribute("userworkid", userworkid);
		return "coach_program/coachInfoList";
	}
	
	
	/**
	 * 获取辅导月计划列表
	 * @param request
	 * @param response
	 * @param cm
	 */
	
	@RequestMapping(value = "/getCoachMonth", method = RequestMethod.POST)
	public void getCoachMonth(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			String role_flag=String.valueOf(request
					.getAttribute(Constans.ROLE_FLAG));
			String userid=String.valueOf(request
					.getAttribute(Constans.USER_INFO_USERID));
			PageHelper.startPage(currentPage, pageSize);
			if("role_1".equals(role_flag)){
				cm.setUserid(Integer.parseInt(userid));			
			}else if("role_3".equals(role_flag)){		
				cm.setCreateid(Integer.parseInt(userid));
			}
			cm.setSumImprove(coachService.getSummarizeState(cm.getPass()));
			if(cm.getPass()!=null&&cm.getPass()!=""){
			cm.setPass(coachService.getPassState(cm.getPass()));
			}
			
			List<CoachMain> cmList = coachService.queryCoachMain(cm);
			int counts = coachService.queryCoachMainCount(cm);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(cmList).toString() + "}";

			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			// return object.toString();
		} catch (Exception e) {
			log.error("获取辅导月计划列表异常：" + e.getMessage());		
		}

	}

	/**
	 * 个人辅导计划查询的员工信息(单选)
	 */
	@RequestMapping(value = "/getSinglePage", method = RequestMethod.POST)
	public String getSinglePage(HttpServletRequest request,
			HttpServletResponse response)  {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String workId = request.getParameter("workId");// 工号
		String userName = request.getParameter("userName");// 姓名
		String currentPage = request.getParameter("currentPage");
		SearchBean search = new SearchBean();
		if (currentPage != null) {
			search.setCurrentPage(Integer.parseInt(currentPage));
		}
		Map<String, String> map = new HashMap<String, String>(); // 
		map.put("workId", workId);
		map.put("userName", userName);
		map.put("start", String.valueOf(search.getStart()));
		map.put("end", String.valueOf(search.getEnd()));

		SearchResultBean result = coachService.queryUserList(map);
		try {
			request.setAttribute("papers", URLDecoder.decode(request
					.getParameter("papers_hid"), "utf-8"));// lfj 2012-10-26 add
		} catch (UnsupportedEncodingException e) {
		log.info(e);
		}
		request.setAttribute("allUserList", result.getData());
		request.setAttribute("pageBean", result.getSearcherBean());
		return "coach_program/operSinglePage";

	}

	@Log(methodname = "addCoachMonth", modulename = "辅导计划", funcname = "新增辅导月计划", description = "新增辅导月计划,{0}", code = "ZJ")
	@RequestMapping(value = "/addCoachMonth", method = RequestMethod.POST)
	public void addCoachMonth(HttpServletRequest request,
			HttpServletResponse response, CoachMain coac) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String username=request.getParameter("username");
		String userid=request.getParameter("userid");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String coachproject=request.getParameter("coachproject");
		 coachproject=coachproject.substring(0, coachproject.lastIndexOf(",")); 
		String improve=request.getParameter("improve");
		String specificationlanguage=request.getParameter("specificationlanguage");
		String politetoneoofvoice=request.getParameter("politetoneoofvoice");
		String abilitytocommunicate=request.getParameter("abilitytocommunicate");
		String objectionhandling=request.getParameter("objectionhandling");
		String flowstandard=request.getParameter("flowstandard");
		String deadlyerror=request.getParameter("deadlyerror");
		String marketingskills=request.getParameter("marketingskills");
		String createuserid=String.valueOf(request
				.getAttribute(Constans.USER_INFO_USERID));
		coac.setUsername(username);
		coac.setCreateid(Integer.parseInt(createuserid));
		coac.setUserid(Integer.parseInt(userid));
		coac.setStarttime(starttime);
		coac.setEndtime(endtime);
		coac.setRemaker(improve);
		coac.setCoachproject(coachproject);
		boolean blag = coachService.insert(coac);
		CoachServer cocachserver = new CoachServer();
		cocachserver.setAbilitytocommunicate(abilitytocommunicate);
		cocachserver.setCoachmainid(coac.getCoachmainId());
		cocachserver.setDeadlyerror(deadlyerror);
		cocachserver.setFlowstandard(flowstandard);
		cocachserver.setMarketingskills(marketingskills);
		cocachserver.setObjectionhandling(objectionhandling);
		cocachserver.setPolitetoneoofvoice(politetoneoofvoice);
		cocachserver.setSpecificationlanguage(specificationlanguage);

		boolean flag = coachService.insertcoachserver(cocachserver);
		JSONObject json = new JSONObject();
		json.put("flag", flag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,月计划id:"+coac.getCoachmainId() }));
		} catch (IOException e) {
			log.error("新增月计划异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}

	}

	@RequiresPermissions(value={"coach:upd","coach:addmon"},logical=Logical.OR)
	@RequestMapping(value = "/getcocahbyid", method = RequestMethod.GET)
	public String getcocahbyid(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String coachmainId = request.getParameter("coachmainId");
		String pd = request.getParameter("pd");
		CoachMain coac = coachService.selectByPrimaryKey(Integer
				.parseInt(coachmainId));
		CoachServer coacs = coachService.selectCoachServerByid(Integer
				.parseInt(coachmainId));
		List<CoachMain> coachteam = coachService.getcoachteam();
		request.setAttribute("coachteam", coachteam);
		request.setAttribute("coacs", coacs);
		request.setAttribute("coac", coac);
		request.setAttribute("userid", coac.getUserid());
		request.setAttribute("coachproject", coac.getCoachproject());
		request.setAttribute("pd", pd);
		 if("addinfo".equals(pd)){
			 boolean blag=true;
			List<CoachInfo> cmList = coachService.selectByKeyCoachInfo(Integer.parseInt(coachmainId));
			//判断该月计划下的周计划是否有待受理员班长处理的  如果有 则不显示辅导总结按钮
			for (CoachInfo coachInfo : cmList) {
				if("1".equals(coachInfo.getState())){
					blag=false;
					break;
				}
			}		
			if(cmList.size()==0){  //表明该月计划没有周计划 则不显示辅导总结按钮
				blag=false;
			}
			request.setAttribute("blag", blag);
			 return "coach_program/coachInfo_items_qc";
		 }else{
		return "coach_program/editcoachmain";
		 }
	}

	/**
	 * 督导查询自己审核的月计划
	 * **/
	@RequiresPermissions("dbcoach:query")
	@RequestMapping(value = "/getDDCheckCoachMonth", method = RequestMethod.POST)
	public void getDDCheckCoachMonth(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String tz=request.getParameter("tz");	
		List<CoachMain> coachteam=coachService.getcoachteam();
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			if(cm.getPass()!=null&&cm.getPass()!=""){
				System.out.println("pass:"+ Integer.parseInt(cm.getPass()));
				switch (Integer.parseInt(cm.getPass())) {
				case 0:
					cm.setPass("0");
					break;
				case 1:
					cm.setPass("1");
					cm.setSumImprove(null);
					break;
				case 2:
					cm.setPass("2");
					break;
				case 3:
					cm.setSumImprove("0");
					cm.setPass(null);
					break;
				case 4:
					cm.setSumImprove("1");
					cm.setPass(null);
					break;
				case 5:
					cm.setSumImprove("2");
					cm.setPass(null);
					break;
				default:					
				}
				
			}
			
			
	/*	cm.setPass(coachService.getPassState(cm.getPass()));
			cm
					.setSumImprove(coachService.getSummarizeState(cm
							.getSumImprove()));*/
			List<CoachMain> cmList = coachService.queryCoachMain(cm);
			int counts = coachService.queryCoachMainCount(cm);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(cmList).toString() + "}";

			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功" }));
			// return object.toString();
		} catch (Exception e) {
			log.error("获取督导查询自己审核的月计划列表异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}

	}

	/**
	 * 督导查询月计划详情信息
	 * **/
	@RequiresPermissions("dbcoach:getinfo")
	@RequestMapping(value = "/getCoachInfoDuDao", method = RequestMethod.GET)
	public String getCoachInfoDuDao(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String coachmainId = request.getParameter("coachmainId");
			CoachMain coac = coachService.selectByPrimaryKey(Integer
					.parseInt(coachmainId));
			CoachServer coacs = coachService.selectCoachServerByid(Integer
					.parseInt(coachmainId));
			List<CoachMain> coachteam = coachService.getcoachteam();
			request.setAttribute("coachteam", coachteam);
			request.setAttribute("coacs", coacs);
			request.setAttribute("coac", coac);
			request.setAttribute("coachmainId", coachmainId);
			request.setAttribute("coachproject", coac.getCoachproject());
			return "coach_program/coachInfo_items_dudao";
		} catch (Exception e) {
			log.error("督导查询月计划详情信息异常：" + e.getMessage());
		}
		return "";
	}

	/**
	 * 显示督导审核月计划页面
	 * **/
	@RequiresPermissions("dbcoach:check")
	@RequestMapping(value = "/getCoachInfoCheck", method = RequestMethod.GET)
	public String getCoachInfoCheck(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String coachmainId = request.getParameter("coachmainId");
			CoachMain coac = coachService.selectByPrimaryKey(Integer
					.parseInt(coachmainId));
			CoachServer coacs = coachService.selectCoachServerByid(Integer
					.parseInt(coachmainId));
			List<CoachMain> coachteam = coachService.getcoachteam();
			request.setAttribute("coachteam", coachteam);
			request.setAttribute("coacs", coacs);
			request.setAttribute("coac", coac);
			request.setAttribute("coachmainId", coachmainId);
			request.setAttribute("coachproject", coac.getCoachproject());
			return "coach_program/check_coachmain";
		} catch (Exception e) {
			log.error("显示督导审核月计划页面异常：" + e.getMessage());
		}
		return "";
	}
	@RequiresPermissions(value={"coach:add","coach:zj","csrcoach:query"},logical=Logical.OR)
	@RequestMapping(value = "/getcoachtime", method = RequestMethod.GET)
	public String getcoachtime(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
		String  tz=request.getParameter("tz");
		List<CoachMain> coachteam = coachService.getcoachteam();
		request.setAttribute("coachteam", coachteam);
		if("zjx".equals(tz)){
			String  id=request.getParameter("id");
			String  coachmainId=request.getParameter("coachmainId");
			if(!"0".equals(id)){
			CoachInfo coachinfo=coachService.selecCoachInfotByid(Integer.parseInt(id));
			request.setAttribute("coachinfo", coachinfo);	
			request.setAttribute("msg", "info");
			}
			CoachMain coach=coachService.selectByPrimaryKey(Integer.parseInt(coachmainId));
			request.setAttribute("coach", coach);
			request.setAttribute("coachmainId", coachmainId);
			request.setAttribute("id", id);
			return "coach_program/coachInfo_items_one_info";
		}else if("fdzj".equals(tz)){
			String  coachmainId=request.getParameter("coachmainId");
			request.setAttribute("coachmainId", coachmainId);
			CoachMain coac = coachService.selectByPrimaryKey(Integer.parseInt(coachmainId));
			request.setAttribute("coac", coac);
			request.setAttribute("coachproject", coac.getCoachproject());
			if(null!=coac.getSumImprove()){
				if(coac.getSumImprove().equals("2") || coac.getSumImprove().equals("1")){
					return "coach_program/coachInfo_zongjie_qcinfo";
				}
			}
			return "coach_program/coachInfo_zongjie_qc";
		}else{		
			return "coach_program/edit_coachInfo_main";
		}
		} catch (Exception e) {
			log.error("获取辅导项目异常：" + e.getMessage(),e);
		}
		return "";
	}
	
	
	
	@RequestMapping(value = "/getcoachtimeByDudao", method = RequestMethod.GET)
	public String getcoachtimeByDudao(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
		List<CoachMain> coachteam = coachService.getcoachteam();
		request.setAttribute("coachteam", coachteam);
			String  id=request.getParameter("id");
			String  coachmainId=request.getParameter("coachmainId");
			if(!"0".equals(id)){
			CoachInfo coachinfo=coachService.selecCoachInfotByid(Integer.parseInt(id));
			request.setAttribute("coachinfo", coachinfo);	
			request.setAttribute("msg", "info");
			}
			CoachMain coach=coachService.selectByPrimaryKey(Integer.parseInt(coachmainId));
			request.setAttribute("coach", coach);
			request.setAttribute("coachmainId", coachmainId);
			request.setAttribute("id", id);
			return "coach_program/coachInfo_items_one_info";
		
		} catch (Exception e) {
			log.error("获取辅导项目异常：" + e.getMessage());
		}
		return "";
	}
	
	
	@RequiresPermissions(value={"coach:upd","csrcoach:sub"},logical=Logical.OR)
	@Log(methodname="updCoachmain",modulename="辅导计划",funcname="修改月辅导计划",description="修改月辅导计划,{0}", code = "ZJ")
	@RequestMapping(value="/updCoachmain",method = RequestMethod.POST)
	public void updCoachmain(HttpServletRequest request,HttpServletResponse response,CoachMain coac){
		String coachmainId=request.getParameter("coachmainId");
		String username=request.getParameter("username");
		String userid=request.getParameter("userid");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String coachproject=request.getParameter("coachproject");
		coachproject=coachproject.substring(0, coachproject.lastIndexOf(",")); 
		String improve=request.getParameter("improve");
		String specificationlanguage=request.getParameter("specificationlanguage");
		String politetoneoofvoice=request.getParameter("politetoneoofvoice");
		String abilitytocommunicate=request.getParameter("abilitytocommunicate");
		String objectionhandling=request.getParameter("objectionhandling");
		String flowstandard=request.getParameter("flowstandard");
		String deadlyerror=request.getParameter("deadlyerror");
		String marketingskills=request.getParameter("marketingskills");
		String upduserid=String.valueOf(request
				.getAttribute(Constans.USER_INFO_USERID));
		coac.setUsername(username);
		coac.setModifid(Integer.parseInt(upduserid));
		if(userid!=""&&userid!=null){
		coac.setUserid(Integer.parseInt(userid));
		}
		coac.setStarttime(starttime);
		coac.setEndtime(endtime);
		coac.setPass("2");
		coac.setRemaker(improve);
		coac.setCoachproject(coachproject);
		CoachServer cocachserver=new CoachServer();
		cocachserver.setAbilitytocommunicate(abilitytocommunicate);
		cocachserver.setCoachmainid(Integer.parseInt(coachmainId));
		cocachserver.setDeadlyerror(deadlyerror);
		cocachserver.setFlowstandard(flowstandard);
		cocachserver.setMarketingskills(marketingskills);
		cocachserver.setObjectionhandling(objectionhandling);
		cocachserver.setPolitetoneoofvoice(politetoneoofvoice);
		cocachserver.setSpecificationlanguage(specificationlanguage);	
		boolean flag=false;
		try {
			flag = coachService.updateCoachByid(coac, cocachserver);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.info(e1);
		}
		JSONObject json=new JSONObject();
		json.put("flag", flag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,辅导计划id:"+coachmainId}));
		} catch (IOException e) {
			log.error("修改月辅导计划异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	@RequiresPermissions("coach:add")
	@Log(methodname="addCoachInfo",modulename="辅导计划",funcname="新增辅导周计划",description="新增辅导周计划,{0}", code = "ZJ")
	@RequestMapping(value="/addCoachInfo",method = RequestMethod.POST)
	public void addCoachInfo(HttpServletRequest request,HttpServletResponse response,CoachInfo coac){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String coachmainId=request.getParameter("coachmainId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String coachproject=request.getParameter("coachproject");
		 coachproject=coachproject.substring(0, coachproject.lastIndexOf(",")); 
		String coachmethod=request.getParameter("coachmethod");
		String instructions=request.getParameter("instructions");
		String coachrecord=request.getParameter("coachrecord");
		String comforablestate=request.getParameter("comforablestate");
		String uncomforable=request.getParameter("uncomforable");
		String describe=request.getParameter("describe");
		String createuserid=String.valueOf(request
				.getAttribute(Constans.USER_INFO_USERID));
		coac.setCoachmainId(Integer.parseInt(coachmainId));
		coac.setStarttime(starttime);
		coac.setEndtime(endtime);
		coac.setCoachproject(coachproject);
		coac.setCoachmethod(coachmethod);
		coac.setInstructions(instructions);
		coac.setCoachrecord(coachrecord);
		coac.setComforablestate(comforablestate);
		coac.setUncomforable(uncomforable);
		coac.setDescribe(describe);
		coac.setCreateid(createuserid);
		coac.setValid("0");
		coac.setState("1");
		boolean flag=coachService.insertCoachInfo(coac);
		JSONObject json=new JSONObject();
		json.put("flag", flag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,周计划id:"+coac.getId()}));
		} catch (IOException e) {
			log.error("新增周计划异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	@RequiresPermissions(value={"coach:update","csrcoach:sub"},logical=Logical.OR)
	@Log(methodname="updCoachInfo",modulename="辅导计划",funcname="修改辅导周计划",description="修改辅导周计划,{0}", code = "ZJ")
	@RequestMapping(value="/updCoachInfo",method = RequestMethod.POST)
	public void updCoachInfo(HttpServletRequest request,HttpServletResponse response,CoachInfo coac){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String comforablestate = request.getParameter("comforablestate");
		String uncomforable =request.getParameter("uncomforable");
		String describe =request.getParameter("describe");
		String id =request.getParameter("id");
		String roleflag=String.valueOf(request
				.getAttribute(Constans.ROLE_FLAG));
		String userid=String.valueOf(request
				.getAttribute(Constans.USER_INFO_USERID));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		if("role_1".equals(roleflag)){
			coac.setState("2");
			coac.setModifid(userid);
			coac.setModiftime(sdf.format(date));
		}else if("role_2".equals(roleflag)){
			coac.setState("3");	
			coac.setPassuserid(userid);
			coac.setPasstime(sdf.format(date));
		}
		coac.setComforablestate(comforablestate);
		coac.setUncomforable(uncomforable);
		coac.setDescribe(describe);
		coac.setId(Integer.parseInt(id));
		boolean flag=coachService.updcoachInfo(coac);
		JSONObject json=new JSONObject();
		json.put("flag", flag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,周计划id:"+id}));
		} catch (IOException e) {
			log.error("修改辅导周计划："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	
	
	/**
	 * 督导查询审核的月计划中的周计划列表
	 * **/
	@RequestMapping(value = "/getDDCheckCoachInfo", method = RequestMethod.POST)
	public void getDDCheckCoachInfo(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			int coachmainId = Integer.parseInt(request
					.getParameter("coachmainId"));
			List<CoachInfo> cmList = coachService
					.selectByKeyCoachInfo(coachmainId);
			for (int i = 0; i < cmList.size(); i++) {
				Map<String,String> map=new HashMap<String, String>();
				String id=cmList.get(i).getCoachproject();
				map.put("id", id);
				String name=coachService.getCoachProjectById(map);
				cmList.get(i).setCoachproject(name);
			}
			int counts = coachService.selectByKeyCoachInfoCount(coachmainId);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(cmList).toString() + "}";

			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			// return object.toString();
		} catch (Exception e) {
			log.error("获取督导查询审核的月计划中的周计划列表异常：" + e.getMessage());
		}

	}

	/**
	 * @author niewq 删除辅导计划
	 * **/
	@RequiresPermissions("dbcoach:del")
	@Log(methodname = "deleteCoachMain", modulename = "辅导计划", funcname = "删除辅导计划", description = "删除辅导计划,{0}", code = "ZJ")
	@RequestMapping(value = "/deleteCoachMain", method = RequestMethod.POST)
	public void deleteCoachMain(HttpServletRequest request,
			HttpServletResponse response)  {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject object=new JSONObject();
		try {
			int coachmainId = Integer.parseInt(request
					.getParameter("coachmainId"));
			coachService.deleteCoachMain(coachmainId);
		//	response.getWriter().write(
				//	"<script language='javascript'>alert('操作成功!'); "
					//		+ "window.location.href='"
					//		+ request.getContextPath()
					//		+ "/page/coach_program/coachInfoList_dudao.jsp'"
					//		+ "</script>");
			object.put("status", "1");
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,月计划id:"+coachmainId}));
		} catch (Exception e) {
			log.error("删除辅导计划异常：" + e.getMessage());
			//response.getWriter().write("<script>alert('操作失败!');</script>");
			object.put("status", "0");
			try {
				response.getWriter().write(object.toString());
			} catch (IOException e1) {
				log.info(e1);
			}
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}

	}

	/**
	 * 督导审核月计划处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Log(methodname = "DuDaoCheckCoachMain", modulename = "辅导计划", funcname = "督导审核月计划处理", description = "督导审核月计划处理,{0}", code = "ZJ")
	@RequestMapping(value = "/DuDaoCheckCoachMain", method = RequestMethod.POST)
	public void DuDaoCheckCoachMain(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		try {
			String coachmainId = request.getParameter("coachmainId");
			CoachMain cm = new CoachMain();
			cm.setCoachmainId(Integer.parseInt(coachmainId));
			cm.setPass(request.getParameter("pass"));
			cm.setPassinfo(request.getParameter("passinfo"));	
			String userid = String.valueOf(request
					.getAttribute(Constans.USER_INFO_USERID));
			cm.setPassuserid(userid);
			int result = coachService.updateDuDaoCheckCoachMain(cm);
			JSONObject json = new JSONObject();
			json.put("data", result);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,月计划id:"+coachmainId}));
		} catch (Exception e) {
			log.error("督导审核月计划处理异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}
	}

	/**
	 * 督导审核辅导总结页面详情
	 * **/
	@RequiresPermissions("dbcoach:zjcheck")
	@RequestMapping(value = "/getCoachMainCheck", method = RequestMethod.GET)
	public String getCoachMainCheck(HttpServletRequest request,
			HttpServletResponse response, CoachMain cm) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String coachmainId = request.getParameter("coachmainId");
			CoachMain coac = coachService.selectByPrimaryKey(Integer
					.parseInt(coachmainId));
			CoachServer coacs = coachService.selectCoachServerByid(Integer
					.parseInt(coachmainId));
			List<CoachMain> coachteam = coachService.getcoachteam();
			request.setAttribute("coachteam", coachteam);
			request.setAttribute("coacs", coacs);
			request.setAttribute("coac", coac);
			request.setAttribute("coachmainId", coachmainId);
			request.setAttribute("coachproject", coac.getCoachproject());
			return "coach_program/coachInfo_zongjie_check";
		} catch (Exception e) {
			log.error("获取督导审核辅导总结页面详情异常：" + e.getMessage());
		}
		return "";
	}

	/**
	 * 督导审核辅导总结操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("dbcoach:zjcheck")
	@Log(methodname = "checkCoachSummary", modulename = "辅导计划", funcname = "督导审核辅导总结操作", description = "督导审核辅导总结操作,{0}", code = "ZJ")
	@RequestMapping(value = "/checkCoachSummary", method = RequestMethod.POST)
	public void checkCoachSummary(HttpServletRequest request,
			HttpServletResponse response)  {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		try {
			String coachmainId = request.getParameter("coachmainId");
			CoachMain cm = new CoachMain();
			cm.setCoachmainId(Integer.parseInt(coachmainId));
			cm.setSumImprove(request.getParameter("sumImprove"));
			cm.setSumSuggest(request.getParameter("sumSuggest"));
			String userid = String.valueOf(request
					.getAttribute(Constans.USER_INFO_USERID));
			cm.setSumCreateUserId(Integer.parseInt(userid));
			int result = coachService.updateCoachSummary(cm);
			JSONObject json = new JSONObject();
			json.put("data", result);
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("督导审核辅导总结操作：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}
	}
	//@RequiresPermissions("coach:query")
	@Log(methodname = "deleteCoachInfo", modulename = "辅导计划", funcname = "删除辅导周计划", description = "删除辅导周计划,{0}", code = "ZJ")
	@RequestMapping(value = "/deleteCoachInfo", method = RequestMethod.POST)
	public void deleteCoachInfo(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String id = request.getParameter("id");
		boolean blag=coachService.deleteCoachInfo(Integer.parseInt(id));
		JSONObject json=new JSONObject();
		json.put("blag", blag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,周计划id:"+id}));
		} catch (IOException e) {
			log.error("删除周计划异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
			
	}
	@RequiresPermissions("coach:zjsub")
	@Log(methodname = "addCoachzj", modulename = "辅导计划", funcname = "新增辅导周总结", description = "辅导总结添加,{0}", code = "ZJ")
	@RequestMapping(value = "/addCoachzj", method = RequestMethod.POST)
	public void addCoachzj(HttpServletRequest request,HttpServletResponse response,CoachMain coach){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String sumCoachproject=request.getParameter("sumCoachproject");
		sumCoachproject=sumCoachproject.substring(0, sumCoachproject.lastIndexOf(",")); 
		String coachmainId=request.getParameter("coachmainId");
		String sumSummarize=request.getParameter("sumSummarize");
		String isgj=request.getParameter("isgj");
		String qcremark=request.getParameter("qcremark");
		coach.setSumCoachproject(sumCoachproject);
		coach.setCoachmainId(Integer.parseInt(coachmainId));
		coach.setSumSummarize(sumSummarize);
		coach.setIsgj(isgj);
		coach.setQcremark(qcremark);
		coach.setSumImprove("2");
		boolean blag=coachService.updatezjBycoachmainid(coach);
		JSONObject json=new JSONObject();
		json.put("blag", blag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功:对应月计划id:"+coachmainId}));
		} catch (IOException e) {
			log.error("新增辅导总结异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
		
	}
	
	@RequiresPermissions("csrcoach:query")
	@RequestMapping(value = "/getCoachInfo", method = RequestMethod.POST)
	public void getCoachInfo(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String acceptorWorkId=request.getParameter("acceptorWorkId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		CoachInfo coac=new CoachInfo();
		coac.setAcceptorWorkId(acceptorWorkId);
		coac.setStarttime(starttime);
		coac.setEndtime(endtime);
		List<CoachInfo> listcoac=coachService.selectByinfo(coac);
		int total=coachService.selectByinfoCount(coac);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(listcoac).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());	
		} catch (IOException e) {
			log.error("获取辅导周计划列表异常："+e.getMessage());	
		}
		
	}

}
