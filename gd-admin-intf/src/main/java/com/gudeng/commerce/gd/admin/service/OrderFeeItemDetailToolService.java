package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;


public interface OrderFeeItemDetailToolService extends BaseToolService<OrderFeeItemDetailDTO> {
	public Long insert(OrderFeeItemDetailEntity entity) throws Exception;

	public int deleteByParam(Map<String, Object> feelMap) throws Exception;

	public int batchInsert(List<OrderFeeItemDetailEntity> feelList) throws Exception;
}