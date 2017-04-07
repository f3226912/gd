package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProSupplyofgoodHandoutDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProSupplyofgoodHandoutEntity;
import com.gudeng.commerce.gd.bi.service.GrdProSupplyofgoodHandoutService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProSupplyofgoodHandoutServiceImpl implements GrdProSupplyofgoodHandoutService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProSupplyofgoodHandoutDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProSupplyofgoodHandoutEntity.getById", map, GrdProSupplyofgoodHandoutDTO.class);
	}

	@Override
	public List<GrdProSupplyofgoodHandoutDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProSupplyofgoodHandoutEntity.getList", map, GrdProSupplyofgoodHandoutDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProSupplyofgoodHandoutEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProSupplyofgoodHandoutEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProSupplyofgoodHandoutDTO t) throws Exception {
		return baseDao.execute("GrdProSupplyofgoodHandoutEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProSupplyofgoodHandoutEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProSupplyofgoodHandoutEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProSupplyofgoodHandoutDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProSupplyofgoodHandoutEntity.getListPage", map, GrdProSupplyofgoodHandoutDTO.class);
	}
}