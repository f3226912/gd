package com.gudeng.commerce.gd.api.enums;

public enum AreaInfoEnum {

	TAI_WAN("710000","台湾省"), 
	HONG_KONG("810000", "香港特别行政区"),
	MACAO("820000", "澳门特别行政区");
	
	private final String key;
	private final String value;
	
	AreaInfoEnum(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getkey(){
		return this.key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static String getValueByKey(String key){
		for (AreaInfoEnum item :AreaInfoEnum.values()){
			if (item.getkey().equals(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	public static AreaInfoEnum getByKey(String key){
		for (AreaInfoEnum item :AreaInfoEnum.values()){
			if (item.getkey().equals(key)){
				return item;
			}
		}
		return null;
	}
}
