package com.gudeng.commerce.gd.order.bo;

public class OrderRedisKeys {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		//订单详情
		ORDER_DEAIL_ID("ORDER_DETAIL_");
		
		public String value;
		
		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
