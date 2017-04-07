package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.SubHelpToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.service.SubHelpService;

@Service
public class SubHelpToolServiceImpl implements SubHelpToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static SubHelpService subHelpService;
	
	private SubHelpService gethessianSubHelpServiceService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getSubHelpServiceUrl();
		if (subHelpService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subHelpService = (SubHelpService) factory.create(SubHelpService.class, hessianUrl);
		}
		return subHelpService;
	}


	@Override
	public Map<String, Integer> getSubOutMarket(String carNumber, String status,
			Long marketId, Date date) throws Exception {
		return gethessianSubHelpServiceService().getSubOutMarket(carNumber,status,marketId, date);
	}

}
