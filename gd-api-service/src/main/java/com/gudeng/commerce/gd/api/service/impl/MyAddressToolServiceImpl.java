package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.MyAddressToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MyAddressDTO;
import com.gudeng.commerce.gd.customer.entity.MyAddress;
import com.gudeng.commerce.gd.customer.service.MyAddressService;

public class MyAddressToolServiceImpl implements MyAddressToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static MyAddressService myAddressService;

	protected MyAddressService getHessianMyAddressService() throws MalformedURLException {
		String url = gdProperties.getMyAddressServiceUrl();
		if (myAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			myAddressService = (MyAddressService) factory.create(MyAddressService.class, url);
		}
		return myAddressService;
	}

	@Override
	public MyAddressDTO getById(String id) throws Exception {
		return getHessianMyAddressService().getById(id);
	}

	@Override
	public List<MyAddressDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianMyAddressService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianMyAddressService().deleteById(id);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianMyAddressService().deleteBatch(list);
	}

	@Override
	public int update(MyAddressDTO t) throws Exception {
		return getHessianMyAddressService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMyAddressService().getTotal(map);
	}

	@Override
	public Long insert(MyAddress entity) throws Exception {
		return getHessianMyAddressService().insert(entity);
	}

	@Override
	public List<MyAddressDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianMyAddressService().getListPage(map);
	}

	@Override
	public void prefer(Map<String, Object> map) throws Exception {
		getHessianMyAddressService().prefer(map);
	}
}