package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.DatasourceBaiduDTO;
import com.gudeng.commerce.info.customer.entity.DatasourceBaiduEntity;

public interface DatasourceBaiduToolService {
	/**
	 * 添加
	 * 
	 * @param DatasourceBaiduEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(DatasourceBaiduEntity obj) throws Exception;

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
	 * @param DatasourceBaiduDTO
	 * @return int
	 * 
	 */
	public int updateDTO(DatasourceBaiduDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param DatasourceBaiduDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<DatasourceBaiduDTO> objList) throws Exception;
	
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
	 * @return DatasourceBaiduDTO
	 * 
	 */
	public DatasourceBaiduDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<DatasourceBaiduDTO>
	 */
	public List<DatasourceBaiduDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<DatasourceBaiduDTO>
	 */
	public List<DatasourceBaiduDTO> getListByCondition(Map<String, Object> map) throws Exception;
}
