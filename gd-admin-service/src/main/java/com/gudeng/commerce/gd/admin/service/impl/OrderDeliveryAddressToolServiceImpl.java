package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderDeliveryAddressToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.service.OrderDeliveryAddressService;

public class OrderDeliveryAddressToolServiceImpl implements OrderDeliveryAddressToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static OrderDeliveryAddressService orderDeliveryAddressService;

	protected OrderDeliveryAddressService getHessianOrderDeliveryAddressService() throws MalformedURLException {
		String url = gdProperties.getOrderDeliveryAddressServiceUrl();
		if (orderDeliveryAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderDeliveryAddressService = (OrderDeliveryAddressService) factory.create(OrderDeliveryAddressService.class, url);
		}
		return orderDeliveryAddressService;
	}

	@Override
	public List<OrderDeliveryAddressDTO> getList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianOrderDeliveryAddressService().getList(map);
	}
	
}