package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.commerce.gd.order.service.CarBaseinfoService;

public class CarBaseinfoToolServiceImpl implements CarBaseinfoToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static CarBaseinfoService carBaseinfoService;
	
	public CarBaseinfoService getHessianCarBaseinfoService() throws MalformedURLException{
		if(carBaseinfoService == null){
			String url = gdProperties.getCarBaseinfoServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carBaseinfoService = (CarBaseinfoService) factory.create(CarBaseinfoService.class, url);
		}
		return carBaseinfoService;
	}
	
	@Override
	public CarBaseinfoDTO getByCarNumber(String carNumber) throws Exception {
		return getHessianCarBaseinfoService().getByCarNumber(carNumber);
	}

	@Override
	public Long insertEntity(CarBaseinfoEntity entity) throws Exception{
		return getHessianCarBaseinfoService().insertEntity(entity);
	}

	@Override
	public int update(CarBaseinfoEntity carEntity) throws Exception {
		return getHessianCarBaseinfoService().update(carEntity);	
	}

}
