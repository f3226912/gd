package com.gudeng.commerce.gd.order.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.SubAmountDTO;
import com.gudeng.commerce.gd.order.entity.SubAmountEntity;
import com.gudeng.commerce.gd.order.service.SubAmountService;
import com.gudeng.framework.dba.util.DalUtils;

@SuppressWarnings(value={"rawtypes","unchecked"})
@Service
public class SubAmountServiceImpl implements SubAmountService {
	
	
	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	@Transactional
	public int add(SubAmountEntity subAmountEntity) throws Exception {
		int r = -1;
		Map<String, Object> paramUnset = new HashMap<String, Object>();
		paramUnset.put("marketId", subAmountEntity.getMarketId());
		paramUnset.put("updateUserId", subAmountEntity.getUpdateUserId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("subRuleId", subAmountEntity.getSubRuleId());
		paramMap.put("subAmountBal", subAmountEntity.getSubAmountBal());
		paramMap.put("subAmountTotal", subAmountEntity.getSubAmountTotal());
		paramMap.put("marketId", subAmountEntity.getMarketId());
		paramMap.put("marketName", subAmountEntity.getMarketName());
		paramMap.put("isAvailable", subAmountEntity.getIsAvailable());
		paramMap.put("hasSubBalance", subAmountEntity.getHasSubBalance());
		paramMap.put("updateUserId", subAmountEntity.getUpdateUserId());
		paramMap.put("createUserId", subAmountEntity.getCreateUserId());
		
		r = this.baseDao.execute("SubAudit.setUnavailableTotalAmountByMarkeId", paramUnset);
		r = this.baseDao.execute("SubAudit.addTotalAmount", paramMap);
		
		return r;
	}

	@Override
	public SubAmountEntity getByMarketId(Integer marketId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("marketId", marketId);
		
		return (SubAmountEntity)this.baseDao.queryForObject("SubAudit.getSubTotalAmountByMarkeId", paramMap, SubAmountEntity.class);
	}

	@Override
	public int subductAmount(Double amount, Integer marketId, String updateUserId) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("amount", amount);
		paramMap.put("updateUserId", updateUserId);
		paramMap.put("marketId", marketId);
		
		return this.baseDao.execute("SubAudit.subTotalAmountByMarketId", paramMap);
	}

	@Override
	public int subductAmountBySubRuleId(Long subRuleId, Double amount, String updateUserId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("subRuleId", subRuleId);
		paramMap.put("amount", amount);
		paramMap.put("updateUserId", updateUserId);
		return this.baseDao.execute("SubAudit.subTotalAmountBySubRuleId", paramMap);
	}
	
	@Override
	public int[] subductAmountBatch(Map[] paramMap) throws Exception {
		return this.baseDao.batchUpdate("SubAudit.subTotalAmountBySubRuleId", paramMap);
		//return this.baseDao.batchUpdate("SubAudit.subTotalAmountByMarketId", paramMap);
	}

	@Override
	public int setUnavailableByMarketId(Integer marketId, String updateUserId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("marketId", marketId);
		paramMap.put("updateUserId", updateUserId);
		
		
		return this.baseDao.execute("SubAudit.setUnavailableTotalAmountByMarkeId", paramMap);
	}

	@Override
	public Map<Integer, Double> getAllBalAmount() throws Exception {
		Map<Integer, Double> r = new HashMap<Integer, Double>();
		List<Map<String, Object>> list = this.baseDao.queryForList("SubAudit.getBalAmount", null);
		
		for(Map<String, Object> map: list){
			r.put((Integer)map.get("marketId"), ((BigDecimal)map.get("subAmountBal")).doubleValue());
		}
		
		return r;
	}
	
	@Override
	public Map<Long, Double> getAllBalAmountBySubRuleId() throws Exception {
		Map<Long, Double> r = new HashMap<Long, Double>();
		List<Map<String, Object>> list = this.baseDao.queryForList("SubAudit.getBalAmountWithSubRuleId", null);
		
		for(Map<String, Object> map: list){
			r.put((Long)map.get("subRuleId"), ((BigDecimal)map.get("subAmountBal")).doubleValue());
		}
		
		return r;
	}

	@Override
	public int updateSubAmount(SubAmountDTO subAmount) throws ServiceException {
		return baseDao.execute("SubAudit.updateSubAmount", DalUtils.convertToMap(subAmount));
	}

}
