package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;

public interface ProductDeliveryDetailToolService {

	/**
	 * 根据发货物流id获取商品列表
	 * @param memberAddressId
	 * @return
	 */
	public List<ProductDeliveryDetailDTO> getProductByMemberAddressId(Long memberAddressId) throws Exception;
	
	/**
	 * 根据发货物流id获取订单及订单对应商品列表
	 * @param memberAddressId
	 * @return
	 */
	public List<OrderDeliveryDetailDTO> getOrderByMemberAddressId(Long memberAddressId) throws Exception;

	/**
	 * 根据发货物流id获取货物来源
	 * @param memberAddressId
	 * @return
	 */
	public Integer getTypeByMemberAddressId(Long memberAddressId) throws Exception;	
	
	/**
	 *  根据发货物流信息获取订单及订单对应商品列表
	 * @param params
	 * @return
	 */
	List<OrderDeliveryDetailDTO> getOrderByMember(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据发货物流信息获取订单及订单对应商品列表
	 * @param params
	 * @return
	 */
	List<ProductDeliveryDetailDTO> getProductByMember(Map<String, Object> params) throws ServiceException;
}
