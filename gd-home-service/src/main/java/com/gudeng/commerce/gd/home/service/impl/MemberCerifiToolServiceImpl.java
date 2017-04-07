package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;
import com.gudeng.commerce.gd.home.service.MemberCerifiToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

@Service
public class MemberCerifiToolServiceImpl implements MemberCerifiToolService {
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberCertifiService certifiService;

	/**
	 * 功能描述:certifiService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberCertifiService getHessianMemberCertifiService() throws MalformedURLException {
		String url = gdProperties.getMemberCertifiServiceUrl();
		if(certifiService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifiService = (MemberCertifiService) factory.create(MemberCertifiService.class, url);
		}
		return certifiService;
	}

	@Override
	public MemberCertifiDTO getById(String id) throws Exception {
		return getHessianMemberCertifiService().getById(id);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMemberCertifiService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianMemberCertifiService().deleteById(id);
	}

	@Override
	public int addMemberCertifiByMap(Map<String, Object> map) throws Exception {
		return getHessianMemberCertifiService().addMemberCertifiByMap(map);
	}

	@Override
	public int addMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		return getHessianMemberCertifiService().addMemberCertifiDTO(mc);
	}

	@Override
	public int updateMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		return getHessianMemberCertifiService().updateMemberCertifiDTO(mc);
	}

	@Override
	public List<MemberCertifiDTO> getBySearch(Map map) throws Exception {
		return getHessianMemberCertifiService().getBySearch(map);
	}

	@Override
	public MemberCertifiDTO getByUserId(String string) throws Exception {
		return getHessianMemberCertifiService().getByUserId(string);
	}
}
