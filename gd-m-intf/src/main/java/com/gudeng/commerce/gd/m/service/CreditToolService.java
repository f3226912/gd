package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年5月25日 上午10:42:27
 */
public interface CreditToolService {
	
	public Long saveFinanceCredit(FinanceCreditEntity creditEntity) throws Exception;

}
