package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftteamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftteamEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftteamService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdGdGiftteamServiceImpl implements GrdGdGiftteamService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdGdGiftteamDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGdGiftteamEntity.getById", map, GrdGdGiftteamDTO.class);
	}

	@Override
	public List<GrdGdGiftteamDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftteamEntity.getList", map, GrdGdGiftteamDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGdGiftteamEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdGdGiftteamEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdGdGiftteamDTO t) throws Exception {
		return baseDao.execute("GrdGdGiftteamEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftteamEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGdGiftteamEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGdGiftteamDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftteamEntity.getListPage", map, GrdGdGiftteamDTO.class);
	}

	@Override
	public Integer insert(GrdGdGiftteamDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}