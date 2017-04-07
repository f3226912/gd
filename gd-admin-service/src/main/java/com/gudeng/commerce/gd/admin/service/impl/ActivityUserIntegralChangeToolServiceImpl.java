package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ActivityUserIntegralChangeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.service.ActivityUserIntegralChangeService;

public class ActivityUserIntegralChangeToolServiceImpl implements ActivityUserIntegralChangeToolService
{
	
	@Autowired
	public GdProperties gdProperties;
	
	private static ActivityUserIntegralChangeService userIntegralChangeService;
	
	protected ActivityUserIntegralChangeService getUserIntegralChangeSerivce() throws MalformedURLException {
		String url = gdProperties.getActUserIntegralChangeServiceUrl();
		if (userIntegralChangeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			userIntegralChangeService = (ActivityUserIntegralChangeService) factory.create(ActivityUserIntegralChangeService.class, url);
		}
		return userIntegralChangeService;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getUserIntegralChangeSerivce().getTotal(map);
	}
	
	@Override
	public List<ActivityUserIntegralChangeDTO> getListPage(Map<String, Object> map) throws Exception {
		return getUserIntegralChangeSerivce().getListPage(map);
	}

	@Override
	public int insert(ActivityUserIntegralChangeDTO entity) throws Exception {
		return getUserIntegralChangeSerivce().insert(entity);
	}
	
	@Override
	public ActivityUserIntegralChangeDTO getById(String id) throws Exception {
		return getUserIntegralChangeSerivce().getById(id);
	}
	
	@Override
	public List<ActivityUserIntegralChangeDTO> getList(Map<String, Object> map) throws Exception {
		return getUserIntegralChangeSerivce().getList(map);
	}
	
	@Override
	public int deleteById(String id) throws Exception {
		return 0;
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ActivityUserIntegralChangeDTO t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
