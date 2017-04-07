package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProPersonalAuthDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPersonalAuthEntity;
import com.gudeng.commerce.gd.bi.service.GrdProPersonalAuthService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProPersonalAuthServiceImpl implements GrdProPersonalAuthService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProPersonalAuthDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProPersonalAuthEntity.getById", map, GrdProPersonalAuthDTO.class);
	}

	@Override
	public List<GrdProPersonalAuthDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProPersonalAuthEntity.getList", map, GrdProPersonalAuthDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProPersonalAuthEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProPersonalAuthEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProPersonalAuthDTO t) throws Exception {
		return baseDao.execute("GrdProPersonalAuthEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProPersonalAuthEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProPersonalAuthEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProPersonalAuthDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProPersonalAuthEntity.getListPage", map, GrdProPersonalAuthDTO.class);
	}
}