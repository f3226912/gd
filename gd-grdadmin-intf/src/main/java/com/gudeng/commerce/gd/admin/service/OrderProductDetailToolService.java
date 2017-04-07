package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;

public interface OrderProductDetailToolService {
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
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderProductDetailDTO>
	 */
	public List<OrderProductDetailDTO> getListByOrderNo(Map<String, Object> map) throws Exception;
}
