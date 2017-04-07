package com.gudeng.commerce.gd.admin.entity;

public enum AuditStatusEnum {
	
	NOT_PASS("0","审核不通过"), PASS("1","审核通过");
	
	private final String key;
	private final String value;
	
	AuditStatusEnum(String key, String value){
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
		for (AuditStatusEnum item :AuditStatusEnum.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
