package com.gudeng.commerce.gd.customer.service;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;

/**
 * 功能描述：PosBankCardEntity增删改查实现类
 *
 */
public interface PosBankCardService {
	/**
	 * 根据银行卡号查找会员id
	 * 
	 * @param bankNo 付款卡号
	 * @return MemberBaseinfoDTO
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getByBankNo(String bankNo) throws Exception;
	
	/**
	 * 增加付款卡号和会员关联记录
	 * 
	 * @param posBankCardDTO
	 * @return
	 * @throws Exception
	 */
	public int addPosBankCardDTO(PosBankCardDTO posBankCardDTO) throws Exception;
	
	/**
	 * 根据会员id查找付款卡号
	 * 
	 * @param memberId 会员id
	 * @return PosBankCardEntity
	 * @throws Exception
	 */
	public PosBankCardEntity getByMemberId(String memberId) throws Exception;

}