package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ActActivityBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.dto.ActStatisticDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.ActReUserActivityService;

public class ActActivityBaseinfoToolServiceImpl implements ActActivityBaseinfoToolService{

	@Resource
	private GdProperties gdProperties;
	
	private ActActivityBaseinfoService actActivityBaseinfoService;
	
	private ActReUserActivityService actReUserActivityService;
	

	protected ActActivityBaseinfoService getActActivityHessianService() throws MalformedURLException{
		if(actActivityBaseinfoService == null){
			String hessianUrl = gdProperties.getActActvityBaseinoServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actActivityBaseinfoService = (ActActivityBaseinfoService) factory.create(ActActivityBaseinfoService.class, hessianUrl);
		}
		return actActivityBaseinfoService;
	}
	
	private ActReUserActivityService getReUserActivityHessianService() throws MalformedURLException{
		if(actReUserActivityService == null){
			String hessianUrl = gdProperties.getReUserActivityServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);	
			actReUserActivityService = (ActReUserActivityService) factory.create(ActReUserActivityService.class, hessianUrl);
		}
		return actReUserActivityService;
	}
	
	@Override
	public List<ActStatisticDto> queryStatisticPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getActActivityHessianService().queryStatisticPage(map);
	}

	@Override
	public Integer getStatisticTotalCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getActActivityHessianService().getStatisticTotalCount(map);
	}
	
	
	@Override
	public Long add(ActActivityBaseinfoEntity entity) throws Exception {
		return getActActivityHessianService().add(entity);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryPageByCondition(Map<String, Object> map) throws Exception{
		return getActActivityHessianService().queryPageByCondition(map);
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) throws Exception {
		return getActActivityHessianService().getTotalCountByCondition(map);
	}

	@Override
	public ActActivityBaseinfoDTO getById(Integer id) throws Exception {
		return getActActivityHessianService().getById(id);
	}

	@Override
	public Long addActivity(ActActivityBaseinfoDTO dto) throws Exception {
		return getActActivityHessianService().addActivity(dto);
	}

	@Override
	public int delete(ActActivityBaseinfoDTO dto) throws Exception {
		return getActActivityHessianService().delete(dto);
	}

	@Override
	public int updateActivity(ActActivityBaseinfoDTO dto) throws Exception {
		return getActActivityHessianService().updateActivity(dto);
	}

	@Override
	public int updateLaunch(ActActivityBaseinfoDTO dto) throws Exception{
		return getActActivityHessianService().updateLaunch(dto);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryListByCondition(Map<String, Object> map) throws Exception {
		return getActActivityHessianService().queryListByCondition(map);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryActivityStatisticPage(
			Map<String, Object> map) throws Exception{
		return getActActivityHessianService().queryActivityStatisticPage(map);
	}

	@Override
	public Integer getActivityStatisticTotalCount(Map<String, Object> map) throws Exception{
		return getActActivityHessianService().getActivityStatisticTotalCount(map);
	}

	@Override
	public List<ActActivityBaseinfoDTO> queryActStatisticListByCondition(Map<String, Object> map) throws Exception {
		return getActActivityHessianService().queryActStatisticListByCondition(map);
	}

	@Override
	public ActReUserActivityDto getActivityUser(String activityId, String userId) throws Exception {
		return getReUserActivityHessianService().getReUserActivityInfo(activityId, userId);
	}

	@Override
	public List<ActStatisticDto> queryExportDate(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getActActivityHessianService().queryExportDate(map);
	}

	

}
