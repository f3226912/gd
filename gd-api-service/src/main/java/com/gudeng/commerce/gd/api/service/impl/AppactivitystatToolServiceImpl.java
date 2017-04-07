package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.AppactivitystatToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AppactivitystatDTO;
import com.gudeng.commerce.gd.customer.entity.AppactivitystatEntity;
import com.gudeng.commerce.gd.customer.service.AppactivitystatService;

public class AppactivitystatToolServiceImpl implements AppactivitystatToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static AppactivitystatService appactivitystatService;

	protected AppactivitystatService getHessianAppactivitystatService() throws MalformedURLException {
		String url = gdProperties.getAppactivitystatServiceUrl();
		if (appactivitystatService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			appactivitystatService = (AppactivitystatService) factory.create(AppactivitystatService.class, url);
		}
		return appactivitystatService;
	}

	@Override
	public AppactivitystatDTO getById(String id) throws Exception {
		return getHessianAppactivitystatService().getById(id);
	}

	@Override
	public List<AppactivitystatDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianAppactivitystatService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianAppactivitystatService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianAppactivitystatService().deleteBatch(list);
	}

	@Override
	public int update(AppactivitystatDTO t) throws Exception {
		return getHessianAppactivitystatService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianAppactivitystatService().getTotal(map);
	}

	@Override
	public Long insert(AppactivitystatEntity entity) throws Exception {
		return getHessianAppactivitystatService().insert(entity);
	}

	@Override
	public List<AppactivitystatDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianAppactivitystatService().getListPage(map);
	}
}