package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProOrderRecievedDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProOrderRecievedEntity;
import com.gudeng.commerce.gd.bi.service.GrdProOrderRecievedService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProOrderRecievedServiceImpl implements GrdProOrderRecievedService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProOrderRecievedDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProOrderRecievedEntity.getById", map, GrdProOrderRecievedDTO.class);
	}

	@Override
	public List<GrdProOrderRecievedDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProOrderRecievedEntity.getList", map, GrdProOrderRecievedDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProOrderRecievedEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProOrderRecievedEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProOrderRecievedDTO t) throws Exception {
		return baseDao.execute("GrdProOrderRecievedEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProOrderRecievedEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProOrderRecievedEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProOrderRecievedDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProOrderRecievedEntity.getListPage", map, GrdProOrderRecievedDTO.class);
	}
}