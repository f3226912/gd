package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.service.OrderDeliveryAddressService;

public class OrderDeliveryAddressServiceImpl implements OrderDeliveryAddressService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<OrderDeliveryAddressDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderDeliveryAddress.getList", map, OrderDeliveryAddressDTO.class);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderDeliveryAddress.getTotal", map, Integer.class);
	}

	@Override
	public List<OrderDeliveryAddressDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderDeliveryAddress.getListPage", map, OrderDeliveryAddressDTO.class);
	}

	@Override
	public OrderDeliveryAddressDTO getByOrderNo(String orderNo) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderNo", orderNo);
		return baseDao.queryForObject("OrderDeliveryAddress.getByOrderNo", map, OrderDeliveryAddressDTO.class);
	}
}