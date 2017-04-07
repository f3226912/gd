package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;


public interface AccTransInfoService {
	

	
	public int	 add(AccTransInfoDTO accTransInfoDTO) ;
	
	/**
	 * 通过memberId查询账号流水信息
	 * @param map
	 * @return
	 */
	public List<AccTransInfoDTO> getAccTransInfoListByMemberId(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过memberId查询账号流水信息总数
	 * @param map
	 * @return
	 */
	public Long getAccTransInfoListTotalByMemberId(Map<String, Object> map) throws Exception;
}
