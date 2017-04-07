package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityBaseService;

public class GdOrderActivityBaseToolServiceImpl implements GdOrderActivityBaseToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static GdOrderActivityBaseService gdOrderActivityBaseService;

	private GdOrderActivityBaseService getHessianGdOrderActivityBaseService() throws MalformedURLException {
		String url = gdProperties.getGdOrderActivityBaseServiceUrl();
		if (gdOrderActivityBaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdOrderActivityBaseService = (GdOrderActivityBaseService) factory.create(GdOrderActivityBaseService.class, url);
		}
		return gdOrderActivityBaseService;
	}
	
	/**
	 * 清除活动缓存
	 * @return
	 */
	
	public boolean deleteCach(Integer type) throws MalformedURLException {
		return getHessianGdOrderActivityBaseService().deleteCach(type);
	}

	@Override
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO) throws Exception {
		return getHessianGdOrderActivityBaseService().queryOrderPenalty(queryDTO);
	}

}
