package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.IntegralToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.service.CarsService;
import com.gudeng.commerce.gd.customer.service.IntegralService;

public class IntegralToolServiceImpl implements IntegralToolService {

	@Autowired
	public GdProperties gdProperties;
	
	
	
	private static IntegralService integralService;
	
	
	private IntegralService hessianCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getIntegralServiceUrl();
		if (integralService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			integralService = (IntegralService) factory.create(
					IntegralService.class, hessianUrl);
		}
		return integralService;
	}
	
	@Override
	public List<IntegralDTO> selectIntegralFlow(Long memberId)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianCategoryService().selectIntegralFlow(memberId);
	}

}
