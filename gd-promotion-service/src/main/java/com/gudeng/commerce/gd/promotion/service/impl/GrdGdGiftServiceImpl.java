package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdGdGiftServiceImpl implements GrdGdGiftService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdGdGiftDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGdGiftEntity.getById", map, GrdGdGiftDTO.class);
	}

	@Override
	public List<GrdGdGiftDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftEntity.getList", map, GrdGdGiftDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGdGiftEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdGdGiftEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdGdGiftDTO t) throws Exception {
		return baseDao.execute("GrdGdGiftEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGdGiftEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGdGiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftEntity.getListPage", map, GrdGdGiftDTO.class);
	}
	
	
	
	@Override
	public int getTotalByPurchase(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftEntity.getTotalByPurchase", map, Integer.class);
	}


	@Override
	public List<GrdGdGiftDTO> getListPageByPurchase(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftEntity.getListPageByPurchase", map, GrdGdGiftDTO.class);
	}

	@Override
	public Integer getMaxId() {
		return baseDao.queryForObject("GrdGdGiftEntity.getMaxId",null, Integer.class);
	}

	@Override
	public Integer insert(GrdGdGiftDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
}