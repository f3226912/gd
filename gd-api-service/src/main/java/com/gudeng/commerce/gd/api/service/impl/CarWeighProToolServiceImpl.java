package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.CarWeighProToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.service.CarWeighProService;

@Service
public class CarWeighProToolServiceImpl implements CarWeighProToolService{
	
	@Autowired
	private GdProperties gdProperties;

	public static CarWeighProService carWeighProService;
	
	public CarWeighProService getHessianCarWeighProService() throws MalformedURLException{
		String url = gdProperties.getCarWeighProUrl();
		if(carWeighProService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carWeighProService = (CarWeighProService) factory.create(CarWeighProService.class, url);
		}
		return carWeighProService;
	}
	
	@Override
	public List<CarWeighProDTO> getValidCarWeighProList(Map<String, Object> map) throws Exception{
		map.put("status", 1);
		return getHessianCarWeighProService().getCarWeighProList(map);
	}

}
