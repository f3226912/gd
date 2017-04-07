package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.OrderBillToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.OrderBillDTO;
import com.gudeng.commerce.info.customer.service.OrderBillService;

@Service
public class OrderBillToolServiceImpl implements OrderBillToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static OrderBillService orderBillService;
	
	private OrderBillService gethessianOrderBillService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getOrderBillServiceUrl();
		if (orderBillService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBillService = (OrderBillService) factory.create(OrderBillService.class, hessianUrl);
		}
		return orderBillService;
	}

	@Override
	public Long getTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderBillService().getOrderBillTotal(map);
	}

	@Override
	public List<OrderBillDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianOrderBillService().getOrderBillByCondition(map);
	}

	
}
