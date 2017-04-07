package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;

public class MemberCertifiApiServiceImpl implements MemberCertifiApiService {

	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties; 
	
	private static MemberCertifiService memberCertifiApiService;
	
	protected MemberCertifiService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.memberCertifiService.url");
		if(memberCertifiApiService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberCertifiApiService = (MemberCertifiService) factory.create(MemberCertifiService.class, url);
		}
		return  memberCertifiApiService;
	}
	@Override
	public MemberCertifiDTO getByUserId(Long id) throws Exception {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		MemberCertifiDTO mcDto=getHessianMemberService().getByUserId(id+"");
		if(mcDto!=null){
			mcDto.setBzlPhotoUrl_relative(mcDto.getBzlPhotoUrl());
			mcDto.setCardPhotoUrl_relative(mcDto.getCardPhotoUrl());//最前面加上imageHost
			mcDto.setOrgCodePhotoUrl_relative(mcDto.getOrgCodePhotoUrl());
			
			mcDto.setBzlPhotoUrl(imageHost+mcDto.getBzlPhotoUrl());
			mcDto.setCardPhotoUrl(imageHost+mcDto.getCardPhotoUrl());//最前面加上imageHost
			if(mcDto.getCardPhotoUrl()!=null){
				mcDto.setCardPhotoUrl(mcDto.getCardPhotoUrl().replace(",", ","+imageHost )) ;//所有的","后面，加上imageHost
			}
			mcDto.setOrgCodePhotoUrl(imageHost+mcDto.getOrgCodePhotoUrl());
		}
		return mcDto;
	}
	@Override
	public int addMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		return getHessianMemberService().addMemberCertifiDTO(mc);
		
	}
	@Override
	public int updateMemberCertifiDTO(MemberCertifiDTO mc) throws Exception {
		return getHessianMemberService().updateMemberCertifiDTO(mc);
		
	}
	@Override
	public int getMcId(Long id) throws Exception {
		return getHessianMemberService().getMcId(id);
	}
	@Override
	public MemberCertifiDTO getById(Long id) throws Exception {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		MemberCertifiDTO mcDto=getHessianMemberService().getById(String.valueOf(id));
		if(mcDto!=null){
			mcDto.setBzlPhotoUrl_relative(mcDto.getBzlPhotoUrl());
			mcDto.setCardPhotoUrl_relative(mcDto.getCardPhotoUrl());//最前面加上imageHost
			mcDto.setOrgCodePhotoUrl_relative(mcDto.getOrgCodePhotoUrl());
			
			mcDto.setBzlPhotoUrl(imageHost+mcDto.getBzlPhotoUrl());
			mcDto.setCardPhotoUrl(imageHost+mcDto.getCardPhotoUrl());//最前面加上imageHost
			if(mcDto.getCardPhotoUrl()!=null){
				mcDto.setCardPhotoUrl(mcDto.getCardPhotoUrl().replace(",", ","+imageHost )) ;//所有的","后面，加上imageHost
			}
			mcDto.setOrgCodePhotoUrl(imageHost+mcDto.getOrgCodePhotoUrl());
		}
		return  mcDto;
	}
	@Override
	public String queryCertStatus(Long id) throws Exception {
		MemberCertifiDTO mcDto = getHessianMemberService().getByUserId(id + "");
		String status = "0";
		if (mcDto != null) {
			// 先查询农速通认证
			if ("1".equals(mcDto.getNstStatus())) {
				status = mcDto.getNstStatus();
				
			}else if ("1".equals(mcDto.getStatus())) {
			    status = mcDto.getStatus();
			}  
		}
		return status;
	}
}
