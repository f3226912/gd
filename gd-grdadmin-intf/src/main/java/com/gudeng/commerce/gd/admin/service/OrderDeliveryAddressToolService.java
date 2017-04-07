package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;

public interface OrderDeliveryAddressToolService {
	
	public List<OrderDeliveryAddressDTO> getList(Map<String, Object> map) throws Exception;
	
}