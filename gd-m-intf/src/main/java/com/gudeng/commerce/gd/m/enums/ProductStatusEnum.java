package com.gudeng.commerce.gd.m.enums;

public enum ProductStatusEnum {
	
	DRAFT("0","新增"), NEEDAUDIT("1","待审核"), REFUSE("2", "审核不通过"),
	ON("3", "已上架"), OFF("4","已下架"), DELETED("5", "已删除");
	
	private final String key;
	private final String value;
	
	ProductStatusEnum(String key, String value){
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
		for (ProductStatusEnum item :ProductStatusEnum.values()){
			if (item.getkey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
