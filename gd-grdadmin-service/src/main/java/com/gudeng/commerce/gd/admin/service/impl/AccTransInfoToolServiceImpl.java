package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AccTransInfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.service.AccTransInfoService;

public class AccTransInfoToolServiceImpl implements AccTransInfoToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private AccTransInfoService accTransInfoService;
	
	protected AccTransInfoService getHessianTransInfoServiceService() throws MalformedURLException {
		String url = gdProperties.getAccTransInfoUrl();
		if (accTransInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accTransInfoService = (AccTransInfoService) factory.create(AccTransInfoService.class, url);
		}
		return accTransInfoService;
	}

	@Override
	public List<AccTransInfoDTO> getAccTransInfoListByMemberId(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianTransInfoServiceService().getAccTransInfoListByMemberId(map);
	}

	@Override
	public Long getAccTransInfoListTotalByMemberId(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianTransInfoServiceService().getAccTransInfoListTotalByMemberId(map);
	}

}
