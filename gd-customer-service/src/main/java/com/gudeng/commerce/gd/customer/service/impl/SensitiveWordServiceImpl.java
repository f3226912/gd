package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.SensitiveWordDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveWordEntity;
import com.gudeng.commerce.gd.customer.service.SensitiveWordService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class SensitiveWordServiceImpl implements SensitiveWordService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public SensitiveWordDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("SensitiveWordEntity.getById", map, SensitiveWordDTO.class);
	}

	@Override
	public List<SensitiveWordDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("SensitiveWordEntity.getList", map, SensitiveWordDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("SensitiveWordEntity.deleteById", map);
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
		return baseDao.batchUpdate("SensitiveWordEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(SensitiveWordDTO t) throws Exception {
		return baseDao.execute("SensitiveWordEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("SensitiveWordEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(SensitiveWordEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<SensitiveWordDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("SensitiveWordEntity.getListPage", map, SensitiveWordDTO.class);
	}
}