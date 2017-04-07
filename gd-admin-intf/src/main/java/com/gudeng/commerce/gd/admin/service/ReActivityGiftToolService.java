package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;

public interface ReActivityGiftToolService {

	List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception;

	int getCostById(Integer id) throws Exception;

	/**
	 * 活动礼品列表分页
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ActReActivitityGiftDto> queryActivityGiftPage(Map<String, Object> map)throws Exception;

	int getActivityGiftTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取某个活动的礼品
	 * @param activityId
	 * @param giftId
	 * @return
	 * @throws Exception
	 */
	ActReActivitityGiftDto getActivityGift(Integer activityId, Integer giftId) throws Exception;
}
