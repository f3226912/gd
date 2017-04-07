package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PosBankCardToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;
import com.gudeng.commerce.gd.customer.service.PosBankCardService;

public class PosBankCardToolServiceImpl implements PosBankCardToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PosBankCardToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private PosBankCardService posBankCardService;

	private PosBankCardService getHessianPosBankCardService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.posBankCardService.url");
		if (posBankCardService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			posBankCardService = (PosBankCardService) factory.create(PosBankCardService.class, hessianUrl);
		}
		return posBankCardService;
	}
	
	/**
	 * 根据会员id查找付款卡号
	 * 
	 * @param memberId 会员id
	 * @return PosBankCardEntity
	 * @throws Exception
	 */
	public PosBankCardEntity getByMemberId(String memberId) throws Exception{
		return getHessianPosBankCardService().getByMemberId(memberId);
	}
}
