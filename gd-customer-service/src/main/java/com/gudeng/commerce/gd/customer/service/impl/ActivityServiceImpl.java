package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.service.ActivityService;

public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<ActivityEntity> getByCondition(Map<String, Object> map) {
		return baseDao.queryForList("activity.getByCondition", map, ActivityEntity.class);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return (int) baseDao.queryForObject("activity.getTotal", map, Integer.class);
	}

	@Override
	public int update(ActivityDTO activityDTO) throws Exception {
		return (int) baseDao.execute("activity.updateActivity", activityDTO);
	}
	
	@Override
	public int add(ActivityDTO activityDTO){    

		return (int) baseDao.execute("activity.addActivity", activityDTO);
		
		
		
	}
	
	@Override
	public ActivityDTO getById(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (ActivityDTO) baseDao.queryForObject("activity.getByActivityId", map, ActivityDTO.class);
				
	}
	
	@Override
	public List<GiftDTO> getByActivityId(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return baseDao.queryForList("activity.getActivityId", map, GiftDTO.class);
				
	}
	
	@Override
	public int getLastEndTimeByStartTime(Map map) throws Exception {
		return (int) baseDao.queryForObject("activity.getLastEndTimeByStartTime", map, Integer.class);
	}
}
