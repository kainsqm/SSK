package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/secqc")
public class SecondaryQCController {
	private static Logger log = Logger.getLogger(SecondaryQCController.class);
	@Resource
	private ISecondaryQcService secondaryQcService;
	
	@Resource
	private IQCService QCService;
	
	@Resource
	private IUserInfoService userInfoService;
	
	@RequiresPermissions("senc:query")
	@RequestMapping(value="/togetSecondaryQc",method=RequestMethod.GET)
	public String togetSecondaryQc(){		
		return "secondaryCheckList";
	}
	
	/**
	 * 二级质检列表查询
	 * @param request
	 * @param response
	 * @param secondaryQc
	 */
	@RequestMapping(value="/getSecondaryQc",method = RequestMethod.POST)
	public void getSecondaryQc(HttpServletRequest request,HttpServletResponse response,SecondaryQc secondaryQc){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<SecondaryQc> qcList=secondaryQcService.selectByParam(secondaryQc);
		int total=secondaryQcService.selectCountByParam(secondaryQc);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";
		
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("二级质检列表查询："+e.getMessage());
		}
	}
	
	//@RequiresRoles("role_4")
	@RequiresPermissions("senc:del")
	@Log(methodname="deleteSecQc",modulename="二级质检查询",funcname="删除二级质检记录",description="删除二级质检记录,{0}", code = "ZJ")
	@RequestMapping(value="/deleteSecQc",method = RequestMethod.POST)
	public void deleteSecQc(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String sid=request.getParameter("sid");
		Integer sId=0;
		if(StringUtils.isNotEmpty(sid)){
			sId=Integer.parseInt(sid);
		}
		SecondaryQc sqc=new SecondaryQc();
		sqc.setsId(sId);
		sqc.setUpdateUserId(Integer.parseInt(request.getAttribute(Constans.USER_INFO_USERID).toString()));
		secondaryQcService.updateByPrimaryKey(sqc);
		String json = "{\"status\":\"1\"}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,二级质检id:"+sId}));
		} catch (IOException e) {
			log.error("删除二级质检记录："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	@Log(methodname="addSecQc",modulename="二级质检查询",funcname="新增二级质检",description="新增二级质检,{0}", code = "ZJ")
	@RequestMapping(value="/addSecQc",method = RequestMethod.POST)
	public void addSecQc(HttpServletRequest request,HttpServletResponse response,SecondaryQc secondaryQc){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		//检查人userid
		secondaryQc.setCreateUserId(Integer.parseInt(request.getAttribute(Constans.USER_INFO_USERID).toString()));
		//检查人工号
		secondaryQc.setsWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
	
		
		//获取质检员信息：质检员工号，姓名，userid
		QC qc=QCService.selectByQcid(secondaryQc.getQc_id());
		secondaryQc.setsQcUserid(qc.getQcUserId());
		secondaryQc.setsQcWorkid(qc.getQcWorkid());
		//根据工号取姓名
		UserInfo userinfo =userInfoService.userinfoByworkid(qc.getQcWorkid());
		String qcName ="";
		if(userinfo!=null){
			 qcName =userinfo.getUserName();
		}
		secondaryQc.setsQcName(qcName);
		secondaryQcService.insert(secondaryQc);
		String json = "{\"status\":\"1\"}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,质检id:"+qc.getQcId()}));
		} catch (IOException e) {
			log.error("新增二级质检："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	
	
}
