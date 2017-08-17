package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.QCHistory;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCHistoryService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.IRecordInfoSevice;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;

@Controller
@RequestMapping("/controller/qchis")
public class QCHistoryController {
	private static Logger log = Logger.getLogger(QCHistoryController.class);
	@Resource
	private IQCHistoryService QCHistoryService;
	@Resource
	private IQCService QCService;
	@Resource
	private IRecordInfoSevice recordInfoService;
	@Resource 
	private IQCDetailService QCDetailService;	
	@Resource
	private ISecondaryQcService secondaryQcService;
	
	/**
	 * 查询录音质检评分记录
	 * @return
	 */
	@RequiresPermissions("hisrecord:query")
	@RequestMapping(value="/togetQcHistory",method=RequestMethod.GET)
	public String togetQcHistory(){		
		return "history_qcInfoList";
	}
	
	@RequestMapping(value="/getQcHistory",method = RequestMethod.POST)
	public void getQcHistory(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.selectQcHisByParam(qcHistory);
		int total=QCHistoryService.selectQcHisCountByParam(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("查询历史质检记录："+e.getMessage());
		}
	}
	//@RequiresRoles("role_4")
	@RequiresPermissions("hisrecord:del")
	@Log(methodname="deleteQcHistory",modulename="历史质检记录查询",funcname="删除历史质检记录",description="删除历史质检记录,{0}", code = "ZJ")
	@RequestMapping(value="/deleteQcHistory",method = RequestMethod.POST)
	public void deleteQcHistory(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String recordId=request.getParameter("recordId");
		Integer recordid=0;
		String reservedThree=null;
		String json="{\"status\":\"0\"}";
		if(StringUtils.isNotEmpty(recordId)){
			recordid=Integer.parseInt(recordId);
		}
		QCService.deleteByRecordid(recordid);
		RecordInfo re=recordInfoService.selectByPrimaryKey(recordid);
		if(re!=null ){
			reservedThree=re.getReservedThree();
			if(StringUtil.isNotEmpty(reservedThree)){
				SecondaryQc seQc=new SecondaryQc();
				seQc.setsRecordThree(reservedThree);
				seQc.setUpdateUserId(Integer.parseInt(request.getAttribute(Constans.USER_INFO_USERID).toString()));
				secondaryQcService.updateByPrimaryKey(seQc);
				json = "{\"status\":\"1\"}";
			}
		}
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,录音流水号:"+reservedThree}));
		} catch (IOException e) {
			log.error("删除历史质检记录："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	/**
	 * 跳转无工单无录音
	 * @return
	 */
	@RequiresPermissions("none:query")
	@RequestMapping(value="/togetQcNone",method=RequestMethod.GET)
	public String togetQcNone(){		
		return "noneqcInfoList";
	}
	
	
	/**
	 * @author lk 2017/04/07
	 * @param request
	 * @param response
	 * 查询无工单无录音评分
	 * 
	 */
	@RequestMapping(value="/getQcNone",method = RequestMethod.POST)
	public void getQcNone(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		qcHistory.setQctype("6");//查询无录音无工单质检记录
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.noneQclist(qcHistory);
		int total=QCHistoryService.noneQclistsum(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("查询无录音无工单质检记录："+e.getMessage());
		}
		
		
	}
	
	
	/**
	 * 查询112工单质检评分记录
	 * @return
	 */
	@RequiresPermissions("his112order:query")
	@RequestMapping(value="/toget112orderqc",method=RequestMethod.GET)
	public String toget112orderqc(){		
		return "112/history_112orderInfoList";
	}
	
	@RequestMapping(value="/get112orderqc",method = RequestMethod.POST)
	public void get112orderqc(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.getQcHisByorder(qcHistory);
		int total=QCHistoryService.getQcHisByordersum(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("查询历史质检记录："+e.getMessage());
		}
	}
	
	/**
	 * 跳转c网质检记录界面
	 * @return
	 */
	
	@RequiresPermissions("hiscworder:query")
	@RequestMapping(value="/togetcworderqc",method=RequestMethod.GET)
	public String togetcworderqc(){		
		return "history_cwqcInfoList";
	}
	
	
	@RequestMapping(value="/getcworderqc",method = RequestMethod.POST)
	public void getcworderqc(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.getcwQcHisByorder(qcHistory);
		int total=QCHistoryService.getcwQcHisByordersum(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());		
		} catch (IOException e) {
			log.error("查询历史质检记录："+e.getMessage());
		}
	}
	
	/**
	 * 跳转112小结质检记录
	 * @return
	 */
	@RequiresPermissions("his112xj:query")
	@RequestMapping(value="/toget112xjQcHistory",method=RequestMethod.GET)
	public String toget112xjQcHistory(){		
		return "dhxj/history_112xjqcInfoList";
	}
	
	@RequestMapping(value="/get112xjQcHistory",method = RequestMethod.POST)
	public void get112xjQcHistory(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.get112xjQcHisByorder(qcHistory);
		int total=QCHistoryService.get112xjQcHisByordersum(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("查询历史质检记录："+e.getMessage());
		}
	}
	
	/**
	 * 跳转c网小结质检记录
	 * @return
	 */
	@RequiresPermissions("hiscwxj:query")
	@RequestMapping(value="/togetcwxjQcHistory",method=RequestMethod.GET)
	public String togetcwxjQcHistory(){		
		return "dhxj/history_cwxjqcInfoList";
	}
	
	@RequestMapping(value="/getcwxjQcHistory",method = RequestMethod.POST)
	public void getcwxjQcHistory(HttpServletRequest request,HttpServletResponse response,QCHistory qcHistory){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<QCHistory> qcList=QCHistoryService.getcwxjQcHisByorder(qcHistory);
		int total=QCHistoryService.getcwxjQcHisByordersum(qcHistory);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(qcList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("查询历史质检记录："+e.getMessage());
		}
	}
	
	@RequiresPermissions(value = {"none:del","his112order:del","hiscworder:del","his112xj:del","hiscwxj:del"}, logical = Logical.OR)
	@Log(methodname="deleteAllQcHistory",modulename="质检记录",funcname="删除历史质检记录",description="删除历史质检记录,{0}", code = "ZJ")
	@RequestMapping(value="/deleteAllQcHistory",method = RequestMethod.POST)
	public void deleteAllQcHistory(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String qcid=request.getParameter("qcid");
		Integer QcId=0;
		String json="{\"status\":\"0\"}";
		if(StringUtils.isNotEmpty(qcid)){
			QcId=Integer.parseInt(qcid);
		}
		int row= QCService.deleteByqcid(QcId);
		if(row>0){
			QCDetailService.delqcdetailbyQcid(QcId);
			json = "{\"status\":\"1\"}";
		}
		/*RecordInfo re=recordInfoService.selectByPrimaryKey(recordid);
		if(re!=null ){
			String reservedThree=re.getReservedThree();
			if(StringUtil.isNotEmpty(reservedThree)){
				SecondaryQc seQc=new SecondaryQc();
				seQc.setsRecordThree(reservedThree);
				seQc.setUpdateUserId(Integer.parseInt(request.getSession().getAttribute(Constans.USER_INFO_USERID).toString()));
				secondaryQcService.updateByPrimaryKey(seQc);
			
			}
		}*/
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,质检id:"+QcId}));
		} catch (IOException e) {
			log.error("删除历史质检记录："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	
	
	
}
