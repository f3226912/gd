package com.gudeng.commerce.gd.supplier.enums;

public enum ProductTypeEnum {
	
	Common("1","会员产品"), FAST_PRODUCT("2", "快速制单产品");
	
	private final String key;
	private final String value;
	
	ProductTypeEnum(String key, String value){
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
		for (ProductTypeEnum item :ProductTypeEnum.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
