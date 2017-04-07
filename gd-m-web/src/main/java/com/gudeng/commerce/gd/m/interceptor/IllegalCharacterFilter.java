package com.gudeng.commerce.gd.m.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 预防XSS攻击，对请求进行脚本等安全过滤，
 * @Project gd-home-web
 * @ClassName IllegalCharacterFilter.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年3月29日 下午12:45:30
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class IllegalCharacterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        request = new MHttpServletRequest(request);
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }

}