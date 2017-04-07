package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;

public class ProCategoryServiceImpl implements ProCategoryService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static ProductCategoryService productCategoryService;
	
	@Override
	public List<ProductCategoryDTO> getProductCategory() throws Exception{
		return hessianCategoryService().listAllProductCategory();
	}
	
	public List<ProductCategoryDTO> getProductCategoryByMarketId(Long marketId) throws Exception{
		return hessianCategoryService().listProductCategoryByMarketId(marketId);
	}
	
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) throws Exception{
		return hessianCategoryService().listTopProductCategoryByMarketId(marketId);
	}
	
	public ProductCategoryDTO getProductCategoryById(Long id) throws Exception{
		return hessianCategoryService().getProductCategory(id);
	}

	@Override
	public Long persistProductCategory(ProductCategoryEntity dto) throws Exception{
	
		return 	hessianCategoryService().persistProductCategory(dto);
	}

	@Override
	public ProductCategoryEntity updateProductCategory(ProductCategoryEntity productCategoryEntity) throws Exception{

		return hessianCategoryService().updateProductCategory(productCategoryEntity);
	}

	@Override
	public String deleteProductCategory(Long id) throws Exception {
		
		return hessianCategoryService().deleteProductCategory(id);
	}

	private ProductCategoryService hessianCategoryService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProductCategoryUrl();
		if(productCategoryService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(ProductCategoryService.class, hessianUrl);
		}
		return productCategoryService;
	}

}
