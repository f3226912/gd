package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.service.MarketService;
import com.gudeng.commerce.gd.customer.service.ProductCategoryService;

@Service
public class MarketManageServiceImpl implements  MarketManageService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MarketService marketService;
	
	private static ProductCategoryService productCategoryService;
	/**
	 * 功能描述:街市管理接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ProductCategoryService getHessianProductCategoryService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.customer.productCategory.url");
		if(productCategoryService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(ProductCategoryService.class, url);
		}
		return productCategoryService;
	}
	protected MarketService getHessianMarketService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.market.url");
		if(marketService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			marketService = (MarketService) factory.create(MarketService.class, url);
		}
		return marketService;
	}
	
	@Override
	public MarketDTO getById(String id) throws Exception {
		return getHessianMarketService().getById(id);
	}

	@Override
	public List<MarketDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianMarketService().getByCondition(map);
	}
	
	@Override
	public List<MarketDTO> getAllByStatus(String status) throws Exception {
		
		return  getHessianMarketService().getAllByStatus(status);
	}
	
	@Override
	public List<MarketDTO> getAllByType(String type) throws Exception {
		
		return  getHessianMarketService().getAllByType(type);
	}

	@Override
	public List<MarketDTO> getByName(Map<String, Object> map) throws Exception {
		return   getHessianMarketService().getByName(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMarketService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		 return getHessianMarketService().deleteById(id);
	}

	@Override
	public int addMarketDTO(Map<String,Object> map)throws Exception{
		 return getHessianMarketService().addMarketDTO(map);
	}
	@Override
	public int addMarketDTO(MarketDTO market) throws Exception {
		 return getHessianMarketService().addMarketDTO(market);
	}
	@Override
	public int updateMarketDTO(MarketDTO market) throws Exception {
		 return getHessianMarketService().updateMarketDTO(market);
	}

	@Override
	public MarketDTO getMarketByName(String marketName) throws Exception {
		return getHessianMarketService().getMarketByName(marketName);
	}

	@Override
	public List<MarketDTO> getAllByStatusAndType(String status, String type) throws Exception {
		return getHessianMarketService().getAllByStatusAndType(status, type);
	}

	@Override
	public List<MarketDTO> getAllByTypes(List<String> types) throws Exception {
		return getHessianMarketService().getAllByTypes(types);
	}

	@Override
	public String getCateNamebyMarket(Long marketId) throws Exception {
		return getHessianProductCategoryService().getCateNamebyMarket(marketId);
	}

	@Override
	public int getCountOfMarketList(Map<String, Object> params) throws Exception {
		return getHessianMarketService().getCountOfMarketList(params);
	}
	
	@Override
	public List<MarketDTO> getListOfMarket(Map<String, Object> params) throws Exception {
		return getHessianMarketService().getListOfMarket(params);
	}
	
	@Override
	public int getMarketCountByCondition(Map<String, Object> map)
			throws Exception {
		return getHessianMarketService().getMarketCountByCondition(map);
	}
	@Override
	public List<MarketDTO> getMarketListByCondition(Map<String, Object> params)
			throws Exception {
		return getHessianMarketService().getMarketListByCondition(params);
	}
}
	
