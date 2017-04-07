package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.OrderoutmarketinfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;

public class OrderoutmarketinfoToolServiceImpl implements OrderoutmarketinfoToolService {

	@Autowired
	private GdProperties gdProperties;
	
	private static OrderBaseinfoService orderBaseinfoService;
	
	private static OrderOutmarketinfoService orderOutmarketinfoService;
	
	protected OrderBaseinfoService getOrderBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if(orderBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, url);
		}
		return orderBaseinfoService;
	}
	
	protected OrderOutmarketinfoService getOrderOutmarketinfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderOutmarketinfoService.url");
		if(orderOutmarketinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderOutmarketinfoService = (OrderOutmarketinfoService) factory.create(OrderOutmarketinfoService.class, url);
		}
		return orderOutmarketinfoService;
	}
	
	@Override
	public List<OrderBaseinfoDTO> getOderInfoList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception {
		return getOrderBaseinfoService().getOderInfoList(orderBaseinfoDTO);
	}

	@Override
	public int insertOrderOutmark(String orderNoList, String omId) throws Exception {
		return getOrderOutmarketinfoService().insertOrderOutmark(orderNoList, omId);
	}

	@Override
	public Long insert(OrderOutmarketinfoEntity orderOutmarketinfoEntity) throws Exception {
		return getOrderOutmarketinfoService().insertEntity(orderOutmarketinfoEntity);
	}

	@Override
	public Long shipperOutMarket(OrderOutmarketinfoEntity outmarketEntity,List<String> productIdList) throws Exception {
		return getOrderOutmarketinfoService().shipperOutMarket(outmarketEntity, productIdList);
	}

	@Override
	public int updateCarNumberImage(OrderOutmarketinfoEntity entity) throws Exception {
		return getOrderOutmarketinfoService().updateCarNumberImage(entity);
	}

	@Override
	public Long purchaserOutmarket(OrderOutmarketinfoEntity orderOutmarketEntity,List<String> orderNoList) throws Exception {
		return getOrderOutmarketinfoService().purchaserOutMarket(orderOutmarketEntity, orderNoList);
	}
	
	@Override
	public Long purchaserOutmarketV2(OrderOutmarketinfoEntity orderOutmarketEntity,List<OrderBaseinfoDTO> orderList) throws Exception {
		return getOrderOutmarketinfoService().purchaserOutMarketV2(orderOutmarketEntity, orderList);
	}

	@Override
	public List<OrderOutmarketInfoDTO> getPageByCreateUserId(Map<String, Object> query) throws Exception{
		return getOrderOutmarketinfoService().getPageByCreateUserId(query);
	}

	@Override
	public Long getTotalCountByCreateUserId(Map<String, Object> query) throws Exception{
		return getOrderOutmarketinfoService().getTotalCountByCreateUserId(query);
	}

	@Override
	public List<OrderBaseinfoDTO> getOutMarketOrderList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception{
		return getOrderBaseinfoService().getOutmarketOrderList(orderBaseinfoDTO);
	}

}
