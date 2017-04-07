package com.gudeng.commerce.gd.home.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.home.util.CookieUtil;
import com.gudeng.commerce.gd.home.util.LoginUserUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getQueryString();

		if (filterUrlString(uri)) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			response.sendRedirect(basePath + "login/initLogin");
			return false;
		}
		/*
		 * 判断登录情况和market
		 */
		String marketId = CookieUtil.getValue("marketId");
		if (null == marketId || "".equals(marketId)) {
			Cookie cookie = new Cookie("marketId", "1");
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
			// CookieUtil.addCookie(response, "", Constant.MARKETID, "1",
			// 600000);
		}
		// 如果用户的ID为null，则直接到登录页面;
		if (LoginUserUtil.getLoginUserId(request) == null) {
			String url = request.getRequestURI();
			String str = "userCenter";
			if (url.indexOf(str) >= 0) {
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path + "/";
				response.sendRedirect(basePath + "login/initLogin");
				return false;
			}
			return super.preHandle(request, response, handler);
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 判断是否存在特殊字符
	 * 
	 * @param value
	 * @return
	 */
	public boolean filterUrlString(String uri) {
		if (uri == null) {
			return false;
		}

		if (uri.indexOf(";") > -1) {
			return true;
		}

		if (uri.indexOf("%3C") > -1) {
			return true;
		}

		if (uri.indexOf("%3E") > -1) {
			return true;
		}

		return false;
	}

}
