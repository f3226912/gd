package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.AdToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.service.AdAdvertiseService;

public class AdToolServiceImpl implements AdToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private static AdAdvertiseService adAdvertiseService;
	


	/**
	 * 
	 * @param
	 * @return
	 */
	protected AdAdvertiseService getHessianAdAdvertiseService() throws MalformedURLException {
		if(adAdvertiseService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			adAdvertiseService = (AdAdvertiseService) factory.create(AdAdvertiseService.class, gdProperties.getProperties().getProperty("gd.adAdvertiseService.url"));
		}
		return adAdvertiseService;
	}
	
	
	
	@Override
	public List<AdAdvertiseDTO> getAdByMenuId(int menuId) throws Exception {
		return getHessianAdAdvertiseService().getAdByMenuId(menuId);
	}



	@Override
	public List<AdAdvertiseDTO> getAdBySignAndMarketId(Map<String, Object> params) throws Exception {
		return getHessianAdAdvertiseService().getAdBySignAndMarketId(params);
	}



}
