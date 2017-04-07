package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectProductService;
import com.gudeng.commerce.gd.m.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

/**
 * 功能描述：
 */
@Service
public class UsercollectProductToolServiceImpl implements
		UsercollectProductToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static UsercollectProductService usercollectProductService;

	/*
	 * @Autowired private BaseDao baseDao;
	 */

	/**
	 * 功能描述:businessProducttypeService接口服务
	 * 
	 * @param
	 * @return
	 */
	protected UsercollectProductService getHessianUsercollectShopService()
			throws MalformedURLException {
		String url = gdProperties.getUsercollectProductServiceUrl();
		if (usercollectProductService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectProductService = (UsercollectProductService) factory
					.create(UsercollectProductService.class, url);
		}
		return usercollectProductService;
	}



	@Override
	public Long addFocus(Long userId, Long productId, Long categoryId) throws Exception {
		return getHessianUsercollectShopService().addFocus(userId, productId, categoryId);
	}

	@Override
	public void cancelFocus(Long userId, Long productId) throws Exception {
		getHessianUsercollectShopService().cancelFocus(userId, productId);
	}

	@Override
	public UsercollectProductDTO getCollect(Long userId, Long productId) throws Exception {
		return getHessianUsercollectShopService().getCollect(userId, productId);
	}

	

}
