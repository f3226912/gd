package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProFreightOrderDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProFreightOrderEntity;
import com.gudeng.commerce.gd.bi.service.GrdProFreightOrderService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProFreightOrderServiceImpl implements GrdProFreightOrderService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProFreightOrderDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProFreightOrderEntity.getById", map, GrdProFreightOrderDTO.class);
	}

	@Override
	public List<GrdProFreightOrderDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProFreightOrderEntity.getList", map, GrdProFreightOrderDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProFreightOrderEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProFreightOrderEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProFreightOrderDTO t) throws Exception {
		return baseDao.execute("GrdProFreightOrderEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProFreightOrderEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProFreightOrderEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProFreightOrderDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProFreightOrderEntity.getListPage", map, GrdProFreightOrderDTO.class);
	}
}