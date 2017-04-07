package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;

public interface ReActivityGiftToolService {

	/**
	 * 获取活动-礼品信息
	 * @param params
	 * @return
	 */
	public List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception;

	/**
	 * 查询活动积分获取记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getActivityScoreRecordCount(Map<String, Object> params) throws Exception;

	/**
	 * 更新活动-礼品信息()
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateActivityGift(Map<String, Object> params) throws Exception;

}
