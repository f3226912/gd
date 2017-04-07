package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;

public interface ActivityToolService {

	public List<ActivityEntity> getByCondition(Map<String, Object> map) throws Exception;
	
	public int getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过Activity对象更新数据库
	 * 
	 * @param GiftDTO
	 * @return int
	 * 
	 */
	public int update(ActivityDTO activityDTO) throws Exception;
	
	public int add(ActivityDTO activityDTO) throws Exception ;
	
	public ActivityDTO getById(String id) throws Exception;
	
	public List<GiftDTO> getByActivityId(String id) throws Exception;
	
	public int getLastEndTimeByStartTime(Map map)throws Exception;
}
