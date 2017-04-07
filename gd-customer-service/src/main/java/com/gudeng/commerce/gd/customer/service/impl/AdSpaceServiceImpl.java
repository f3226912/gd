package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;
import com.gudeng.commerce.gd.customer.service.AdSpaceService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class AdSpaceServiceImpl implements AdSpaceService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public Long persist(AdSpaceEntity entity) {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<AdSpaceDTO> queryByCondition(Map<String, Object> map) {
		return baseDao.queryForList("AdSpace.queryByCondition", map, AdSpaceDTO.class);
	}

	@Override
	public Long countByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("AdSpace.countByCondition", map, Long.class);
	}

	@Override
	public int deleteById(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.execute("AdSpace.deleteById", paramMap);
	}

	@Override
	public AdSpaceDTO getById(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("AdSpace.getById", paramMap, AdSpaceDTO.class);
	}

	@Override
	public int update(AdSpaceEntity entity) {
		return baseDao.execute("AdSpace.update", entity);
	}

	@Override
	public boolean isExist(Long menuId, String spaceSign) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuId", menuId);
		paramMap.put("spaceSign", spaceSign);
		int count = baseDao.queryForObject("AdSpace.isExist", paramMap, Integer.class);
		return count > 0;
	}

	@Override
	public boolean canDelete(Long adSpaceId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adSpaceId", adSpaceId);
		paramMap.put("nowDate", new Date());
		int count = baseDao.queryForObject("AdSpace.canDelete", paramMap, Integer.class);
		return count == 1;
	}

	@Override
	@Transactional
	public int deleteAdSpace(Long id, String updateUserId, String updateUserName) {
		//广告位状态禁用
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("id", id);
		paramMap1.put("state", "2");
		paramMap1.put("updateUserId", updateUserId);
		paramMap1.put("updateUserName", updateUserName);
		int result = baseDao.execute("AdSpace.updateState", paramMap1);
		
		//广告位广告状态停用
		Map<String, Object> paramMap2 = new HashMap<String, Object>(); 
		paramMap2.put("adSpaceId", id);
		paramMap2.put("updateUserId", updateUserId);
		paramMap2.put("updateUserName", updateUserName);
		baseDao.execute("AdAdvertise.deleteByAdSpace", paramMap2);
		return result;
	}

}
