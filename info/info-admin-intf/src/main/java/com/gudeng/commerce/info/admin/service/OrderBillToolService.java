package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.OrderBillDTO;

public interface OrderBillToolService {
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public Long getTotal(Map<String, Object> map) throws Exception;


	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBillDTO>
	 */
	public List<OrderBillDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

}
