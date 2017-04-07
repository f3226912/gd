package com.gudeng.commerce.gd.promotion.bo;

public class RedisConstant {

	/**
	 * redis key
	 */
	public static enum RedisKey{

		PROM_ID("PROM_"),
		PROM_PROD_ID("PROM_PROD_"),
		//会员账号
		MEMBER_ACCOUNT("MEMBER_ACCOUNT_"),
		
		GD_ACTIVITY("GD_ACTIVITY_KEY");
		
		public String value;

		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
