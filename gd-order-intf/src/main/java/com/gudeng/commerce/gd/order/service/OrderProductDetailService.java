package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;

public interface OrderProductDetailService {
	/**
	 * 添加
	 * 
	 * @param OrderProductDetailEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(OrderProductDetailEntity obj) throws Exception;
	
	/**
	 * 批量添加
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	public int batchInsertEntity(List<OrderProductDetailEntity> entityList) throws Exception;

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
	 * @param OrderProductDetailDTO
	 * @return int
	 * 
	 */
	public int updateDTO(OrderProductDetailDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param OrderProductDetailDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<OrderProductDetailDTO> objList) throws Exception;
	
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
	 * @return OrderProductDetailDTO
	 * 
	 */
	public OrderProductDetailDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderProductDetailDTO>
	 */
	public List<OrderProductDetailDTO> getListByConditionPage(Map<String, Object> map) throws Exception;
	

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderProductDetailDTO>
	 */
	public List<OrderProductDetailDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 根据订单号列表查询list
	 * @param orderNoList
	 * @return
	 * @throws Exception
	 */
	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderProductDetailDTO>
	 */
	public List<OrderProductDetailDTO> getListByOrderNo(Map<String, Object> map) throws Exception;
	
	/**
	 * @Title: batchUpdate
	 * @param orderDetails
	 * @return
	 * @throws ServiceException
	 */
	public int batchUpdate(List<OrderProductDetailDTO> orderDetails) throws ServiceException;

	/**
	 * 根据商品名字
	 * 搜索店铺的订单商品列表
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderProductDetailDTO> searchByProductName(
			Map<String, Object> map) throws ServiceException;
	
	/**
	 * 查询第一条产品
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public OrderProductDetailDTO getFirstProductByOrderNo(Map<String, Object> map) throws ServiceException;

}
