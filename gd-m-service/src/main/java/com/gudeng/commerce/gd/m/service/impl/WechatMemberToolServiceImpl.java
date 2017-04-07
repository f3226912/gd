package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.WechatMemberToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatMemberEntity;
import com.gudeng.commerce.gd.promotion.service.WechatMemberService;

public class WechatMemberToolServiceImpl implements WechatMemberToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static WechatMemberService wechatMemberService;

	protected WechatMemberService getHessianWechatMemberService() throws MalformedURLException {
		String url = gdProperties.getWechatMemberServiceUrl();
		if (wechatMemberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			wechatMemberService = (WechatMemberService) factory.create(WechatMemberService.class, url);
		}
		return wechatMemberService;
	}

	@Override
	public WechatMemberDTO getById(String id) throws Exception {
		return getHessianWechatMemberService().getById(id);
	}

	@Override
	public List<WechatMemberDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianWechatMemberService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianWechatMemberService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianWechatMemberService().deleteBatch(list);
	}

	@Override
	public int update(WechatMemberDTO t) throws Exception {
		return getHessianWechatMemberService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianWechatMemberService().getTotal(map);
	}

	@Override
	public Long insert(WechatMemberEntity entity) throws Exception {
		return getHessianWechatMemberService().persist(entity);
	}

	@Override
	public List<WechatMemberDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianWechatMemberService().getListPage(map);
	}
}