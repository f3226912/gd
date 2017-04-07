package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.WechatLoginToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.WechatLoginDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatLoginEntity;
import com.gudeng.commerce.gd.promotion.service.WechatLoginService;

public class WechatLoginToolServiceImpl implements WechatLoginToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static WechatLoginService wechatLoginService;

	protected WechatLoginService getHessianWechatLoginService() throws MalformedURLException {
		String url = gdProperties.getWechatLoginServiceUrl();
		if (wechatLoginService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			wechatLoginService = (WechatLoginService) factory.create(WechatLoginService.class, url);
		}
		return wechatLoginService;
	}

	@Override
	public WechatLoginDTO getById(String id) throws Exception {
		return getHessianWechatLoginService().getById(id);
	}

	@Override
	public List<WechatLoginDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianWechatLoginService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianWechatLoginService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianWechatLoginService().deleteBatch(list);
	}

	@Override
	public int update(WechatLoginDTO t) throws Exception {
		return getHessianWechatLoginService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianWechatLoginService().getTotal(map);
	}

	@Override
	public List<WechatLoginDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianWechatLoginService().getListPage(map);
	}

	@Override
	public Long insert(WechatLoginEntity entity) throws Exception {
		return getHessianWechatLoginService().persist(entity);
	}
}