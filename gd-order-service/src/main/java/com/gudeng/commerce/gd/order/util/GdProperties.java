package com.gudeng.commerce.gd.order.util;

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

	/** demo的地址 */
	public String getDemoUrl() {
		return properties.getProperty("gd.demo.url");
	}
	
	public String getPayCenterRefundUrl() {
		return properties.getProperty("gd.payCenterRefundAmtService.url");
	}
	
	public String getPayCenterRefundSearchUrl() {
		return properties.getProperty("gd.payCenterRefundAmtSearchService.url");
	}

	public String getNstCallbackStatusUrl() {
		return properties.getProperty("gd.nstCallbackStatusService.url");
	}
	public String getNstPayPrePaymenOverdueUrl() {
		return properties.getProperty("gd.nstPayPrePaymenOverdue.url");
	}
	
	public String getPayCenterClearUrl(){
		return properties.getProperty("gd.payCenterCleanLogService.url");
	}

	public String getPayCenterKey() {
		return properties.getProperty(properties.getProperty("gd.payCenter.key"));
	}
}
