package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.customer.service.CreditService;
import com.gudeng.commerce.gd.m.service.CreditToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年5月25日 上午10:43:29
 */
public class CreditToolServiceImpl implements CreditToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static CreditService creditService;
	
	/**
	 * billDetailService接口服务
	 * @return
	 */
	protected CreditService getHessianCreditService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.creditService.url");
		if(creditService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			creditService = (CreditService) factory.create(CreditService.class, url);
		}
		return creditService;
	}

	@Override
	public Long saveFinanceCredit(FinanceCreditEntity creditEntity) throws Exception {
		return getHessianCreditService().saveFinanceCredit(creditEntity);
	}

}
