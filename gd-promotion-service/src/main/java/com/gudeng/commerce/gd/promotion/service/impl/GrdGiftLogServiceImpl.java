package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftLogService;

public class GrdGiftLogServiceImpl implements GrdGiftLogService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdGiftLogDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGiftLog.getByCondition", map, GrdGiftLogDTO.class);
	}

	@Override
	public List<GrdGiftLogDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftLog.getByCondition", map, GrdGiftLogDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGiftLog.deleteById", map);
	}

	@Override
	public int update(GrdGiftLogDTO t) throws Exception {
		return baseDao.execute("GrdGiftLog.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGiftLog.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGiftLogEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGiftLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftLog.queryByConditionPage", map, GrdGiftLogDTO.class);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer insert(GrdGiftLogDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}