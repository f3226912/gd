package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ReActivityGiftToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.service.ReActivityGiftService;

public class ReActivityGiftToolServiceImpl implements ReActivityGiftToolService{
	@Resource
	private GdProperties gdProperties;
	
	private ReActivityGiftService reActivityGiftService;
	
	protected ReActivityGiftService getReActivityGiftHessianService() throws MalformedURLException{
		if(reActivityGiftService == null){
			String hessianUrl = gdProperties.getReActvityGiftServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reActivityGiftService = (ReActivityGiftService) factory.create(ReActivityGiftService.class, hessianUrl);
		}
		return reActivityGiftService;
	}
	
	@Override
	public List<ActReActivitityGiftDto> getActivityGiftList(Map<String, Object> params) throws Exception {
		return getReActivityGiftHessianService().getActivityGiftList(params);
	}

	@Override
	public int getCostById(Integer id) throws Exception {
		Integer cost = getReActivityGiftHessianService().getCostById(id);
		if(cost == null){
			return 0;
		}
		return cost;
	}

	@Override
	public List<ActReActivitityGiftDto> queryActivityGiftPage(Map<String, Object> map) throws Exception {
		return getReActivityGiftHessianService().queryActivityGiftPage(map);
	}

	@Override
	public int getActivityGiftTotal(Map<String, Object> map) throws Exception{
		return getReActivityGiftHessianService().getActivityGiftTotal(map);
	}

	@Override
	public ActReActivitityGiftDto getActivityGift(Integer activityId, Integer giftId) throws Exception {
		return getReActivityGiftHessianService().getActivityGift(activityId, giftId);
	}
}
