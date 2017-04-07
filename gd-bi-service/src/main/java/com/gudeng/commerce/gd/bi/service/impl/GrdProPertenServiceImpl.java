package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPertenEntity;
import com.gudeng.commerce.gd.bi.service.GrdProPertenService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProPertenServiceImpl implements GrdProPertenService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProPertenDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProPertenEntity.getById", map, GrdProPertenDTO.class);
	}

	@Override
	public List<GrdProPertenDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProPertenEntity.getList", map, GrdProPertenDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProPertenEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdProPertenEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProPertenDTO t) throws Exception {
		return baseDao.execute("GrdProPertenEntity.update", t);
	}
	@Override
	public int batchUpdate(String memberId, List<Map> nstOrderMaps)throws Exception {
		int len = nstOrderMaps.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map entity = nstOrderMaps.get(i);
			entity.put("memberId", memberId);
			entity.put("status", 1);
			batchValues[i] = entity;
		}
		return baseDao.batchUpdate("GrdProPertenEntity.updateStatus", batchValues).length;

	}
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProPertenEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProPertenEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdProPertenDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProPertenEntity.getListPage", map, GrdProPertenDTO.class);
	}

}