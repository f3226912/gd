package com.gudeng.commerce.gd.task.enm;

/**
 * 付款常量
 */
public enum PaySerialnumberEnum {
	/**
	 * 付款状态
	 */
	PAY_STATUS_0("0","未支付"),
	PAY_STATUS_1("1","已支付"),
	PAY_STATUS_9("3","支付失败"),
	
	/**
	 * 支付方式
	 */
	PAY_TYPE_1("1","钱包余额"),
	PAY_TYPE_2("2","线下刷卡"),
	PAY_TYPE_3("3","现金交易"),
	
	/**
	 * 支付子类型
	 */
	PAY_SUB_TYPE_1("1","旺POS"),
	PAY_SUB_TYPE_2("2","E农管家");
	
	private String key;
	private String value;
	
    private PaySerialnumberEnum(String key, String value)
   	{
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

	public void setName(String value) {
		this.value = value;
	}
	
}
