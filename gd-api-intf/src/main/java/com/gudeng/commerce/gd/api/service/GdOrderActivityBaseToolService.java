package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;

import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;

public interface GdOrderActivityBaseToolService {
	
	/**
	 * 清除活动缓存
	 * @return
	 */
	public boolean deleteCach(Integer type) throws MalformedURLException;
	
	

	/**
	 * 查询商铺/订单活动信息
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public GdOrderActivityResultDTO queryOrderActivty(GdOrderActivityQueryDTO queryDTO) throws Exception;
	
	
	/**
	 * 查询单个商品配送活动信息
	 * @return
	 */
	public GdProductActivityQueryDTO queryProductAct(GdProductActivityQueryDTO queryDTO) throws Exception ;
	
	

}
