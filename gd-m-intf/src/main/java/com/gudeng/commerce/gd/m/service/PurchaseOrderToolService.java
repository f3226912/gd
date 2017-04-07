package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.m.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.m.dto.StatusCodeEnumWithInfo;

public interface PurchaseOrderToolService {
	
	/**
	 * 增加采购订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addPurchaseOrder(OrderAppInputDTO inputDTO) throws Exception;
	
}
