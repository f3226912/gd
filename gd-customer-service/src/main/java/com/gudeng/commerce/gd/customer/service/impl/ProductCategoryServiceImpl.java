package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.customer.service.ProductCategoryService;


/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private BaseDao<?> baseDao;

	
	@Override
	public List<ProductCategoryDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ProductCategoryEntity.getList", map, ProductCategoryDTO.class);
	}
	@Override
	public List<ProductCategoryDTO> getCategoryNameList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ProductCategoryEntity.getCategoryNameList", map, ProductCategoryDTO.class);
	}
	@Override
	public ProductCategoryDTO getCategoryById(Long categoryId) {
		Map<String,Object> paraMap=new HashMap<>();
		paraMap.put("categoryId", categoryId);
		return (ProductCategoryDTO)baseDao.queryForObject("ProductCategoryEntity.getById", paraMap, ProductCategoryDTO.class);
	}
	@Override
	public String getCateNamebyMarket(Long marketId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("marketId", marketId);
		return baseDao.queryForObject("ProductCategoryEntity.getCateNamebyMarket", map, String.class);
	}}