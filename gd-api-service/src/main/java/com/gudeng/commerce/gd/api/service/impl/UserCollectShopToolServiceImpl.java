package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.UserCollectShopToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectProductService;
import com.gudeng.commerce.gd.customer.service.UsercollectShopService;

public class UserCollectShopToolServiceImpl implements UserCollectShopToolService{
	private static Logger logger = LoggerFactory.getLogger(UserCollectShopToolServiceImpl.class);

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	
	private static UsercollectShopService usercollectShopService;

	@Override
	public int focusShop(Long userId, Long businessId) {
		List<UsercollectShopDTO> list=getAll(userId);
		List< Long> listFocusBussinessId=new ArrayList<>();
		try {
		
			for (UsercollectShopDTO usercollectShopDTO : list) {
				listFocusBussinessId.add(usercollectShopDTO.getBusinessId());
			}
			if (listFocusBussinessId.contains(businessId)) {
				//已经关注过该农批商
				logger.error("已经关注过该农批商  businessId"   +businessId);
			}
			else {
					return getUserCollectShopService().focusShop(userId, businessId);
			}
		}
		catch (MalformedURLException e) {
			logger.error("收藏商城异常"   +businessId);
		}catch (Exception e) {
			logger.error("收藏商城异常"   +businessId);
		
		}
		return 0;
	}

	@Override
	public int blurShop(Long userId, Long businessId) {
		try {
			return getUserCollectShopService().blurShop(userId, businessId);
		} catch (MalformedURLException e) {
			logger.error("收藏商城异常"   +businessId);
		}
		catch (Exception e) {
			logger.error("收藏商城异常"   +businessId);
		}
		return 0;
	}

	@Override
	public int blurShop(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsercollectShopDTO> getAll(Long userId) {

		try {
			List<UsercollectShopDTO> list=getUserCollectShopService().getAll(userId);
			return list;
		} catch (MalformedURLException e) {
			logger.error("获取用户关注农批商异常",e);
		}
		return null;
	}
	private UsercollectShopService getUserCollectShopService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.userCollectShop.url");
		if (usercollectShopService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectShopService = (UsercollectShopService) factory.create(
					UsercollectShopService.class, hessianUrl);
		}
		return usercollectShopService;
	}
}
