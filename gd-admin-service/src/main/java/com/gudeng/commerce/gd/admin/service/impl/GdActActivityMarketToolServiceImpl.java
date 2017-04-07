package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityMarketToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityMarketDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityMarketEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityMarketService;

public class GdActActivityMarketToolServiceImpl implements GdActActivityMarketToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GdActActivityMarketService gdActActivityMarketService;

	protected GdActActivityMarketService getHessianGdActActivityMarketService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityMarketServiceUrl();
		if (gdActActivityMarketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityMarketService = (GdActActivityMarketService) factory.create(GdActActivityMarketService.class, url);
		}
		return gdActActivityMarketService;
	}

	@Override
	public GdActActivityMarketDTO getById(String id) throws Exception {
		return getHessianGdActActivityMarketService().getById(id);
	}

	@Override
	public List<GdActActivityMarketDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityMarketService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityMarketService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityMarketService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityMarketDTO t) throws Exception {
		return getHessianGdActActivityMarketService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityMarketService().getTotal(map);
	}

	@Override
	public void insert(GdActActivityMarketEntity entity) throws Exception {
		getHessianGdActActivityMarketService().insert(entity);
	}

	@Override
	public List<GdActActivityMarketDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityMarketService().getListPage(map);
	}
}