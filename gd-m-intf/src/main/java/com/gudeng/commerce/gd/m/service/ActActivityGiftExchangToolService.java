package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;

public interface ActActivityGiftExchangToolService {

	/**
	 * 获取活动的礼品兑换记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<ActGiftExchangeApplyDto> getActivityExchangeRecord(Map<String, Object> params) throws Exception;

	/**
	 *	新增活动的礼品兑换记录(即用户申请兑换礼品)
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Long insertActivityExchangeRecord(ActGiftExchangeApplyEntity entity) throws Exception;
	/**
	 *	查询当前用户在当前活动已使用积分
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int getUserExchangeScore(String activityId, String userid) throws Exception;
}
