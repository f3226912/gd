package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityDistributionModeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityDistributionModeEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityDistributionModeService;

public class GdActActivityDistributionModeToolServiceImpl implements GdActActivityDistributionModeToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GdActActivityDistributionModeService gdActActivityDistributionModeService;

	protected GdActActivityDistributionModeService getHessianGdActActivityDistributionModeService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityDistributionModeServiceUrl();
		if (gdActActivityDistributionModeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityDistributionModeService = (GdActActivityDistributionModeService) factory.create(GdActActivityDistributionModeService.class, url);
		}
		return gdActActivityDistributionModeService;
	}

	@Override
	public GdActActivityDistributionModeDTO getById(String id) throws Exception {
		return getHessianGdActActivityDistributionModeService().getById(id);
	}

	@Override
	public List<GdActActivityDistributionModeDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityDistributionModeService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityDistributionModeService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityDistributionModeService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityDistributionModeDTO t) throws Exception {
		return getHessianGdActActivityDistributionModeService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityDistributionModeService().getTotal(map);
	}

	@Override
	public Long insert(GdActActivityDistributionModeEntity entity) throws Exception {
		return getHessianGdActActivityDistributionModeService().insert(entity);
	}

	@Override
	public List<GdActActivityDistributionModeDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityDistributionModeService().getListPage(map);
	}
}