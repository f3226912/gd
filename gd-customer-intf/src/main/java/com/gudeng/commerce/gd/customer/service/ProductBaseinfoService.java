package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;


public interface ProductBaseinfoService {
	List<ProductBaseinfoDTO> getSupplierProduct(Map<String,Object> param) throws Exception;
	int getSupplierProductTotal(Map<String, Object> param)throws Exception;
	List<ProductBaseinfoDTO> getSupplierProductByPrecise(Map<String,Object> param) throws Exception;
	int getSupplierProductTotalByPrecise(Map<String, Object> param)throws Exception;
	List<ProductBaseinfoDTO> getSupplierProductByRecommend(Map<String,Object> param) throws Exception;
	int getSupplierProductTotalByRecommend(Map<String, Object> param)throws Exception;
	
	/**
	 * 获取搜索的精准货源商品列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	PageQueryResultDTO<ProductBaseinfoDTO> getListSearchAccurateProduct(
			Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 获取搜索的精准货源商铺列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	PageQueryResultDTO<BusinessBaseinfoDTO> getListSearchAccurateShop(
			Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 获取商品信息 通过各种条件
	 * 用于活动中搜索对应商铺用户的商品
	 * @param paramsMap
	 * @return
	 */
	public PageQueryResultDTO<ProductBaseinfoDTO> getListPage(Map<String, Object> paramsMap);
	
	/**
	 * 获取商品数量 通过各种条件
	 * 用于活动中搜索对应商铺用户的商品
	 * @param paramsMap
	 * @return
	 */
	public Integer getTotal(Map<String, Object> paramsMap);
	
	/**
	 * 获取单个商品
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	ProductBaseinfoDTO getProductById(Long productId) throws Exception;
	/**
	 * 获取商品列表
	 * @param paramMap
	 * @return
	 */
	List<ProductBaseinfoDTO> getProductList(Map<String, Object> paramMap);
}