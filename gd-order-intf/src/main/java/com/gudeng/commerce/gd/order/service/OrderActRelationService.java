package com.gudeng.commerce.gd.order.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;

public interface OrderActRelationService {

	public int add(OrderActRelationEntity entity) throws Exception;
	
	public List<OrderActRelationDTO> getByOrderNo(Long orderNo) throws Exception;
	
	public List<OrderActRelationDTO> getByActId(Integer actId) throws Exception;

	public int batchInsertEntity(List<OrderActRelationEntity> orderActList) throws Exception;
}
