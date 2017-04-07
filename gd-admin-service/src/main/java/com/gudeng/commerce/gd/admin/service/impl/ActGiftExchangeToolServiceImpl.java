package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ActGiftExchangeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.service.ActActivityGiftExchangService;

public class ActGiftExchangeToolServiceImpl implements ActGiftExchangeToolService{

	@Resource
	private GdProperties gdProperties;
	
	private ActActivityGiftExchangService actActivityGiftExchangService;
	
	protected ActActivityGiftExchangService getActActivityGiftExchangHessianService() throws MalformedURLException{
		if(actActivityGiftExchangService == null){
			String url = gdProperties.getActivityGiftExchangServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actActivityGiftExchangService = (ActActivityGiftExchangService) factory.create(ActActivityGiftExchangService.class, url);
		}
		return actActivityGiftExchangService;
	}
	
	@Override
	public List<ActGiftExchangeApplyDto> queryPageByCondition(Map<String, Object> map) throws Exception {
		return getActActivityGiftExchangHessianService().queryPageByCondition(map);
	}

	@Override
	public int getTotalCountByCondtion(Map<String, Object> map) throws Exception {
		return getActActivityGiftExchangHessianService().getTotalCountByCondtion(map);
	}

	@Override
	public List<ActGiftExchangeApplyDto> queryListByCondition(Map<String, Object> map) throws Exception{
		return getActActivityGiftExchangHessianService().queryListByCondition(map);
	}

	@Override
	public Long addGiftExchangeRecord(ActGiftExchangeApplyDto dto)throws Exception {
		return getActActivityGiftExchangHessianService().addGiftExchangeRecord(dto);
	}

	@Override
	public ActGiftExchangeApplyDto getById(Integer id) throws Exception {
		return getActActivityGiftExchangHessianService().getById(id);
	}

	@Override
	public int updateGiftExchangeRecord(ActGiftExchangeApplyDto dto) throws Exception{
		return getActActivityGiftExchangHessianService().updateGiftExchangeRecord(dto);
	}

	@Override
	public int updateStatus(ActGiftExchangeApplyDto dto) throws Exception{
		return getActActivityGiftExchangHessianService().updateStatus(dto);
	}

	@Override
	public int hasExchangeGiftCount(Integer activityId, Long userId) throws Exception {
		return getActActivityGiftExchangHessianService().hasExchangeGiftCount(activityId, userId);
	}

}
