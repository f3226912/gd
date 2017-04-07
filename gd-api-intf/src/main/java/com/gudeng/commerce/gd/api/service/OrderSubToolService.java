package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;

public interface OrderSubToolService {
	/**
	 * @Title: queryProductSubList
	 * @Description: (查询产品是否有补贴)
	 * @return
	 * @throws ServiceException
	 * @throws Exception 
	 */
	public List<OrderProductDetailDTO> queryProductSubList(List<OrderProductDetailDTO> orderProductDetails) throws Exception;

	/**
	 * 查询补贴总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getSubListTotal(Map<String, Object> map) throws Exception;

	/**
	 * 查询补贴列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SubAuditDTO> getSubList(Map<String, Object> map) throws Exception;

	/**
	 * 为推送产品增加
	 * 是否有活动标志
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<PushProductDTO> addActivityDetail(List<PushProductDTO> list) throws Exception;
	
	/**
	 * 添加是否有补贴进产品列表
	 * @param plist
	 * @throws Exception
	 */
	public List<OrderProductDetailDTO> addSubIntoProduct(List<OrderProductDetailDTO> appList) throws Exception;
}
