package cn.sh.ideal.intercept;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.IUserService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.WebUtil;

/**
 * 日志处理的拦截器
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private IOperateLogService logService;
	@Resource
	private IUserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		/**
		 * 将当前request放入到LogUitls的LOCAL_REQUEST中
		 */
		LogUitls.putRequest(request);
		return true;
	}
	
	
   @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
	/*	Class class1=handlerMethod.getClass();*/
		// param存放共有属性：userId ip method
		
		final Log log = method.getAnnotation(Log.class);
		if (null != log) {
			UserInfo user=userService.getRedisUser(request);
			final Map<String, String> param = new HashMap<String, String>();
			if(user!=null){
				param.put("userid", user.getUserId().toString());
				param.put("username",user.getUserName());
			}
			// 获取请求当前方法的日志注解
			param.put("ip", WebUtil.getIpAddr(request));
			param.put("classname", "");
			param.put("method", log.methodname());
			// 得到LogMessageObject,日志参数
			final LogDescriptionArgsObject logMessageObject = LogUitls.getArgs();
			// 另起线程异步操作
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						LogDescriptionArgsObject defaultLogMessageObject = logMessageObject;
						if (defaultLogMessageObject == null) {// 建议controller不要传入空值
							defaultLogMessageObject = LogDescriptionArgsObject.newInstant();
						}
						// 覆盖，直接写入日志
						logService.addLog(log.modulename(),log.funcname(),log.description(), defaultLogMessageObject.getObjects(), param,log.code());
					} catch (Exception e) {
					}
				}
			}).start();
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		LogUitls.removeRequest();
	}

}
