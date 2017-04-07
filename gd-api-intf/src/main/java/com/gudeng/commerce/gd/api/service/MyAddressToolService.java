package com.gudeng.commerce.gd.api.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MyAddressDTO;
import com.gudeng.commerce.gd.customer.entity.MyAddress;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface MyAddressToolService extends BaseToolService<MyAddressDTO> {
	public Long insert(MyAddress entity) throws Exception;

	public void prefer(Map<String, Object> map) throws Exception;
}