package com.gudeng.commerce.gd.customer.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.BillDetailDTO;
import com.gudeng.commerce.gd.customer.dto.BillDetailStatDTO;

public interface BillDetailService {
	/**
	 * 按月份查询账单列表
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailDTO> 账单列表
	 */
	public List<BillDetailDTO> queryBillDetailList(String account, String payTime, String channelType);
	
	/**
	 * 按月份查询采购金额
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月采购金额列表
	 */
	public List<BillDetailStatDTO> queryMonthAmountList(String account, String payTime, String channelType);
	
	/**
	 * 按月份查询订单量
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月订单量列表
	 */
	public List<BillDetailStatDTO> queryMonthOrderList(String account, String payTime, String channelType);
	
	/**
	 * @Title: countTradeAmount
	 * @Description: TODO(统计交易金额)
	 * @param memberId
	 * @return
	 */
	public Double countTradeAmount(Long memberId);
	
	/**
	 * @Title: queryOrderSubjectList
	 * @Description: TODO(查询订单主题map)
	 * @param orderNos
	 * @return
	 */
	public List<BillDetailDTO> queryOrderSubjectList(List<Long> orderNos);
}
