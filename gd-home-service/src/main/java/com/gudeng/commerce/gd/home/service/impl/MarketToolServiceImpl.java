package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MarketService;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class MarketToolServiceImpl implements MarketToolService{
	
	@Autowired
	public GdProperties gdProperties;
	
	private static MarketService marketService;

	/**
	 * 功能描述:MarketService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MarketService getHessianMarketService() throws MalformedURLException {
		String url = gdProperties.getMarketServiceUrl();
		if(marketService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketService = (MarketService) factory.create(MarketService.class, url);
		}
		return marketService;
	}
	
	@Override
	public List<MarketDTO> getAllByType(String marketType) throws Exception {
		return getHessianMarketService().getAllByType(marketType);

	}

	@Override
	public List<MarketDTO> getAllByStatusAndType(String status, String type) throws Exception {
		return getHessianMarketService().getAllByStatusAndType(status, type);
	}

	@Override
	public MarketDTO getById(String id) throws Exception {
		return getHessianMarketService().getById(id);
	}
 
	
}
