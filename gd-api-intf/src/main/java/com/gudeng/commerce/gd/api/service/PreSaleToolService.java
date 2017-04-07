package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;

public interface PreSaleToolService {

	/**
	 * 添加预售订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addOrders(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 根据二维码或订单号
	 * 查找预售订单
	 * @param orderNo 订单号
	 * @param qcCode 二维码
	 * @return
	 * @throws Exception
	 */
	public PreSaleDTO getByOrderNo(Long orderNo, String qcCode) throws Exception;
	
	/**
	 * 更新预售订单状态
	 * @param orderNo
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Integer updateOrderStatus(Long orderNo, String status) throws Exception;
	
	/**
	 * 确认订单
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo confirmOrder(OrderAppInputDTO inputDTO) throws Exception;

	/**
	 * 获取预售订单详细信息
	 * @param orderNo
	 * @param memberId 
	 * @param qcCode
	 * @return
	 * @throws Exception
	 */
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId, String qcCode) throws Exception;

	/**
	 * 获取总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getOrdersTotal(Map<String, Object> map) throws Exception;

	/**
	 * 分页获取list
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SellerOrderListDTO> getOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 取消制单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception;
}
