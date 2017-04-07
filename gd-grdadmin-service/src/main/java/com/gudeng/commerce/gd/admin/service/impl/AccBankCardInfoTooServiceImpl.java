package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AccBankCardInfoTooService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;

public class AccBankCardInfoTooServiceImpl implements AccBankCardInfoTooService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private static AccBankCardInfoService accBankCardInfoService;
	
	protected AccBankCardInfoService getHessianAccBankCardInfoService() throws MalformedURLException {
		String url = gdProperties.getAccBankCardInfoUrl();
		if (accBankCardInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accBankCardInfoService = (AccBankCardInfoService) factory.create(AccBankCardInfoService.class, url);
		}
		return accBankCardInfoService;
	}
	
	@Override
	public List<AccBankCardInfoEntity> getCardInfoById(Long memberId) throws Exception {
		
		return getHessianAccBankCardInfoService().getBankCards(memberId);
	}


}
