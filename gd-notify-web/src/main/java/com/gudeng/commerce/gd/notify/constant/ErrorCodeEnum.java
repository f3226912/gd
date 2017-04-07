package com.gudeng.commerce.gd.notify.constant;

public enum ErrorCodeEnum {
	/** 成功返回 */
	SUCCESS(0),
	/** 返回失败 */
	FAIL(-1),
	/** 账户错误 */
	ACCOUNT_ERROR(-2),
	/** 密码错误 */
	PASSWORD_ERROR(-3),
	/** 交易密码不存在 */
	TRADEPASSWORD_NOT_EXISTED(-4),
	/** 库存不足  */
	STOCK_COUNT_LESS(-5),
	
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
