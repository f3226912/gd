package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftteamToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftteamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftteamEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftteamService;

public class GrdGdGiftteamToolServiceImpl implements GrdGdGiftteamToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGdGiftteamService grdGdGiftteamService;

	protected GrdGdGiftteamService getHessianGrdGdGiftteamService() throws MalformedURLException {
		String url = gdProperties.getGrdGdGiftteamServiceUrl();
		if (grdGdGiftteamService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGdGiftteamService = (GrdGdGiftteamService) factory.create(GrdGdGiftteamService.class, url);
		}
		return grdGdGiftteamService;
	}

	@Override
	public GrdGdGiftteamDTO getById(String id) throws Exception {
		return getHessianGrdGdGiftteamService().getById(id);
	}

	@Override
	public List<GrdGdGiftteamDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftteamService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGdGiftteamService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdGdGiftteamService().deleteBatch(list);
	}

	@Override
	public int update(GrdGdGiftteamDTO t) throws Exception {
		return getHessianGrdGdGiftteamService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftteamService().getTotal(map);
	}

	@Override
	public Long insert(GrdGdGiftteamEntity entity) throws Exception {
		return getHessianGrdGdGiftteamService().insert(entity);
	}

	@Override
	public List<GrdGdGiftteamDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftteamService().getListPage(map);
	}
}