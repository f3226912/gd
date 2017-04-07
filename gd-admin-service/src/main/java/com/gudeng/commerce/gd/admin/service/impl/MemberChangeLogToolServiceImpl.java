package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberChangeLogToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberChangeLogDTO;
import com.gudeng.commerce.gd.customer.service.MemberChangeLogService;

/**
 * 功能描述：
 */
@Service
public class MemberChangeLogToolServiceImpl implements MemberChangeLogToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static MemberChangeLogService memberChangeLogService;

	/**
	 * 功能描述: memberChangeLogService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberChangeLogService getHessianMemberChangeLogService() throws MalformedURLException {
		String url = gdProperties.getMemberChangeLogUrl();
		if (memberChangeLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberChangeLogService = (MemberChangeLogService) factory.create(MemberChangeLogService.class, url);
		}
		return memberChangeLogService;
	}

	@Override
	public void addMemberChangeLog(Map map) throws Exception {
		getHessianMemberChangeLogService().addMemberChangeLog(map);
		// return 0;
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return getHessianMemberChangeLogService().getTotal(map);
	}

	@Override
	public List<MemberChangeLogDTO> getBySearch(Map map) throws Exception {
		return getHessianMemberChangeLogService().getBySearch(map);
	}
}
