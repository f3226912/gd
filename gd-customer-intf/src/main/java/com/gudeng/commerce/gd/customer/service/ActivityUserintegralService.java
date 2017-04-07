package com.gudeng.commerce.gd.customer.service;

import com.gudeng.commerce.gd.customer.dto.ActivityUserintegralDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserintegral;

/**
 *
 */
public interface ActivityUserintegralService extends BaseService<ActivityUserintegralDTO> {
	public Long persist(ActivityUserintegral entity) throws Exception;
	public Integer insert(ActivityUserintegralDTO entity) throws Exception;
}