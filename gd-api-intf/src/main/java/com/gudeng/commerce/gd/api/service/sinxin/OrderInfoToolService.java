package com.gudeng.commerce.gd.api.service.sinxin;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.OrderSinxinDTO;


public interface OrderInfoToolService {
	
	/**
	 * 订单同步
	 */
	public Long syncOrder(OrderSinxinDTO orderBaseDTO) throws Exception;
	
	/**
	 * 订单查询
	 */
	public List<OrderSinxinDTO> queryOrder(OrderSinxinDTO queryDTO) throws Exception;
	
	/**
	 * 更新订单
	 */
	public Long updateOrder(OrderSinxinDTO orderBaseDTO) throws Exception;

}