package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.customer.service.ProductCategoryService;

public class ProductCategoryToolServiceImpl implements ProductCategoryToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static ProductCategoryService productCategoryService;

	protected ProductCategoryService getHessianProductCategoryService() throws MalformedURLException {
		String url = gdProperties.getProductCategoryServiceUrl();
		if (productCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(ProductCategoryService.class, url);
		}
		return productCategoryService;
	}


	@Override
	public List<ProductCategoryDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianProductCategoryService().getList(map);
	}


	@Override
	public List<ProductCategoryDTO> getCategoryNameList(Map<String, Object> categoryMap) throws Exception {
		// TODO Auto-generated method stub
		return getHessianProductCategoryService().getCategoryNameList(categoryMap);
	}


	@Override
	public ProductCategoryDTO getCategoryById(Long categoryId) throws Exception {
		return getHessianProductCategoryService().getCategoryById(categoryId);
	}
	
	
}