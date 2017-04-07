package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.customer.service.AppshareService;
import com.gudeng.commerce.gd.m.service.AppshareToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;


public class AppshareToolServiceImpl implements AppshareToolService {
	
	@Autowired
	public GdProperties gdProperties;
	
	private static AppshareService appshareService;
	
	public AppshareService getHessianAppshareService() throws MalformedURLException{
		if(appshareService == null){
			String url = gdProperties.getAppshareServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			appshareService = (AppshareService) factory.create(AppshareService.class, url);
		}
		return appshareService;
	}

	@Override
	public AppshareDTO getAppShareByCondition(AppshareDTO inputDTO)
			throws Exception {
		return getHessianAppshareService().getAppShareByCondition(inputDTO);
	}

	@Override
	public int getVisitorIpCount(AppshareDTO inputDTO) throws Exception {
		return getHessianAppshareService().getVisitorIpCount(inputDTO);
	}

	@Override
	public int updateAppShareViewCount(AppshareDTO inputDTO) throws Exception {
		return getHessianAppshareService().updateAppShareViewCount(inputDTO);
	}

	@Override
	public int updateAppShareAndVisitorIp(AppshareDTO inputDTO)
			throws Exception {
		return getHessianAppshareService().updateAppShareAndVisitorIp(inputDTO);
	}
}
