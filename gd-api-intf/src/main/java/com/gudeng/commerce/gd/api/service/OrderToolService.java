package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppReturnDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;

public interface OrderToolService {
	
	public StatusCodeEnumWithInfo addOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception;
	
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId) throws Exception;
	
	public List<OrderAppReturnDTO> getBuyerOrderList(Map<String,Object> map) throws Exception;

	public int getOrdersTotal(Map<String, Object> map) throws Exception;
	
	public ErrorCodeEnum confirmPay(OrderAppInputDTO inputDTO) throws Exception;
	
	public OrderBaseinfoDTO getOrderByOrderNo(Long orderNo) throws Exception;

	public int updateByOrderNo(OrderBaseinfoDTO orderBaseinfoDTO)throws Exception;

	/**
	 * 恢复订单的商品库存
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int resumeStock(Map<String, Object> map) throws Exception;

	/**
	 * 卖家列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SellerOrderListDTO> getSellerOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 再次上传凭证
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum uploadVoucherAgain(OrderAppInputDTO inputDTO) throws Exception;

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
	 *  获取可以发放礼品的订单，条件如下：
	 *  1系统当前时间向前，取72小时内成功支付 ，POS支付
	 *  2订单内包含商品记录  
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getAbleGiftOrder(Long memberId) throws Exception;
}
