package cn.sh.ideal.util; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
public class WebUtil {
	
	/**
	 * 
	 * @param req
	 * @throws Exception 
	 * @return 
	 */
	public static String getWorkIdFromSession(HttpServletRequest req){
		HttpSession session = req.getSession();
		String workId=(String)session.getAttribute(Constans.USER_INFO_WORKID);
		if(StringUtils.isEmpty(workId)){
			throw new NullPointerException("从session中未获取到workId");
		}
		
		return workId;
	}
	/**
	 * 获取当前请求的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		  String ip = request.getHeader("X-Real-IP");
		  if(!StringUtils.isBlank(ip)&&!"unknown".equalsIgnoreCase(ip))
		  {
			  return ip;
		  }
		  ip = request.getHeader("X-Forwarded-For");
		  if(!StringUtils.isBlank(ip)&& !"unknown".equalsIgnoreCase(ip))
		  {
			  int index = ip.indexOf(',');
			  if(index != -1){
				  return ip.substring(0, index);
			  }else{
				  return ip;
			  }
		  }
		  ip = request.getRemoteAddr();
		  if("0:0:0:0:0:0:0:1".equals(ip)){
			  ip = ConfigProperties.getProperty("HOSTIP");
		  }
        return ip;
     }
}
