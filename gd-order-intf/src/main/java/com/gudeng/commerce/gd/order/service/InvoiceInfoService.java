package com.gudeng.commerce.gd.order.service;

import java.util.Map;

import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;

public interface InvoiceInfoService {

	/**
	 * 查询对象
	 * 
	 * @return PaySerialnumberDTO
	 * 
	 */
	public InvoiceInfoDTO getBySearch(Map map) throws Exception;
	
	/**
	 * 插入
	 * @return
	 * @throws Exception
	 */
	public int insertInvoiceInfo(Map map) throws Exception;
}





