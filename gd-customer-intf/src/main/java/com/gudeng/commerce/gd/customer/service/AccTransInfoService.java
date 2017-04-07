package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AccTransInfoDTO;

public interface AccTransInfoService {
	

	
	public int	 add(AccTransInfoDTO accTransInfoDTO) ;
	public  List<AccTransInfoDTO>	 getByMemberId(Map map) ;
	public Integer getTotal(Map map) throws Exception;
	public List<AccTransInfoDTO> getByMemberIdAndDay(Map map)  throws Exception;
	
	
	
}
