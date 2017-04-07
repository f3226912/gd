package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.GrdProMemberInvitedRegisterToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProMemberInvitedRegisterDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProMemberInvitedRegisterEntity;
import com.gudeng.commerce.gd.bi.service.GrdProMemberInvitedRegisterService;

public class GrdProMemberInvitedRegisterToolServiceImpl implements GrdProMemberInvitedRegisterToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProMemberInvitedRegisterService grdProMemberInvitedRegisterService;

	protected GrdProMemberInvitedRegisterService getHessianGrdProMemberInvitedRegisterService() throws MalformedURLException {
		String url = gdProperties.getGrdProMemberInvitedRegisterServiceUrl();
		if (grdProMemberInvitedRegisterService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProMemberInvitedRegisterService = (GrdProMemberInvitedRegisterService) factory.create(GrdProMemberInvitedRegisterService.class, url);
		}
		return grdProMemberInvitedRegisterService;
	}

	@Override
	public GrdProMemberInvitedRegisterDTO getById(String id) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().getById(id);
	}

	@Override
	public List<GrdProMemberInvitedRegisterDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().deleteBatch(list);
	}

	@Override
	public int update(GrdProMemberInvitedRegisterDTO t) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().getTotal(map);
	}

	@Override
	public Long insert(GrdProMemberInvitedRegisterEntity entity) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().insert(entity);
	}

	@Override
	public List<GrdProMemberInvitedRegisterDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProMemberInvitedRegisterService().getListPage(map);
	}
}