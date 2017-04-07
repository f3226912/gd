package com.gudeng.commerce.gd.customer.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.IpAddressBlackDTO;

public interface IpAddressBlackService {

	public int countTotal(Map map) throws Exception;
	
	public int save(IpAddressBlackDTO addressBlackDTO) throws Exception;
}
