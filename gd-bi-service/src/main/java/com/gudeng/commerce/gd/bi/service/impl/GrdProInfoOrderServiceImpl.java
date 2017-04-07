package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProInfoOrderDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProInfoOrderEntity;
import com.gudeng.commerce.gd.bi.service.GrdProInfoOrderService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProInfoOrderServiceImpl implements GrdProInfoOrderService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProInfoOrderDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProInfoOrderEntity.getById", map, GrdProInfoOrderDTO.class);
	}

	@Override
	public List<GrdProInfoOrderDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProInfoOrderEntity.getList", map, GrdProInfoOrderDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProInfoOrderEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProInfoOrderEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProInfoOrderDTO t) throws Exception {
		return baseDao.execute("GrdProInfoOrderEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProInfoOrderEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProInfoOrderEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProInfoOrderDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProInfoOrderEntity.getListPage", map, GrdProInfoOrderDTO.class);
	}
}