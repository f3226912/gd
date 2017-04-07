package com.gudeng.commerce.gd.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.customer.service.CreditService;

public class CreditServiceImpl implements CreditService{
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long saveFinanceCredit(FinanceCreditEntity creditEntity) throws Exception {
		return baseDao.persist(creditEntity, Long.class);
	}

}
