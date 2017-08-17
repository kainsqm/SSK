package cn.sh.ideal.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping("/controller/noneqc")
public class NoneQCController {

	private static Logger log = Logger.getLogger(QCController.class);
	@Resource
	private IQCService QCService;
	@Resource
	private IQCDetailService QCDetailService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private ISysCodeService sysCodeService;
	@Resource
	private ISecondaryQcService secondaryQcService;
	
	@RequiresPermissions("none:qc")
	@RequestMapping(value = "/getNoneqc", method = RequestMethod.GET)
	public String getNoneqc(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			Integer qcid = null;
			if (request.getParameter("qcid") != null
					&& !request.getParameter("qcid").equals("")) {
				qcid = Integer.parseInt(request.getParameter("qcid"));
			}
			request.setAttribute("listentime", DateUtil
					.getDateTimeStr(new Date()));
			request.setAttribute("workname", ((vSysuser) request
					.getAttribute(Constans.USER)).getUserName());

			// 获取录音关联的工单信息以及最新的评分信息
			// 判断该录音是否关联工单
			if(qcid!=null){
			QC qc = QCService.selectByQcid(qcid);
			if (qc != null) {// 已评过分
				// 获取最新的评分信息
				UserInfo user=new UserInfo();
				user.setSmailWorkid(qc.getAgentWorkid());
				UserInfo u2=userInfoService.selectbysmailworkId(user);
				QCDetail qcDetail = QCDetailService.selectDetailByQcid(qc
						.getQcId());
				request.setAttribute("qcDetail", qcDetail);
				request.setAttribute("qcid", qc.getQcId());
				request.setAttribute("agentName", u2.getUserName());
				request.setAttribute("agentworkid", qc.getAgentWorkid());
				request
						.setAttribute("workname", userInfoService
								.userinfoByuserid(qcDetail.getQcUserId())
								.getUserName());
				request.setAttribute("display", "0");// 已评分不能修改页面信息
				// 根据业务类型和是否tl9000加载处理情况
				SysCode syscode = new SysCode();
				syscode.setPid(Integer.parseInt(qcDetail.getBusinessType()));
				syscode.setIsTl9000(qcDetail.getIsTl9000());
				List<SysCode> codelist = sysCodeService.selectSyscode(syscode);
				request.setAttribute("codelist", codelist);
				SysCode syscode2 = new SysCode();
				if ("10".equals(qcDetail.getBusinessType())) {
					syscode2.setPid(124);
				} else if ("54".equals(qcDetail.getBusinessType())) {
					syscode2.setPid(124);
				} else if ("32".equals(qcDetail.getBusinessType())) {
					syscode2.setPid(96);
				} else if ("76".equals(qcDetail.getBusinessType())) {
					syscode2.setPid(172);
				}else if("178".equals(qcDetail.getBusinessType())){
					syscode2.setPid(124);
				}else if("200".equals(qcDetail.getBusinessType())){
					syscode2.setPid(96);
				} else {
					syscode2.setPid(Integer
							.parseInt(qcDetail.getBusinessType()));
				}
				List<SysCode> checkcodelist = sysCodeService
						.selectSyscode(syscode2);
				request.setAttribute("checkcodelist", checkcodelist);
				// 合格的情况
				if (qcDetail.getQualityLevel().equals(Constans.PASS)) {
					// 提交按钮不显示
					request.setAttribute("subflag", "0");
				}
				// 判断是否被二级质检；根据工单流水判断
				int num = secondaryQcService.seccountbyqcId(qc.getQcId());
				if (num < 1) {
					// 填写意见按钮显示,其他情况不显示
					request.setAttribute("secflag", "0");
						      }

			}
			}else{// 没关联情况，初始化业务类型为112，是否tl9000为是，加载处理情况
				SysCode syscode = new SysCode();
				syscode.setPid(Constans.PID_112);
				syscode.setIsTl9000(Constans.TL9000);
				List<SysCode> codelist = sysCodeService.selectSyscode(syscode);
				request.setAttribute("codelist", codelist);
				SysCode syscode2 = new SysCode();
				syscode2.setPid(Constans.PIDCHECK_112);
				List<SysCode> checkcodelist = sysCodeService
						.selectSyscode(syscode2);
				request.setAttribute("checkcodelist", checkcodelist);
				// 填写意见按钮不显示
				request.setAttribute("secflag", "1");
			}	
		} catch (Exception e) {
			log.error("获取录音详情异常：" + e.getMessage());		
		}
		return "nonenew_qc";
	}
}
