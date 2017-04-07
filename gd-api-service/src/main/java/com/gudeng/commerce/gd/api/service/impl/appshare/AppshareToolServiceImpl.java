package com.gudeng.commerce.gd.api.service.impl.appshare;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.appshare.AppshareToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.customer.service.AppshareService;


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
	public int addAppShare(AppshareDTO inputDTO) throws Exception {
		return getHessianAppshareService().addAppShare(inputDTO);
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

	
}
