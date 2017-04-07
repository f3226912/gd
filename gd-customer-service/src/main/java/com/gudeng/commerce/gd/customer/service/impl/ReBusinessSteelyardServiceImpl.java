package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessSteelyardEntity;
import com.gudeng.commerce.gd.customer.service.ReBusinessSteelyardService;

@Service
public class ReBusinessSteelyardServiceImpl implements
		ReBusinessSteelyardService {

	@Autowired
	private BaseDao<?> baseDao;
	
	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public Long addReBusinessSteelyardEntity(ReBusinessSteelyardEntity entity)
			throws Exception {
		return (Long)baseDao.persist(entity, Long.class);
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		return (int)baseDao.execute("reBusinessSteelyard.deleteByBusinessId", paramMap);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return (int)baseDao.execute("reBusinessSteelyard.deleteById", paramMap);
	}

	@Override
	public List<ReBusinessSteelyardDTO> getReBusinessSteelyardByBusinessId(
			Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		return baseDao.queryForList("reBusinessSteelyard.queryByBusinessId", paramMap,ReBusinessSteelyardDTO.class);
	}

	@Override
	public Long getByCondition(Map<String, Object> paramMap) throws Exception {
		return baseDao.queryForObject("reBusinessSteelyard.getByCondition", paramMap, Long.class);
	}

}
