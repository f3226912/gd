package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GdActActivityParticipationRuleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityParticipationRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityParticipationRuleEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityParticipationRuleService;

public class GdActActivityParticipationRuleToolServiceImpl implements GdActActivityParticipationRuleToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GdActActivityParticipationRuleService gdActActivityParticipationRuleService;

	protected GdActActivityParticipationRuleService getHessianGdActActivityParticipationRuleService() throws MalformedURLException {
		String url = gdProperties.getGdActActivityParticipationRuleServiceUrl();
		if (gdActActivityParticipationRuleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdActActivityParticipationRuleService = (GdActActivityParticipationRuleService) factory.create(GdActActivityParticipationRuleService.class, url);
		}
		return gdActActivityParticipationRuleService;
	}

	@Override
	public GdActActivityParticipationRuleDTO getById(String id) throws Exception {
		return getHessianGdActActivityParticipationRuleService().getById(id);
	}

	@Override
	public List<GdActActivityParticipationRuleDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityParticipationRuleService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGdActActivityParticipationRuleService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGdActActivityParticipationRuleService().deleteBatch(list);
	}

	@Override
	public int update(GdActActivityParticipationRuleDTO t) throws Exception {
		return getHessianGdActActivityParticipationRuleService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityParticipationRuleService().getTotal(map);
	}

	@Override
	public void insert(GdActActivityParticipationRuleEntity entity) throws Exception {
		getHessianGdActActivityParticipationRuleService().insert(entity);
	}

	@Override
	public List<GdActActivityParticipationRuleDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGdActActivityParticipationRuleService().getListPage(map);
	}
}