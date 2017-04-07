package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;

/**
 * 商铺浏览量业务
 * 
 * @author Ailen
 *
 */
public class PvStatisticBusinessToolServiceImpl implements PvStatisticBusinessToolService {
	@Autowired
	public GdProperties gdProperties;
	private static PvStatisticBusinessService pvStatisticBusinessService;

	private PvStatisticBusinessService getHessionPvStatisticBusinessService() throws MalformedURLException {
		String hessianUrl = gdProperties.getPvStatisticBusinessServiceUrl();
		System.out.println("hessianUrl*************" + hessianUrl);
		if (pvStatisticBusinessService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pvStatisticBusinessService = (PvStatisticBusinessService) factory.create(PvStatisticBusinessService.class,
					hessianUrl);
		}
		return pvStatisticBusinessService;
	}

	@Override
	public void addPv(BusinessPvStatisDTO businessPvStatisDTO) throws Exception {
		getHessionPvStatisticBusinessService().addPv(businessPvStatisDTO);
	}

	@Override
	public void addPvForProduct(Long productId, Integer addPv) throws Exception {
		getHessionPvStatisticBusinessService().addPvForProduct(productId, addPv);
	}

}
