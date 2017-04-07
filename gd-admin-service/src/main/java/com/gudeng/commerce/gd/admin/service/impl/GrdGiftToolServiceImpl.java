package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGiftToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftService;

public class GrdGiftToolServiceImpl implements GrdGiftToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGiftService grdGiftService;

	protected GrdGiftService getHessianGrdGiftService() throws MalformedURLException {
		String url = gdProperties.getGrdGiftServiceUrl();
		if (grdGiftService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGiftService = (GrdGiftService) factory.create(GrdGiftService.class, url);
		}
		return grdGiftService;
	}

	@Override
	public GrdGiftDTO getById(String id) throws Exception {
		return getHessianGrdGiftService().getById(id);
	}

	@Override
	public List<GrdGiftDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGiftService().deleteById(id);
	}

	@Override
	public int update(GrdGiftDTO t) throws Exception {
		return getHessianGrdGiftService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getTotal(map);
	}

	@Override
	public Long insert(GrdGiftEntity entity) throws Exception {
		return getHessianGrdGiftService().insert(entity);
	}

	@Override
	public List<GrdGiftDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getListPage(map);
	}

	@Override
	public int batchDelete(List<String> list) throws Exception {
		return getHessianGrdGiftService().batchDelete(list);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNoCount(String giftId) throws Exception {
		return getHessianGrdGiftService().getNoCount(giftId);
	}

	@Override
	public int getGrdGiftRecordCount(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftService().getGrdGiftRecordCount(map);
	}
}