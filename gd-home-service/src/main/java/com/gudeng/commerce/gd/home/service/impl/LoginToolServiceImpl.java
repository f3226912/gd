package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.home.service.LoginToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class LoginToolServiceImpl implements LoginToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberBaseinfoService memberBaseinfoService;

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberBaseinfoService getHessianMemberBaseinfoService()
			throws MalformedURLException {
		String url = gdProperties.getMemberBaseinfoUrl();
		if (memberBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(
					MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}
	
	
	@Override
	public MemberBaseinfoDTO getLogin(Map<String, Object> map) throws Exception {
		return getHessianMemberBaseinfoService().getLogin(map);
	}
	
	@Override
	public List<MemberBaseinfoDTO> commitLogin(Map<String, Object> map) throws Exception {
		return getHessianMemberBaseinfoService().commitLogin(map);
	}


	@Override
	public Long register(MemberBaseinfoEntity mb) throws Exception {
		return getHessianMemberBaseinfoService().addMemberBaseinfoEnt(mb);
	}
	
	@Override
	public MemberBaseinfoDTO getById(String memberId) throws Exception {
		return getHessianMemberBaseinfoService().getById(memberId);
	}


	@Override
	public int updatePassword(MemberBaseinfoDTO mbdto) throws Exception {
		return getHessianMemberBaseinfoService().updateMemberBaseinfoDTO(mbdto);
	}

	@Override
	public MemberBaseinfoDTO checkUser(Map<String, Object> map) throws Exception {
		return  getHessianMemberBaseinfoService().checkRegister(map);
	}


}
