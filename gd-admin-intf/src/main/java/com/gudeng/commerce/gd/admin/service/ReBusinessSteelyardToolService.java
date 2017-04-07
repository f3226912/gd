package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.admin.dto.BusinessSteelyardInputDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;

public interface ReBusinessSteelyardToolService {

	/**
	 * 增加商铺与电子称关系
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public Long addEntities(BusinessSteelyardInputDTO inputDTO) throws Exception;
	
	/**
	 * 根据商铺id删除商铺与电子称关系
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public int deleteByBusinessId(Long businessId) throws Exception;
	
	/**
	 * 根据id删除商铺与电子称关系
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteById(Long id) throws Exception; 
	
	/**
	 * 根据商铺id查找商铺与电子称关系
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessSteelyardDTO> getReBusinessSteelyardByBusinessId(Long businessId) throws Exception;

	/**
	 * 根据电子称mac地址
	 * 查找businessId
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public Long getByMacAddr(BusinessSteelyardInputDTO inputDTO) throws Exception;
}
