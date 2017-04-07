package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.SystemCodeToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.customer.service.SystemCodeService;

public class SystemCodeToolServiceImpl implements SystemCodeToolService {

	@Autowired
	public GdProperties gdProperties;
	public static SystemCodeService systemCodeService;

	protected SystemCodeService getHessianSystemCodeService()
			throws MalformedURLException {
		String url = gdProperties.getSystemCodeUrl();
		if (systemCodeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemCodeService = (SystemCodeService) factory.create(
					SystemCodeService.class, url);
		}
		return systemCodeService;

	}
	
	@Override
	public String showValueByCode(String codeType, String codeKey) throws Exception {
		return getHessianSystemCodeService().showValueByCode(codeType, codeKey);
	}

	@Override
	public List<SystemCode> getListViaType(String type) throws Exception {
		return getHessianSystemCodeService().getListViaType(type);
	}

}
