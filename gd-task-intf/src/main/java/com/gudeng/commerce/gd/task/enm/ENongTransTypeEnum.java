package com.gudeng.commerce.gd.task.enm;

/**
 * E农平台交易类型
 */
public enum ENongTransTypeEnum {
	
	TRANSTYPE_ORDER("0","订单支付"),
	TRANSTYPE_CARD("1", "刷卡消费");
	
	private String key;
	private String value;
	
	ENongTransTypeEnum(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
