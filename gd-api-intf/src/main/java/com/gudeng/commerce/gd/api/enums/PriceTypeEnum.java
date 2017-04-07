package com.gudeng.commerce.gd.api.enums;

public enum PriceTypeEnum {
	
	REGION("1","区间价格"), SINGLE("0", "单价");
	
	private final String key;
	private final String value;
	
	PriceTypeEnum(String key, String value){
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
		for (PriceTypeEnum item :PriceTypeEnum.values()){
			if (item.getkey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
