package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.home.service.MemberLoginLogToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberLoginLogDTO;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;
import com.gudeng.commerce.gd.customer.service.MemberLoginLogService;

public class MemberLoginLogToolServiceImpl implements MemberLoginLogToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static MemberLoginLogService memberLoginLogService;

	protected MemberLoginLogService getHessianMemberLoginLogService() throws MalformedURLException {
		String url = gdProperties.getMemberLoginLogServiceUrl();
		if (memberLoginLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberLoginLogService = (MemberLoginLogService) factory.create(MemberLoginLogService.class, url);
		}
		return memberLoginLogService;
	}

	@Override
	public MemberLoginLogDTO getById(String id) throws Exception {
		return getHessianMemberLoginLogService().getById(id);
	}

	@Override
	public List<MemberLoginLogDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianMemberLoginLogService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianMemberLoginLogService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianMemberLoginLogService().deleteBatch(list);
	}

	@Override
	public int update(MemberLoginLogDTO t) throws Exception {
		return getHessianMemberLoginLogService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMemberLoginLogService().getTotal(map);
	}

	@Override
	public Long insert(MemberLoginLogEntity entity) throws Exception {
		return getHessianMemberLoginLogService().insert(entity);
	}

	@Override
	public List<MemberLoginLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianMemberLoginLogService().getListPage(map);
	}
}