package com.gudeng.commerce.gd.promotion.enums;

public enum GdActivityTypeEnums {
	
	SIXPLUSONE("1","6+1促销");
	
	private final String key;
	private final String value;
	
	GdActivityTypeEnums(String key, String value){
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
		for (GdActivityTypeEnums item :GdActivityTypeEnums.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
