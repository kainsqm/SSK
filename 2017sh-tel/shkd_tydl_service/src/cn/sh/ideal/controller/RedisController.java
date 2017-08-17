package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.redis.RedisClientTemplate;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/redis")
public class RedisController {
	private static Logger log = Logger.getLogger(RedisController.class);
	 @Autowired
	private RedisClientTemplate redisClientTemplate;
	/*****
	 * 获取redis存储的值
	 * @author niewenqiang
	 * @date 2017-5-4
	 * ******/
	@RequestMapping(value="/getRedis",method = RequestMethod.GET)
	public void getRedis(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String token=request.getParameter("token");
		String redisValue=redisClientTemplate.get(token);
		try {
			if(null!=redisValue){ //如果redis服务器中有该key，则更新过期时间
				redisClientTemplate.expireTime(token, Integer.parseInt(ConfigProperties.getProperty("redisTimeOut")));
			}
			response.getWriter().print(redisValue);
		} catch (IOException e) {
			log.error("获取获取redis存储的值异常："+e.getMessage(),e);
		}	
		
		
		
	}
	
		
}
