package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityDistributionModeEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityDistributionModeService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityDistributionModeServiceImpl implements GdActActivityDistributionModeService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GdActActivityDistributionModeDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityDistributionModeEntity.getById", map, GdActActivityDistributionModeDTO.class);
	}

	@Override
	public List<GdActActivityDistributionModeDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityDistributionModeEntity.getList", map, GdActActivityDistributionModeDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityDistributionModeEntity.deleteById", map);
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
		return baseDao.batchUpdate("GdActActivityDistributionModeEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityDistributionModeDTO t) throws Exception {
		return baseDao.execute("GdActActivityDistributionModeEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityDistributionModeEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GdActActivityDistributionModeEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GdActActivityDistributionModeDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityDistributionModeEntity.getListPage", map, GdActActivityDistributionModeDTO.class);
	}

	@Override
	public List<GdActActivityDistributionModeDTO> getModeList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityDistributionModeEntity.getModeList", map, GdActActivityDistributionModeDTO.class);
	}

	@Override
	public Integer insert(GdActActivityDistributionModeDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}