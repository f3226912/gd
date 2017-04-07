package com.gudeng.commerce.gd.pay.enm;

/**
 * 旺pos支付通知常量
 */
public enum WangPosPayNotifyEnum {
	/**
	 * 交易状态
	 */
	TRADE_STATUS_WAIT_PAY("WAIT_PAY","待支付"),
	TRADE_STATUS_PAYING("PAYING","支付中"),
	TRADE_STATUS_PART_PAY("PART_PAY","部分支付"),
	TRADE_STATUS_PAY("PAY","已支付"),
	TRADE_STATUS_REFUNDING("REFUNDING","退款中"),
	TRADE_STATUS_REFUND("REFUND","已退款"),
	TRADE_STATUS_CLOSED("CLOSED","已关闭"),
	
	/**
	 * 异常编码，1开头来自第三方旺pos文档，2开头是自定义的
	 */
	ERROR_CODE_0("0","操作成功"),
	ERROR_CODE_1001("1001","无访问权限"),
	ERROR_CODE_1002("1002","签名错误"),
	ERROR_CODE_1003("1003","商户订单号为空"),
	ERROR_CODE_1004("1004","订单不存在或者状态不对"),
	ERROR_CODE_1005("1005","操作非法[bp_id 不匹配]"),
	ERROR_CODE_2001("2001","交易状态为空"),
	ERROR_CODE_2002("2002","收银系统订单号为空"),
	ERROR_CODE_2003("2003","订单支付记录已存在"),
	ERROR_CODE_2004("2004","交易状态不是已支付状态"),
	ERROR_CODE_2005("2005","支付方式为空"),
	ERROR_CODE_2101("2101","系统异常");
	
	private String key;
	private String value;
    
    private WangPosPayNotifyEnum(String key, String value)
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
