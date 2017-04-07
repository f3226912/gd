package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;
import com.gudeng.commerce.gd.task.service.NstOrderToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class NstOrderToolServiceImpl implements NstOrderToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static NstOrderBaseinfoService nstOrderBaseinfoService;
	
	private NstOrderBaseinfoService getHessianNstOrderBaseinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getNstOrderBaseinfoUrl();
		if (nstOrderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderBaseinfoService = (NstOrderBaseinfoService) factory.create(NstOrderBaseinfoService.class, hessianUrl);
		}
		return nstOrderBaseinfoService;
	}
	
	@Override
	public int autoComfirmOrder() throws Exception {
		return getHessianNstOrderBaseinfoService().autoComfirmOrder();
	}

	@Override
	public Integer autoCancelOrderBySameCity() throws Exception {
		// TODO Auto-generated method stub
		Integer status=getHessianNstOrderBaseinfoService().autoCancelOrderBySameCity();
		return status;
	}
}
