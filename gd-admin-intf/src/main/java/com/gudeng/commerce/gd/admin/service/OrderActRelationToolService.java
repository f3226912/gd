package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;

public interface OrderActRelationToolService{
	List<OrderActRelationDTO> getByOrderNo(Long orderNo) throws Exception;
}