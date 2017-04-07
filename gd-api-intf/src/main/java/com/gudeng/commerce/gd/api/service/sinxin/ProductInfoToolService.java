package com.gudeng.commerce.gd.api.service.sinxin;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductSinxinDTO;


public interface ProductInfoToolService {
	
	/**
	 * 商品查询
	 */
	public List<ProductSinxinDTO> queryProduct(ProductSinxinDTO queryDTO) throws Exception;

}