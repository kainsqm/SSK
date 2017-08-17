package cn.sh.ideal.util;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IFunctionService;

public class CheckUrlFilter implements Filter {

	private static Logger log = Logger.getLogger(CheckUrlFilter.class);
	/** 
	* 需要排除的页面 
	*/  
	  
	private String[] excludedPageArray;  
	
	public void init(FilterConfig fConfig) throws ServletException {
		
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
	    String token = CookieUtil.getUrlToken(request);
		boolean isExcludedPage = false;
		log.info("进入拦截器:");
		if (request.getServletPath().equals(ConfigProperties.getProperty("filterUrl"))) {
				isExcludedPage = true;
		}
		// 获取地址栏访问的地址IP
		String ip = request.getServerName();
		log.info("当前链接:"+request.getServletPath());
		// 判断当前访问的地址是ENI地址还是内网地址 eni10开头 内网172开头
		boolean isEni = false;
		if (ip.contains("10.4.15")) { // ENI地址
				isEni = true;
		} else if (ip.contains("172.16.67")) { // 内网地址
				isEni = false;
		}
		String systemUrl="";
		if(isEni){ //内网地址
			systemUrl=ConfigProperties.getProperty("eniSystemUrl");
		}else{
			systemUrl=ConfigProperties.getProperty("SystemUrl");
		}
		log.info("是否在过滤在外:"+isExcludedPage);
		if (isExcludedPage) {// 在过滤url之外
			chain.doFilter(request, response);
		} else {
			log.info("token的值:"+token);
			if (token != null) {
				request.getSession().setAttribute("token", token);
			} else {
				token = (String) request.getSession().getAttribute("token");
			}
			log.info("SSO_BASE_URL:"+ConfigProperties.getProperty("SSO_BASE_URL"));
			log.info("SSO_USER_TOKEN_SERVICE:"+ConfigProperties.getProperty("SSO_USER_TOKEN_SERVICE"));
			String json = HttpUtil.doGet(systemUrl+ ConfigProperties.getProperty("SSO_BASE_URL")
					+ ConfigProperties.getProperty("SSO_USER_TOKEN_SERVICE")
					+ token);
			log.info("json的值:"+json);
			if (!"null".equals(json) && !json.equals("")) {
				vSysuser user = (vSysuser) CookieUtil.jsonToBean(json,
						vSysuser.class);
				if (user == null) {
					if (request.getHeader("x-requested-with") != null
							&& "XMLHttpRequest".equalsIgnoreCase(request
									.getHeader("x-requested-with"))) {
						response.setHeader("sessionstatus", "timeout");
					} else {
						response.sendRedirect(systemUrl);
					}
				} else {
					log.info("用户信息:"+user);
					request.setAttribute("User", user);
					request.setAttribute("userworkid", user.getWorkId());
					request.setAttribute("userid", user.getUserId());
					String role = CookieUtil.getRoleFlag(user.getRoleFlag());
					request.setAttribute("role_flag", role);
					user.setToken(token);
					UsernamePasswordToken usertoken = new UsernamePasswordToken(
							user.getToken()+","+isEni, user.getWorkId());
					Subject currentUser = SecurityUtils.getSubject();
					if (!currentUser.isAuthenticated()) {
						usertoken.setRememberMe(true);
						currentUser.login(usertoken);
					}
					chain.doFilter(request, response);
				}
			} else {
				if (request.getHeader("x-requested-with") != null
						&& "XMLHttpRequest".equalsIgnoreCase(request
								.getHeader("x-requested-with"))) {
					response.setHeader("sessionstatus", "timeout");
				} else {
					response.sendRedirect(systemUrl);
				}

			}
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
