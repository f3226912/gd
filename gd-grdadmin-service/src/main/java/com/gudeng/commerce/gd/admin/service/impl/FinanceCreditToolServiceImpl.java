package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.FinanceCreditToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.customer.service.FinanceCreditService;
import com.gudeng.commerce.gd.order.service.EnPostLogService;
@Service
public class FinanceCreditToolServiceImpl implements FinanceCreditToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	FinanceCreditService financeCreditService;
	
	protected FinanceCreditService getHessianCarsService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.financeCredit.url");
		if(financeCreditService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			financeCreditService = (FinanceCreditService) factory.create(FinanceCreditService.class, url);
		}
		return financeCreditService;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCarsService().getTotal(map);
	}

	@Override
	public FinanceCreditEntity getById(Map<String, Object> map)
			throws Exception {
		return getHessianCarsService().getById(map);
	}

	@Override
	public List<FinanceCreditEntity> getListByConditionPage(
			Map<String, Object> map) throws Exception {
		return getHessianCarsService().getListByConditionPage(map);
	}

}
