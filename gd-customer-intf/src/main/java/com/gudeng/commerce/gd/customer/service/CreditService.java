package com.gudeng.commerce.gd.customer.service;

import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年5月25日 上午9:56:23
 */
public interface CreditService {
	
	public Long saveFinanceCredit(FinanceCreditEntity creditEntity) throws Exception;

}
