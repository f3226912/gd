package com.gudeng.commerce.gd.customer.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserIntegralChangeEntity;

public interface ActivityUserIntegralChangeService extends BaseService<ActivityUserIntegralChangeDTO> {
	
	public Long persist(ActivityUserIntegralChangeEntity entity) throws Exception;
	
	public Integer insert(ActivityUserIntegralChangeDTO entity) throws Exception;
	
	public Integer getTotalIntegralByMemberId(Map<String, Object> queryMap) throws Exception;
	
}
