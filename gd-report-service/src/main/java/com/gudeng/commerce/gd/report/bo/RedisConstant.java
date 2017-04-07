package com.gudeng.commerce.gd.report.bo;

public class RedisConstant {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		
		//会员账号
		MEMBER_ACCOUNT("MEMBER_ACCOUNT_");
		
		public String value;

		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
