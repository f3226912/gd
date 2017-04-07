package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstOrderNoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.service.NstOrderNoService;

public class NstOrderNoToolServiceImpl implements NstOrderNoToolService{

	@Autowired
	private GdProperties gdProperties;
	
	public static NstOrderNoService nstOrderNoService;
	
	public NstOrderNoService getHessianNstOrderNoService() throws MalformedURLException{
		if(nstOrderNoService == null){
			String hessinUrl = gdProperties.getNstOrderNoServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderNoService = (NstOrderNoService)factory.create(NstOrderNoService.class, hessinUrl);
		}
		return nstOrderNoService;
	}
	
	@Override
	public String getNstOrderNo(String provinceAreaId) throws Exception {
		return getHessianNstOrderNoService().getNstOrderNo(provinceAreaId);
	}

}
