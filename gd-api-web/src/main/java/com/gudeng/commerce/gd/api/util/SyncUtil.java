package com.gudeng.commerce.gd.api.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.gudeng.commerce.gd.api.redis.RedisKeys.RedisKey;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 同步方法公共类
 * @author TerryZhang
 *
 */
public class SyncUtil {

	/**
	 * 设置手机校验码
	 * @param mobile 手机号
	 * @param verifycode 校验码
	 */
	public static synchronized void setVerifyCode(String mobile, String verifycode, IGDBinaryRedisClient redisClient){
		Map<String, Object> map = redisClient.get(RedisKey.VERIFY_CODE_KEY.value + mobile);
		if(map == null){
			map = new HashMap<String, Object>();
		}
		
		map.put("time", System.currentTimeMillis());
		map.put("verifyCode", verifycode);
		redisClient.set(RedisKey.VERIFY_CODE_KEY.value + mobile, (Serializable) map);
	}
	
	/**
	 * 移除手机验证码
	 * @param key
	 * @param map
	 */
	public static synchronized void removeVerifyCode(String mobile, IGDBinaryRedisClient redisClient){
		redisClient.del(RedisKey.VERIFY_CODE_KEY.value + mobile);
	}
}
