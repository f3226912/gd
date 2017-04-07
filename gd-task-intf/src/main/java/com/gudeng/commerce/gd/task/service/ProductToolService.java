package com.gudeng.commerce.gd.task.service;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public interface ProductToolService {
	/**
	 * 根据产品id列表查找产品信息
	 * @param pIdList
	 * @return
	 * @throws Exception
	 */
	public List<ProductDto> getListByIds(List<Long> pIdList) throws Exception;

	/**
	 * 查询商品信息
	 */
	public ProductDto getById(Long productId) throws Exception;
	
}
