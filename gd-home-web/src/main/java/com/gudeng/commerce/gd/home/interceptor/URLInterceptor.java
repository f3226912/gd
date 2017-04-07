package com.gudeng.commerce.gd.home.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class URLInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(URLInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String url = request.getRequestURI();
        if (url.contains(".")) {
            if (url.endsWith(".html") || url.endsWith(".jsp") || url.endsWith(".apk") || url.endsWith(".shtml")) {
                return super.preHandle(request, response, handler);
            } else {
                 response.sendRedirect(basePath + "nopage");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
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
