package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;



import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;

public interface CashRequestToolService {
	
	public int	 add(CashRequestDTO cashRequestDTO) throws MalformedURLException;
	public  List<CashRequestDTO>	 getByMemberId(Map map) throws MalformedURLException;
	@SuppressWarnings("rawtypes")
	public Integer getTotal(Map map) throws Exception;
	
	public void withdraw(CashRequestDTO cashRequestDTO) throws Exception;
	
}
