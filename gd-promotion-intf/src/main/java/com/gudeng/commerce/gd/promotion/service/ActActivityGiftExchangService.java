package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;

public interface ActActivityGiftExchangService {

	/**
	 * 获取活动礼品兑换记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<ActGiftExchangeApplyDto> getActivityExchangeRecord(Map<String, Object> params) throws Exception;

	/**
	 * 新增活动的礼品兑换记录(即用户申请兑换礼品)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Long insertActivityExchangeRecord(ActGiftExchangeApplyEntity entity) throws Exception;

	public List<ActGiftExchangeApplyDto> queryPageByCondition(Map<String, Object> map);

	public int getTotalCountByCondtion(Map<String, Object> map);

	public List<ActGiftExchangeApplyDto> queryListByCondition(Map<String, Object> map);

	public Long addGiftExchangeRecord(ActGiftExchangeApplyDto dto);
	
	public ActGiftExchangeApplyDto getById(Integer id);

	public int updateGiftExchangeRecord(ActGiftExchangeApplyDto dto);

	public int updateStatus(ActGiftExchangeApplyDto dto);

	/**
	 * 用户兑换过某个活动的礼品次数
	 * @param activity_id
	 * @param userId
	 * @return
	 */
	public int hasExchangeGiftCount(Integer activityId, Long userId);
	
	/**
	 *	查询当前用户在当前活动已使用积分
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int getUserExchangeScore(String activityId, String userid) throws Exception;
}
