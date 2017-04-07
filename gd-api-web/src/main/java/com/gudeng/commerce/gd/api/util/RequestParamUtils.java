package com.gudeng.commerce.gd.api.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class RequestParamUtils {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(RequestParamUtils.class);
	
	/**
	 * 根据request参数获取Integer值
	 * @param request 
	 * @param paramName 参数名
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Integer getIntegerValue(HttpServletRequest request, 
			String paramName, Integer defaultValue){
		try{
			String paramValue = request.getParameter(paramName);
			if(StringUtils.isBlank(paramValue)){
				return defaultValue;
			}
			
			Integer value = Integer.parseInt(paramValue);
			return value;
		}catch(Exception e){
			logger.warn("[ERROR]Get Integer value error:", e);
			return defaultValue;
		}
	}
	
	/**
	 * 根据request参数获取Long值
	 * @param request 
	 * @param paramName 参数名
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Long getLongValue(HttpServletRequest request, 
			String paramName, Long defaultValue){
		try{
			String paramValue = request.getParameter(paramName);
			if(StringUtils.isBlank(paramValue)){
				return defaultValue;
			}
			
			Long value = Long.parseLong(paramValue);
			return value;
		}catch(Exception e){
			logger.warn("[ERROR]Get Long value error:", e);
			return defaultValue;
		}
	}
}
