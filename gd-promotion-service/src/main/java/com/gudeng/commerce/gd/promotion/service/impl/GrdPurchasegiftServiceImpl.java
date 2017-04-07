package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdPurchasegiftService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdPurchasegiftServiceImpl implements GrdPurchasegiftService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdPurchasegiftDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseGiftId", id);
		return baseDao.queryForObject("GrdPurchasegiftEntity.getById", map, GrdPurchasegiftDTO.class);
	}

	@Override
	public List<GrdPurchasegiftDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchasegiftEntity.getList", map, GrdPurchasegiftDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseGiftId", id);
		return baseDao.execute("GrdPurchasegiftEntity.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseGiftId", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdPurchasegiftEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdPurchasegiftDTO t) throws Exception {
		return baseDao.execute("GrdPurchasegiftEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdPurchasegiftEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdPurchasegiftEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdPurchasegiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdPurchasegiftEntity.getListPage", map, GrdPurchasegiftDTO.class);
	}

	@Override
	public Integer insert(GrdPurchasegiftDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}