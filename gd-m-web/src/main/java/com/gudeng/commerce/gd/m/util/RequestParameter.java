package com.gudeng.commerce.gd.m.util;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 参数数据工具集
 * @author Administrator
 *
 */
public final class RequestParameter {
	
	private RequestParameter(){		
	}
	
	/**
	 * 获取参数
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(HttpServletRequest request, String key, String defaultValue) {
		
		String value = request.getParameter(key);
		
		if(isEmpty(value)){
			return defaultValue;
		}	
		
		return value;		
	}
	
	/**
	 * 获取参数
	 * @param request
	 * @param key
	 * @param destType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(HttpServletRequest request, String key, Class<T> destType) {		
		
		String value = request.getParameter(key);
		T rs = null;		
		
		if(!isEmpty(value)){
			rs = (T) value;
		}
		
		return rs;		
	}

}
