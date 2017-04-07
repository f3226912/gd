package com.gudeng.commerce.gd.promotion.enums;

public enum GdActRuleChargeModeEnums {
	
	PERCENT("1","按比例"), ORDER("2","按订单");
	
	private final String key;
	private final String value;
	
	GdActRuleChargeModeEnums(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){
		return this.key;
	}
	public String getValue(){
		return this.value;
	}
	public static String getValue(String key){
		for (GdActRuleChargeModeEnums item :GdActRuleChargeModeEnums.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
