package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberMessageFlagToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.MemberMessageFlagEntity;
import com.gudeng.commerce.gd.customer.service.MemberMessageFlagService;

/**
 * 功能描述：
 */
@Service
public class MemberMessageFlagToolServiceImpl implements MemberMessageFlagToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberMessageFlagService memberMessageFlagService;

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberMessageFlagService getHessianMemberMessageFlagService() throws MalformedURLException {
		String url = gdProperties.getMemberMessageFlagUrl();
		if (memberMessageFlagService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberMessageFlagService = (MemberMessageFlagService) factory.create(MemberMessageFlagService.class, url);
		}
		return memberMessageFlagService;
	}

	
	@Override
	public Number insert(MemberMessageFlagEntity entity) throws Exception {
		return getHessianMemberMessageFlagService().insert(entity);
	}

	@Override
	public int update(MemberMessageFlagEntity entity) throws Exception {
		return getHessianMemberMessageFlagService().update(entity);
	}
}
