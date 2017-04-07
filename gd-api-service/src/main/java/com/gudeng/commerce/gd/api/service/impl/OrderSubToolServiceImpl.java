package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.service.OrderSubService;
import com.gudeng.commerce.gd.order.service.SubAuditService;

@Service
public class OrderSubToolServiceImpl implements OrderSubToolService{
	
	@Autowired
	public GdProperties gdProperties;
	
	private OrderSubService orderSubService;
	
	private SubAuditService subAuditService;
	
	private OrderSubService getHessianOrderSubService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderSub.url");
		if (orderSubService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderSubService = (OrderSubService) factory.create(OrderSubService.class, hessianUrl);
		}
		return orderSubService;
	}
	
	private SubAuditService getHessianSubAuditService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.subAudit.url");
		if (subAuditService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subAuditService = (SubAuditService) factory.create(SubAuditService.class, hessianUrl);
		}
		return subAuditService;
	}
	
	@Override
	public List<OrderProductDetailDTO> queryProductSubList(
			List<OrderProductDetailDTO> orderProductDetails) throws Exception {
		return getHessianOrderSubService().queryProductSubList(orderProductDetails);
	}
	@Override
	public int getSubListTotal(Map<String, Object> map) throws Exception {
		return getHessianSubAuditService().getSubListTotal(map);
	}
	@Override
	public List<SubAuditDTO> getSubList(Map<String, Object> map) throws Exception {
		return getHessianSubAuditService().getSubList(map);
	}

	@Override
	public List<PushProductDTO> addActivityDetail(List<PushProductDTO> appList)
			throws Exception {
		if (appList == null || appList.isEmpty()){
			return appList;
		}
		List<OrderProductDetailDTO> orderProductList = new ArrayList<OrderProductDetailDTO>();
		for (PushProductDTO appDTO :appList){
			OrderProductDetailDTO detailDTO = new OrderProductDetailDTO();
			detailDTO.setProductId(appDTO.getProductId().intValue());
			orderProductList.add(detailDTO);
		}
		List<OrderProductDetailDTO> resultList = queryProductSubList(orderProductList);
		for (PushProductDTO appDTO :appList){
			for(OrderProductDetailDTO pDTO : resultList){
				if(pDTO.getProductId() == appDTO.getProductId().intValue()){
					appDTO.setHasActivity((Integer.parseInt(pDTO.getHasBuySub())));
				}
			}
		}
		
		return appList;
	}

	@Override
	public List<OrderProductDetailDTO> addSubIntoProduct(
			List<OrderProductDetailDTO> appList) throws Exception {
		if (appList == null || appList.isEmpty()){
			return appList;
		}
		List<OrderProductDetailDTO> resultList = queryProductSubList(appList);
		for (OrderProductDetailDTO appDTO :appList){
			for(OrderProductDetailDTO pDTO : resultList){
				if(pDTO.getProductId() == appDTO.getProductId().intValue()){
					appDTO.setHasBuySub((pDTO.getHasBuySub()));
					appDTO.setHasSellSub(pDTO.getHasSellSub());
				}
			}
		}
		return appList;
	}
}
