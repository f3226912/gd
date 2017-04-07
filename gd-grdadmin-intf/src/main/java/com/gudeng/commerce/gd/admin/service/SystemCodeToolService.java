package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.entity.SystemCode;

public interface SystemCodeToolService {

	/**根据编码类型以及key值从数据字典中获取value值
	 * @param codeType
	 * @param codeKey
	 * @return
	 * @throws Exception 
	 */
	public String showValueByCode(String codeType,String codeKey) throws Exception;
	
	/**
	 * 根据类型获取列表数据
	 * @param type
	 * @return
	 */
	public List<SystemCode> getListViaType(String type) throws Exception;
}
