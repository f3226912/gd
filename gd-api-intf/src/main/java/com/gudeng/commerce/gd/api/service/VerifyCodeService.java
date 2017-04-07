package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

public interface VerifyCodeService {

	public void getVerifyCode(String mobile, String typeStr, String encryptStr,
			MemberBaseinfoDTO memberBaseinfoDTO) throws Exception;

	public void verifyByMobile(String verifyType, String mobile,
			String verifyCode)throws Exception;

	public void verifyBySid(String verifyType, String sid, String verifyCode)throws Exception;

}
