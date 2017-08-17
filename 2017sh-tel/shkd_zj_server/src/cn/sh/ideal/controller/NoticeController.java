package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.Dump.INode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.Notice;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.ItNoticeService;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;
/*****
 * 公告类
 * @author niewenqiang
 * 2017-4-18
 * ******/
@Controller
@RequestMapping("/controller/notice")
public class NoticeController {
	private static Logger log = Logger.getLogger(NoticeController.class);
	@Resource 
	private  ItNoticeService noticeService;
	
	/****
	 * 进入公告查询页面
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequiresPermissions("notice:query")
	@RequestMapping(value="/toNoticeList",method = RequestMethod.GET)
	public String toNoticeList(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		return "notice/noticeInfoList";
	}
	
	
	/****
	 * 首页进入公告查询页面
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequestMapping(value="/toNoticeListByMain",method = RequestMethod.GET)
	public String toNoticeListByMain(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		return "notice/noticeInfoListShow";
	}
	
	
	
	/****
	 * 进入公告新增页面
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequiresPermissions("notice:add")
	@RequestMapping(value="/toNoticeAdd",method = RequestMethod.GET)
	public String toNoticeAdd(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		return "notice/add_notice";
	}
	
	
	/****
	 * 进入公告修改页面
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
    @RequiresPermissions("notice:update")
	@RequestMapping(value="/toNoticeUpdate",method = RequestMethod.GET)
	public String toNoticeUpdate(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		int id=Integer.parseInt(request.getParameter("noticeId"));
		Notice notice=noticeService.queryNotice(id);
		request.setAttribute("notice",notice);
		return "notice/update_notice";
	}
	
	
	
	/****
	 * 进入公告详情页面
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequestMapping(value="/toNoticeInfo",method = RequestMethod.GET)
	public String toNoticeInfo(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		int id=Integer.parseInt(request.getParameter("noticeId"));
		Notice notice=noticeService.queryNotice(id);
		request.setAttribute("notice",notice);
		return "notice/noticeinfo";
	}
	
	
	/*****
	 * 公告查询列表
	 * ****/
	@RequiresPermissions("notice:query")
	@RequestMapping(value="/getNoticelist",method = RequestMethod.POST)
	public void getNoticelist(HttpServletRequest request,Notice notice,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<Notice> listnotice=noticeService.queryNoticeList(notice);;
		int count=noticeService.queryNoticeListCount(notice);;
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listnotice).toString() + "}";
		try {
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取公告列表异常："+e.getMessage());
		}	
	}
	
	
	
	/*****
	 * 首页更多公告查询
	 * ****/
	@RequestMapping(value="/getNoticelistByMain",method = RequestMethod.POST)
	public void getNoticelistByMain(HttpServletRequest request,Notice notice,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<Notice> listnotice=noticeService.queryNoticeListByMain(notice);;
		int count=noticeService.queryNoticeListByMainCount(notice);;
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listnotice).toString() + "}";
		try {
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取公告列表异常："+e.getMessage());
		}	
	}
	
	

	/*****
	 * 公告新增
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequiresPermissions("notice:add")
	@Log(methodname="insertNotice",modulename="公告信息维护",funcname="公告新增",description="新增了一条公告,标题为{0},{1}",code="SYS")
	@RequestMapping(value="/insertNotice",method = RequestMethod.POST)
	public void insertNotice(HttpServletRequest request,Notice notice,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		try {
			notice.setInsertUserId(userid);
			noticeService.insertNotice(notice);
			JSONObject object=new JSONObject();
			object.put("result","success");
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{notice.getTitle(),"操作成功"}));
		} catch (Exception e) {
			log.error("用户"+userid+"新增公告异常："+e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{notice.getTitle(),"操作失败"+e.getMessage()}));
		}	
	}
	
	

	/*****
	 * 公告修改
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequiresPermissions("notice:update")
	@Log(methodname="updateNotice",modulename="公告信息维护",funcname="公告修改",description="修改了公告{0},{1}",code="SYS")
	@RequestMapping(value="/updateNotice",method = RequestMethod.POST)
	public void updateNotice(HttpServletRequest request,Notice notice,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		try {
			notice.setUpdateUserId(userid);
			noticeService.updateNotice(notice);
			JSONObject object=new JSONObject();
			object.put("result","success");
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{notice.getTitle(),"操作成功"}));
		} catch (Exception e) {
			log.error("操作人"+userid+"修改公告"+notice.getPkAutoId()+"出现异常："+e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{notice.getTitle(),"操作失败"+e.getMessage()}));
		}	
	}
	
	
	/*****
	 * 公告删除
	 * @author niewenqiang
	 * 2017-4-19
	 * ****/
	@RequiresPermissions("notice:delete")
	@Log(methodname="deleteNotice",modulename="公告信息维护",funcname="公告删除",description="删除了一条公告ID为{0},{1}",code="SYS")
	@RequestMapping(value="/deleteNotice",method = RequestMethod.POST)
	public void deleteNotice(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		 String  pkAutoId=request.getParameter("pkAutoId"); //获取ID
		 vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		try {
			Notice notice=new Notice();
			notice.setUpdateUserId(userid);
			notice.setPkAutoId(Integer.parseInt(pkAutoId));
			noticeService.deleteNotice(notice);
			JSONObject object=new JSONObject();
			object.put("result","success");
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{pkAutoId,"操作成功"}));
		} catch (Exception e) {
			log.error("操作人"+userid+"删除公告"+pkAutoId+"出现异常："+e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{pkAutoId,"操作失败"+e.getMessage()}));
		}	
	}
		
}
