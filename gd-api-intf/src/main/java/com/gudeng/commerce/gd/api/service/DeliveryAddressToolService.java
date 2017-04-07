package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface DeliveryAddressToolService extends BaseToolService<DeliveryAddressDTO> {
	public Long insert(DeliveryAddress entity) throws Exception;
}