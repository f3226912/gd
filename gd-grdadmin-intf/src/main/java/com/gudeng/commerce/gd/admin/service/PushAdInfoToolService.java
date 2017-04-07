package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;

public interface PushAdInfoToolService {
	/**
	 * 添加
	 * 
	 * @param PushAdInfoEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(PushAdInfoEntity obj) throws Exception;

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
	 * @param PushAdInfoDTO
	 * @return int
	 * 
	 */
	public int updateDTO(PushAdInfoDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param PushAdInfoDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<PushAdInfoDTO> objList) throws Exception;
	
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
	 * @return PushAdInfoDTO
	 * 
	 */
	public PushAdInfoDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<PushAdInfoDTO>
	 */
	public List<PushAdInfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<PushAdInfoDTO>
	 */
	public List<PushAdInfoDTO> getListByCondition(Map<String, Object> map) throws Exception;
}
