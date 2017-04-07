package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectShopService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

/**
 * 功能描述：
 */
@Service
public class UsercollectShopToolServiceImpl implements
		UsercollectShopToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static UsercollectShopService usercollectShopService;

	/*
	 * @Autowired private BaseDao baseDao;
	 */

	/**
	 * 功能描述:businessProducttypeService接口服务
	 * 
	 * @param
	 * @return
	 */
	protected UsercollectShopService getHessianUsercollectShopService()
			throws MalformedURLException {
		String url = gdProperties.getUsercollectShopServiceUrl();
		if (usercollectShopService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectShopService = (UsercollectShopService) factory.create(
					UsercollectShopService.class, url);
		}
		return usercollectShopService;
	}

	@Override
	public int focusShop(Long userId, Long businessId) throws Exception {
		return getHessianUsercollectShopService().focusShop(userId, businessId);
	}

	@Override
	public int blurShop(Long userId, Long businessId) throws Exception {
		return getHessianUsercollectShopService().blurShop(userId, businessId);
	}

	@Override
	public int blurShop(Long id) throws Exception {
		return getHessianUsercollectShopService().blurShop(id);
	}

	@Override
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId)
			throws Exception {
		return getHessianUsercollectShopService().getCollectShop(userId,
				businessId);
	}

	@Override
	public List<UsercollectShopDTO> getCollectList(Map<String, Object> map)
			throws Exception {
		return getHessianUsercollectShopService().getCollectList(map);
	}

	@Override
	public Integer getCount(Map<String, Object> map) throws Exception {
		return getHessianUsercollectShopService().getCount(map);
	}

	@Override
	public List<UsercollectShopDTO> getFocusMeCollectList(
			Map<String, Object> map)  throws Exception{
		return getHessianUsercollectShopService().getFocusMeCollectList(map);
	}

	@Override
	public Integer getFocusMeCount(Map<String, Object> map) throws Exception {
		return getHessianUsercollectShopService().getFocusMeCount(map);
	}

	@Override
	public Integer blurMoreShop(Long userId, String businessIds)
			throws Exception {
		return getHessianUsercollectShopService().blurMoreShop(userId, businessIds);
	}

}
