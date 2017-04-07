package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.WechatUserinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatUserinfoEntity;
import com.gudeng.commerce.gd.promotion.service.WechatUserinfoService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class WechatUserinfoServiceImpl implements WechatUserinfoService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public WechatUserinfoDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("WechatUserinfoEntity.getById", map, WechatUserinfoDTO.class);
	}

	@Override
	public List<WechatUserinfoDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatUserinfoEntity.getList", map, WechatUserinfoDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("WechatUserinfoEntity.deleteById", map);
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
		return baseDao.batchUpdate("WechatUserinfoEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(WechatUserinfoDTO t) throws Exception {
		return baseDao.execute("WechatUserinfoEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("WechatUserinfoEntity.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(WechatUserinfoDTO entity) throws Exception {
		return baseDao.execute("WechatUserinfoEntity.insert", entity);
	}
	
	@Override
	public Long persist(WechatUserinfoEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<WechatUserinfoDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatUserinfoEntity.getListPage", map, WechatUserinfoDTO.class);
	}
}