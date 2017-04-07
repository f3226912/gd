package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.m.service.PromInfoToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;
import com.gudeng.commerce.gd.promotion.service.PromActProdInfoService;
import com.gudeng.commerce.gd.promotion.service.prom.PromChainControllerInti;

public class PromInfoToolServiceImpl implements PromInfoToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private PromChainControllerInti promChainControllerInti;
	
	private PromActProdInfoService promActProdInfoService;

	private PromActProdInfoService getHessianPromActProdInfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promActProdInfoService.url");
		if (promActProdInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promActProdInfoService = (PromActProdInfoService) factory.create(PromActProdInfoService.class, hessianUrl);
		}
		return promActProdInfoService;
	}
	
	private PromChainControllerInti getHessianPromInfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promInfoService.url");
		if (promChainControllerInti == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promChainControllerInti = (PromChainControllerInti) factory.create(PromChainControllerInti.class, hessianUrl);
		}
		return promChainControllerInti;
	}

	@Override
	public Map<String, Object> getActProductInfo(Map<String, Object> map) throws Exception {
		return getHessianPromInfoService().execute(map);
	}

	@Override
	public PromOrderProminfoDTO getPromInfoByOrderNo(Long orderNo)
			throws Exception {
		return getHessianPromActProdInfoService().getPromOrderInfoByOrderNo(orderNo);
	}

	@Override
	public CommonFeeAppDTO getIsFeePaidByAct(Integer memberId, Integer sellMemberId,
			Integer actId, CommonFeeAppDTO feeDTO) throws Exception {
		return feeDTO;
	}
}
