package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderActRelationToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.service.OrderActRelationService;

public class OrderActRelationToolServiceImpl implements OrderActRelationToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static OrderActRelationService orderActRelationService;

	protected OrderActRelationService getHessianOrderActRelationService() throws MalformedURLException {
		String url = gdProperties.getOrderActRelationServiceUrl();
		if (orderActRelationService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderActRelationService = (OrderActRelationService) factory.create(OrderActRelationService.class, url);
		}
		return orderActRelationService;
	}



	@Override
	public List<OrderActRelationDTO> getByOrderNo(Long orderNo) throws Exception{
		return getHessianOrderActRelationService().getByOrderNo(orderNo);
	}
}