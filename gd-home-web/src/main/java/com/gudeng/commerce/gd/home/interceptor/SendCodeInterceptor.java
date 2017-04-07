package com.gudeng.commerce.gd.home.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.home.util.CookieUtil;

/**
 * 发送验证码过滤器
 * 
 * @author lidong
 *
 */
public class SendCodeInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(SendCodeInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String value = CookieUtil.getValue("marketId");
		if (!StringUtils.isEmpty(value)) {
			return super.preHandle(request, response, handler);
		} else {
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
