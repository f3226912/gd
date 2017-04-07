package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.commerce.gd.exception.ServiceException;


/**
 *功能描述：(同城)收发货地址管理
 */
public interface NstSameCityAddressToolService{	
	
	/**
	 * 删除同城的收发地址
	 * @param entity
	 * @throws Exception
	 */
	Integer delete(NstSameCityAddressEntity entity) throws ServiceException;
}