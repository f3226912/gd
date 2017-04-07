package com.gudeng.paltform.pushmsg;

import java.util.Map;

public class GdMessageDTO {

	/**
	 * sendApp:推送对象:1:农商友,2:农速通,3农批商
	 */
	public String sendApp;
	/**
	 * deviceType:设备类型:1:Android,2:Ios
	 */
	public String deviceType;
	
	/**
	 * SendType:
	 *  1:unicast-单播
	 *  2:broadcast-广播
	 */
	public String sendType;
	/**
	 * ticket:通知栏提示文字
	 */
	public String ticket;
	/**
	 * title:通知的标题
	 */
	public String title;
	/**
	 * content:通知的内容
	 */
	public String content;
	
	/**
	 * 点击"通知"的后续行为，默认为打开app
	 * "go_app": 打开应用
       "go_url": 跳转到URL
       "go_activity": 打开特定的activity
       "go_custom": 用户自定义内容。
	 */
	public String after_open;

	/**
	 * url:跳转地址
	 */
	public String url;
	
	/**
	 * 可选 当"after_open"为"go_activity"时，必填。
                         通知栏点击后打开的Activity
	 */
	public String activity;
	/**
	 * memberId:会员id
	 */
	public String memberId;
	/**
	 * device_tokens:推送对象唯一表示
	 */
	public String device_tokens;
	
	/**
	 * true:正式/false:测试模式。测试模式下，只会将消息发给测试设备。
                           测试设备需要到web上添加。
	 */
	public Boolean production_mode;
	
	/**
	 * 20151030 semon 添加
	 * 用户自定义key-value。只对"通知"
       (display_type=notification)生效。
                    可以配合通知到达后,打开App,打开URL,打开Activity使用。
	 */
	public Map<String,String> extraMap;
	

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	public String getSendApp() {
		return sendApp;
	}

	public void setSendApp(String sendApp) {
		this.sendApp = sendApp;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getAfter_open() {
		return after_open;
	}

	public void setAfter_open(String after_open) {
		this.after_open = after_open;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Boolean getProduction_mode() {
		return production_mode;
	}

	public void setProduction_mode(Boolean production_mode) {
		this.production_mode = production_mode;
	}

	public Map<String, String> getExtraMap() {
		return extraMap;
	}

	public void setExtraMap(Map<String, String> extraMap) {
		this.extraMap = extraMap;
	}
	
	

}
