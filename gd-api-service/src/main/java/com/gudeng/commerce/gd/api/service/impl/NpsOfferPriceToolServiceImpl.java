package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NpsOfferPriceToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;
import com.gudeng.commerce.gd.customer.service.NpsOfferPriceService;

public class NpsOfferPriceToolServiceImpl implements NpsOfferPriceToolService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(NpsOfferPriceToolServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private static NpsOfferPriceService npsOfferPriceService;
	
	
	protected NpsOfferPriceService getHessianNpsPurchaseService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.npsOfferPrice.url");
		logger.info("Market url: " + url);
		if (npsOfferPriceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			npsOfferPriceService = (NpsOfferPriceService) factory.create(NpsOfferPriceService.class,
					url);
		}
		return npsOfferPriceService;
	}


	@Override
	public List<NpsOfferPriceDTO> getList(Map<String, Object> parsMap)
			throws Exception {
		return null;
	}


	@Override
	public Long insert(NpsOfferPriceEntity entity) throws Exception{
		return getHessianNpsPurchaseService().insert(entity);
	}

	
	@Override
	public Integer updateInfo(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().updateInfo(map);
	}


	@Override
	public NpsOfferPriceDTO getOfferPriceId(Map<String, Object> parsMap)
			throws Exception {
		return getHessianNpsPurchaseService().getOfferPriceId(parsMap);
	}


	@Override
	public List<NpsOfferPriceDTO> getOfferPriceList(Map<String, Object> parsMap)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianNpsPurchaseService().getOfferPriceList(parsMap);
	}


	@Override
	public int getOfferPriceTotal(Map<String, Object> parsMap) throws Exception {
		return getHessianNpsPurchaseService().getOfferPriceTotal(parsMap);
	}
	
	@Override
	public int getUserAndOfferPriceCount(NpsOfferPriceEntity entity)
			throws Exception {
		return getHessianNpsPurchaseService().getUserAndOfferPriceCount(entity); 
	}


	
}
