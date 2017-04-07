package com.gudeng.commerce.gd.report.util;

import java.util.Properties;

/**
 * 惠农参数属性;
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
	
	/** demo的地址 */
	public String getDemoUrl() {
		return properties.getProperty("gd.demo.url");
	}
	
	/**
	 * 获取系统编码
	 * @author wwj
	 * @return
	 */
	public String getCasSystemCode() {
		return properties.getProperty("CAS_SYSTEM_CODE");
	}
	
}
