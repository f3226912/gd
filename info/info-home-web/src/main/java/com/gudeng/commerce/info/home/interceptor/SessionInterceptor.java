package com.gudeng.commerce.info.home.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.customer.util.PathUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {
    /** 不用检查的checkUrl */
    private List<String> doNotCheckUrl;

    public List<String> getDoNotCheckUrl() {
        return doNotCheckUrl;
    }

    public void setDoNotCheckUrl(List<String> doNotCheckUrl) {
        this.doNotCheckUrl = doNotCheckUrl;
    }

    /**
     * 用户注册
     */

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if (request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER) == null) {
            if (doNotCheckUrl != null) {
                String str = "";
                for (int i = 0; i < doNotCheckUrl.size(); i++) {
                    str = doNotCheckUrl.get(i);
                    if (url.indexOf(str) >= 0) {
                        return super.preHandle(request, response, handler);
                    }
                }
            }
            response.sendRedirect(PathUtil.getBasePath(request));
            return false;
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
