package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.service.ActGiftBaseinfoService;

public class ActGiftBaseinfoServiceImpl implements ActGiftBaseinfoService{

	@Resource
	private BaseDao<?> baseDao;

	@Override
	public Long add(ActGiftBaseinfoEntity entity) {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<ActGiftBaseinfoDTO> queryPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActGift.queryPageByCondition", map, ActGiftBaseinfoDTO.class);
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("ActGift.getTotalCountByCondition", map, Integer.class);
	}

	@Override
	public Integer update(ActGiftBaseinfoEntity entity) {
		return baseDao.execute("ActGift.update", entity);
	}

	@Override
	public ActGiftBaseinfoDTO getById(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("ActGift.getById", paramMap, ActGiftBaseinfoDTO.class);
	}

	@Override
	public boolean exist(String name) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		int count = baseDao.queryForObject("ActGift.exist", paramMap, Integer.class);
		return count > 0;
	}

	@Override
	public List<ActGiftBaseinfoDTO> queryListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActGift.queryListByCondition", map, ActGiftBaseinfoDTO.class);
	}

	@Override
	public int delete(ActGiftBaseinfoDTO dto) {
		return baseDao.execute("ActGift.updateIsDeleted", dto);
	}

	@Override
	public Integer sumActivityGiftCost(Integer giftId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("giftId", giftId);
		return baseDao.queryForObject("ReActivityGift.sumActivityGiftCost", paramMap, Integer.class);
	}

	@Override
	public List<ActGiftBaseinfoDTO> getListByCondition(Map<String, Object> map) {
		return baseDao.queryForList("ActGift.queryListByCondition", map, ActGiftBaseinfoDTO.class);
	}

	@Override
	public int updateGiftBaseInfo(Map<String, Object> params) throws Exception {
		return baseDao.execute("ActGift.updateGiftBaseInfo", params);
	}
}
