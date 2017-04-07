package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;

public interface OrderOutmarketinfoService {
	/**
	 * 添加
	 * 
	 * @param OrderBaseinfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(OrderOutmarketinfoEntity obj) throws Exception;
	
	/**
	 * 添加
	 * 
	 * @param OrderBaseinfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Integer insertSQL(OrderOutmarketinfoEntity obj) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(Long id) throws Exception;

	/**
	 * 批量通过ID删除对象
	 * 
	 * @param idList
	 * @return void
	 * 
	 */
	public int batchDeleteById(List<Long> idList) throws Exception;
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param OrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateDTO(OrderOutmarketInfoDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param OrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<OrderOutmarketInfoDTO> objList) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return OrderBaseinfoDTO
	 * 
	 */
	public OrderOutmarketInfoDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderOutmarketInfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 插入订单出场对应关系
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertOrderOutmark(String orderNoList,String omId) throws Exception;
	
	/**
	 * 获取对应出场表的所有Order信息
	 * @param omId
	 * @return
	 * @throws Exception
	 */
	public List<OrderBaseinfoDTO> getOrderInfoByOmId(Long omId) throws Exception;
	
	/**
	 * 货主确认出场
	 * @return
	 */
	public Long shipperOutMarket(OrderOutmarketinfoEntity outmarketEntity, List<String> productIdList);
	
	/**
	 * @Title: queryOrderOutmarkCarInfoByOrderNo
	 * @Description: TODO(根据订单号查询出场车辆满载度)
	 * @param outmarketInfo
	 * @return
	 * @throws ServiceException
	 */
	public OrderOutmarketInfoDTO queryOrderOutmarkCarInfoByOrderNo(OrderOutmarketInfoDTO outmarketInfo) throws ServiceException;
	
	/**
	 * @Title: queryOrderOutmarkCarInfoByCarNumber
	 * @Description: TODO(根据车牌号查询)
	 * @param outmarketInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderOutmarketInfoDTO> queryOrderOutmarkCarInfoByCarNumber(OrderOutmarketInfoDTO outmarketInfo) throws ServiceException;

	/**
	 * 修改出场记录的车牌图片
	 * @param entity
	 * @return
	 */
	public int updateCarNumberImage(OrderOutmarketinfoEntity entity);
	
	/**
	 * 采购商出场
	 * @throws ServiceException 
	 * @return 出场id
	 */
	public Long purchaserOutMarket(OrderOutmarketinfoEntity entity, List<String> orderNoList) throws Exception;
	
	/**
	 * 版本V2：采购商出场
	 * @throws ServiceException 
	 * @return 出场id
	 */
	public Long purchaserOutMarketV2(OrderOutmarketinfoEntity entity, List<OrderBaseinfoDTO> orderList) throws Exception;
	
	/**
	 * @Title: getListByMap
	 * @Description: TODO(查询订单出场信息)
	 * @param queryMap
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderOutmarketInfoDTO> getListByMap(Map<String, Object> queryMap) throws ServiceException;

	/**
	 * 分页获取出场记录列表
	 * @param query
	 * @return
	 */
	public List<OrderOutmarketInfoDTO> getPageByCreateUserId(Map<String, Object> query);
	
	public Long getTotalCountByCreateUserId(Map<String, Object> query);


}
