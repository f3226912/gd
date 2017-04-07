package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

public interface PromChainToolService {

	/**
	 * 获取活动产品信息
	 * 
	 * @param productId 产品id
	 * @return PromProdInfoDTO 活动产品信息 
	 */
	public PromProdInfoDTO getPromotionProduct(Long productId) throws Exception;
}