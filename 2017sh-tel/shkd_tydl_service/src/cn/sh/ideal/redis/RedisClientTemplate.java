package cn.sh.ideal.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import cn.sh.ideal.model.UserInfo;

import redis.clients.jedis.ShardedJedis;

@Repository("redisClientTemplate")
public class RedisClientTemplate {

	private static Logger log = Logger.getLogger(RedisClientTemplate.class);

    @Autowired
    private RedisDataSource     redisDataSource;

    public void disconnect() {
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        shardedJedis.disconnect();
    }

    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @param seconds 生存时间 秒
     * @return
     */
    public String set(String key, Object value,int seconds) {
        String result = null;
        String objectJson = JSON.toJSONString(value);  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
        	result=shardedJedis.setex(key, seconds, objectJson); //设置redis超时时间为30分钟
        } catch (Exception e) {
            log.error("添加redis服务器数据异常："+e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    
    
    /**
     * 删除单个值
     * 
     * @param key
     * @param value
     * @return
     */
    public void delete(String key) {
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return ;
        }
        boolean broken = false;
        try {
        	shardedJedis.del(key);
        } catch (Exception e) {
            log.error("redis删除key出现异常："+e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
    }
    
    
    
    
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error("获取redis服务器数据异常："+e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }
    
    /**
     * 更新key的生存时间
     * 
     * @param key 主键
     * @param seconds 生存时间 秒
     * @return
     */
    public void expireTime(String key,int seconds) {
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("更新key的生存时间异常："+e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
    }
    
    
    /*******
     * 判断服务器中是否有该值
     * *********/
    public boolean isexist(String key){
    	  ShardedJedis shardedJedis = redisDataSource.getRedisClient();
    	  return shardedJedis.exists(key);
    }
     
}
