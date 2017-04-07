package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.admin.dto.TransferListDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoSendnowDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;

public interface OrderBaseinfoToolService {
	/**
	 * 添加
	 * 
	 * @param OrderBaseinfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(OrderBaseinfoEntity obj) throws Exception;

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
	public int updateDTO(OrderBaseinfoDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param OrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<OrderBaseinfoDTO> objList) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getOrderTotal(Map<String, Object> map) throws Exception; 
	
	/**
	 * 查询供应商订单记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getSuppOrderTotal(Map<String, Object> map) throws Exception; 

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return OrderBaseinfoDTO
	 * 
	 */
	public OrderBaseinfoDTO getById(Long id) throws Exception;
	
	/**
	 * 根据orderNo查询对象
	 * 
	 * @param orderNo
	 * @return OrderBaseinfoDTO
	 * 
	 */
	public OrderBaseinfoDTO getByOrderNo(Long orderNo) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getOrderListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception;
	
	/**
	 * 订单审核
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int orderExamine(Integer payId,Long orderNo,String type,String description,String statementId,String updateUserId,String userName) throws Exception;

	/**
	 * 查询个人交易记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getPageTotal(Map<String, Object> map) throws Exception;

	/**
	 * 分页查询个人交易记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<TransferListDTO> getListByPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)供应商
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoDTO> getSuppOrderListByConditionPage(
			Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)鲜农
	 * 
	 * @param map
	 * @return List<OrderBaseinfoDTO>
	 */
	public List<OrderBaseinfoSendnowDTO> getSendnowOrderListByConditionPage(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 补单需求，根据订单查询银行相似流水
	 * @param persaleId
	 * @return
	 * @throws Exception
	 */
	public String queryOderSameStatement(String persaleId) throws Exception;
	
	/**
	 * 9月2 补单需求 保存补单
	 * @throws Exception
	 */
	public void saveSupplementOrder(String userId,String persaleId,String statementId) throws Exception;

}
