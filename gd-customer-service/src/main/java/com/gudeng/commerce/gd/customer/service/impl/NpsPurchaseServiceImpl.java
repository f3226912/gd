package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;
import com.gudeng.commerce.gd.customer.service.NpsPurchaseService;
@Service
public class NpsPurchaseServiceImpl implements NpsPurchaseService {
	@Autowired
	private BaseDao<NpsPurchaseEntity>  npsPurchaseBaseDao;
	@Autowired
	private BaseDao<?>  baseDao;
	
	@Override
	public Long insert(Map<String, Object> map) throws Exception {
		return npsPurchaseBaseDao.persist(map, Long.class);
	}
	
	public List<NpsPurchaseDTO> getList(Map<String, Object> parasMap)
			throws Exception {
		List<NpsPurchaseDTO> list= baseDao.queryForList("NpsPurchase.getByCondition", parasMap, NpsPurchaseDTO.class);
		return list;
	}
 
	@Override
	public Integer update(NpsPurchaseEntity entity) throws Exception {
		return baseDao.execute("NpsPurchase.updateApp", entity);
	}
	@Override
	public NpsPurchaseDTO getNpsPurchaseById(Map<String, Object> parasMap)
			throws Exception {
		return baseDao.queryForObject("NpsPurchase.getNpsPurchaseById", parasMap, NpsPurchaseDTO.class);
	}



	@Override
	public int getTotal(Map<String, Object> parasMap) throws Exception {
		return (int)baseDao.queryForObject("NpsPurchase.getTotal", parasMap, Integer.class);
	}
	
	public NpsPurchaseDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("NpsPurchase.getById", map, NpsPurchaseDTO.class);
	}


	@Override
	public Integer updateStatus(Map<String, Object> map) throws Exception {
		return baseDao.execute("NpsPurchase.updateStatusApp", map);
	}
	
	@Override
	public Integer delete(Map<String, Object> map) throws Exception {
		return baseDao.execute("NpsPurchase.delete", map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("NpsPurchase.deleteById", map);
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
		return baseDao.batchUpdate("NpsPurchase.deleteById", batchValues).length;
	}

	@Override
	public int update(NpsPurchaseDTO t) throws Exception {
		return baseDao.execute("NpsPurchase.update", t);
	}


	@Override
	public Long insert(NpsPurchaseEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<NpsPurchaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("NpsPurchase.getListPage", map, NpsPurchaseDTO.class);
	}

	@Override
	public NpsOfferPriceDTO getPriceById(int purchaseId) throws Exception {
		Map<String, Object> parasMap = new HashMap<String, Object>();
		parasMap.put("purchaseId", purchaseId);
		return baseDao.queryForObject("NpsPurchase.getPriceById", parasMap, NpsOfferPriceDTO.class);
	}

	@Override
	public int getNoPurchaseListTotal(Map<String, Object> parasMap) throws Exception {
		return (int)baseDao.queryForObject("NpsPurchase.getNoPurchaseListTotal", parasMap, Integer.class);
	}

	@Override
	public List<NpsPurchaseDTO> getNoPurchaseList(Map<String, Object> parasMap)
			throws Exception {
		return baseDao.queryForList("NpsPurchase.getNoPurchaseList", parasMap, NpsPurchaseDTO.class);
	}

	@Override
	public int getPurchaseListTotal(Map<String, Object> parasMap) throws Exception {
		return (int)baseDao.queryForObject("NpsPurchase.getPurchaseListTotal", parasMap, Integer.class);
	}

	@Override
	public List<NpsPurchaseDTO> getPurchaseList(Map<String, Object> parasMap)
			throws Exception {
		return baseDao.queryForList("NpsPurchase.getPurchaseList", parasMap, NpsPurchaseDTO.class);
	}

	@Override
	public int getBackgroundTotal(Map<String, Object> map) {
		return (int)baseDao.queryForObject("NpsPurchase.getBackgroundTotal", map, Integer.class);
	}

	@Override
	public List<NpsPurchaseDTO> getBackgroundList(Map<String, Object> map) {
		return baseDao.queryForList("NpsPurchase.getBackgroundListPage", map, NpsPurchaseDTO.class);
	}

	@Override
	public int getEndPurchaseListTotal(Map<String, Object> parasMap)
			throws Exception {
		return (int)baseDao.queryForObject("NpsPurchase.getEndPurchaseListTotal", parasMap, Integer.class);
	}

	@Override
	public List<NpsPurchaseDTO> getEndPurchaseList(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("NpsPurchase.getEndPurchaseList", map, NpsPurchaseDTO.class);
	}
	
	@Override
	public void updateSeeCount(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		baseDao.execute("NpsPurchase.updateVisitCount", paramMap);
	}
		

}
