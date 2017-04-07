package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;

public interface FinanceCreditToolService {

	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return FarmersMarketDTO
	 * 
	 */
	public FinanceCreditEntity getById(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<FinanceCreditEntity>
	 */
	public List<FinanceCreditEntity> getListByConditionPage(Map<String, Object> map) throws Exception;

}
