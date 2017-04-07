package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.service.PushNstMessageService;
import com.gudeng.commerce.gd.api.service.PushNstMessageApiService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;

public class PushNstMessageApiServiceImpl implements PushNstMessageApiService {
	@Autowired
	public GdProperties gdProperties;
	private static PushNstMessageService pushNstMessageService;
	
	private PushNstMessageService hessianPustNstService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPushNstMessageUrl();
		System.out.println("hessianUrl*************"+hessianUrl);
		if (pushNstMessageService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushNstMessageService = (PushNstMessageService) factory.create(
					PushNstMessageService.class, hessianUrl);
		}
		return pushNstMessageService;
	}
	@Override
	public int setReadStatus(PushNstMessageDTO pushNstMessageDTO)
			throws Exception {
		
		
		return hessianPustNstService().setReadStatus(pushNstMessageDTO);
	}
	@Override
	public int getNewCount(PushNstMessageDTO pushNstMessageDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return hessianPustNstService().getNewCount(pushNstMessageDTO);
	}
	@Override
	public int delNstPush(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return hessianPustNstService().delNstPush(map);
	}

}
