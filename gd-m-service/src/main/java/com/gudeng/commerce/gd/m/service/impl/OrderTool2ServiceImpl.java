package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.OrderTool2Service;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;

public class OrderTool2ServiceImpl implements OrderTool2Service {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderTool2ServiceImpl.class);

	private OrderProductDetailService orderProductDetailService;
	
	@Autowired
	public GdProperties gdProperties;
	
	private OrderProductDetailService getHessianOrderProductService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderProductDetail.url");
		if (orderProductDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class, hessianUrl);
		}
		return orderProductDetailService;
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception {
		return getHessianOrderProductService().getListByOrderNoList(orderNoList);
	}

}
