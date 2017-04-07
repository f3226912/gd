package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGdGiftService;

public class GrdGdGiftToolServiceImpl implements GrdGdGiftToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGdGiftService grdGdGiftService;

	protected GrdGdGiftService getHessianGrdGdGiftService() throws MalformedURLException {
		String url = gdProperties.getGrdGdGiftServiceUrl();
		if (grdGdGiftService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGdGiftService = (GrdGdGiftService) factory.create(GrdGdGiftService.class, url);
		}
		return grdGdGiftService;
	}

	@Override
	public GrdGdGiftDTO getById(String id) throws Exception {
		return getHessianGrdGdGiftService().getById(id);
	}

	@Override
	public List<GrdGdGiftDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGdGiftService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdGdGiftService().deleteBatch(list);
	}

	@Override
	public int update(GrdGdGiftDTO t) throws Exception {
		return getHessianGrdGdGiftService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftService().getTotal(map);
	}

	@Override
	public Long insert(GrdGdGiftEntity entity) throws Exception {
		return getHessianGrdGdGiftService().insert(entity);
	}

	@Override
	public List<GrdGdGiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGdGiftService().getListPage(map);
	}
	
	@Override
	public int getTotalByPurchase(Map<String, Object> map) throws Exception{
		return getHessianGrdGdGiftService().getTotalByPurchase(map);
	}


	@Override
	public List<GrdGdGiftDTO> getListPageByPurchase(Map<String, Object> map) throws Exception{
		return getHessianGrdGdGiftService().getListPageByPurchase(map);
	}
	
	@Override
	public Integer getMaxId() throws Exception{
		return getHessianGrdGdGiftService().getMaxId();
	}
	
}