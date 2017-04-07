package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessProducttypeDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.service.BusinessProducttypeService;
import com.gudeng.commerce.gd.customer.service.UsercollectProductService;
import com.gudeng.commerce.gd.customer.service.UsercollectShopService;
import com.gudeng.commerce.gd.home.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.home.service.UsercollectShopToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

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
	public List<PushProductDTO> getList(Long userId, Long businessId,
			Long marketId, Long productId, int startRow, int endRow) throws Exception {
		return getHessianUsercollectShopService().getList(userId, businessId, marketId,
				productId, startRow, endRow);
	}

	@Override
	public List<UsercollectProductDTO> getProductList(Long memberId,
			Long marketId) throws Exception {
		return getHessianUsercollectShopService().getProductList(memberId, marketId);
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

	@Override
	public Integer getCount(Map<String, Object> parammap) throws Exception {
		return getHessianUsercollectShopService().getCount(parammap);
	}

	@Override
	public List<PushProductDTO> getCollectList(Long userId, Long businessId,
			Long marketId, Long productId, int startRow, int endRow)
			throws Exception {
		return getHessianUsercollectShopService().getCollectList( userId,  businessId,
				 marketId,  productId,  startRow,  endRow);
	}

	@Override
	public Integer cancelMoreFocus(Long userId, String productIds) throws Exception {
		return getHessianUsercollectShopService().cancelMoreFocus(userId,productIds);
	}

}
