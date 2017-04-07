package com.gudeng.commerce.gd.api.interceptor;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecialCharacterFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(SpecialCharacterFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		request = new MHttpServletRequest(req);
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
