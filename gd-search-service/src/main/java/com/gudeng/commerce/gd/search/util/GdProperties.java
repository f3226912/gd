package com.gudeng.commerce.gd.search.util;

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
	public String getSolrProductUrl(){
		return properties.getProperty("gd.search.solrProduct.url");
	}
	public String getSolrBusinessUrl(){
		return properties.getProperty("gd.search.solrBusiness.url");
	}
	
}
