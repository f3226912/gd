package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProInfoOrderToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProInfoOrderDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProInfoOrderEntity;
import com.gudeng.commerce.gd.bi.service.GrdProInfoOrderService;

public class GrdProInfoOrderToolServiceImpl implements GrdProInfoOrderToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProInfoOrderService grdProInfoOrderService;

	protected GrdProInfoOrderService getHessianGrdProInfoOrderService() throws MalformedURLException {
		String url = gdProperties.getGrdProInfoOrderServiceUrl();
		if (grdProInfoOrderService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProInfoOrderService = (GrdProInfoOrderService) factory.create(GrdProInfoOrderService.class, url);
		}
		return grdProInfoOrderService;
	}

	@Override
	public GrdProInfoOrderDTO getById(String id) throws Exception {
		return getHessianGrdProInfoOrderService().getById(id);
	}

	@Override
	public List<GrdProInfoOrderDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProInfoOrderService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProInfoOrderService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProInfoOrderService().deleteBatch(list);
	}

	@Override
	public int update(GrdProInfoOrderDTO t) throws Exception {
		return getHessianGrdProInfoOrderService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProInfoOrderService().getTotal(map);
	}

	@Override
	public Long insert(GrdProInfoOrderEntity entity) throws Exception {
		return getHessianGrdProInfoOrderService().insert(entity);
	}

	@Override
	public List<GrdProInfoOrderDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProInfoOrderService().getListPage(map);
	}
}