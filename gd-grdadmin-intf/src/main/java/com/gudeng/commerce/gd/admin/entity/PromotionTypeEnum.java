package com.gudeng.commerce.gd.admin.entity;

public enum PromotionTypeEnum {

	NST("1","农速通"), NSY("2", "农商友"), NPS("3", "农批商");
	
	private final String key;
	private final String value;
	
	PromotionTypeEnum(String key, String value){
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
		for (PromotionTypeEnum item :PromotionTypeEnum.values()){
			if (item.getkey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
}
