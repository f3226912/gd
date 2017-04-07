package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.WechatLoginDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatLoginEntity;
import com.gudeng.commerce.gd.promotion.service.WechatLoginService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class WechatLoginServiceImpl implements WechatLoginService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public WechatLoginDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("WechatLoginEntity.getById", map, WechatLoginDTO.class);
	}

	@Override
	public List<WechatLoginDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatLoginEntity.getList", map, WechatLoginDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("WechatLoginEntity.deleteById", map);
	}
	
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("WechatLoginEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(WechatLoginDTO t) throws Exception {
		return baseDao.execute("WechatLoginEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("WechatLoginEntity.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(WechatLoginDTO entity) throws Exception {
		return baseDao.execute("WechatLoginEntity.insert", entity);
	}
	
	@Override
	public Long persist(WechatLoginEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<WechatLoginDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatLoginEntity.getListPage", map, WechatLoginDTO.class);
	}
}