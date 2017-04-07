package com.gudeng.commerce.gd.customer.service;

import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface DeliveryAddressService extends BaseService<DeliveryAddressDTO> {
	public Long insert(DeliveryAddress entity) throws Exception;

	/**
	 * 根据货源ID更新运达时间
	 * @param orderNo
	 */
	public int updateArriveTime(String orderNo);
}