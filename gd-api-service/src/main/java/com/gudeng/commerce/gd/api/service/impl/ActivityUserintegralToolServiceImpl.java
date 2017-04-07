package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ActivityUserintegralToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ActivityUserintegralDTO;
import com.gudeng.commerce.gd.customer.entity.ActivityUserintegral;
import com.gudeng.commerce.gd.customer.service.ActivityUserintegralService;

public class ActivityUserintegralToolServiceImpl implements ActivityUserintegralToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static ActivityUserintegralService activityUserintegralService;

	protected ActivityUserintegralService getHessianActivityUserintegralService() throws MalformedURLException {
		String url = gdProperties.getActivityUserintegralServiceUrl();
		if (activityUserintegralService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			activityUserintegralService = (ActivityUserintegralService) factory.create(ActivityUserintegralService.class, url);
		}
		return activityUserintegralService;
	}

	@Override
	public ActivityUserintegralDTO getById(String id) throws Exception {
		return getHessianActivityUserintegralService().getById(id);
	}

	@Override
	public List<ActivityUserintegralDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getList(map);
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
	public int update(ActivityUserintegralDTO t) throws Exception {
		return getHessianActivityUserintegralService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getTotal(map);
	}

	@Override
	public Long persist(ActivityUserintegral entity) throws Exception {
		return getHessianActivityUserintegralService().persist(entity);
	}

	@Override
	public Integer insert(ActivityUserintegralDTO entity) throws Exception {
		return getHessianActivityUserintegralService().insert(entity);
	}

	@Override
	public List<ActivityUserintegralDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianActivityUserintegralService().getListPage(map);
	}
}