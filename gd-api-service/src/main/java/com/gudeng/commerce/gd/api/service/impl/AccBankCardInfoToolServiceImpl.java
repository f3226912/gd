package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.AccBankCardInfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO.BANK_CARD_STATUS;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.innovane.win9008.exception.BusinessException;

public class AccBankCardInfoToolServiceImpl implements AccBankCardInfoToolService{

	@Autowired
	private GdProperties gdProperties;
	
	
	@Autowired
	private WalletToolServiceImpl walletToolServiceImpl;
	
	
	private static AccBankCardInfoService accBankCardInfoService;

	
	protected AccBankCardInfoService getBankCardInfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.accBankCardInfo.url");
		if(accBankCardInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accBankCardInfoService = (AccBankCardInfoService) factory.create(AccBankCardInfoService.class, url);
		}
		return accBankCardInfoService;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getBankCards(Long memberId) throws MalformedURLException {
		return getBankCardInfoService().getBankCards(memberId);
	
	}


	@Override
	public int addBankCard(AccBankCardInfoDTO accBankCardInfoDTO) throws MalformedURLException {
		Long memberId=accBankCardInfoDTO.getMemberId();
		AccInfoDTO accinfoTo = walletToolServiceImpl.getWalletIndex(memberId);
		if (null == accinfoTo) {
			throw new BusinessException("请先创建一个账户");
		}
		
		Integer accountId=accinfoTo.getAccId();
		accBankCardInfoDTO.setAccountId(Long.valueOf(accountId));
		accBankCardInfoDTO.setStatus(BANK_CARD_STATUS.VALIDATE.getValue());
		return getBankCardInfoService().addBankCard(accBankCardInfoDTO);
	}


	@Override
	public int updateBankCard(AccBankCardInfoDTO accBankCardInfoDTO) throws MalformedURLException {
		return getBankCardInfoService().updateBankCard(accBankCardInfoDTO);
	}


	@Override
	public List<AccBankCardInfoDTO> getByCondition(AccBankCardInfoDTO accBankCardInfoDTO) throws Exception {

		return getBankCardInfoService().getByCondition(accBankCardInfoDTO);
	}


	@Override
	public int deleteBankCard(AccBankCardInfoDTO accBankCardInfoDTO) throws MalformedURLException {
		
		accBankCardInfoDTO.setStatus(BANK_CARD_STATUS.INVALIDATE.getValue());
		return getBankCardInfoService().updateBankCard(accBankCardInfoDTO);
	}


	@Override
	public Long newBankCard(AccBankCardInfoEntity entity) throws Exception {
		return getBankCardInfoService().persistBankCard(entity);
	}


	@Override
	public AccBankCardInfoDTO getBankCardBySearch(Map<String, Object> params) throws Exception {
		return getBankCardInfoService().getBankCardBySearch(params);
	}


	@Override
	public int updateBankCardAuditStatus(Map<String, Object> params) throws Exception {
		return getBankCardInfoService().updateBankCardAuditStatus(params);
	}



}
