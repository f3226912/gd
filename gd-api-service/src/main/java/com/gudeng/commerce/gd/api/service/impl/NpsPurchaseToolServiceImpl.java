package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NpsPurchaseToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;
import com.gudeng.commerce.gd.customer.service.NpsPurchaseService;

public class NpsPurchaseToolServiceImpl implements NpsPurchaseToolService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(NpsPurchaseToolServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private static NpsPurchaseService npsPurchaseService;
	
	
	protected NpsPurchaseService getHessianNpsPurchaseService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.npsPurchase.url");
		logger.info("npsPurchase url: " + url);
		if (npsPurchaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			npsPurchaseService = (NpsPurchaseService) factory.create(NpsPurchaseService.class,
					url);
		}
		return npsPurchaseService;
	}
	
	@Override
	public List<NpsPurchaseDTO> getList(Map<String, Object> parsMap) throws Exception {
		return getHessianNpsPurchaseService().getList(parsMap);
	}

	@Override
	public NpsPurchaseDTO getNpsPurchaseById(Map<String, Object> parsMap)
			throws Exception {
		return getHessianNpsPurchaseService().getNpsPurchaseById(parsMap);
	}

	@Override
	public int getTotal(Map<String, Object> parsMap) throws Exception {
		return getHessianNpsPurchaseService().getTotal(parsMap);
	}

	@Override
	public NpsOfferPriceDTO getPriceById(int purchaseId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getPriceById(purchaseId);
	}

	@Override
	public Long insert(NpsPurchaseEntity entity) throws Exception {
		return getHessianNpsPurchaseService().insert(entity);
	}

	@Override
	public Integer update(NpsPurchaseEntity entity) throws Exception {
		return getHessianNpsPurchaseService().update( entity);
	}
	@Override
	public Integer updateStatus(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().updateStatus(map);
	}
	@Override
	public Integer delete(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().delete(map);
	}

	@Override
	public int getNoPurchaseListTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getNoPurchaseListTotal(map);
	}

	@Override
	public List<NpsPurchaseDTO> getNoPurchaseList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getNoPurchaseList(map);
	}

	@Override
	public int getPurchaseListTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getPurchaseListTotal(map);
	}

	@Override
	public List<NpsPurchaseDTO> getPurchaseList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getPurchaseList(map);
	}
	
	@Override
	public int getEndPurchaseListTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getEndPurchaseListTotal(map);
	}

	@Override
	public List<NpsPurchaseDTO> getEndPurchaseList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getEndPurchaseList(map);
	}

	@Override
	public void updateSeeCount(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		getHessianNpsPurchaseService().updateSeeCount(paramMap);
	}
}
