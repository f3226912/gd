package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AppshareToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.customer.service.AppshareService;

public class AppshareToolServiceImpl implements AppshareToolService {

	@Resource
	private GdProperties gdProperties;
	
	private AppshareService appshareService;
	
	
	protected AppshareService getAppshareHessianService() throws MalformedURLException{
		if(appshareService == null){
			String url = gdProperties.getAppshareServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			appshareService = (AppshareService) factory.create(AppshareService.class, url);
		}
		return appshareService;
	}
	

	@Override
	public int getTotalCountByCondtion(Map<String, Object> map)
			throws Exception {
		return getAppshareHessianService().getTotalCountByCondtion(map);
	}


	@Override
	public List<AppshareDTO> queryPageByCondition(Map<String, Object> map)
			throws Exception {
		return getAppshareHessianService().queryPageByCondition(map);
	}


	@Override
	public List<AppshareDTO> queryListByCondition(Map<String, Object> map)
			throws Exception {
		return getAppshareHessianService().queryListByCondition(map);
	}


	@Override
	public int updateStatus(AppshareDTO dto) throws Exception {
		return getAppshareHessianService().updateStatus(dto);
	}


	@Override
	public List<AppshareDTO> queryDetailPageByCondition(Map<String, Object> map)
			throws Exception {
		return getAppshareHessianService().queryDetailPageByCondition(map);
	}


	@Override
	public int getDetailTotalCountByCondtion(Map<String, Object> map)
			throws Exception {
		return getAppshareHessianService().getDetailTotalCountByCondtion(map);
	}

	
}
