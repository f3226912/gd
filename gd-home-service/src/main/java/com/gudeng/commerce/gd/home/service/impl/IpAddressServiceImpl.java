package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.IpAddressBlackDTO;
import com.gudeng.commerce.gd.customer.dto.IpAddressLogDTO;
import com.gudeng.commerce.gd.customer.service.IpAddressBlackService;
import com.gudeng.commerce.gd.customer.service.IpAddressLogService;
import com.gudeng.commerce.gd.home.service.IpAddressService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class IpAddressServiceImpl implements IpAddressService {
	@Autowired
	public GdProperties gdProperties;
	
	private static IpAddressBlackService ipAddressBlackService;
	
	private static IpAddressLogService ipAddressLogService;

	protected IpAddressBlackService getHessianIpAddressBlackService()
			throws MalformedURLException {
		String url = gdProperties.getIpAddressBlackUrl();
		if (ipAddressBlackService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			ipAddressBlackService = (IpAddressBlackService) factory.create(IpAddressBlackService.class, url);
		}
		return ipAddressBlackService;
	}
	
	
	protected IpAddressLogService getHessianIpAddressLogService() throws MalformedURLException{
		String url = gdProperties.getIpAddressLogUrl();
		if (ipAddressLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			ipAddressLogService = (IpAddressLogService) factory.create(IpAddressLogService.class, url);
		}
		return ipAddressLogService;
	}
	

	@Override
	public int countBlackTotal(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianIpAddressBlackService().countTotal(map);
	}

	@Override
	public int saveBlack(IpAddressBlackDTO addressBlackDTO) throws Exception {
		// TODO Auto-generated method stub
		return getHessianIpAddressBlackService().save(addressBlackDTO);
	}

	@Override
	public int countLogTotal(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianIpAddressLogService().countTotal(map);
	}

	@Override
	public int saveLog(IpAddressLogDTO addressLogDTO) throws Exception {
		// TODO Auto-generated method stub
		return getHessianIpAddressLogService().save(addressLogDTO);
	}

}
