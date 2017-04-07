package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.service.MarketService;
import com.gudeng.commerce.gd.m.service.MarketToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

public class MarketToolServiceImpl implements MarketToolService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MarketToolServiceImpl.class);
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static MarketService marketService;

	protected MarketService getHessianMarketService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.market.url");
		logger.info("Market url: " + url);
		if (marketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketService = (MarketService) factory.create(MarketService.class,
					url);
		}
		return marketService;
	}

	
	
	@Override
	public List<MarketDTO> getMarketList(Map<String, Object> paramsMap) throws Exception {
		return this.getHessianMarketService().getMarketList(paramsMap);
	}
}
