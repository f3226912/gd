package com.gudeng.commerce.gd.api.service.pos;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;

public interface PosOrderToolService {
	
	/**
	 * 增加订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 取消订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 获取订单详情
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public PosOrderDetailDTO getOrderByOrderNo(Long orderNo) throws Exception;

	/**
	 * 卖家Pos机订单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PosOrderListDTO> getPosOrderList(Map<String, Object> map) throws Exception;
	
	public int getPosOrdersTotal(Map<String, Object> map) throws Exception;

	/**
	 * 修改订单支付金额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo update(Long orderNo, Double payAmount, Integer version) throws Exception;

	/**
	 * 增加匿名订单
	 * 用于直接支付用户
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addAnonymousOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 卖家确认现金收款
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum confirmReceive(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * e农卖家确认收款
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public String confirmEnongReceive(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 刷卡消费
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public String payByCard(OrderAppInputDTO inputDTO) throws Exception;

	/**
	 * 锁住订单
	 * 防止收银时订单取消或者改价
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum lock(OrderAppInputDTO inputDTO, boolean isLock) throws Exception;
}
