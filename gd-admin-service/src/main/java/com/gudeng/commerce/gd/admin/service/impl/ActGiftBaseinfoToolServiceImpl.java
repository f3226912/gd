package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ActGiftBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.service.ActGiftBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.ReActivityGiftService;

public class ActGiftBaseinfoToolServiceImpl implements ActGiftBaseinfoToolService{

	private ActGiftBaseinfoService actGiftBaseinfoService;
	
	private ReActivityGiftService reActivityGiftService;
	
	@Resource
	private GdProperties gdProperties;
	
	protected ActGiftBaseinfoService getActGiftHessianService() throws MalformedURLException{
		if(actGiftBaseinfoService == null){
			String hessianUrl = gdProperties.getActGiftBaseinfoServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actGiftBaseinfoService = (ActGiftBaseinfoService) factory.create(ActGiftBaseinfoService.class, hessianUrl);
		}
		return actGiftBaseinfoService;
	}
	
	protected ReActivityGiftService getReActivityGiftHessianService() throws MalformedURLException{
		if(reActivityGiftService == null){
			String hessianUrl = gdProperties.getReActivityGiftServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reActivityGiftService = (ReActivityGiftService) factory.create(ReActivityGiftService.class, hessianUrl);
		}
		return reActivityGiftService;
	}

	@Override
	public Long add(ActGiftBaseinfoEntity entity) throws Exception {
		return getActGiftHessianService().add(entity);
	}

	@Override
	public Integer update(ActGiftBaseinfoEntity entity) throws Exception {
		return getActGiftHessianService().update(entity);
	}
	
	@Override
	public List<ActGiftBaseinfoDTO> queryPageByCondition(Map<String, Object> map) throws Exception {
		return getActGiftHessianService().queryPageByCondition(map);
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) throws Exception {
		return getActGiftHessianService().getTotalCountByCondition(map);
	}

	@Override
	public ActGiftBaseinfoDTO getById(Integer id) throws Exception {
		return getActGiftHessianService().getById(id);
	}

	@Override
	public boolean exist(String name) throws Exception {
		return getActGiftHessianService().exist(name);
	}

	@Override
	public List<ActGiftBaseinfoDTO> queryListByCondition(Map<String, Object> map) throws Exception {
		return getActGiftHessianService().queryListByCondition(map);
	}

	@Override
	public int delete(ActGiftBaseinfoDTO dto) throws Exception {
		return getActGiftHessianService().delete(dto);
	}

	@Override
	public Integer sumActivityGiftCost(Integer giftId) throws Exception {
		return getActGiftHessianService().sumActivityGiftCost(giftId);
	}

	@Override
	public List<ActGiftBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return getActGiftHessianService().getListByCondition(map);
	}

	@Override
	public int getActivityUseGiftCount(int giftId) throws Exception {
		return getReActivityGiftHessianService().getActivityUseGiftCount(giftId);
	}

}
