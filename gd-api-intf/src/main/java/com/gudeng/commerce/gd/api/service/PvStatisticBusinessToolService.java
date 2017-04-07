package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;

/**
 * 商铺浏览量业务
 * @author Ailen
 *
 */
public interface PvStatisticBusinessToolService {
	
	/**
	 * 添加商铺浏览量
	 * @param businessPvStatisDTO
	 */
	public void addPv(BusinessPvStatisDTO businessPvStatisDTO) throws Exception;
	
	/**
	 * 添加商品浏览量
	 * @param productId
	 */
	public void addPvForProduct(Long productId, Integer addPv) throws Exception;

}
