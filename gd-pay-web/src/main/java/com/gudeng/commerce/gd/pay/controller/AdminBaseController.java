package com.gudeng.commerce.gd.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gudeng.commerce.gd.pay.util.Des3Request;
import com.gudeng.framework.web.controller.BaseController;

public class AdminBaseController  extends BaseController {
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	public void putModel(String key,Object result){
		HttpServletRequest request = this.getRequest();
		request.setAttribute(key, result);
	}
	
	protected String getParamDecodeStr(HttpServletRequest request) throws Exception{
		return  Des3Request.decode(request.getParameter("param"));
	}
}
