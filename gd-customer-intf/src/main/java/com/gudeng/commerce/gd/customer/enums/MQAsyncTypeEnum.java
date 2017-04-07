package com.gudeng.commerce.gd.customer.enums;

public enum MQAsyncTypeEnum {

	TYPE_MEMBER(1,"会员"), TYPE_BANK(2,"银行卡"), TYPE_POS(3,"银行卡");

	private final Integer key;
	private final String value;

	MQAsyncTypeEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}

	public Integer getKey(){
		return this.key;
	}
	public String getValue(){
		return this.value;
	}
	public static String getValue(Integer key){
		for (MQAsyncTypeEnum item :MQAsyncTypeEnum.values()){
			if (item.getKey().intValue()==key){
				return item.getValue();
			}
		}
		return "";
	}


}
