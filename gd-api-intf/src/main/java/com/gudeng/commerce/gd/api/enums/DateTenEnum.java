package com.gudeng.commerce.gd.api.enums;

public enum DateTenEnum {
	DATE_FIRST(1,"上旬"),  
	DATE_SECOND(2,"中旬"),  
	DATE_THIRD(3,"下旬");  

	
	private final Integer key;
	private final String value;
	
	DateTenEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}
	
	public Integer getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
}
