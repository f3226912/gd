package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AppactivitystatDTO;
import com.gudeng.commerce.gd.customer.entity.AppactivitystatEntity;
import com.gudeng.commerce.gd.customer.service.AppactivitystatService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class AppactivitystatServiceImpl implements AppactivitystatService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public AppactivitystatDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("AppactivitystatEntity.getById", map, AppactivitystatDTO.class);
	}

	@Override
	public List<AppactivitystatDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("AppactivitystatEntity.getList", map, AppactivitystatDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("AppactivitystatEntity.deleteById", map);
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
		return baseDao.batchUpdate("AppactivitystatEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(AppactivitystatDTO t) throws Exception {
		return baseDao.execute("AppactivitystatEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("AppactivitystatEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(AppactivitystatEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<AppactivitystatDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("AppactivitystatEntity.getListPage", map, AppactivitystatDTO.class);
	}
}