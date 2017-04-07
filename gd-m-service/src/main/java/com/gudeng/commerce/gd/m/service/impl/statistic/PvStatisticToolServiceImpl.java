package com.gudeng.commerce.gd.m.service.impl.statistic;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;
import com.gudeng.commerce.gd.m.service.statistic.PvStatisticToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * 浏览量业务
 * @author TerryZhang
 *
 */
public class PvStatisticToolServiceImpl implements PvStatisticToolService {
	@Autowired
	public GdProperties gdProperties;
	
	private static PvStatisticBusinessService pvStatisticBusinessService;

	private PvStatisticBusinessService getHessionPvStatisticBusinessService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.pvStatisticBusinessService.url");
		if (pvStatisticBusinessService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pvStatisticBusinessService = (PvStatisticBusinessService) factory.create(PvStatisticBusinessService.class,
					hessianUrl);
		}
		return pvStatisticBusinessService;
	}

	@Override
	public void addPvForProduct(Long productId, Integer addPv) throws Exception {
		getHessionPvStatisticBusinessService().addPvForProduct(productId, addPv);
	}

}
