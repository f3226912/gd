package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreSaleDetailDTO;
import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;

public interface PreSaleDetailService {
	/**
	 * 添加
	 * 
	 * @param PreSaleDetailEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(PreSaleDetailEntity obj) throws Exception;

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
	 * @param PreSaleDetailDTO
	 * @return int
	 * 
	 */
	public int updateDTO(PreSaleDetailDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param PreSaleDetailDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<PreSaleDetailDTO> objList) throws Exception;
	
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
	 * @return PreSaleDetailDTO
	 * 
	 */
	public PreSaleDetailDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<PreSaleDetailDTO>
	 */
	public List<PreSaleDetailDTO> getListByConditionPage(Map<String, Object> map) throws Exception;
	

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<PreSaleDetailDTO>
	 */
	public List<PreSaleDetailDTO> getListByCondition(Map<String, Object> map) throws Exception;

	public int batchInsertEntity(List<PreSaleDetailEntity> entityList) throws Exception;

	public List<PreSaleDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception;
}
