package com.gudeng.commerce.gd.supplier.enums;


public enum SystemLogTypeEnum {
	
	PRODUCT("1","产品"), MEMBER("2", "会员"), INFOMATION("3", "信息")
	, SHOP("4", "商铺"), TRANSPORT("5", "物流"), SYSTEM("6", "系统")
	, SUBSIDY("7", "补贴"), ORDER("8", "订单"), WALLET("9", "钱包");
	
	private final String key;
	private final String value;
	
	SystemLogTypeEnum(String key, String value){
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
		for (SystemLogTypeEnum item :SystemLogTypeEnum.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getValue();
			}
		}
		return "";
	}
}
