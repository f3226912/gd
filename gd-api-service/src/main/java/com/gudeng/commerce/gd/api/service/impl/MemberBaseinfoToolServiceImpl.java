package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.NpsPurchaseService;

public class MemberBaseinfoToolServiceImpl implements MemberBaseinfoToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberBaseinfoToolServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private static MemberBaseinfoService memberBaseinfoService;
	
	
	protected MemberBaseinfoService getHessianMemberBaseinfoService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.memberBaseinfoService.url");
		if (memberBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class,
					url);
		}
		return memberBaseinfoService;
	}
	
	@Override
	public MemberBaseinfoDTO getById(String memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberBaseinfoService().getAppById(memberId);
	}

}
