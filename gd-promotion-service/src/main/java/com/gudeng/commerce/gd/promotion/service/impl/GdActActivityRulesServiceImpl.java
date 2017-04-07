package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityRulesDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityRulesEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityRulesService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityRulesServiceImpl implements GdActActivityRulesService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GdActActivityRulesDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityRulesEntity.getById", map, GdActActivityRulesDTO.class);
	}

	@Override
	public List<GdActActivityRulesDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityRulesEntity.getList", map, GdActActivityRulesDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityRulesEntity.deleteById", map);
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
		return baseDao.batchUpdate("GdActActivityRulesEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityRulesDTO t) throws Exception {
		return baseDao.execute("GdActActivityRulesEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityRulesEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GdActActivityRulesEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GdActActivityRulesDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityRulesEntity.getListPage", map, GdActActivityRulesDTO.class);
	}

	@Override
	public Integer insert(GdActActivityRulesDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}