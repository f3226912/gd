package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;


/** 
 *功能描述：
 */
@Service
public class MemberCertifiToolServiceImpl implements MemberCertifiToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberCertifiService memberBaseinfoService;

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberCertifiService getHessianMemberCertifiService() throws MalformedURLException {
		String url = gdProperties.getMemberCertifiUrl();
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberCertifiService) factory.create(MemberCertifiService.class, url);
		}
		return memberBaseinfoService;
	}

	@Override
	public int addMemberCertifiDTO(MemberCertifiDTO mb) throws Exception {
		return getHessianMemberCertifiService().addMemberCertifiDTO(mb);
//		return 0;
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianMemberCertifiService().deleteById(id);
	}

	@Override
	public int updateMemberCertifiDTO(MemberCertifiDTO mb) throws Exception {
		return getHessianMemberCertifiService().updateMemberCertifiDTO(mb);
	}

	@Override
	public MemberCertifiDTO getById(String id) throws Exception {
		return getHessianMemberCertifiService().getById(id);
	}
	
	@Override
	public MemberCertifiDTO getByUserId(String string) throws Exception {
		return getHessianMemberCertifiService().getByUserId(string);
	}

	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int getTotal(Map map) throws Exception {
		return getHessianMemberCertifiService().getTotal(map);
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<MemberCertifiDTO> getBySearch(Map map) throws Exception {
		return getHessianMemberCertifiService().getBySearch(map);
	}

	@Override
	public List<MemberCertifiDTO> getNstListBySearch(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberCertifiService().getNstListBySearch(map);
	}

	@Override
	public int getNstTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberCertifiService().getNstTotal(map);
	}

	@Override
	public List<MemberCertifiDTO> getList(Map map) throws Exception {
		return getHessianMemberCertifiService().getList(map);
	}
	
	 
}
