package com.gudeng.commerce.gd.home.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.IpAddressBlackDTO;
import com.gudeng.commerce.gd.customer.dto.IpAddressLogDTO;

public interface IpAddressService {
	
    public int countBlackTotal(Map map) throws Exception;
	
	public int saveBlack(IpAddressBlackDTO addressBlackDTO) throws Exception;
     
	public int countLogTotal(Map map) throws Exception;
	
	public int saveLog(IpAddressLogDTO addressLogDTO) throws Exception;
}
