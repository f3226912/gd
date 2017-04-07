package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProCallstatisticsDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProCallstatisticsEntity;
import com.gudeng.commerce.gd.bi.service.GrdProCallstatisticsService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProCallstatisticsServiceImpl implements GrdProCallstatisticsService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProCallstatisticsDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProCallstatisticsEntity.getById", map, GrdProCallstatisticsDTO.class);
	}

	@Override
	public List<GrdProCallstatisticsDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProCallstatisticsEntity.getList", map, GrdProCallstatisticsDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProCallstatisticsEntity.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdProCallstatisticsEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProCallstatisticsDTO t) throws Exception {
		return baseDao.execute("GrdProCallstatisticsEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProCallstatisticsEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProCallstatisticsEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProCallstatisticsDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProCallstatisticsEntity.getListPage", map, GrdProCallstatisticsDTO.class);
	}
}