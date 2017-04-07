package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;

import com.gudeng.commerce.gd.api.dto.AppProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;

/**
 * date 2014-02-27
 * 
 * @author Administrator
 * 
 */
public interface ProCategoryService {

	/**
	 * 获得所有分类
	 * 
	 * @return
	 */
	public List<ProductCategoryDTO> getProductCategory(Long marketId) throws Exception;

	/**
	 * 保存产品分类
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long persistProductCategory(ProductCategoryEntity dto)
			throws Exception;

	/**
	 * 修改产品分类
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public ProductCategoryEntity updateProductCategory(ProductCategoryEntity dto)
			throws Exception;

	/**
	 * 删除产品分类
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String deleteProductCategory(Long id) throws Exception;

	/**
	 * 功能描述:查询一级产品分类信息接口
	 * 
	 * @return ProductCategory集合
	 */
	public List<AppProductCategoryDTO> listTopProductCategory(Long userId,Long marketId) throws Exception;

	public List<AppProductCategoryDTO> getChildProductCategory(Long id,
			Long userId,Long marketId)
			throws Exception;

	public List<AppProductCategoryDTO> listTopCateWithSub() throws Exception;

	
	public List<AppProductCategoryDTO> getTotalCateList(Long userId,Long marketId) throws Exception;

	
	/**
	 * 功能描述:根据分类名称查询产品分类信息接口
	 * @param name 分类名称
	 * @return ProductCategory对象
	 */
	public ProductCategoryDTO getProductCategoryByName(String name) throws MalformedURLException;
	
	
	
	public List<ProductCategoryDTO> getListTopProductCategoryByMarketId(Long marketId) throws Exception;
	
}