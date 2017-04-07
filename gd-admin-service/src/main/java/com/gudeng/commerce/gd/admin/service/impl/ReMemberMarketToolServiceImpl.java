package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ReMemberMarkeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.service.ReMemberMarketService;

public class ReMemberMarketToolServiceImpl implements ReMemberMarkeToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	public static ReMemberMarketService reMemberMarketService;

	protected ReMemberMarketService getHessianReMemberMarketService()
			throws MalformedURLException {
		String url = gdProperties.getReMemberMarketUrl();
		if (reMemberMarketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reMemberMarketService = (ReMemberMarketService) factory.create(
					ReMemberMarketService.class, url);
		}
		return reMemberMarketService;

	}
	
	@Override
	public int addReMemberMarketDTO(ReMemberMarketDTO rb) throws Exception {
		return getHessianReMemberMarketService().addReMemberMarketDTO(rb);
	}

	@Override
	public int deleteReMemberMarketDTO(ReMemberMarketDTO rb) throws Exception {
		return getHessianReMemberMarketService().deleteReMemberMarketDTO(rb);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return getHessianReMemberMarketService().getTotal(map);
	}

	@Override
	public List<ReMemberMarketDTO> getBySearch(Map map) throws Exception {
		return getHessianReMemberMarketService().getBySearch(map);
	}

	@Override
	public List<ReMemberMarketDTO> getByDTO(ReMemberMarketDTO rmm)
			throws Exception {
		return getHessianReMemberMarketService().getByDTO(rmm);
	}

}
