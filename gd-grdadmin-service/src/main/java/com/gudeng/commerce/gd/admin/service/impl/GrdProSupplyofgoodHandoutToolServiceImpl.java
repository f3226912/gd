package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProSupplyofgoodHandoutToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProSupplyofgoodHandoutDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProSupplyofgoodHandoutEntity;
import com.gudeng.commerce.gd.bi.service.GrdProSupplyofgoodHandoutService;

public class GrdProSupplyofgoodHandoutToolServiceImpl implements GrdProSupplyofgoodHandoutToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProSupplyofgoodHandoutService grdProSupplyofgoodHandoutService;

	protected GrdProSupplyofgoodHandoutService getHessianGrdProSupplyofgoodHandoutService() throws MalformedURLException {
		String url = gdProperties.getGrdProSupplyofgoodHandoutServiceUrl();
		if (grdProSupplyofgoodHandoutService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProSupplyofgoodHandoutService = (GrdProSupplyofgoodHandoutService) factory.create(GrdProSupplyofgoodHandoutService.class, url);
		}
		return grdProSupplyofgoodHandoutService;
	}

	@Override
	public GrdProSupplyofgoodHandoutDTO getById(String id) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().getById(id);
	}

	@Override
	public List<GrdProSupplyofgoodHandoutDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().deleteBatch(list);
	}

	@Override
	public int update(GrdProSupplyofgoodHandoutDTO t) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().getTotal(map);
	}

	@Override
	public Long insert(GrdProSupplyofgoodHandoutEntity entity) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().insert(entity);
	}

	@Override
	public List<GrdProSupplyofgoodHandoutDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProSupplyofgoodHandoutService().getListPage(map);
	}
}