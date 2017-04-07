package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProCallstatisticsToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProCallstatisticsDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProCallstatisticsEntity;
import com.gudeng.commerce.gd.bi.service.GrdProCallstatisticsService;

public class GrdProCallstatisticsToolServiceImpl implements GrdProCallstatisticsToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProCallstatisticsService grdProCallstatisticsService;

	protected GrdProCallstatisticsService getHessianGrdProCallstatisticsService() throws MalformedURLException {
		String url = gdProperties.getGrdProCallstatisticsServiceUrl();
		if (grdProCallstatisticsService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProCallstatisticsService = (GrdProCallstatisticsService) factory.create(GrdProCallstatisticsService.class, url);
		}
		return grdProCallstatisticsService;
	}

	@Override
	public GrdProCallstatisticsDTO getById(String id) throws Exception {
		return getHessianGrdProCallstatisticsService().getById(id);
	}

	@Override
	public List<GrdProCallstatisticsDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProCallstatisticsService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProCallstatisticsService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProCallstatisticsService().deleteBatch(list);
	}

	@Override
	public int update(GrdProCallstatisticsDTO t) throws Exception {
		return getHessianGrdProCallstatisticsService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProCallstatisticsService().getTotal(map);
	}

	@Override
	public Long insert(GrdProCallstatisticsEntity entity) throws Exception {
		return getHessianGrdProCallstatisticsService().insert(entity);
	}

	@Override
	public List<GrdProCallstatisticsDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProCallstatisticsService().getListPage(map);
	}
}