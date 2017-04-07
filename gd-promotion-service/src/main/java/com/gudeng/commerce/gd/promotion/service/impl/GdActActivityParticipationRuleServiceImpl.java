package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityParticipationRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityParticipationRuleEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityParticipationRuleService;
import com.gudeng.framework.dba.util.DalUtils;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityParticipationRuleServiceImpl implements GdActActivityParticipationRuleService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GdActActivityParticipationRuleDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityParticipationRuleEntity.getById", map, GdActActivityParticipationRuleDTO.class);
	}

	@Override
	public List<GdActActivityParticipationRuleDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityParticipationRuleEntity.getList", map, GdActActivityParticipationRuleDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityParticipationRuleEntity.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GdActActivityParticipationRuleEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityParticipationRuleDTO t) throws Exception {
		return baseDao.execute("GdActActivityParticipationRuleEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityParticipationRuleEntity.getTotal", map, Integer.class);
	}

	@Override
	public void insert(GdActActivityParticipationRuleEntity entity) throws Exception {
		Map<String, Object> params = DalUtils.convertToMap(entity);
		baseDao.execute("GdActActivityParticipationRuleEntity.insert", params);
	}

	@Override
	public List<GdActActivityParticipationRuleDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityParticipationRuleEntity.getListPage", map, GdActActivityParticipationRuleDTO.class);
	}

	@Override
	public Integer insert(GdActActivityParticipationRuleDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}