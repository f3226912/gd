package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.output.ProductOutputDetailDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;

public interface ProductBaseinfoToolService {
	
	List<ProductOutputDetailDTO> getSupplierProduct(Map<String,Object> param) throws Exception;

	int getSupplierProductTotal(Map<String, Object> param) throws Exception;
	
	List<ProductOutputDetailDTO> getSupplierProductByPrecise(Map<String,Object> param) throws Exception;

	int getSupplierProductTotalByPrecise(Map<String, Object> param) throws Exception;

	int getSupplierProductTotalRecommend(Map<String, Object> param)throws Exception;
	List<ProductOutputDetailDTO> getSupplierProductByRecommend(Map<String,Object> param) throws Exception;

	/**
	 * 获取搜索的精准货源商品列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	PageQueryResultDTO<ProductOutputDetailDTO> getListSearchAccurateProduct(
			Map<String, Object> paramMap) throws Exception;

	/**
	 * 获取搜索的精准货源商铺列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	PageQueryResultDTO<BusinessAppDTO> getListSearchAccurateShop(
			Map<String, Object> paramMap) throws Exception;
	
	/**
	 *  获取商品
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductBaseinfoDTO getProductById(Long productId) throws Exception;

	/**
	 * 获取商品列表
	 * @param productIdList
	 * @return
	 * @throws Exception
	 */
	List<ProductBaseinfoDTO> getProductList(List<Long> productIdList) throws Exception;
	
	/**
	 * 根据商品Id，卖家Id，买家Id，判断是否可以平台配送
	 * 
	 * @param productIdList
	 * @return 0 不可平台配送，1可平台配送
	 * @throws Exception
	 */
	public int checkProductActivity(Long productId,Long customerId,Long sellerId,Long marketId) throws Exception;
}