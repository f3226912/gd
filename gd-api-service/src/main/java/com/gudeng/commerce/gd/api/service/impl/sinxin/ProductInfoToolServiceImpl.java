package com.gudeng.commerce.gd.api.service.impl.sinxin;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.sinxin.ProductInfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.supplier.dto.ProductSinxinDTO;
import com.gudeng.commerce.gd.supplier.service.ProductService;

/**
 * 功能描述：
 */
@Service
public class ProductInfoToolServiceImpl implements ProductInfoToolService {

	/** hessian 接口配置工具 **/
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
	public List<ProductSinxinDTO> queryProduct(ProductSinxinDTO queryDTO) throws Exception {
		List<ProductSinxinDTO> products = hessianProductService().queryProductForSinxin(queryDTO);
		if (CollectionUtils.isEmpty(products)) {
			return null;
		}
		for (ProductSinxinDTO product : products) {
			double price = product.getPrice();
			if ("1".equals(product.getUnit())) { // 吨
				price = MathUtil.div(product.getPrice(), 1000, 5);
			} else if ("4".equals(product.getUnit())) { // 克
				price = MathUtil.mul(product.getPrice(), 1000);
			}
			product.setPrice(price);
//			product.setUnit(null);
		}
		return products;
	}
	
}
