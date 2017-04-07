package com.gudeng.commerce.gd.customer.enums;

public enum MemberCertificationStatusEnum {
	
	SUBMIT("0","待认证"),
	YES("1", "已认证"), 
	NO("2","认证未通过");
	
	private final String key;
	private final String value;
	
	private MemberCertificationStatusEnum(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getkey(){
		return this.key;
	}
	public String getValue(){
		return this.value;
	}
	public static String getValue(String key){
		for (MemberCertificationStatusEnum item :MemberCertificationStatusEnum.values()){
			if (item.getkey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "未提交认证";
	}
	
	
}
