package com.gudeng.commerce.gd.m.service;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
/**
 * date 2014-02-27
 * @author Administrator
 *
 */
public interface ProCategoryService {
	
	/**
	 * 获得所有分类
	 * @return
	 */
	public List<ProductCategoryDTO> getProductCategory() throws Exception;
	
	/**
	 * 根据市场id获得所有分类
	 * @return
	 */
	public List<ProductCategoryDTO> getProductCategoryByMarketId(Long marketId) throws Exception;
	
	
	/**
	 * 功能描述:根据农批市场查询一级产品分类信息接口
	 * @return ProductCategory集合
	 */
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) throws Exception;
		
	
	/**
	 * 获得单个分类
	 * @return
	 */
	public ProductCategoryDTO getProductCategoryById(Long id) throws Exception;
	/**
	 * 保存产品分类
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long persistProductCategory(ProductCategoryEntity dto) throws Exception;
	/**
	 * 修改产品分类
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public ProductCategoryEntity updateProductCategory (ProductCategoryEntity dto) throws Exception;
	/**
	 * 删除产品分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String deleteProductCategory (Long id) throws Exception;
}