package com.gudeng.commerce.gd.api.service.order;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.gudeng.commerce.gd.api.dto.input.BusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.input.GdOrderActivityApiQueryDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;

public interface OrderActivityToolService {

	/**
	 * 检查订单是否符合活动
	 * 并返回订单活动相关
	 * @param orderEntity
	 * @return 
	 */
	public GdOrderActivityResultDTO checkOrderActivity(OrderBaseinfoEntity orderEntity,
			List<OrderProductDetailEntity> entityList) throws Exception;
	
	/**
	 * 查询活动订单
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo queryOrderActivity(GdOrderActivityApiQueryDTO inputDTO) throws Exception;
	
	/**
	 * 查询活动
	 * 未组装查询参数
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public GdOrderActivityResultDTO queryActivity(GdOrderActivityApiQueryDTO inputDTO) throws Exception;

	/**
	 * 设置订单相关活动信息
	 * @param orderActResult
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderActRelationEntity> getOrderRelationDetail(GdOrderActivityResultDTO orderActResult,
			Long orderNo) throws Exception;

	/**
	 * 批量查询活动
	 * @param businessDetailsJsonArr
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo batchQueryOrderActivity(
			JSONArray businessDetailsJsonArr) throws Exception;

	/**
	 * 查询活动(内部使用)
	 * 已组装查询参数
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public GdOrderActivityResultDTO queryActivity(GdOrderActivityQueryDTO queryDTO) throws Exception;

	/**
	 * 根据订单号
	 * 查找订单活动信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderActRelationDTO> getByOrderNo(Long orderNo) throws Exception;

	/**
	 * 查询商铺是否
	 * 跟收货地同城
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo querySameCity(BusinessInputDTO inputDTO) throws Exception;
	
	
	/**
	 * 查询买家违约金
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO) throws Exception;
	
	/**
	 * 获取积分倍率等信息
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public Integer queryActivityIntegralRate(Map<String, Object> paraMap) throws Exception;
	
	/**
	 * 支付完成，修改积分表，积分明细变更表和订单表中的积分字段
	 * @param orderNo
	 * @return
	 */
	public void IntegralRateForPayFinish(Long orderNo) throws Exception;
	
	/**
	 * 批量保存订单活动对应关系
	 * 
	 * */
	public void saveOrderActRelations(List<OrderActRelationEntity> entitys) throws Exception;
	
	/**
	 * 查询订单产品明细
	 * 
	 * */
	public List<OrderProductDetailDTO> findOrderProductDetailByOrderNo(String orderNo) throws Exception;
}
