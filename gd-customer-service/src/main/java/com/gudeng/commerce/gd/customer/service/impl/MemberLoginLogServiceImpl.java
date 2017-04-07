package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberLoginLogDTO;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;
import com.gudeng.commerce.gd.customer.service.MemberLoginLogService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class MemberLoginLogServiceImpl implements MemberLoginLogService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MemberLoginLogDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MemberLoginLogEntity.getById", map, MemberLoginLogDTO.class);
	}

	@Override
	public List<MemberLoginLogDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MemberLoginLogEntity.getList", map, MemberLoginLogDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("MemberLoginLogEntity.deleteById", map);
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
		return baseDao.batchUpdate("MemberLoginLogEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(MemberLoginLogDTO t) throws Exception {
		return baseDao.execute("MemberLoginLogEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("MemberLoginLogEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(MemberLoginLogEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MemberLoginLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MemberLoginLogEntity.getListPage", map, MemberLoginLogDTO.class);
	}
}