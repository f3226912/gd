package com.gudeng.commerce.gd.customer.bo;

public class RedisConstant {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		//单个地区
		AREA_AREAID("AREA_"),
		//一级所有地区
		AREA_TOP_ALL("AREA_TOP_ALL"),
		//地区子集
		AREA_CHILD_AREAID("AREA_CHILD_"),
		//地区子集树
		AREA_TREE_AREAID("AREA_TREE_"),
		//所有省份和城市
		AREA_ALL_PROVINCE_CITY("AREA_ALL_PROVINCE_CITY"),
		
		//会员id
		MEMBER_ID("MEMBER_"),
		//会员手机号
		MEMBER_MOBILE("MEMBER_MOBILE_"),
		//会员账号
		MEMBER_ACCOUNT("MEMBER_ACCOUNT_"),
		//特殊字符
		CHARACTER_LIST("CHARACTER_LIST_"),
		
		BUSINESS_TRADING_DYNAMICS("BUSINESS_TRADING_DYNAMICS_");
		
		public String value;

		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
