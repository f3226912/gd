package com.gudeng.commerce.gd.promotion.enums;

public enum GdActivityRuleCostTypeEnums {
	
	ADVANCE("1","预付款"), COMMISSION("2","佣金");
	
	private final String key;
	private final String value;
	
	GdActivityRuleCostTypeEnums(String key, String value){
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
		for (GdActivityRuleCostTypeEnums item :GdActivityRuleCostTypeEnums.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
