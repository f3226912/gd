package com.gudeng.commerce.gd.m.service.statistic;

/**
 * 浏览量业务service
 * @author TerryZhang
 *
 */
public interface PvStatisticToolService {
	
	/**
	 * 添加商品浏览量
	 * @param productId
	 */
	public void addPvForProduct(Long productId, Integer addPv) throws Exception;

}
