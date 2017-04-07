package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.service.ReActivityGiftService;

public class ReActivityGiftServiceImpl implements ReActivityGiftService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception{
		return baseDao.queryForList("ReActivityGift.getActivityGiftList", params, ActReActivitityGiftDto.class);
	}


	@Override
	public Integer getCostById(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("ReActivityGift.getCostById", paramMap, Integer.class);
	}

	@Override
	public int getActivityScoreRecordCount(Map<String, Object> params) throws Exception {
		return (int) baseDao.queryForObject("ReActivityGift.getActivityScoreRecordCount", params, Integer.class);
	}

	@Override
	public int updateActivityGift(Map<String, Object> params) throws Exception {
		return (int) baseDao.execute("ReActivityGift.updateActivityGift", params);
	}


	@Override
	public List<ActReActivitityGiftDto> queryActivityGiftPage(Map<String, Object> map) {
		return baseDao.queryForList("ReActivityGift.queryActivityGiftPage", map, ActReActivitityGiftDto.class);

	}

	@Override
	public int getActivityGiftTotal(Map<String, Object> map) {
		return baseDao.queryForObject("ReActivityGift.getActivityGiftTotal", map, Integer.class);
	}


	@Override
	public ActReActivitityGiftDto getActivityGift(Integer activityId, Integer giftId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("giftId", giftId);
		return baseDao.queryForObject("ReActivityGift.getActivityGift", map, ActReActivitityGiftDto.class);
	}


	@Override
	public int getActivityUseGiftCount(int giftId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giftId", giftId);
		return baseDao.queryForObject("ReActivityGift.getActivityUseGiftCount", map, Integer.class);
	}


}
