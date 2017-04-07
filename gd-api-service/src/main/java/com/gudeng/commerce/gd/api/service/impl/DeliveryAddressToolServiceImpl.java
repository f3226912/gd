package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.DeliveryAddressToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;

public class DeliveryAddressToolServiceImpl implements DeliveryAddressToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static DeliveryAddressService deliveryAddressService;

	protected DeliveryAddressService getHessianDeliveryAddressService() throws MalformedURLException {
		String url = gdProperties.getDeliveryAddressServiceUrl();
		if (deliveryAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			deliveryAddressService = (DeliveryAddressService) factory.create(DeliveryAddressService.class, url);
		}
		return deliveryAddressService;
	}

	@Override
	public DeliveryAddressDTO getById(String id) throws Exception {
		return getHessianDeliveryAddressService().getById(id);
	}

	@Override
	public List<DeliveryAddressDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianDeliveryAddressService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianDeliveryAddressService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianDeliveryAddressService().deleteBatch(list);
	}

	@Override
	public int update(DeliveryAddressDTO t) throws Exception {
		return getHessianDeliveryAddressService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianDeliveryAddressService().getTotal(map);
	}

	@Override
	public Long insert(DeliveryAddress entity) throws Exception {
		return getHessianDeliveryAddressService().insert(entity);
	}

	@Override
	public List<DeliveryAddressDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianDeliveryAddressService().getListPage(map);
	}
}