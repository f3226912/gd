package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.ProBaiduToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.service.ProBaiduService;

public class ProBaiduToolServiceImpl implements ProBaiduToolService{

	private static ProBaiduService proBaiduService;
	
	@Autowired
	private GdProperties gdProperties;
	
	protected ProBaiduService getProBaiduHessianService() throws MalformedURLException{
		if(proBaiduService == null){
			String hessianUrl = gdProperties.getProBaiduServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			proBaiduService = (ProBaiduService) factory.create(ProBaiduService.class, hessianUrl);
		}
		return proBaiduService;
	}
	
	@Override
	public List<ProBaiduEntityDTO> sumReport(Map<String, Object> map) throws Exception {
		return getProBaiduHessianService().sumReport(map);
	}

}
