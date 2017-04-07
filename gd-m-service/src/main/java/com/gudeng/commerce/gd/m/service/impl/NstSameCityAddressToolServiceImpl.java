package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.commerce.gd.customer.service.NstSameCityAddressService;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.m.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * 功能描述：【同城】收发货地址管理
 */
@Service
public class NstSameCityAddressToolServiceImpl implements NstSameCityAddressToolService {

	@Autowired
	public GdProperties gdProperties;

	private static NstSameCityAddressService sameCityAddressService;

	private NstSameCityAddressService getHessianService() throws MalformedURLException {

		String hessianUrl = gdProperties.getNstSameCityAddressServiceUrl();

		if (sameCityAddressService == null) {

			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sameCityAddressService = (NstSameCityAddressService) factory.create(NstSameCityAddressService.class, hessianUrl);
		}
		return sameCityAddressService;
	}

	@Override
	public Integer delete(NstSameCityAddressEntity entity) throws ServiceException {

		try {		
			
			return getHessianService().deleteMemberNSCA(entity);
			
		} catch (Exception e) {
			
			throw new ServiceException("【同城】【收发地管理】【删除】【RPC调用】",e);
		}
	}
}
