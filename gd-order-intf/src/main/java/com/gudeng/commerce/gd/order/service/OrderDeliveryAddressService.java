package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;

public interface OrderDeliveryAddressService {
	
	public List<OrderDeliveryAddressDTO> getList(Map<String, Object> map) throws Exception;

	public int getTotal(Map<String, Object> map) throws Exception;

	public List<OrderDeliveryAddressDTO> getListPage(Map<String, Object> map) throws Exception;
	
	public OrderDeliveryAddressDTO getByOrderNo(String orderNo) throws Exception;
	
}