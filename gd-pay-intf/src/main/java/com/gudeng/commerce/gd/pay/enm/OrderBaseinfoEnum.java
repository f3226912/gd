package com.gudeng.commerce.gd.pay.enm;

/**
 * 订单常量
 */
public enum OrderBaseinfoEnum {
	/**
	 * 订单状态
	 */
	ORDER_STATUS_1("1","待付款"),
	ORDER_STATUS_2("2","部分付款"),
	ORDER_STATUS_3("3","已付款"),
	ORDER_STATUS_4("4","已出场"),
	ORDER_STATUS_8("8","已取消"),
	ORDER_STATUS_9("9","已作废"),
	ORDER_STATUS_10("10","已完成");
	
	
	private String key;
	private String value;
    
    private OrderBaseinfoEnum(String key, String value)
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
