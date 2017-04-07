package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityMarketDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityMarketEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityMarketService;
import com.gudeng.framework.dba.util.DalUtils;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityMarketServiceImpl implements GdActActivityMarketService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GdActActivityMarketDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityMarketEntity.getById", map, GdActActivityMarketDTO.class);
	}

	@Override
	public List<GdActActivityMarketDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityMarketEntity.getList", map, GdActActivityMarketDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityMarketEntity.deleteById", map);
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
		return baseDao.batchUpdate("GdActActivityMarketEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityMarketDTO t) throws Exception {
		return baseDao.execute("GdActActivityMarketEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityMarketEntity.getTotal", map, Integer.class);
	}

	@Override
	public void insert(GdActActivityMarketEntity entity) throws Exception {
		Map<String, Object> paramMap = DalUtils.convertToMap(entity);
		baseDao.execute("GdActActivityMarketEntity.insert", paramMap);
	}

	@Override
	public List<GdActActivityMarketDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityMarketEntity.getListPage", map, GdActActivityMarketDTO.class);
	}

	@Override
	public Integer insert(GdActActivityMarketDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}