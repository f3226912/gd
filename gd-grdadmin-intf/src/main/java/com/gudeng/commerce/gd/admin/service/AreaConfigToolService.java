package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AreaConfigDTO;
/**
 *
 */
public interface AreaConfigToolService {
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return AreaConfigDTO
	 * 
	 */
	public AreaConfigDTO getById(String id)throws Exception;
	
	
	
	/**
	 * 
	 * @param areaName
	 * @return
	 * @throws Exception
	 */
	public AreaConfigDTO getByName(String areaName) throws Exception ;
	
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<AreaConfigDTO>
	 */
	public List<AreaConfigDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id)throws Exception;
	


	/**
	 * 通过对象插入数据库
	 * 
	 * @param AreaConfigDTO
	 * @return int
	 * 
	 */
	public int addAreaConfigDTO(AreaConfigDTO dto) throws Exception;
	

	
	/**
	 * 
	 * @param AreaConfigDTO
	 * @return int
	 * 
	 */
	public int updateAreaConfigDTO(AreaConfigDTO dto) throws Exception;
	
	
	
}