package com.gudeng.commerce.gd.admin.entity;

public enum ProductPictureTypeEnum {
	
	MAIN("1","主图"), MUTIPLE("2", "多角度图"), APP("4", "APP图");
	
	private final String key;
	private final String value;
	
	ProductPictureTypeEnum(String key, String value){
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
		for (ProductPictureTypeEnum item :ProductPictureTypeEnum.values()){
			if (item.getkey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
	
	
}
