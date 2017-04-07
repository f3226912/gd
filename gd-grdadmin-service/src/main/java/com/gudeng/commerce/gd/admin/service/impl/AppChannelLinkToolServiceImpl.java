package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AppChannelLinkToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AppChannelLinkDTO;
import com.gudeng.commerce.gd.customer.entity.AppChannelLink;
import com.gudeng.commerce.gd.customer.service.AppChannelLinkService;

public class AppChannelLinkToolServiceImpl implements AppChannelLinkToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static AppChannelLinkService appChannelLinkService;

	protected AppChannelLinkService getHessianAppChannelLinkService() throws MalformedURLException {
		String url = gdProperties.getAppChannelLinkServiceUrl();
		if (appChannelLinkService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			appChannelLinkService = (AppChannelLinkService) factory.create(AppChannelLinkService.class, url);
		}
		return appChannelLinkService;
	}

	@Override
	public AppChannelLinkDTO getById(String id) throws Exception {
		return getHessianAppChannelLinkService().getById(id);
	}

	@Override
	public List<AppChannelLinkDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianAppChannelLinkService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianAppChannelLinkService().deleteById(id);
	}
	
	public int deleteBatch(List<String> list,String userId) throws Exception {
		return getHessianAppChannelLinkService().deleteBatch(list,userId);
	}

	@Override
	public int update(AppChannelLinkDTO t) throws Exception {
		return getHessianAppChannelLinkService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianAppChannelLinkService().getTotal(map);
	}

	@Override
	public Long insert(AppChannelLink entity) throws Exception {
		return getHessianAppChannelLinkService().insert(entity);
	}

	@Override
	public List<AppChannelLinkDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianAppChannelLinkService().getListPage(map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianAppChannelLinkService().deleteBatch(list);
	}
}