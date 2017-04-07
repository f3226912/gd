package com.gudeng.commerce.gd.home.service;

import com.gudeng.commerce.gd.customer.entity.PromotionStatisticsEntity;

public interface PromotionManageService {

	/**
	 * 访问渠道统计(新增)
	 * @param entity
	 * @return
	 */
	public Long addPromotionStatistics(PromotionStatisticsEntity entity) throws Exception;
	
}
