package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;
import com.gudeng.commerce.gd.promotion.service.ActReUserActivityService;

public class ActReUserActivityServiceImpl implements ActReUserActivityService {

	@Autowired
	private BaseDao<?> baseDao;
//	@Autowired
//	private CacheBo cacheBo;

	@Override
	public ActReUserActivityDto getReUserActivityInfo(String activityId, String userid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("userid", userid);
		return baseDao.queryForObject("ReUserActivity.queryByIdentities", map, ActReUserActivityDto.class);
	}

	@Override
	public int updateReUserActivityInfo(ActReUserActivityDto actReUserActivityDto) throws Exception {
		return (int) baseDao.execute("ReUserActivity.updateReUserActivity", actReUserActivityDto);
	}

	@Override
	public Long insertReUserActivityInfo(ActReUserActivityEntity actReUserActivityEntity) throws Exception {
		return baseDao.persist(actReUserActivityEntity, Long.class);
	}

	@Override
	public ActReUserActivityDto getUserActivityByUserid(
			Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("ReUserActivity.queryUserActivityByUserid", params, ActReUserActivityDto.class);
	}

	@Override
	public List<ActReUserActivityDto> getWechatUserInfoByUserid(
			Map<String, Object> params) throws Exception {
		return baseDao.queryForList("ReUserActivity.getWechatUserInfoByUserid", params, ActReUserActivityDto.class);
	}

	@Override
	public Integer getUserScore(Integer userId, Integer actId) throws Exception {
		Map<String,Object> map=new HashMap<>();
		map.put("userid",userId);
		map.put("activity_id",actId);
		return baseDao.queryForObject("ReUserActivity.getUserScore",map,Integer.class);
	}


}
