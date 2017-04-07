package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.MemberPageStatisticToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;
import com.gudeng.commerce.gd.customer.service.statis.MemberPageStatisticService;

/**
 * 金牌会员统计业务
 * 
 * @author Ailen
 *
 */
public class MemberPageStatisticToolServiceImpl implements MemberPageStatisticToolService {
	@Autowired
	public GdProperties gdProperties;
	private static MemberPageStatisticService memberPageStatisticService;

	private MemberPageStatisticService getHessionMemberPageStatisticService() throws MalformedURLException {
		String hessianUrl = gdProperties.getMemberPageStatisticServiceUrl();
		System.out.println("hessianUrl*************" + hessianUrl);
		if (memberPageStatisticService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberPageStatisticService = (MemberPageStatisticService) factory.create(MemberPageStatisticService.class,
					hessianUrl);
		}
		return memberPageStatisticService;
	}

	/**
	 * 添加金牌会员页面统计数据
	 * 
	 * @param pageStatisMemberEntity
	 */
	@Override
	public void addMemberPage(PageStatisMemberEntity pageStatisMemberEntity) throws Exception {
		getHessionMemberPageStatisticService().addMemberPage(pageStatisMemberEntity);
	}

}
