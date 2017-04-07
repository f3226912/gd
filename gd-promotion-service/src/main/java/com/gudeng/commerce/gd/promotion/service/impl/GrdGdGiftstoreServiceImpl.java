package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftInStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftOutStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftstoreEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftstoreService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdGdGiftstoreServiceImpl implements GrdGdGiftstoreService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdGdGiftstoreDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getById", map, GrdGdGiftstoreDTO.class);
	}

	@Override
	public List<GrdGdGiftstoreDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getList", map, GrdGdGiftstoreDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGdGiftstoreEntity.deleteById", map);
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
		return baseDao.batchUpdate("GrdGdGiftstoreEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdGdGiftstoreDTO t) throws Exception {
		return baseDao.execute("GrdGdGiftstoreEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGdGiftstoreEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGdGiftstoreDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getListPage", map, GrdGdGiftstoreDTO.class);
	}
	
	@Override
	public List<GrdGdGiftstoreDTO> getStoreByUserAndType(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getStoreByUserAndType", map, GrdGdGiftstoreDTO.class);
	}

	@Override
	public List<GrdGdGiftInStorageDataDTO> getGiftInStorageData(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getGiftInStorageData", map, GrdGdGiftInStorageDataDTO.class);
	}
	
	@Override
	public Integer getGiftInStorageDataCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getGiftInStorageDataCount", map, Integer.class);
	}

	@Override
	public List<GrdGdGiftOutStorageDataDTO> getGiftOutStorageData(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getGiftOutStorageData", map, GrdGdGiftOutStorageDataDTO.class);
	}

	@Override
	public Integer getGiftOutStorageDataCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getGiftOutStorageDataCount", map, Integer.class);
	}

	@Override
	public List<GrdGdGiftDataDTO> getGrdGdGiftData(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGdGiftstoreEntity.getGrdGdGiftData", map, GrdGdGiftDataDTO.class);

	}

	@Override
	public Integer getGrdGdGiftDataCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getGrdGdGiftDataCount", map, Integer.class);
	}

	@Override
	public Integer getGrdGdGiftDataSum(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGdGiftstoreEntity.getGrdGdGiftDataSum", map, Integer.class);
	}

	@Override
	public Integer insert(GrdGdGiftstoreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}