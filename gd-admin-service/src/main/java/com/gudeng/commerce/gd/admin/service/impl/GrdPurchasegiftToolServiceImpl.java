package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdPurchasegiftToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdPurchasegiftService;

public class GrdPurchasegiftToolServiceImpl implements GrdPurchasegiftToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdPurchasegiftService grdPurchasegiftService;

	protected GrdPurchasegiftService getHessianGrdPurchasegiftService() throws MalformedURLException {
		String url = gdProperties.getGrdPurchasegiftServiceUrl();
		if (grdPurchasegiftService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdPurchasegiftService = (GrdPurchasegiftService) factory.create(GrdPurchasegiftService.class, url);
		}
		return grdPurchasegiftService;
	}

	@Override
	public GrdPurchasegiftDTO getById(String id) throws Exception {
		return getHessianGrdPurchasegiftService().getById(id);
	}

	@Override
	public List<GrdPurchasegiftDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchasegiftService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdPurchasegiftService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdPurchasegiftService().deleteBatch(list);
	}

	@Override
	public int update(GrdPurchasegiftDTO t) throws Exception {
		return getHessianGrdPurchasegiftService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchasegiftService().getTotal(map);
	}

	@Override
	public Long insert(GrdPurchasegiftEntity entity) throws Exception {
		return getHessianGrdPurchasegiftService().insert(entity);
	}

	@Override
	public List<GrdPurchasegiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdPurchasegiftService().getListPage(map);
	}
}