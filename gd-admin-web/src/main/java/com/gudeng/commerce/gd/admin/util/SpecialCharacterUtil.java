package com.gudeng.commerce.gd.admin.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gudeng.commerce.gd.admin.service.SpecialcharacterToolService;
import com.gudeng.commerce.gd.customer.dto.SpecialcharacterDTO;


public class SpecialCharacterUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpecialCharacterUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static String stripXss(String value) {
		if(StringUtils.isNotEmpty(value) && !value.matches("\\d+")){
			SpecialcharacterToolService specialcharacterToolService = (SpecialcharacterToolService) SpecialCharacterUtil
					.getBean("specialcharacterToolService");
			StringBuffer allCharacter = new StringBuffer();
			try {
				List<SpecialcharacterDTO> list = specialcharacterToolService.getList(null);
				if (list != null) {
					for (SpecialcharacterDTO dto : list) {
						allCharacter.append(dto.getCharacters()).append("|");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(value)) {
				value = value.replaceAll(allCharacter.toString(), "");
			}
		}
		return value;
	}

	public static void main(String[] args) {
		String value = "123213123weavds";
		value = value.replaceAll("|？|avd|", "");
		System.out.println(value);
	}

}