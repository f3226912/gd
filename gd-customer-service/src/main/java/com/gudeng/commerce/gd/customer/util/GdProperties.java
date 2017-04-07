package com.gudeng.commerce.gd.customer.util;

import java.util.Properties;

/**
 * 参数属性;
 * 
 */
public class GdProperties {

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public String getMBaseUrl(){
		return properties.getProperty("gd.m.base.url");
	}
	public String getMqMemberTopic(){
		return properties.getProperty("gd.MQ.member.Topic");
	}
	
	public String getMqmqAccBankTopicTopic(){
		return properties.getProperty("gd.MQ.accBank.Topic");
	}
	public String getMqPosMachineTopic(){
		return properties.getProperty("gd.MQ.posmachine.Topic");
	}
	/**
	 * add by weiwenke
	 * 通用的获取topic的方法，不需要一个一个的配置
	 * @return
	 */
	public String getNsyMqMemberTopic(){
		return properties.getProperty("gd.MQ.member.nsy.Topic");
	}
}
