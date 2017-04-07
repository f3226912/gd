package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.m.service.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.service.ProductDeliveryDetailService;


public class ProductDeliveryDetailToolServiceImpl implements ProductDeliveryDetailToolService{

	@Resource
	private GdProperties gdProperties;
	
	private static ProductDeliveryDetailService productDeliveryDetailService;
	
	protected ProductDeliveryDetailService getProductDeliveryDetailHessianService() throws MalformedURLException{
		if(productDeliveryDetailService == null){
			String url = gdProperties.getProductDeliveryDetailServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productDeliveryDetailService = (ProductDeliveryDetailService) factory.create(ProductDeliveryDetailService.class, url);
		}
		return productDeliveryDetailService;
	}
	
	@Override
	public List<ProductDeliveryDetailDTO> getProductByMemberAddressId(
			Long memberAddressId) throws Exception {
		return getProductDeliveryDetailHessianService().getProductByMemberAddressId(memberAddressId);
	}

	@Override
	public List<OrderDeliveryDetailDTO> getOrderByMemberAddressId(
			Long memberAddressId) throws Exception {
		return getProductDeliveryDetailHessianService().getOrderByMemberAddressId(memberAddressId);
	}

	@Override
	public Integer getTypeByMemberAddressId(Long memberAddressId)
			throws Exception {
		return getProductDeliveryDetailHessianService().getTypeByMemberAddressId(memberAddressId);
	}

	@Override
	public List<OrderDeliveryDetailDTO> getOrderByMember(Map<String, Object> params) throws ServiceException {
		
		try{
			return getProductDeliveryDetailHessianService().getOrderByMember(params);			
		}catch(Exception e){			
			throw new ServiceException(e);
		}		
	}

	@Override
	public List<ProductDeliveryDetailDTO> getProductByMember(Map<String, Object> params) throws ServiceException {
		try{
			return getProductDeliveryDetailHessianService().getProductByMember(params);
			//return null;
		}catch(Exception e){			
			throw new ServiceException(e);
		}		
	}
}
