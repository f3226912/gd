package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProPersonalAuthToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProPersonalAuthDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPersonalAuthEntity;
import com.gudeng.commerce.gd.bi.service.GrdProPersonalAuthService;

public class GrdProPersonalAuthToolServiceImpl implements GrdProPersonalAuthToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProPersonalAuthService grdProPersonalAuthService;

	protected GrdProPersonalAuthService getHessianGrdProPersonalAuthService() throws MalformedURLException {
		String url = gdProperties.getGrdProPersonalAuthServiceUrl();
		if (grdProPersonalAuthService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProPersonalAuthService = (GrdProPersonalAuthService) factory.create(GrdProPersonalAuthService.class, url);
		}
		return grdProPersonalAuthService;
	}

	@Override
	public GrdProPersonalAuthDTO getById(String id) throws Exception {
		return getHessianGrdProPersonalAuthService().getById(id);
	}

	@Override
	public List<GrdProPersonalAuthDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProPersonalAuthService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProPersonalAuthService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProPersonalAuthService().deleteBatch(list);
	}

	@Override
	public int update(GrdProPersonalAuthDTO t) throws Exception {
		return getHessianGrdProPersonalAuthService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProPersonalAuthService().getTotal(map);
	}

	@Override
	public Long insert(GrdProPersonalAuthEntity entity) throws Exception {
		return getHessianGrdProPersonalAuthService().insert(entity);
	}

	@Override
	public List<GrdProPersonalAuthDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProPersonalAuthService().getListPage(map);
	}
}