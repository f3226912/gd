package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.WechatUserinfoToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.WechatUserinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatUserinfoEntity;
import com.gudeng.commerce.gd.promotion.service.WechatUserinfoService;

public class WechatUserinfoToolServiceImpl implements WechatUserinfoToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static WechatUserinfoService wechatUserinfoService;

	protected WechatUserinfoService getHessianWechatUserinfoService() throws MalformedURLException {
		String url = gdProperties.getWechatUserinfoServiceUrl();
		if (wechatUserinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			wechatUserinfoService = (WechatUserinfoService) factory.create(WechatUserinfoService.class, url);
		}
		return wechatUserinfoService;
	}

	@Override
	public WechatUserinfoDTO getById(String id) throws Exception {
		return getHessianWechatUserinfoService().getById(id);
	}

	@Override
	public List<WechatUserinfoDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianWechatUserinfoService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianWechatUserinfoService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianWechatUserinfoService().deleteBatch(list);
	}

	@Override
	public int update(WechatUserinfoDTO t) throws Exception {
		return getHessianWechatUserinfoService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianWechatUserinfoService().getTotal(map);
	}

	@Override
	public Long insert(WechatUserinfoEntity entity) throws Exception {
		return getHessianWechatUserinfoService().persist(entity);
	}

	@Override
	public List<WechatUserinfoDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianWechatUserinfoService().getListPage(map);
	}
}