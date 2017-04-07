package com.gudeng.commerce.gd.notify.enums;

/**
 * 农速通请求接口 v1
 * 枚举类
 * @author TerryZhang
 */
public enum ApiRequestV1Enum {
	
	PAY_PREPAY_SUCC(2001,"v1/pay/payPrePaymenSucc","支付预付款成功后，通知农速通"),
	
	PAY_RESTPAY_SUCC(2002,"v1/pay/payRestPaymenSucc","支付尾款成功后，通知农速通");
	
	private ApiRequestV1Enum(int code, String uri, String desc) {
		this.code = code;
		this.uri = uri;
		this.desc = desc;
	}

	private final int code;
	
	private final String uri;
	
	private final String desc;

	public int getCode() {
		return code;
	}

	public String getUri() {
		return uri;
	}

	public String getDesc() {
		return desc;
	}
	
	public static String getUriByCode(int code){
		for (ApiRequestV1Enum item :ApiRequestV1Enum.values()){
			if (item.getCode() == code){
				return item.getUri();
			}
		}
		return "";
	}
	
	public static ApiRequestV1Enum getByCode(int code){
		for (ApiRequestV1Enum item :ApiRequestV1Enum.values()){
			if (item.getCode() == code){
				return item;
			}
		}
		return null;
	}
}
