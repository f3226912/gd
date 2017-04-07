package com.gudeng.commerce.gd.m.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;

public interface OrderTool2Service {

	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception;
	
}
