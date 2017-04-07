package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;

public interface PromChainToolService {

	/**
	 * 获取活动产品信息
	 * 
	 * @param productId 产品id
	 * @return PromProdInfoDTO 活动产品信息 
	 */
	public PromProdInfoDTO getPromotionProduct(Long productId) throws Exception;
	
	/**
	 * 获取活动产品详细信息
	 * 
	 * @param productId 产品id
	 * @return PromProdResult 活动信息 
	 */
	public PromProdResult getPromProdResult(Long productId) throws Exception;
}