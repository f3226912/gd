package com.gudeng.commerce.gd.api.enums;

/**
 * 短信模板枚举类
 * @author TerryZhang
 *
 */
public enum MessageTemplateEnum {

	/**
	 * 注册模板
	 */
	REGISTER("验证码:{P}。您正在进行手机客户端注册操作，请勿向他人泄露。【谷登科技】"),
	/**
	 * 忘记密码模板
	 */
	FORGET_PWD("验证码:{P}，请及时输入以完成新密码设置。【谷登科技】"),
	
	/**
	 * 确认旧手机
	 * */
	CONFIRM_MOBILE("【谷登科技】验证码：{P}，您正在进行手机客户端修改手机号码验证操作，请勿向他人泄漏"),
	
	/**
	 * 绑定新手机
	 * */
	CONFIRM_NEW_MOBILE("【谷登科技】验证码：{P}，您正在进行手机客户端绑定新手机号码验证操作，请勿向他人泄漏"),
	
	
	/**
	 * 自动注册
	 */
	AUTO_REGIST("已经帮您成功注册农商友APP，用户名为当前手机号，密码为：%s，您可以登录后自行修改，下载地址：%s【谷登科技】"),	
	/**
	 * 自动注册
	 */
	FORGET_PAY_PASSWORD("验证码:{P}，请及时输入以完成新支付密码设置。【谷登科技】");
	
	MessageTemplateEnum(String template){
		this.template = template;
	}
	
	private final String template;
	
	public String getTemplate(){
		return template;
	}
}
