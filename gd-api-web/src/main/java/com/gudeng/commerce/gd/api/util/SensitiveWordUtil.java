package com.gudeng.commerce.gd.api.util;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gudeng.commerce.gd.api.service.SensitiveLogToolService;
import com.gudeng.commerce.gd.customer.dto.SensitiveLogDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveLogEntity;
import com.gudeng.framework.ws.utils.StringUtil;


public class SensitiveWordUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SensitiveWordUtil.applicationContext = applicationContext;
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
		SensitiveLogToolService service = (SensitiveLogToolService) SensitiveWordUtil
				.getBean("sensitiveLogToolService");
		try {
//			SensitiveLogDTO dto = service.replaceAllSensitiveWords("在习近平领导的共产党政权下,共党组织得到很好发展");
			if(StringUtils.isNotEmpty(value) && !value.matches("\\d+") && value.length() < 10000){
				SensitiveLogDTO dto = service.replaceAllSensitiveWords(value);
//				System.out.println(value+"......................................");
				if(dto!=null && dto.getCnt()!=null && dto.getCnt().intValue()>0){
					SensitiveLogEntity entity=new SensitiveLogEntity();
//					System.out.println(dto.getCnt()+"  数量......................................");
					entity.setWords(dto.getWords());
					entity.setType(1);
					entity.setCreateTime(new Date());
					service.insert(entity);
					return dto.getStrs();
				}else return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}


}