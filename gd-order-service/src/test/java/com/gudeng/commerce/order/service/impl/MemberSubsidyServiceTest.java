package com.gudeng.commerce.order.service.impl;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.service.MemberSubsidyService;

import junit.framework.TestCase;

/**
 * @Description 会员补贴统计 junit
 * @Project gd-admin-intf
 * @ClassName MemberSubsidyServiceTest.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月9日 下午12:10:20
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class MemberSubsidyServiceTest extends TestCase {

	private static MemberSubsidyService memberSubsidyService;

	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-order/service/memberSubsidyService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		MemberSubsidyServiceTest.memberSubsidyService = (MemberSubsidyService) factory.create(MemberSubsidyService.class, url);
	}
	
	@Test
	public void testQuery() {
		System.out.println(memberSubsidyService);
	}

}
