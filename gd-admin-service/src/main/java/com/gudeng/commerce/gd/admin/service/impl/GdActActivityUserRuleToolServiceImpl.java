package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityUserRuleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityUserRuleService;

public class GdActActivityUserRuleToolServiceImpl implements GdActActivityUserRuleToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GdActActivityUserRuleService gdActActivityUserRuleService;

	protected GdActActivityUserRuleService getHessianGdActActivityUserRuleService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityUserRuleServiceUrl();
		if (gdActActivityUserRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityUserRuleService = (GdActActivityUserRuleService) factory.create(GdActActivityUserRuleService.class, url);
		}
		return gdActActivityUserRuleService;
	}

	@Override
	public GdActActivityUserRuleDTO getById(String id) throws Exception {
		return getHessianGdActActivityUserRuleService().getById(id);
	}

	@Override
	public List<GdActActivityUserRuleDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityUserRuleService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityUserRuleService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityUserRuleService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityUserRuleDTO t) throws Exception {
		return getHessianGdActActivityUserRuleService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityUserRuleService().getTotal(map);
	}

	@Override
	public Long insert(GdActActivityUserRuleEntity entity) throws Exception {
		return getHessianGdActActivityUserRuleService().insert(entity);
	}

	@Override
	public List<GdActActivityUserRuleDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityUserRuleService().getListPage(map);
	}
}