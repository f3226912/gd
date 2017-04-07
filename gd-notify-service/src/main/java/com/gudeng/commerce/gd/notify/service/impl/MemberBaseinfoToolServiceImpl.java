package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.notify.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */
public class MemberBaseinfoToolServiceImpl implements MemberBaseinfoToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberBaseinfoToolServiceImpl.class);

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	
	private static MemberBaseinfoService memberBaseinfoService;

	protected MemberBaseinfoService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getMemberUrl();
		logger.info(url);
		if (memberBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}

	@Override
	public int updateMember(MemberBaseinfoDTO mb) throws Exception {
		return getHessianMemberService().updateMemberBaseinfoDTO(mb);
	}
	
	@Override
	public MemberBaseinfoDTO getMember(String memberId) throws Exception {
		return getHessianMemberService().getById(memberId);
	}

	@Override
	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity memberEntity) throws Exception {
		return getHessianMemberService().addMemberBaseinfoEnt(memberEntity);
	}

}