package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;

/**
 * 农速通运单信息服务
 * @author xiaojun
 */
public interface NstOrderBaseinfoToolService {
	
	
	/**
	 * 
	 * @param NstOrderBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateNstOrderBaseinfoDTO(NstOrderBaseinfoDTO dto) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<NstOrderBaseinfoDTO>
	 */
	public List<NstOrderBaseinfoDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	
	
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 * 根据条件查询订单评论list(分页查询)
	 * 
	 * @param map
	 * @return List<NstOrderBaseinfoDTO>
	 */
	public List<NstOrderBaseinfoDTO> getCommentListByCondition(Map<String,Object> map)throws Exception;
	
	
	
	
	/**
	 * 查询订单评论记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCommentTotal(Map<String,Object> map)throws Exception;
	
	
	public NstOrderCommentDTO getById(String id) throws Exception ;
	
	
	/**
	 * 订单投诉列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderBaseinfoDTO> getOrderComplaintListByCondition(
			Map<String, Object> map) throws Exception;

	public int getOrderComplaintTotal(Map<String, Object> map) throws Exception; 
	
	public NstOrderBaseinfoDTO getNstOrderComplaintById(String id) throws Exception ;
	
	public int updateStatus(NstOrderComplaintDTO dto) throws Exception;
	
	/**
	 * 获取农速通统计订单列表
	 * @param nstOrderCountDTO
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderCountDTO> getOrderCountList(Map<String, Object> map) throws Exception;
	/**
	 * 根据订单号获取单个订单统计详情
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public NstOrderCountDTO getOrderCountDetailByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 获取农速通统计订单列表Total
	 * @param nstOrderCountDTO
	 * @return
	 * @throws Exception
	 */
	public Long getOrderCountListTotal(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 获取农速通统计订单列表(同城订单)
	 * @param nstOrderCountDTO
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderCountDTO> getSameCityOrderList(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 获取农速通统计订单列表Total(同城订单)
	 * @param nstOrderCountDTO
	 * @return
	 * @throws Exception
	 */
	public Long getSameCityOrderTotal(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 根据订单号获取单个订单统计详情(同城订单)
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public NstOrderCountDTO getSameCityOrderDetailByOrderNo(String orderNo) throws Exception;
	
	
	/**
	 * 同城订单查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getSameCityOrderById(String id) throws Exception ;
	

	
	/**
	 * 根据条件查询同城list(分页查询)
	 * 
	 * @param map
	 * @return List<NstOrderBaseinfoDTO>
	 */
	public List<NstOrderBaseinfoDTO> getSameCityOrdersByCondition(Map<String,Object> map)throws Exception;
	
	
	
	
	/**
	 * 查询同城记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getSameCityOrdersTotal(Map<String,Object> map)throws Exception;
	
	
}
