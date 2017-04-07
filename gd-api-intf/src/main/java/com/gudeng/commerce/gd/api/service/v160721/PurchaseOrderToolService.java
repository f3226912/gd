package com.gudeng.commerce.gd.api.service.v160721;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;

public interface PurchaseOrderToolService {
	
	/**
	 * 增加采购订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addPurchaseOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 修改采购订单
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum updatePurchaseOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	/**
	 * 获取采购订单详情
	 * @param orderNo
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public PurchaseOrderDetailDTO getPurchaseOrderDetail(Long orderNo) throws Exception;
	
	/**
	 * 根君订单号获取订单信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OrderBaseinfoDTO getPurchaseOrderByOrderNo(Long orderNo) throws Exception;
	
	/**
	 * 采购订单总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getPurchaseOrderTotal(Map<String, Object> map) throws Exception;

	/**
	 * 采购订单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PurchaseOrderListDTO> getPurchaseOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 搜索采购订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageQueryResultDTO<PurchaseOrderListDTO> searchPruchaseOrderList(
			Map<String, Object> map) throws Exception;
}
