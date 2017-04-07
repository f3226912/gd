package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.BankInformationToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.entity.BankInformationEntity;
import com.gudeng.commerce.gd.order.service.BankInformationService;

public class BankInformationToolServiceImpl implements BankInformationToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static BankInformationService bankInformationService;

	protected BankInformationService getHessianBankInformationService() throws MalformedURLException {
		String url = gdProperties.getBankInformationServiceUrl();
		if (bankInformationService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			bankInformationService = (BankInformationService) factory.create(BankInformationService.class, url);
		}
		return bankInformationService;
	}

	@Override
	public BankInformationDTO getById(String id) throws Exception {
		return getHessianBankInformationService().getById(id);
	}

	@Override
	public List<BankInformationDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianBankInformationService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianBankInformationService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianBankInformationService().deleteBatch(list);
	}

	@Override
	public int update(BankInformationDTO t) throws Exception {
		return getHessianBankInformationService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianBankInformationService().getTotal(map);
	}

	@Override
	public Long insert(BankInformationEntity entity) throws Exception {
		return getHessianBankInformationService().insert(entity);
	}

	@Override
	public List<BankInformationDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianBankInformationService().getListPage(map);
	}

	@Override
	public int updateBatch(Map<String, Object> param) throws Exception {
		return getHessianBankInformationService().updateBatch(param);
	}

	@Override
	public int batchInsert(List<BankInformationEntity> entityList) throws Exception {
		return getHessianBankInformationService().batchInsert(entityList);
	}
}