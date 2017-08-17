package cn.sh.ideal.util;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.sh.ideal.redis.RedisClientTemplate;

public class RedisUtil {
	/******
	 * 获取redis中的数据
	 * *******/

	private RedisClientTemplate redisTemplate;

	public RedisClientTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisClientTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	
}
