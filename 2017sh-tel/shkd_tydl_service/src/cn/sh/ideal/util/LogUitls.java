package cn.sh.ideal.util; 

import javax.servlet.http.HttpServletRequest;

import cn.sh.ideal.intercept.LogDescriptionArgsObject;

/**
 * 处理日志的工具类
 */
public class LogUitls {
	// 用于存储每个线程的request请求
	private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();
	
	public static void putRequest(HttpServletRequest request) {
		LOCAL_REQUEST.set(request);
	}
	
	public static HttpServletRequest getRequest() {
		return LOCAL_REQUEST.get();
	}
	
	public static void removeRequest() {
		LOCAL_REQUEST.remove();
	}
	/**
	 * 将LogDescriptionArgsObject放入LOG_ARGUMENTS。
	 * 描述
	 * @param logDescriptionArgsObject
	 */
	public static void setArgs(LogDescriptionArgsObject logDescriptionArgsObject) {
		HttpServletRequest request = getRequest();
		request.setAttribute(Constans.CONSTANT_LOG_DESCRIPTION_ARGS_OBJECT, logDescriptionArgsObject);
	}
	
	/**
	 * 得到LogDescriptionArgsObject。
	 * 描述
	 */
	public static LogDescriptionArgsObject getArgs() {
		HttpServletRequest request = getRequest();
		return (LogDescriptionArgsObject)request.getAttribute(Constans.CONSTANT_LOG_DESCRIPTION_ARGS_OBJECT);
	}
}
