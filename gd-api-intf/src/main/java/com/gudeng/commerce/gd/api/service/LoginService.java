package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

public interface LoginService {
	
	public String  handleLogin(MemberBaseinfoDTO memberBaseinfoDTO) throws Exception;

	public void  handleLogout(String sid) throws Exception;
}
