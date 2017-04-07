package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ActivityToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.service.ActivityService;

public class ActivityToolServiceImpl implements ActivityToolService{

	@Autowired
	public GdProperties gdProperties;
	
	public static ActivityService activityService;
	
	public ActivityService getActivityHessianService() throws MalformedURLException{
		String url = gdProperties.getActivityServiceUrl();
		if(activityService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			activityService = (ActivityService) factory.create(ActivityService.class, url);
		}
		return activityService;
	}
	
	@Override
	public List<ActivityEntity> getByCondition(Map<String, Object> map) throws Exception {
		return getActivityHessianService().getByCondition(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getActivityHessianService().getTotal(map);
	}

	@Override
	public int update(ActivityDTO activityDTO) throws Exception {
		return getActivityHessianService().update(activityDTO);
	}
	
	@Override
	public int add(ActivityDTO activityDTO) throws Exception {
		return getActivityHessianService().add(activityDTO);
	}
	
	@Override
	public ActivityDTO getById(String id) throws Exception {
		return getActivityHessianService().getById(id);
	}
	
	@Override
	public List<GiftDTO> getByActivityId(String id) throws Exception {
		return getActivityHessianService().getByActivityId(id);
	}
	
	@Override
	public int getLastEndTimeByStartTime(Map map) throws Exception {
		return getActivityHessianService().getLastEndTimeByStartTime(map);
	}
}
