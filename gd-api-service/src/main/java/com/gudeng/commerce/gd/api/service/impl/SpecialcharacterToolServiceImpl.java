package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.SpecialcharacterToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.SpecialcharacterDTO;
import com.gudeng.commerce.gd.customer.entity.SpecialcharacterEntity;
import com.gudeng.commerce.gd.customer.service.SpecialcharacterService;

public class SpecialcharacterToolServiceImpl implements SpecialcharacterToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static SpecialcharacterService specialcharacterService;

	protected SpecialcharacterService getHessianSpecialcharacterService() throws MalformedURLException {
		String url = gdProperties.getSpecialcharacterServiceUrl();
		if (specialcharacterService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			specialcharacterService = (SpecialcharacterService) factory.create(SpecialcharacterService.class, url);
		}
		return specialcharacterService;
	}

	@Override
	public SpecialcharacterDTO getById(String id) throws Exception {
		return getHessianSpecialcharacterService().getById(id);
	}

	@Override
	public List<SpecialcharacterDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianSpecialcharacterService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianSpecialcharacterService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianSpecialcharacterService().deleteBatch(list);
	}

	@Override
	public int update(SpecialcharacterDTO t) throws Exception {
		return getHessianSpecialcharacterService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianSpecialcharacterService().getTotal(map);
	}

	@Override
	public Long insert(SpecialcharacterEntity entity) throws Exception {
		return getHessianSpecialcharacterService().insert(entity);
	}

	@Override
	public List<SpecialcharacterDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianSpecialcharacterService().getListPage(map);
	}
}