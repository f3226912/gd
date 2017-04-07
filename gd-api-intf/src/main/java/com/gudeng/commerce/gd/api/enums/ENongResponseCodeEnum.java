package com.gudeng.commerce.gd.api.enums;

/**
 * E农平台响应码
 * @author TerryZhang
 */
public enum ENongResponseCodeEnum {
	
	PAY_SUCCESS("0000","支付成功"), 
	PAY_FAIL("0001", "支付失败"),
	OPERATION_SUCCESS("0000", "操作成功"), 
	OPERATION_FAIL("0001","操作失败"), 
	ORDER_NOT_EXISTED("0164","订单号不存在"), 
	ORDER_ALREADY_PAID("0166", "订单已支付");
	
	private final String resultCode;
	private final String resultMsg;
	
	ENongResponseCodeEnum(String resultCode, String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public String getResultCode(){
		return this.resultCode;
	}
	
	public String getResultMsg(){
		return this.resultMsg;
	}
	
	public static String ResultmsgByCode(String resultCode){
		for (ENongResponseCodeEnum item :ENongResponseCodeEnum.values()){
			if (item.getResultCode().equalsIgnoreCase(resultCode)){
				return item.getResultMsg();
			}
		}
		return "";
	}
}
