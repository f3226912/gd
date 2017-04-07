package com.gudeng.commerce.gd.api.service.order;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;

public interface OrderFeeItemDetailToolService {

	/**
	 * 根据订单号和费用类型
	 * 查找订单佣金和补贴
	 * @param orderNo 订单号
	 * @param feeType 费用类型: 1佣金 2补贴 null全部
	 * @param payerType 付方类型: nsy农商友、nps农批商、platfm平台
	 * @return
	 * @throws Exception
	 */
	public List<OrderFeeItemDetailDTO> getOrderFeeItemList(Long orderNo, Integer feeType, String payerType) throws Exception;

	/**
	 * 设置订单佣金和补贴信息
	 * @param orderDetailReturn
	 * @param orderBaseDTO
	 * @throws Exception
	 */
	public void setOrderCommAndSubsidyInfo(OrderDetailAppDTO orderDetailReturn,
			OrderBaseinfoDTO orderBaseDTO) throws Exception;

	/**
	 * 设置订单佣金和补贴信息
	 * @param orderDetailReturn
	 * @param orderBaseDTO
	 * @throws Exception
	 */
	public void setOrderCommAndSubsidyInfo(PosOrderDetailDTO orderDetailReturn,
										   OrderBaseinfoDTO orderBaseDTO) throws Exception;

	/**
	 * 获取订单费用信息
	 * @param orderActResult
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderFeeItemDetailDTO> getOrderFeeDetail(
			GdOrderActivityResultDTO orderActResult, Long orderNo) throws Exception;

	/**
	 * 根据订单号批量
	 * 更新订单费用信息
	 * @param orderActFeeList
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public int batchUpdate(List<OrderFeeItemDetailDTO> orderActFeeList,
			Long orderNo) throws Exception;

	/**
	 * 根据类型
	 * 生成订单费用信息
	 * @param orderNo 
	 * @param fee
	 * @param type 1买家市场佣金
	 * @return
	 * @throws Exception
	 */
	public OrderFeeItemDetailEntity getOrderFeeEntityByType(
			Long orderNo, Double fee, int type) throws Exception;
	
	
	/**
	 * 获取订单费用项明细表
	 * @return
	 * @throws Exception
	 */
	public List<OrderFeeItemDetailDTO> getOrderFeeByParam(Map<String,Object> param) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	public int deleteByParam(Map<String, Object> feelMap) throws Exception;
	
	public int batchInsert(List<OrderFeeItemDetailEntity> feelList) throws Exception;
}
