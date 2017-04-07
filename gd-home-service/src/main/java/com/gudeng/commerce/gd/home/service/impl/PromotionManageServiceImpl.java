package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.entity.PromotionStatisticsEntity;
import com.gudeng.commerce.gd.customer.service.PromotionSourceService;
import com.gudeng.commerce.gd.home.service.PromotionManageService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class PromotionManageServiceImpl implements PromotionManageService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	public static PromotionSourceService promotionSourceService;
	
	protected PromotionSourceService getHessianPromotionSourceService()
			throws MalformedURLException {
		String url = gdProperties.getPromotionStatistics();
		if (promotionSourceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promotionSourceService = (PromotionSourceService) factory.create(
					PromotionSourceService.class, url);
		}

		return promotionSourceService;
	}
	
	@Override
	public Long addPromotionStatistics(PromotionStatisticsEntity entity)
			throws Exception {
		return getHessianPromotionSourceService().addPromotionStatistics(entity);
	}

	
}
