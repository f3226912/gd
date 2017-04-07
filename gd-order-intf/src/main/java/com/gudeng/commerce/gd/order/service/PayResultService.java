package com.gudeng.commerce.gd.order.service;

import java.util.List;

import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

/**
 * 支付结果数据操作
 * 
 * @author Ailen
 *
 */
public interface PayResultService {

	/**
	 * 修改订单结果
	 * 
	 * @param OrderNo
	 * @param status
	 */
	public void updateOrderStatus(String orderNo, String status);
	
	/**
	 * 修改订单结果
	 * 
	 * @param OrderNo
	 * @param status
	 */
	public void updateOrderBase(OrderBaseinfoEntity entity);


	/**
	 * 修改订单结果 关闭用
	 * 
	 * @param orderNo
	 * @param status
	 * @param cancelReason
	 */
	public void updateOrderStatus(String orderNo, String status, String cancelReason);

	/**
	 * 添加退款信息
	 */
	public void addOrderRefundRecord(OrderRefundRecordEntity orderRefundRecordEntity);

	/**
	 * 添加支付流水信息
	 * 
	 * @param paySerialnumberEntity
	 * @throws Exception
	 */
	public void addPaySerialnumber(PaySerialnumberEntity paySerialnumberEntity) throws Exception;

	/**
	 * 获得超时支付定金的订单
	 * 
	 * @return
	 */
	public List<OrderBaseinfoDTO> getDinJinPayOuter() throws Exception;
	
	/**
	 * 修改会员为金牌会员
	 * @param memberId
	 */
	public void updateMemberGrade(Long memberId);
	
	public PaySerialnumberEntity getByStatementId(String statementId) throws Exception;

}
