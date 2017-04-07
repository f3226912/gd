package com.gudeng.commerce.gd.task.util;

public class RedisConstant {

	/**
	 * redis key
	 * @author tanjun
	 * @date 2014年3月12日
	 */
	public static enum RedisKey{
		
		//test
		TEST("TEST_");
		
		public String value;

		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
