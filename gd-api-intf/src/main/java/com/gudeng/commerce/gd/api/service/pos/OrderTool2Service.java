package com.gudeng.commerce.gd.api.service.pos;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppReturnDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.ProductDeliverListAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.input.MemberInfoInputDTO;
import com.gudeng.commerce.gd.api.dto.output.SellerOrderList2DTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;

public interface OrderTool2Service {
	
	public StatusCodeEnumWithInfo addOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId) throws Exception;
	
	public List<OrderAppReturnDTO> getBuyerOrderList(Map<String,Object> map) throws Exception;

	public int getOrdersTotal(Map<String, Object> map) throws Exception;
	
	public OrderBaseinfoDTO getOrderByOrderNo(Long orderNo) throws Exception;

	/**
	 * 卖家列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SellerOrderListDTO> getSellerOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 卖家确认收款
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum confirmReceive(OrderAppInputDTO inputDTO) throws Exception;

	/**
	 * 补贴列表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getSubListTotal(Map<String, Object> map) throws Exception;

	/**
	 * 补贴列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderAppReturnDTO> getSubList(Map<String, Object> map) throws Exception;

	/**
	 * 获取E农订单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getENongOrderList(Map<String, Object> map) throws Exception;
	
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 卖家订单列表接口2
	 * 买卖家通用
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SellerOrderList2DTO> getSellerOrderList2(Map<String, Object> map) throws Exception;

	public int getDeliveredProductTotal(Map<String, Object> map) throws Exception;

	public List<ProductDeliverListAppDTO> getDeliveredProductList(
			Map<String, Object> map) throws Exception;

	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception;

	/**
	 * 搜索卖家订单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageQueryResultDTO<SellerOrderList2DTO> searchSellerOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 增加订单商品信息
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum addProductInfo(OrderAppInputDTO inputDTO) throws Exception;

	/**
	 * 增加订单买家信息
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum addBuyerInfo(MemberInfoInputDTO inputDTO, boolean hasProductInfo) throws Exception;

	/**
	 * 购买金牌供应商
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo buyGoldMedal(MemberInfoInputDTO inputDTO, HttpServletRequest request) throws Exception;

	/**
	 * 查找订单商品信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	List<OrderProductDetailDTO> getOrderProductsByOrderNo(Long orderNo)
			throws Exception;
	

	
}
