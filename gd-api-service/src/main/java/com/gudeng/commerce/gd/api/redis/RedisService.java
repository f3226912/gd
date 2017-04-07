package com.gudeng.commerce.gd.api.redis;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

@Service
public class RedisService {

	public enum EXPIRE_TIME {
		ONE_DAY(86400l), 
		ONE_WEEK(604800l), 
		ONE_MONTH(18144000L);

		EXPIRE_TIME(Long value) {
			this.value = value;
		}

		private final Long value;

		public Long getValue() {
			return value;
		}
	}

	@Autowired
	private IGDBinaryRedisClient redisClient;

	//
	// public IGDBinaryRedisClient getRedisClient() {
	// return redisClient;
	// }
	//
	// public void setRedisClient(IGDBinaryRedisClient redisClient) {
	// this.redisClient = redisClient;
	// }

	public String set(String key, String value) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		return redisClient.set(key, value);
	}

	/**
	 * 设定过期时间的set方法
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 *            秒数为单位
	 * @throws Exception
	 */
	public String setex(String key, String value, Long expireTime)
			throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		
		String result = redisClient.setex(key, expireTime.intValue() * 1000, value);
		redisClient.expire(key, EXPIRE_TIME.ONE_MONTH.getValue().intValue());
		return result;
	}

	public String get(String key) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		return redisClient.get(key);
	}

	public Long ttl(String key) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		return redisClient.ttl(key);
	}

	public Long delete(String key) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		return redisClient.del(key);
	}

	public boolean exists(String key) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("redis的key值不能为null");
		}
		return redisClient.exists(key);
	}

	
	public String flushDB() throws Exception {
		return redisClient.flushDB();
	}
	
	public Set<String> keys(String pattern) {
		return redisClient.keys(pattern);
	}
}
