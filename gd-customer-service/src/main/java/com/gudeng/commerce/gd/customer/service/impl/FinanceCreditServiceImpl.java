package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.customer.service.FinanceCreditService;
/**
 * 贷款信息
 * @author wangkai
 * 2016-07-06
 */

@Service
public class FinanceCreditServiceImpl implements FinanceCreditService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("financeCredit.getTotal", map, Integer.class);
	}

	@Override
	public FinanceCreditEntity getById(Map<String, Object> map) throws Exception {
		FinanceCreditEntity ff= (FinanceCreditEntity)baseDao.queryForObject("financeCredit.getById", map, FinanceCreditEntity.class);
		 return ff;
	}

	@Override
	public List<FinanceCreditEntity> getListByConditionPage(
			Map<String, Object> map) throws Exception {
		List<FinanceCreditEntity> list= baseDao.queryForList("financeCredit.getListByConditionPage", map, FinanceCreditEntity.class);
		return list;
	}

}
