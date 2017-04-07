package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ActivityUserIntegralChangeToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserIntegralChangeEntity;
import com.gudeng.commerce.gd.customer.service.ActivityUserIntegralChangeService;


public class ActivityUserIntegralChangeToolServiceImpl implements ActivityUserIntegralChangeToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static ActivityUserIntegralChangeService activityUserintegralChangeService;

	protected ActivityUserIntegralChangeService getHessianActivityUserintegralService() throws MalformedURLException {
		String url = gdProperties.getActivityUserintegralChangeServiceUrl();
		if (activityUserintegralChangeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			activityUserintegralChangeService = (ActivityUserIntegralChangeService) factory.create(ActivityUserIntegralChangeService.class, url);
		}
		return activityUserintegralChangeService;
	}

	@Override
	public ActivityUserIntegralChangeDTO getById(String id) throws Exception {
		return getHessianActivityUserintegralService().getById(id);
	}

	@Override
	public List<ActivityUserIntegralChangeDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getList(map);
	}

	@Override
	public List<ActivityUserIntegralChangeDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getListPage(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianActivityUserintegralService().deleteById(id);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianActivityUserintegralService().deleteBatch(list);
	}

	@Override
	public int update(ActivityUserIntegralChangeDTO t) throws Exception {
		return getHessianActivityUserintegralService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getTotal(map);
	}

	@Override
	public Long persist(ActivityUserIntegralChangeEntity entity) throws Exception {
		return getHessianActivityUserintegralService().persist(entity);
	}

	@Override
	public Integer insert(ActivityUserIntegralChangeDTO entity) throws Exception {
		return getHessianActivityUserintegralService().insert(entity);
	}
    /**
     * param中需要一个参数 memberId 
     * 查询此用户当天已获取积分数
     */
	@Override
	public Integer getTotalIntegralByMemberId(Map<String, Object> param) throws Exception {
		
		return getHessianActivityUserintegralService().getTotalIntegralByMemberId(param);
	}

	
}