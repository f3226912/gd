package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.ReActivityGiftToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.service.ReActivityGiftService;

public class ReActivityGiftToolServiceImpl implements ReActivityGiftToolService {

	@Autowired
	public GdProperties gdProperties;

	protected static ReActivityGiftService reActivityGiftService;

	protected ReActivityGiftService getHessianActReActivityGiftService() throws MalformedURLException {

		if (reActivityGiftService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reActivityGiftService = (ReActivityGiftService) factory.create(ReActivityGiftService.class, gdProperties.getReActivityGift());
		}
		return reActivityGiftService;
	}

	@Override
	public List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception {
		return getHessianActReActivityGiftService().getActivityGiftList(params);
	}

	@Override
	public int getActivityScoreRecordCount(Map<String, Object> params) throws Exception {
		return getHessianActReActivityGiftService().getActivityScoreRecordCount(params);
	}

	@Override
	public int updateActivityGift(Map<String, Object> params) throws Exception {
		return getHessianActReActivityGiftService().updateActivityGift(params);
	}

}
