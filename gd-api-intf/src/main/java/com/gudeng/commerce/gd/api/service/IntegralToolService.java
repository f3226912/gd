package com.gudeng.commerce.gd.api.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.IntegralDTO;

public interface IntegralToolService {
	
	/** api接口查询积分流水*/
	public List<IntegralDTO> selectIntegralFlow(Long memberId) throws Exception;
}
