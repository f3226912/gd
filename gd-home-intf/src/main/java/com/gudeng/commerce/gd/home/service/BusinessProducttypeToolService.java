package com.gudeng.commerce.gd.home.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.*;

/**
 * 商铺自定义类别信息处理接口
 * 
 * @author Administrator
 * 
 */
public interface BusinessProducttypeToolService {

	/**
	 * 通过商铺ID 获得对应商铺的自定义产品类别
	 * 
	 * @param businessId
	 * @return
	 */
	public List<BusinessProducttypeDTO> getByBusinessId(String businessId)
			throws Exception;

	/**
	 * 通过上级类别ID 获取对应的商铺自定义产品类别
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<BusinessProducttypeDTO> getByParentId(Long parentId)
			throws Exception;

}
