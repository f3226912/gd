package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;

public class AccBankCardInfoServiceImpl implements AccBankCardInfoService{
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<AccBankCardInfoDTO> getBankCards(Long memberId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", memberId);
		return this.baseDao.queryForList("accBankInfo.getBankCards", params ,AccBankCardInfoDTO.class);
	}

	@Override
	public int addBankCard(AccBankCardInfoDTO accBankCardInfoDTO) {
		return this.baseDao.execute("accBankInfo.addBankCard", accBankCardInfoDTO);
	}

	@Override
	public int updateBankCard(AccBankCardInfoDTO accBankCardInfoDTO) {
		return this.baseDao.execute("accBankInfo.updateBankCard",accBankCardInfoDTO);
			
	}

	@Override
	public  List<AccBankCardInfoDTO> getByCondition(AccBankCardInfoDTO accBankCardInfoDTO) {
		return this.baseDao.queryForList("accBankInfo.getByCondition", accBankCardInfoDTO ,AccBankCardInfoDTO.class);
	}

	@Override
	public Long persistBankCard(AccBankCardInfoEntity entity) throws Exception{
		return this.baseDao.persist(entity, Long.class);
	}

	@Override
	public AccBankCardInfoDTO getBankCardBySearch(Map<String, Object> params) throws Exception {
		return this.baseDao.queryForObject("accBankInfo.getBankCardBySearch", params, AccBankCardInfoDTO.class);
	}

	@Override
	public int updateBankCardAuditStatus(Map<String, Object> params) throws Exception {
		return this.baseDao.execute("accBankInfo.updateBankCard", params);
	}
	
	
}
