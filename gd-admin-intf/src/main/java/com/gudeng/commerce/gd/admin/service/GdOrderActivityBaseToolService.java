package com.gudeng.commerce.gd.admin.service;

import java.net.MalformedURLException;

import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;

public interface GdOrderActivityBaseToolService {
	
	/**
	 * 清除活动缓存
	 * @return
	 */
	public boolean deleteCach(Integer type) throws MalformedURLException;
	
	/**
	 * 查询订单违约金信息
	 * @return
	 */
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO) throws Exception ;

}
