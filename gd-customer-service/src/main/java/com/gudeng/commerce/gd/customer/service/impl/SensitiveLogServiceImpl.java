package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.SensitiveLogDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveLogEntity;
import com.gudeng.commerce.gd.customer.service.SensitiveLogService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class SensitiveLogServiceImpl implements SensitiveLogService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public SensitiveLogDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("SensitiveLogEntity.getById", map, SensitiveLogDTO.class);
	}

	@Override
	public List<SensitiveLogDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("SensitiveLogEntity.getList", map, SensitiveLogDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("SensitiveLogEntity.deleteById", map);
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
		return baseDao.batchUpdate("SensitiveLogEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(SensitiveLogDTO t) throws Exception {
		return baseDao.execute("SensitiveLogEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("SensitiveLogEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(SensitiveLogEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<SensitiveLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("SensitiveLogEntity.getListPage", map, SensitiveLogDTO.class);
	}

	@Override
	public SensitiveLogDTO replaceAllSensitiveWords(String str) throws Exception {
		return baseDao.queryForObjectByCall(SensitiveLogDTO.class, "pro_sensitiveword", str);
	}
}