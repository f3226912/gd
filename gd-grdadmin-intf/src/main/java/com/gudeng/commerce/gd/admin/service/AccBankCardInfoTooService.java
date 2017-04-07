package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;

public interface AccBankCardInfoTooService {
	/**
	 * 获取账户下的银行卡信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<AccBankCardInfoEntity> getCardInfoById(Long memberId) throws Exception;
}
