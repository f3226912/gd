package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.SensitiveWordToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.SensitiveWordDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveWordEntity;
import com.gudeng.commerce.gd.customer.service.SensitiveWordService;

public class SensitiveWordToolServiceImpl implements SensitiveWordToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static SensitiveWordService sensitiveWordService;

	protected SensitiveWordService getHessianSensitiveWordService() throws MalformedURLException {
		String url = gdProperties.getSensitiveWordServiceUrl();
		if (sensitiveWordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sensitiveWordService = (SensitiveWordService) factory.create(SensitiveWordService.class, url);
		}
		return sensitiveWordService;
	}

	@Override
	public SensitiveWordDTO getById(String id) throws Exception {
		return getHessianSensitiveWordService().getById(id);
	}

	@Override
	public List<SensitiveWordDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianSensitiveWordService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianSensitiveWordService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianSensitiveWordService().deleteBatch(list);
	}

	@Override
	public int update(SensitiveWordDTO t) throws Exception {
		return getHessianSensitiveWordService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianSensitiveWordService().getTotal(map);
	}

	@Override
	public Long insert(SensitiveWordEntity entity) throws Exception {
		return getHessianSensitiveWordService().insert(entity);
	}

	@Override
	public List<SensitiveWordDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianSensitiveWordService().getListPage(map);
	}
}