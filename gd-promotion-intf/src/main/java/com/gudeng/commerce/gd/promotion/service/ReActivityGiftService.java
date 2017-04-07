package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;

public interface ReActivityGiftService {

	/**
	 * 获取活动-礼品信息
	 * @param params
	 * @return
	 */
	public List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception;

	/**
	 * 获取预算
	 */
	public Integer getCostById(Integer id);

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
	
	/**
	 * 活动礼品列表分页
	 * @param map
	 * @return
	 */
	public List<ActReActivitityGiftDto> queryActivityGiftPage(Map<String, Object> map);
	
	public int getActivityGiftTotal(Map<String, Object> map);

	/**
	 * 获取某个活动的礼品
	 * @param activityId
	 * @param giftId
	 * @return
	 * @throws Exception
	 */
	public ActReActivitityGiftDto getActivityGift(Integer activityId, Integer giftId);
	
	/**
	 * 还在使用某个礼品的活动数量：礼品兑换结束时间大于当前时间
	 * @param giftId
	 * @return
	 */
	public int getActivityUseGiftCount(int giftId);
}
