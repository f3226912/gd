package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.WechatStarsToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.WechatStarsDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatStarsEntity;
import com.gudeng.commerce.gd.promotion.service.WechatStarsService;

public class WechatStarsToolServiceImpl implements WechatStarsToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static WechatStarsService wechatStarsService;

	protected WechatStarsService getHessianWechatStarsService() throws MalformedURLException {
		String url = gdProperties.getWechatStarsServiceUrl();
		if (wechatStarsService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			wechatStarsService = (WechatStarsService) factory.create(WechatStarsService.class, url);
		}
		return wechatStarsService;
	}

	@Override
	public WechatStarsDTO getById(String id) throws Exception {
		return getHessianWechatStarsService().getById(id);
	}

	@Override
	public List<WechatStarsDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianWechatStarsService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianWechatStarsService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianWechatStarsService().deleteBatch(list);
	}

	@Override
	public int update(WechatStarsDTO t) throws Exception {
		return getHessianWechatStarsService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianWechatStarsService().getTotal(map);
	}

	@Override
	public Long insert(WechatStarsEntity entity) throws Exception {
		return getHessianWechatStarsService().persist(entity);
	}

	@Override
	public List<WechatStarsDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianWechatStarsService().getListPage(map);
	}
}