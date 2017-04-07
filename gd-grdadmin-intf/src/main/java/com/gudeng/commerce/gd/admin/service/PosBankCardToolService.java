package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;

public interface PosBankCardToolService {
	/**
	 * 根据会员id查找付款卡号
	 * 
	 * @param memberId 会员id
	 * @return PosBankCardEntity
	 * @throws Exception
	 */
	public PosBankCardEntity getByMemberId(String memberId) throws Exception;
}
