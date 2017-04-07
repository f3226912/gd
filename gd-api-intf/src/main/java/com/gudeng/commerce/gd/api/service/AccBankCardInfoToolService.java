package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;

public interface AccBankCardInfoToolService {

	public List getBankCards(Long memberId)  throws MalformedURLException;
	public int	addBankCard(AccBankCardInfoDTO accBankCardInfoDTO)  throws MalformedURLException;
	public int	updateBankCard(AccBankCardInfoDTO accBankCardInfoDTO)  throws MalformedURLException;
	public int	deleteBankCard(AccBankCardInfoDTO accBankCardInfoDTO)  throws MalformedURLException;
	public  List<AccBankCardInfoDTO> getByCondition(AccBankCardInfoDTO accBankCardInfoDTO) throws Exception;
	
	/**
	 * 新增“账户银行卡信息表”(新版)
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Long newBankCard(AccBankCardInfoEntity entity) throws Exception;
	
	public AccBankCardInfoDTO getBankCardBySearch(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新绑定银行卡审核状态
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int	updateBankCardAuditStatus(Map<String, Object> params)  throws Exception;
	
}
