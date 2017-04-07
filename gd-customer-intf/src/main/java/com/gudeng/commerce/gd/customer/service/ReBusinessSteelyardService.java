package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessSteelyardEntity;

public interface ReBusinessSteelyardService {

public Long addReBusinessSteelyardEntity(ReBusinessSteelyardEntity entity) throws Exception;
	
	public int deleteByBusinessId(Long businessId) throws Exception;
	
	public int deleteById(Long id) throws Exception; 
	
	public List<ReBusinessSteelyardDTO> getReBusinessSteelyardByBusinessId(Long businessId) throws Exception;

	public Long getByCondition(Map<String, Object> paramMap) throws Exception;
}
