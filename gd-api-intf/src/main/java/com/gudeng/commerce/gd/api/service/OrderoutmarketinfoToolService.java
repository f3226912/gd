package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;

public interface OrderoutmarketinfoToolService {
	/**
	 * 查询订单详细信息：
	 * @param orderBaseinfoDTO
	 * @return
	 */
	public List<OrderBaseinfoDTO> getOderInfoList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception;
	
	/**
	 * 插入订单出场对应关系
	 * @param orderNoList,omId
	 * @return
	 * @throws Exception
	 */
	public int insertOrderOutmark(String orderNoList, String omId) throws Exception;
	
	/**
	 * 插入出场信息
	 * @param orderOutmarketInfoDTO
	 * @return
	 * @throws Exception
	 */
	public Long insert(OrderOutmarketinfoEntity orderOutmarketinfoEntity) throws Exception;

	/**
	 * 货主确认出场
	 * @param orderOutmarketinfoEntity
	 * @param productList
	 * @return
	 * @throws Exception
	 */
	public Long shipperOutMarket(OrderOutmarketinfoEntity orderOutmarketinfoEntity,List<String> productIdList) throws Exception;

	/**
	 * 修改出场记录的车牌图片
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public int updateCarNumberImage(OrderOutmarketinfoEntity entity) throws Exception;

	/**
	 * 订单出场
	 * @param orderOutmarketEntity
	 * @param orderNoList
	 * @return
	 * @throws Exception
	 */
	public Long purchaserOutmarket(OrderOutmarketinfoEntity orderOutmarketEntity, List<String> orderNoList) throws Exception;
	
	/**
	 * 版本V2:订单出场
	 * @param orderOutmarketEntity
	 * @param orderNoList
	 * @return
	 * @throws Exception
	 */
	public Long purchaserOutmarketV2(OrderOutmarketinfoEntity orderOutmarketEntity, List<OrderBaseinfoDTO> orderList) throws Exception;

	
	/**
	 * 分页获取出场记录列表
	 * @param query
	 * @return
	 */
	public List<OrderOutmarketInfoDTO> getPageByCreateUserId(Map<String, Object> query) throws Exception;
	
	public Long getTotalCountByCreateUserId(Map<String, Object> query) throws Exception;

	/**
	 * 获取有补贴订单列表
	 * @param orderBaseinfoDTO
	 * @return
	 * @throws Exception 
	 */
	public List<OrderBaseinfoDTO> getOutMarketOrderList(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception;

}
