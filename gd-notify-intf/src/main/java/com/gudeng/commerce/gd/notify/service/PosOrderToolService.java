package com.gudeng.commerce.gd.notify.service;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.notify.dto.PosPayNotifyDto;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;

public interface PosOrderToolService {
	
	/**
	 * 刷卡消费
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public OrderBaseinfoDTO payByCard(PosPayNotifyDto dto) throws Exception;
	
	public String getOrderNo() throws Exception;
	
	public ReBusinessPosDTO getByPosDevNo(String posDevNo, String businessNo) throws Exception;

	public BusinessBaseinfoDTO getBusinessBaseinfoById(String id)throws Exception;
	/**
	 * 
	 * 根据订单号，获取农速通运单号
	 * 
	 * */
	public String getNstOrderNo(Long orderNo) throws Exception;
	
}
