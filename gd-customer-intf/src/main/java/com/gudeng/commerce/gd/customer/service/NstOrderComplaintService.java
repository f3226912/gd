package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;

public interface NstOrderComplaintService {

	Long persist(NstOrderComplaintEntity nstOrderComplainEntity);

	NstOrderComplaintDTO getById(Long id);
	
	/**
	 * 订单投诉列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderBaseinfoDTO> getOrderComplaintListByCondition(
			Map<String, Object> map) throws Exception;

	public int getOrderComplaintTotal(Map<String, Object> map) throws Exception;
	
	public NstOrderBaseinfoDTO getById(String id) throws Exception ;
	
	/**
	 * 同城订单查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NstOrderBaseinfoDTO getSameCityOrderById(String id) throws Exception ;
	
	public int updateStatus(NstOrderComplaintDTO dto) throws Exception;

}
