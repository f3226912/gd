package com.gudeng.commerce.gd.customer.enums;

/**
 * 会员相关的常量
 */
public enum MemberBaseinfoEnum {
	/**
	 * 会员类别
	 */
	LEVEl_1("1","谷登农批"),
	LEVEl_2("2","农速通"),
	LEVEl_3("3","农商友"),
	LEVEl_4("4","产地供应商"),
	LEVEl_5("5","门岗"),
	
	/**
	 * 会员状态
	 */
	STATUS_0("0","未启用"),
	STATUS_1("1","农速通"),
	
	
	/**
	 * 农速通会员类型
	 */
	USER_TYPE_1("1","个人"),
	USER_TYPE_2("2","企业"),
	
	/**
	 * 注册类型
	 */
	REGETYPE_0("0","管理后台创建用户"),
	REGETYPE_1("1","web前端注册用户"),
	REGETYPE_2("2","农速通注册用户"),
	REGETYPE_3("3","农商友-买家版注册用户"),
	REGETYPE_4("4","农商友-卖家版注册用户"),
	REGETYPE_5("5","门岗添加用户"),
	REGETYPE_7("7","pos刷卡"),
    REGETYPE_8("8","微信注册用户");
	
	private String key;
	private String value;
    
    private MemberBaseinfoEnum(String key, String value)
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
