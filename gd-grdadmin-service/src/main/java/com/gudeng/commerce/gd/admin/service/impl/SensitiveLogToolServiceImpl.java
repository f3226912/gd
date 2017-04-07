package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.SensitiveLogToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.SensitiveLogDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveLogEntity;
import com.gudeng.commerce.gd.customer.service.SensitiveLogService;

public class SensitiveLogToolServiceImpl implements SensitiveLogToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static SensitiveLogService sensitiveLogService;

	protected SensitiveLogService getHessianSensitiveLogService() throws MalformedURLException {
		String url = gdProperties.getSensitiveLogServiceUrl();
		if (sensitiveLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sensitiveLogService = (SensitiveLogService) factory.create(SensitiveLogService.class, url);
		}
		return sensitiveLogService;
	}

	@Override
	public SensitiveLogDTO getById(String id) throws Exception {
		return getHessianSensitiveLogService().getById(id);
	}

	@Override
	public List<SensitiveLogDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianSensitiveLogService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianSensitiveLogService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianSensitiveLogService().deleteBatch(list);
	}

	@Override
	public int update(SensitiveLogDTO t) throws Exception {
		return getHessianSensitiveLogService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianSensitiveLogService().getTotal(map);
	}

	@Override
	public Long insert(SensitiveLogEntity entity) throws Exception {
		return getHessianSensitiveLogService().insert(entity);
	}

	@Override
	public List<SensitiveLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianSensitiveLogService().getListPage(map);
	}
}