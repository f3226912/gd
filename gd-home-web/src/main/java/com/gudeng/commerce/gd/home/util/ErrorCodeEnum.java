package com.gudeng.commerce.gd.home.util;

public enum ErrorCodeEnum {
	/** 成功返回 */
	SUCCESS(0),
	/** 返回失败 */
	FAIL(-1),
	/** 账户错误 */
	ACCOUNT_ERROR(-2),
	/** 密码错误 */
	PASSWORD_ERROR(-3),
	/** 需要登录 */
	NEED_LOGIN(-999);
	
	ErrorCodeEnum(Integer value){
		this.value = value;
	}
	
	private final Integer value;
	
	public Integer getValue(){
		return value;
	}
}
