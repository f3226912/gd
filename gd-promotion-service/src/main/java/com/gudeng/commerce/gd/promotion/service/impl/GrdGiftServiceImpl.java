package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftService;

public class GrdGiftServiceImpl implements GrdGiftService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public GrdGiftDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGift.getByCondition", map, GrdGiftDTO.class);
	}

	@Override
	public List<GrdGiftDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGift.getByCondition", map, GrdGiftDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGift.deleteById", map);
	}

	@Override
	public int update(GrdGiftDTO t) throws Exception {
		return baseDao.execute("GrdGift.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGift.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGiftEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGift.queryByConditionPage", map, GrdGiftDTO.class);
	}

	@Override
	public List<GrdGiftDTO> getGiftList(String marketId, Map queryMap) throws Exception {
		if(marketId!=null && !marketId.trim().equals("")){
			queryMap.put("marketId", marketId);
		}
		return baseDao.queryForList("GrdGift.getGiftListByMarketId", queryMap, GrdGiftDTO.class);
	}
	
	@Override
	public int getGiftTotal(String marketId) throws Exception {
		Map queryMap=new HashMap();
		if(marketId!=null && !marketId.trim().equals("")){
			queryMap.put("marketId", marketId);
		}
		return (int)baseDao.queryForObject("GrdGift.getGiftTotalByMarketId", queryMap, Integer.class);
	}

	@Override
	public int batchDelete(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdGift.batchDelete", batchValues).length;
	}

	@Override
	public int updateStock(Map<String, Object> mapStock) {
		return baseDao.execute("GrdGift.updateStock", mapStock);
		
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNoCount(String giftId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giftId", giftId);
		return baseDao.queryForObject("GrdGift.getNoCount", map, Integer.class);
	}

	@Override
	public int getGrdGiftRecordCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGift.getGrdGiftRecordCount", map, Integer.class);
	}

	@Override
	public List<GrdGiftDTO> getListPage2(Map queryMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("GrdGift.queryByConditionPage2", queryMap, GrdGiftDTO.class);
	}

	@Override
	public int getTotal2(Map queryMap) throws Exception {
		return baseDao.queryForObject("GrdGift.getTotal2", queryMap, Integer.class);
	}

	@Override
	public Integer insert(GrdGiftDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}