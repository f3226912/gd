package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.FarmersMarketDTO;
import com.gudeng.commerce.gd.customer.entity.FarmersMarketEntity;

public interface FarmersMarketService {

	/**
	 * 添加
	 * 
	 * @param FarmersMarketEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(FarmersMarketEntity obj) throws Exception;

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
	 * @param FarmersMarketDTO
	 * @return int
	 * 
	 */
	public int updateDTO(FarmersMarketDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param FarmersMarketDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<FarmersMarketDTO> objList) throws Exception;
	
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
	 * @return FarmersMarketDTO
	 * 
	 */
	public FarmersMarketDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<FarmersMarketDTO>
	 */
	public List<FarmersMarketDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<FarmersMarketDTO>
	 */
	public List<FarmersMarketDTO> getListByCondition(Map<String, Object> map) throws Exception;
}
