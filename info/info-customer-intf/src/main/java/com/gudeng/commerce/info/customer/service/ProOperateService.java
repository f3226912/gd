package com.gudeng.commerce.info.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.entity.ProOperateEntity;


public interface ProOperateService {

	/**
	 * 添加
	 * 
	 * @param ProOperateEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(ProOperateEntity obj) throws Exception;

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
	 * @param ProOperateDTO
	 * @return int
	 * 
	 */
	public int updateDTO(ProOperateDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param ProOperateDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<ProOperateDTO> objList) throws Exception;
	
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
	 * @return ProOperateDTO
	 * 
	 */
	public ProOperateDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<ProOperateDTO>
	 */
	public List<ProOperateDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<ProOperateDTO>
	 */
	public List<ProOperateDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 获取单个报表数据
	 * @param map
	 * @return
	 */
	public List<ProOperateDTO> sumReport(Map<String, Object> map);
}
