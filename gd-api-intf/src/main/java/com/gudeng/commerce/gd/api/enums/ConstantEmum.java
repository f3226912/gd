package com.gudeng.commerce.gd.api.enums;

public enum ConstantEmum {
	
	
//	public static final String LOGIN_SESSION = "loginSession";
	
	// 全局角色session
//	public static final String USER_ROLE_SESSION = "userRoleSession";
	
	/** 成功返回 */
	LOGIN_SESSION("loginSession"),
	/** 返回失败 */
	USER_ROLE_SESSION("userRoleSession"),
	 COOKIE_LOGIN_USERNAME ("login_username"),
	  COOKIE_LOGIN_PASSWORD   ("login_password");
	ConstantEmum( String value){
		this.value = value;
	}
	
	private final String value;
	
	public   String getValue(){
		return value;
	}
}
