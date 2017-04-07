package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityRulesToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityRulesDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityRulesEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityRulesService;

public class GdActActivityRulesToolServiceImpl implements GdActActivityRulesToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GdActActivityRulesService gdActActivityRulesService;

	protected GdActActivityRulesService getHessianGdActActivityRulesService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityRulesServiceUrl();
		if (gdActActivityRulesService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityRulesService = (GdActActivityRulesService) factory.create(GdActActivityRulesService.class, url);
		}
		return gdActActivityRulesService;
	}

	@Override
	public GdActActivityRulesDTO getById(String id) throws Exception {
		return getHessianGdActActivityRulesService().getById(id);
	}

	@Override
	public List<GdActActivityRulesDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityRulesService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityRulesService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityRulesService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityRulesDTO t) throws Exception {
		return getHessianGdActActivityRulesService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityRulesService().getTotal(map);
	}

	@Override
	public Long insert(GdActActivityRulesEntity entity) throws Exception {
		return getHessianGdActActivityRulesService().insert(entity);
	}

	@Override
	public List<GdActActivityRulesDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityRulesService().getListPage(map);
	}
}