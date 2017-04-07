package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.commerce.gd.task.service.ProductToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

@Service
public class ProductToolServiceImpl implements ProductToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private static ProductService productService;
	
	private ProductService hessianProductService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.productTool.url");
		if (productService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productService = (ProductService) factory.create(ProductService.class, hessianUrl);
		}
		return productService;
	}
	
	@Override
	public List<ProductDto> getListByIds(List<Long> productIdList)
			throws Exception {
		return hessianProductService().getListByIds(productIdList);
	}

	@Override
	public ProductDto getById(Long productId) throws Exception {
		return hessianProductService().getById(productId.toString());
	}
}
