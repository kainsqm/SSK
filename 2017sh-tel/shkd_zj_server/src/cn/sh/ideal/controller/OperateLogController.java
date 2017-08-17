package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/operlog")
public class OperateLogController {
	private static Logger log = Logger.getLogger(RecordInfoController.class);
	@Resource 
	 private  IOperateLogService operatelogService;
	@Resource
	private ISysCodeService sysCodeService;
	
	/******
	 * 进入日志查询页面
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequestMapping(value="/toOperlist",method = RequestMethod.GET)
	public String toOperlist(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		List<SysCode> codeList=sysCodeService.queryChildrenByFlag("SYSTEM"); //获取配置表中系统列表信息
		request.setAttribute("codeList", codeList);
		return "system/logInfoList";
	}
	@RequiresPermissions("log:query")
	@RequestMapping(value="/getOperlist",method = RequestMethod.POST)
	public void getOperlist(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String workId=request.getParameter("workId");
		String startoperateDate=request.getParameter("startoperateDate");
		String stopoperateDate=request.getParameter("stopoperateDate");
		String system=request.getParameter("system");
		operatelog.setWorkId(workId);
		operatelog.setCode(system);
		operatelog.setStartoperateDate(startoperateDate);
		operatelog.setStopoperateDate(stopoperateDate);
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<OperateLog> listopera=null;
		String json="";
		int count=0;
		try {
		if(system.equals("KS")){ //查询考试日志信息
			listopera=operatelogService.listKsOperatelog(operatelog);
			count=operatelogService.listKsOperatelogCount(operatelog);
		}else{
			listopera=operatelogService.listoperatelog(operatelog);
			count=operatelogService.countOperatelog(operatelog);
		}
		if(listopera==null){
			json = "{\"Total\":" + count + " , \"Rows\":[]}";
		}else{
		   json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listopera).toString() + "}";
		}
	
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取日志列表异常："+e.getMessage(),e);
		}	
	}
	
	
	/******
	 * 报表页面跳转地址
	 * *******/
	@RequestMapping(value="/toMonitorWorkload",method=RequestMethod.GET)
	public String toMonitorWorkload(){		
		return "report/MonitorWorkload";
	}
	
	@RequestMapping(value="/toWorkpaperScore",method=RequestMethod.GET)
	public String toWorkpaperScore(){		
		return "report/workpaperScore";
	}
	
	@RequestMapping(value="/toAcceptQualityCheck",method=RequestMethod.GET)
	public String toAcceptQualityCheck(){		
		return "report/AcceptQualityCheck";
	}
	
	@RequestMapping(value="/toQCBusiness",method=RequestMethod.GET)
	public String toQCBusiness(){		
		return "report/QCBusiness";
	}
	
	
}
