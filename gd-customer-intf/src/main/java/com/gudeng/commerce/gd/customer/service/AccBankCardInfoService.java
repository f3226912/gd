package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;

public interface AccBankCardInfoService {
	
	public List getBankCards(Long memberId );
	
	public int addBankCard(AccBankCardInfoDTO accBankCardInfoDTO);
	
	public int updateBankCard(AccBankCardInfoDTO accBankCardInfoDTO);
	
	public List<AccBankCardInfoDTO>	getByCondition(AccBankCardInfoDTO accBankCardInfoDTO);
	
	/**
	 *  新增"账户银行卡信息表"(新版)
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public Long persistBankCard(AccBankCardInfoEntity entity) throws Exception;
	
	public AccBankCardInfoDTO getBankCardBySearch(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新绑定银行卡审核状态
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int	updateBankCardAuditStatus(Map<String, Object> params)  throws Exception;
	
}
