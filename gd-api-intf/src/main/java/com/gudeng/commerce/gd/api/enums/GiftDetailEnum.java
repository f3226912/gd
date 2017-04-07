package com.gudeng.commerce.gd.api.enums;

public enum GiftDetailEnum {
	GIFT_TYPE("1","礼品明细"),  
	ORDER_TYPE("2","订单明细"),  
	REGIST_TYPE("3","农批注册明细"), 
	NST_REGIST_TYPE("4","农速通注册明细"), 
	NST_SUPPLYOFGOOD_TYPE("5","发布货源"), 
	NST_INFOORDER_TYPE("6","信息订单"),
	NST_FREIGHTORDER_TYPE("7","货运订单"); 

	
	private final String key;
	private final String value;
	
	GiftDetailEnum(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
}
