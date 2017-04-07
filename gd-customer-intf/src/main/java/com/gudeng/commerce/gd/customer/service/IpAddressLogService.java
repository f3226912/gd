package com.gudeng.commerce.gd.customer.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.IpAddressLogDTO;

public interface IpAddressLogService {

	public int countTotal(Map map) throws Exception;
	
	public int save(IpAddressLogDTO addressLogDTO) throws Exception;
}
