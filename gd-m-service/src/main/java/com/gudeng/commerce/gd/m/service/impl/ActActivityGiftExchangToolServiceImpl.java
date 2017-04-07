package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.ActActivityGiftExchangToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityGiftExchangService;

public class ActActivityGiftExchangToolServiceImpl implements ActActivityGiftExchangToolService {

	@Autowired
	public GdProperties gdProperties;

	protected static ActActivityGiftExchangService actActivityGiftExchangService;

	protected ActActivityGiftExchangService getHessianActActivityGiftExchangService() throws MalformedURLException {

		if (actActivityGiftExchangService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actActivityGiftExchangService = (ActActivityGiftExchangService) factory.create(ActActivityGiftExchangService.class, gdProperties.getProperties().getProperty("gd.activityGiftExchange.url"));
		}
		return actActivityGiftExchangService;
	}

	@Override
	public List<ActGiftExchangeApplyDto> getActivityExchangeRecord(Map<String, Object> params) throws Exception {
		return getHessianActActivityGiftExchangService().getActivityExchangeRecord(params);
	}

	@Override
	public Long insertActivityExchangeRecord(ActGiftExchangeApplyEntity entity) throws Exception {
		return getHessianActActivityGiftExchangService().insertActivityExchangeRecord(entity);
	}

	@Override
	public int getUserExchangeScore(String activityId, String userid)
			throws Exception {
		return getHessianActActivityGiftExchangService().getUserExchangeScore(activityId,userid);
	}
}
