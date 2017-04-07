/**
 * 文件：SpringPropertyHolder.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Feb 1, 2012
 */
package com.gudeng.commerce.gd.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Service;



/**
 * 属性工具类
 * @author steven
 *
 */
public class SpringPropertyHolder  extends PropertyPlaceholderConfigurer {

		private static Map<String, Object> ctxPropertiesMap;

		@Override
		protected void processProperties(ConfigurableListableBeanFactory beanFactory,
				Properties props)throws BeansException {

			super.processProperties(beanFactory, props);
			//load properties to ctxPropertiesMap
			ctxPropertiesMap = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				String keyStr = key.toString();
				String value = props.getProperty(keyStr);
				ctxPropertiesMap.put(keyStr, value);
			}
		}

		//static method for accessing context properties
		public static String getContextProperty(String name) {
			return ctxPropertiesMap.get(name)+"";
		}
	

}



