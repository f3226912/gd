package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.WechatStarsDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatStarsEntity;
import com.gudeng.commerce.gd.promotion.service.WechatStarsService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class WechatStarsServiceImpl implements WechatStarsService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public WechatStarsDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("WechatStarsEntity.getById", map, WechatStarsDTO.class);
	}

	@Override
	public List<WechatStarsDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatStarsEntity.getList", map, WechatStarsDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("WechatStarsEntity.deleteById", map);
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
		return baseDao.batchUpdate("WechatStarsEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(WechatStarsDTO t) throws Exception {
		return baseDao.execute("WechatStarsEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("WechatStarsEntity.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(WechatStarsDTO entity) throws Exception {
		return baseDao.execute("WechatStarsEntity.insert", entity);
	}
	
	@Override
	public Long persist(WechatStarsEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<WechatStarsDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatStarsEntity.getListPage", map, WechatStarsDTO.class);
	}
}