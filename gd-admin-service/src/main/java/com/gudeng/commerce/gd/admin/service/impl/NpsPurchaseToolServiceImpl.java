package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.NpsPurchaseToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;
import com.gudeng.commerce.gd.customer.service.NpsPurchaseService;

public class NpsPurchaseToolServiceImpl implements NpsPurchaseToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static NpsPurchaseService npsPurchaseService;

	protected NpsPurchaseService getHessianNpsPurchaseService() throws MalformedURLException {
		String url = gdProperties.getNpsPurchaseServiceUrl();
		if (npsPurchaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			npsPurchaseService = (NpsPurchaseService) factory.create(NpsPurchaseService.class, url);
		}
		return npsPurchaseService;
	}

	@Override
	public NpsPurchaseDTO getById(String id) throws Exception {
		return getHessianNpsPurchaseService().getById(id);
	}

	@Override
	public List<NpsPurchaseDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().getBackgroundList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianNpsPurchaseService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianNpsPurchaseService().deleteBatch(list);
	}

	@Override
	public int update(NpsPurchaseDTO t) throws Exception {
		return getHessianNpsPurchaseService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().getBackgroundTotal(map);
	}

	@Override
	public Long insert(NpsPurchaseEntity entity) throws Exception {
		return getHessianNpsPurchaseService().insert(entity);
	}

	@Override
	public List<NpsPurchaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianNpsPurchaseService().getBackgroundList(map);
	}
}