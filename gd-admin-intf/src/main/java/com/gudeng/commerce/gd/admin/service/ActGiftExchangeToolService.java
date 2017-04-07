package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;

public interface ActGiftExchangeToolService {

	List<ActGiftExchangeApplyDto> queryPageByCondition(Map<String, Object> map) throws Exception;
	
	int getTotalCountByCondtion(Map<String, Object> map) throws Exception;

	List<ActGiftExchangeApplyDto> queryListByCondition(Map<String, Object> map) throws Exception;

	Long addGiftExchangeRecord(ActGiftExchangeApplyDto dto) throws Exception;
	
	ActGiftExchangeApplyDto getById(Integer id) throws Exception;

	int updateGiftExchangeRecord(ActGiftExchangeApplyDto dto) throws Exception;

	int updateStatus(ActGiftExchangeApplyDto dto) throws Exception;

	/**
	 * 兑换过某个活动的礼品次数
	 * @param activity_id
	 * @return
	 * @throws Exception
	 */
	int hasExchangeGiftCount(Integer activityId, Long userId) throws Exception;
}
