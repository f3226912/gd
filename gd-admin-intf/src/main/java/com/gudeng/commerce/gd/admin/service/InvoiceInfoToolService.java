package com.gudeng.commerce.gd.admin.service;

import java.util.Map;

import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;

public interface InvoiceInfoToolService {
	/**
	 * 查询对象
	 * 
	 * @return InvoiceInfoDTO
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
