package com.gudeng.commerce.gd.api.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

public interface MemberBaseinfoToolService {

	public MemberBaseinfoDTO getById(String memberId)throws Exception;
}
