package com.gudeng.commerce.gd.m.enums;

/**
 * E农平台响应码
 * @author TerryZhang
 */
public enum ResponseCodeEnum {
	OPERATION_SUCCESS("0000", "操作成功"), 
	OPERATION_FAIL("0001","操作失败"), 
	ACCOUNT_IS_NULL("0101","会员帐号不能为空"), 
	PAYTIME_IS_NULL("0102", "支付时间为空"),
	CHANNELTYPE_IS_NULL("0103", "渠道类型为空"), 
	CREDIT_IS_NULL("0104", "贷款额度区间为空"), 
	ACCOUNT_ERROR("0105","会员帐号错误"),
	TIMETYPE_IS_NULL("0106", "时间为空"), ;
	
	private final String resultCode;
	private final String resultMsg;
	
	ResponseCodeEnum(String resultCode, String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public String getResultCode(){
		return this.resultCode;
	}
	
	public String getResultMsg(){
		return this.resultMsg;
	}

}
