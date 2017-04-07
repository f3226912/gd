package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MqAsyncErrorDTO;
import com.gudeng.commerce.gd.customer.entity.MqAsyncErrorEntity;
import com.gudeng.commerce.gd.customer.service.MqAsyncErrorService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class MqAsyncErrorServiceImpl implements MqAsyncErrorService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MqAsyncErrorDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MqAsyncErrorEntity.getById", map, MqAsyncErrorDTO.class);
	}

	@Override
	public List<MqAsyncErrorDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MqAsyncErrorEntity.getList", map, MqAsyncErrorDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("MqAsyncErrorEntity.deleteById", map);
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
		return baseDao.batchUpdate("MqAsyncErrorEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(MqAsyncErrorDTO t) throws Exception {
		return baseDao.execute("MqAsyncErrorEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("MqAsyncErrorEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(MqAsyncErrorEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MqAsyncErrorDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MqAsyncErrorEntity.getListPage", map, MqAsyncErrorDTO.class);
	}
}