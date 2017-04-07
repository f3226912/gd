package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AccTransInfoDTO;

public interface AccTransInfoToolService {
	

	
	public int	 add(AccTransInfoDTO accTransInfoDTO)  throws Exception;
	public  List<AccTransInfoDTO>	 getByMemberId(Map map)  throws Exception;
	public Integer getTotal(Map map) throws Exception;
	public List<AccTransInfoDTO> getByMemberIdAndDay(AccTransInfoDTO accTransInfoDTO)  throws Exception;


	
	
	
}
