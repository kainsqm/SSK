package cn.sh.ideal.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import cn.sh.ideal.controller.UserController;


public class CookieUtil {
	private static Logger log = Logger.getLogger(CookieUtil.class);
		/**
		 * 获取cookie中的值
		 */
	
		public static String getCookieName(HttpServletRequest request){			
			String token=null;
			Cookie[] cookies=request.getCookies();
			if(cookies!=null){
				for (int i = 0; i < cookies.length; i++) {
					Cookie c=cookies[i];
					if(c.getName().equalsIgnoreCase(ConfigProperties.getProperty("cookiekey"))){					
						token=c.getValue();
					}
				}					
			}		
			return token;
		}
		/**
		 * 获取url的值
		 */
		public static String getUrlToken(HttpServletRequest request){
			String token=null;
			String url=request.getParameter("token");
			if (url==null || url=="") {
				return null;
			}else{
				token=DES3Util.decrypt(url);
				return token;
			}
		}
		
		
		/**
		 * 将json字符串转成bean
		 */
		public static<T> Object jsonToBean(String jsonStr,Class<T> 	obj){
			T t=null;
			try{
				ObjectMapper objMapper=new ObjectMapper();
				t=objMapper.readValue(jsonStr, obj);		
			}catch (Exception e) {
				log.error("json转对象异常:"+e);
			}			
			return t;
		}
		
		/**
		 * 获取角色标识
		 */
		public static String getRoleFlag(String rolefalg){
			String role_flag=null;
			String [] roles=rolefalg.split(",");
			for (String string : roles) {
				if("role_1".equals(string)){
					role_flag=string;
				}else if("role_2".equals(string)){
					role_flag=string;
				}else if("role_3".equals(string)){
					role_flag=string;
				}else if("role_4".equals(string)){
					role_flag=string;
				}
			}
			return role_flag;
		}
}
