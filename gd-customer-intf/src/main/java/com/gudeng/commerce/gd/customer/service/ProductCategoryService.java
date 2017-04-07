package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.ProductCategoryEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface ProductCategoryService {
	public List<ProductCategoryDTO> getList(Map<String, Object> map) throws Exception;
	public List<ProductCategoryDTO> getCategoryNameList(Map<String, Object> map) throws Exception;
	public ProductCategoryDTO getCategoryById(Long id);
	public String getCateNamebyMarket(Long marketId) throws Exception;
}