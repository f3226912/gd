package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProFreightOrderToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProFreightOrderDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProFreightOrderEntity;
import com.gudeng.commerce.gd.bi.service.GrdProFreightOrderService;

public class GrdProFreightOrderToolServiceImpl implements GrdProFreightOrderToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProFreightOrderService grdProFreightOrderService;

	protected GrdProFreightOrderService getHessianGrdProFreightOrderService() throws MalformedURLException {
		String url = gdProperties.getGrdProFreightOrderServiceUrl();
		if (grdProFreightOrderService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProFreightOrderService = (GrdProFreightOrderService) factory.create(GrdProFreightOrderService.class, url);
		}
		return grdProFreightOrderService;
	}

	@Override
	public GrdProFreightOrderDTO getById(String id) throws Exception {
		return getHessianGrdProFreightOrderService().getById(id);
	}

	@Override
	public List<GrdProFreightOrderDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProFreightOrderService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProFreightOrderService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProFreightOrderService().deleteBatch(list);
	}

	@Override
	public int update(GrdProFreightOrderDTO t) throws Exception {
		return getHessianGrdProFreightOrderService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProFreightOrderService().getTotal(map);
	}

	@Override
	public Long insert(GrdProFreightOrderEntity entity) throws Exception {
		return getHessianGrdProFreightOrderService().insert(entity);
	}

	@Override
	public List<GrdProFreightOrderDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProFreightOrderService().getListPage(map);
	}
}