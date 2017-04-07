package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccBankCardInfoToolService;
import com.gudeng.commerce.gd.api.service.CashRequestToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.gudeng.commerce.gd.order.service.CashRequestService;
import com.innovane.win9008.exception.BusinessException;

public class CashRequestToolServiceImpl implements CashRequestToolService {
	@Autowired
	private GdProperties gdProperties;
	@Autowired
	private WalletToolServiceImpl walletToolServiceImpl;
	@Autowired
	private AccBankCardInfoToolServiceImpl accBankCardInfoToolServiceImpl;
	private static CashRequestService cashRequestService;
	@Autowired
	private AccBankCardInfoToolService accBankCardInfoToolService;

	protected CashRequestService getCashRequestService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty(
				"gd.cashRequestService.url");
		if (cashRequestService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			cashRequestService = (CashRequestService) factory.create(
					CashRequestService.class, url);
		}
		return cashRequestService;
	}

	@Override
	public int add(CashRequestDTO cashRequestDTO) throws MalformedURLException {
		return getCashRequestService().add(cashRequestDTO);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CashRequestDTO> getByMemberId(Map map)
			throws MalformedURLException {
		return getCashRequestService().getByMemberId(map);
	}

	@Override
	public Integer getTotal(Map map) throws Exception {
		return getCashRequestService().getTotal(map);

	}

	/**
	 * 提现
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void withdraw(CashRequestDTO cashRequestDTO) throws Exception {
		AccInfoDTO accInfoDTO = walletToolServiceImpl.getWalletIndex(Long
				.valueOf(cashRequestDTO.getMemberId()));
		List<AccBankCardInfoDTO> accBankCardInfoDTOs = accBankCardInfoToolService
				.getBankCards(Long.valueOf(cashRequestDTO.getMemberId()));
		if (null == accBankCardInfoDTOs || accBankCardInfoDTOs.size() == 0) {
			throw new BusinessException("您还没有绑定银行卡，请先绑定银行卡");
		}
		// 提现的金额
		Double withdrawAmount = cashRequestDTO.getCashAmount();
		if (accInfoDTO.getBalAvailable() < withdrawAmount) {
			throw new BusinessException("余额不足");
		}
		// 交易密码不正确
		if (walletToolServiceImpl.validateTransPwd(
				Long.valueOf(cashRequestDTO.getMemberId()),
				cashRequestDTO.getTransPwd()) == ErrorCodeEnum.TRADEPASSWORD_NOT_EXISTED) {
			throw new BusinessException("还未设置交易密码，请先设置交易密码");
		}
		if (walletToolServiceImpl.validateTransPwd(
				Long.valueOf(cashRequestDTO.getMemberId()),
				cashRequestDTO.getTransPwd()) == ErrorCodeEnum.TRADEPASSWORD_IS_INCORECT) {
			throw new BusinessException("交易密码错误，请重新输入");
		}

		getCashRequestService().withdraw(cashRequestDTO, accInfoDTO);

		AccBankCardInfoDTO accBankCardInfoDTOParam = new AccBankCardInfoDTO();
		accBankCardInfoDTOParam.setBankCardNo(cashRequestDTO.getBankCardNo());
		accBankCardInfoDTOParam.setMemberId(Long.valueOf(cashRequestDTO
				.getMemberId()));
		AccBankCardInfoDTO accBankCardInfoDTO = accBankCardInfoToolServiceImpl
				.getByCondition(accBankCardInfoDTOParam).get(0);

		accBankCardInfoToolServiceImpl.updateBankCard(accBankCardInfoDTO);

	}

}
