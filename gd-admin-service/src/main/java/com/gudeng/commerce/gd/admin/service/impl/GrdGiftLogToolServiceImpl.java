package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdGiftLogToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftLogService;

public class GrdGiftLogToolServiceImpl implements GrdGiftLogToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdGiftLogService grdGiftLogService;

	protected GrdGiftLogService getHessianGrdGiftLogService() throws MalformedURLException {
		String url = gdProperties.getGrdGiftLogServiceUrl();
		if (grdGiftLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdGiftLogService = (GrdGiftLogService) factory.create(GrdGiftLogService.class, url);
		}
		return grdGiftLogService;
	}

	@Override
	public GrdGiftLogDTO getById(String id) throws Exception {
		return getHessianGrdGiftLogService().getById(id);
	}

	@Override
	public List<GrdGiftLogDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftLogService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdGiftLogService().deleteById(id);
	}

	@Override
	public int update(GrdGiftLogDTO t) throws Exception {
		return getHessianGrdGiftLogService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftLogService().getTotal(map);
	}

	@Override
	public Long insert(GrdGiftLogEntity entity) throws Exception {
		return getHessianGrdGiftLogService().insert(entity);
	}

	@Override
	public List<GrdGiftLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdGiftLogService().getListPage(map);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}