package com.gudeng.commerce.gd.api.service;

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
public interface ProductCategoryToolService {

	List<ProductCategoryDTO> getList(Map<String, Object> map) throws Exception;

	List<ProductCategoryDTO> getCategoryNameList(Map<String, Object> categoryMap) throws Exception;
	
    ProductCategoryDTO getCategoryById(Long categoryId) throws Exception;
}