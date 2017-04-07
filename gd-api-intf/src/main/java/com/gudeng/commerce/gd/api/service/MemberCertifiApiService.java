package com.gudeng.commerce.gd.api.service;


import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;

public interface MemberCertifiApiService {
	public MemberCertifiDTO getByUserId(Long id) throws Exception;

	public int addMemberCertifiDTO(MemberCertifiDTO mc)throws Exception;

	public int updateMemberCertifiDTO(MemberCertifiDTO mc)throws Exception;

	public int getMcId(Long id)throws Exception;
	
	public MemberCertifiDTO getById(Long id) throws Exception;
	
	public String queryCertStatus(Long id)throws Exception;

}
