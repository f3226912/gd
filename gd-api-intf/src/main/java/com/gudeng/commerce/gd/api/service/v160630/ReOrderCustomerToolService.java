package com.gudeng.commerce.gd.api.service.v160630;

import java.util.Map;

import com.gudeng.commerce.gd.order.dto.ReOrderCustomerDTO;

public interface ReOrderCustomerToolService {

	/**
	 * 增加订单客户关心
	 * @param map
	 * @throws Exception
	 */
	Integer addCustomerAndProduct(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过订单查找订单客户关系
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	ReOrderCustomerDTO getByOrderNo(Long orderNo) throws Exception; 
}
