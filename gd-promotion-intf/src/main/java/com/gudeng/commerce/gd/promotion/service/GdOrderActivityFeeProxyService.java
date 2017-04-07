package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;

/**
 * 订单活动费用对象代理类
 * 
 * @author TerryZhang
 */
public interface GdOrderActivityFeeProxyService {

	public void setBuyerActInfo(GdActivityRedisDTO activityRedisDTO, GdProductActInfoDTO productInfo,
			GdOrderActivityQueryDTO queryDTO) throws Exception;

	public void setSellerActInfo(GdActivityRedisDTO activityRedisDTO, GdProductActInfoDTO productInfo,
			GdOrderActivityQueryDTO queryDTO) throws Exception;

	public void setBusinessActInfo(
			GdActivityRedisDTO activityRedisDTO, 
			GdOrderActivityQueryDTO queryDTO, GdOrderActivityResultDTO resultDTO) throws Exception;
}
