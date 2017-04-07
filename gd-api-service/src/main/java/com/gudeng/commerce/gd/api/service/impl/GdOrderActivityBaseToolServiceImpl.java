package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;
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
	public GdOrderActivityResultDTO queryOrderActivty(GdOrderActivityQueryDTO queryDTO) throws Exception {
		return getHessianGdOrderActivityBaseService().queryOrderActivty(queryDTO);
	}

	@Override
	public GdProductActivityQueryDTO queryProductAct(GdProductActivityQueryDTO queryDTO) throws Exception {
		return getHessianGdOrderActivityBaseService().queryProductAct(queryDTO);
	}

}
