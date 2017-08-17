package cn.sh.ideal.util; 

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
public class WebUtil {
	
	/**
	 * 获取当前请求的ip地址
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public static String getIpAddr(HttpServletRequest request) throws IOException{
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
		 /* String ip = request.getHeader("x-forwarded-for");  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    }  
		    return ip;  */
		    
		  ip = request.getRemoteAddr();
		  if("0:0:0:0:0:0:0:1".equals(ip)){
			  ip = ConfigProperties.getProperty("localhost");
		  }
        return ip;
     }
}
