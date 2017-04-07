package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BillDetailDTO;
import com.gudeng.commerce.gd.customer.dto.BillDetailStatDTO;
import com.gudeng.commerce.gd.customer.service.BillDetailService;

public class BillDetailServiceImpl implements BillDetailService{
	@Autowired
	private BaseDao<?> baseDao;

	/**
	 * 按月份查询账单列表
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailDTO> 账单列表
	 */
	@Override
	public List<BillDetailDTO> queryBillDetailList(String account, String payTime, String channelType){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("payTime", payTime);
		params.put("channelType", channelType);
		return this.baseDao.queryForList("BillDetail.getBillDetailList", params ,BillDetailDTO.class);
	}
	
	/**
	 * 按月份查询采购金额
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月采购金额列表
	 */
	@Override
	public List<BillDetailStatDTO> queryMonthAmountList(String account, String payTime, String channelType){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("payTime", payTime);
		params.put("channelType", channelType);
		return (List<BillDetailStatDTO>)this.baseDao.queryForList("BillDetail.getMonthAmount", params, BillDetailStatDTO.class);
	}
	
	/**
	 * 按月份查询订单量
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 
	 * @return List<BillDetailStatDTO> 月订单量列表
	 */
	@Override
	public List<BillDetailStatDTO> queryMonthOrderList(String account, String payTime, String channelType){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("payTime", payTime);
		params.put("channelType", channelType);
		return (List<BillDetailStatDTO>)this.baseDao.queryForList("BillDetail.getMonthOrder", params, BillDetailStatDTO.class);
	}

	@Override
	public Double countTradeAmount(Long memberId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForObject("BillDetail.countTradeAmount", paramMap, Double.class);
	}

	@Override
	public List<BillDetailDTO> queryOrderSubjectList(List<Long> orderNos) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNos", orderNos);
		return (List<BillDetailDTO>) baseDao.queryForList("BillDetail.queryOrderSubjectList", params, BillDetailDTO.class);
	}
}
