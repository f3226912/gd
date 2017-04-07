package com.gudeng.commerce.gd.api.service.impl.v160630;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160630.ReOrderCustomerToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.ReOrderCustomerDTO;
import com.gudeng.commerce.gd.order.service.ReOrderCustomerService;

public class ReOrderCustomerToolServiceImpl implements
		ReOrderCustomerToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private ReOrderCustomerService reOrderCustomerService;
	
	private ReOrderCustomerService getReOrderCustomerService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.reOrderCustomerService.url");
		if (reOrderCustomerService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reOrderCustomerService = (ReOrderCustomerService) factory.create(ReOrderCustomerService.class, hessianUrl);
		}
		return reOrderCustomerService;
	}
	
	@Override
	public Integer addCustomerAndProduct(Map<String, Object> map) throws Exception {
		return getReOrderCustomerService().addEntity(map);
	}

	@Override
	public ReOrderCustomerDTO getByOrderNo(Long orderNo) throws Exception {
		return getReOrderCustomerService().getByOrderNo(orderNo);
	}

}
