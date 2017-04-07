package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ActivityUserintegralDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserintegral;
import com.gudeng.commerce.gd.customer.service.ActivityUserintegralService;
/**
 *
 */
public class ActivityUserintegralServiceImpl implements ActivityUserintegralService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public ActivityUserintegralDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("ActivityUserintegral.getById", map, ActivityUserintegralDTO.class);
	}

	@Override
	public List<ActivityUserintegralDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ActivityUserintegral.getList", map, ActivityUserintegralDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("ActivityUserintegral.deleteById", map);
	}
	
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("ActivityUserintegral.deleteById", batchValues).length;
	}

	@Override
	public int update(ActivityUserintegralDTO t) throws Exception {
		return baseDao.execute("ActivityUserintegral.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("ActivityUserintegral.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(ActivityUserintegralDTO entity) throws Exception {
		return baseDao.execute("ActivityUserintegral.insert", entity);
	}
	
	@Override
	public Long persist(ActivityUserintegral entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ActivityUserintegralDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ActivityUserintegral.getListPage", map, ActivityUserintegralDTO.class);
	}
}