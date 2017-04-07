package com.gudeng.commerce.gd.order.util;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * @Description: TODO(freemarket配置类)
 * @author mpan
 * @date 2015年12月24日 上午8:47:17
 */
public class ConfigurationFactory {

	private volatile static Configuration configuration;
	
	public static Configuration getInstance() throws IOException {
		if (configuration == null) {
			synchronized (ConfigurationFactory.class) {
				if (configuration == null) {
					configuration = new Configuration();
					configuration.setClassForTemplateLoading(ConfigurationFactory.class, "/conf/freemarker/");
					configuration.setDefaultEncoding("UTF-8");	
					configuration.setObjectWrapper(new DefaultObjectWrapper());
				}
			}
		}
		return configuration;
	}

}
