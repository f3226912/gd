package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.task.service.MqAsyncErrorToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MqAsyncErrorDTO;
import com.gudeng.commerce.gd.customer.entity.MqAsyncErrorEntity;
import com.gudeng.commerce.gd.customer.service.MqAsyncErrorService;

public class MqAsyncErrorToolServiceImpl implements MqAsyncErrorToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static MqAsyncErrorService mqAsyncErrorService;

	protected MqAsyncErrorService getHessianMqAsyncErrorService() throws MalformedURLException {
		String url = gdProperties.getMqAsyncErrorServiceUrl();
		if (mqAsyncErrorService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			mqAsyncErrorService = (MqAsyncErrorService) factory.create(MqAsyncErrorService.class, url);
		}
		return mqAsyncErrorService;
	}

	@Override
	public MqAsyncErrorDTO getById(String id) throws Exception {
		return getHessianMqAsyncErrorService().getById(id);
	}

	@Override
	public List<MqAsyncErrorDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianMqAsyncErrorService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianMqAsyncErrorService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianMqAsyncErrorService().deleteBatch(list);
	}

	@Override
	public int update(MqAsyncErrorDTO t) throws Exception {
		return getHessianMqAsyncErrorService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMqAsyncErrorService().getTotal(map);
	}

	@Override
	public Long insert(MqAsyncErrorEntity entity) throws Exception {
		return getHessianMqAsyncErrorService().insert(entity);
	}

	@Override
	public List<MqAsyncErrorDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianMqAsyncErrorService().getListPage(map);
	}
}