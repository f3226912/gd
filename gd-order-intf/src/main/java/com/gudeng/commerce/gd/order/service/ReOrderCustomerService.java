package com.gudeng.commerce.gd.order.service;

import java.util.Map;

import com.gudeng.commerce.gd.order.dto.ReOrderCustomerDTO;

public interface ReOrderCustomerService {

	/**
	 * 增加订单客户关心
	 * @param entity
	 * @throws Exception
	 */
	Integer addEntity(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过订单查找订单客户关系
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	ReOrderCustomerDTO getByOrderNo(Long orderNo) throws Exception; 
}
