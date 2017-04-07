package com.gudeng.commerce.gd.api.redis;

public class RedisKeys {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		//农速通token key值
		NST_TOKEN_KEY("NST_TOKEN_"),
		
		/**
		 * 验证码key值
		 */
		VERIFY_CODE_KEY("V_CODE_");
		
		public String value;
		
		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
