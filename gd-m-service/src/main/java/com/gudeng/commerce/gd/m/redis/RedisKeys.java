package com.gudeng.commerce.gd.m.redis;

public class RedisKeys {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		//订单详情
		NST_TOKEN_KEY("NST_TOKEN_");
		
		public String value;
		
		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
