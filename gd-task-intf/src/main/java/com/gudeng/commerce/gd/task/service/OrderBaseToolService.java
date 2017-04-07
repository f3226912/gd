package com.gudeng.commerce.gd.task.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;

/**
 * @Description: 
 * @author mpan
 * @date 2015年12月8日 下午7:59:55
 */
public interface OrderBaseToolService {
	
	/**
	 * @Title: queryOrderDetail
	 * @Description:
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 */
	public OrderBaseinfoDTO queryOrderDetail(Long orderNo) throws Exception;
	
	/**
	 * 查询超过48小时的订单集合
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getOverOrderInfoList(Map<String, Object> map) throws Exception;
	
	/**
	 * 订单72小时后的订单状态可库存的修改并增加相应产品库存
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer cancelOrderByOrderNo(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询2小时后已经确认付款，审核通过，但是卖家没有确认
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getTwoAlreadyPayOrderList(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 更新订单状态
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int updateByOrderNo(OrderBaseinfoDTO dto) throws Exception;

	/**
	 * 获取未支付订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getUnpaidOrderList(Map<String, Object> map) throws Exception;

	/**
	 * 根据订单号查找产品信息
	 * @param orderNoMap
	 * @return
	 * @throws Exception
	 */
	public List<OrderProductDetailDTO> getListByOrderNo(Map<String, Object> orderNoMap) throws Exception;
	
	public OrderBaseinfoDTO getByOrderNo(long orderNo) throws Exception;

}
