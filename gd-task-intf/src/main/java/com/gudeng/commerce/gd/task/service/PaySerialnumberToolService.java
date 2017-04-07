package com.gudeng.commerce.gd.task.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

public interface PaySerialnumberToolService {
	/**
	 * 添加
	 * 
	 * @param PaySerialnumberEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(PaySerialnumberEntity obj) throws Exception;

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
	 * @param PaySerialnumberDTO
	 * @return int
	 * 
	 */
	public int updateDTO(PaySerialnumberDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param PaySerialnumberDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<PaySerialnumberDTO> objList) throws Exception;
	
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
	 * @return PaySerialnumberDTO
	 * 
	 */
	public PaySerialnumberDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<PaySerialnumberDTO>
	 */
	public List<PaySerialnumberDTO> getListByConditionPage(Map<String, Object> map) throws Exception;
	

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<PaySerialnumberDTO>
	 */
	public List<PaySerialnumberDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 根据订单号查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PaySerialnumberDTO getByOrderNo(Long orderNo) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param paySerialnumberEntity
	 * @return 记录数
	 * 
	 */
	public int getTotalByStatementId(String statementId) throws Exception;
	
	/**
	 * 插入银行流水号
	 * @param statementId 银行流水号
	 * @return
	 * @throws Exception
	 */
	public int insertPayStatementId(String statementId) throws Exception;
}
