package com.gudeng.commerce.gd.api.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.util.Des3Request;
import com.gudeng.commerce.gd.api.util.Des3Response;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SensitiveWordUtil;
import com.gudeng.commerce.gd.api.util.SpecialCharacterUtil;

/**
 * 农速通接口拦截器
 * 
 * @author xiaojun
 *
 */
public class NstApiInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(NstApiInterceptor.class);
	/** 不用检查的checkUrl */
	private List<String> doNotCheckUrl;
	@Autowired
	private GdProperties gdProperties;

	public List<String> getDoNotCheckUrl() {
		return doNotCheckUrl;
	}

	public void setDoNotCheckUrl(List<String> doNotCheckUrl) {
		this.doNotCheckUrl = doNotCheckUrl;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String param = request.getParameter("param");
		String url = request.getRequestURI();
		if (doNotCheckUrl != null) {
			String str = "";
			for (int i = 0; i < doNotCheckUrl.size(); i++) {
				str = doNotCheckUrl.get(i);
				if (url.indexOf(str) >= 0) {
					logger.info("同城发货不拦截，其他app需要使用");
					return true;
				}
			}
		}
		ObjectResult result = new ObjectResult();
		result.setStatusCode(ErrorCodeEnum.ACCOUNT_IS_DISABLE.getStatusCode());
		String msg="农速通已全新改版升级，期待您的使用，赶快去体验吧！\n<url>" + gdProperties.getGdNstJumpUrl() + "update/appVersion/jump";
		result.setMsg(msg);
		String json = JSONUtils.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
		// 检查param如果存在 在加密返回，不存在在不加密返回
		if (StringUtils.isNotEmpty(param)) {
			response.getWriter().write(Des3Response.encode(json));
		} else {
			response.getWriter().write(json);
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
