package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;



public interface AreaSettingService{
	


	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return AreaSettingDTO
	 * 
	 */
	public AreaSettingDTO getById(String id)throws Exception;
	
	
	
	/**
	 * 
	 * @param areaName
	 * @return
	 * @throws Exception
	 */
	public AreaSettingDTO getByName(String areaName) throws Exception ;
	
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<AreaSettingDTO>
	 */
	public List<AreaSettingDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AreaSettingDTO> getAllAreaName(Map<String,Object> map) throws Exception ;
	
	
	
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
	 * @param AreaSettingDTO
	 * @return int
	 * 
	 */
	public int addAreaSettingDTO(AreaSettingDTO dto) throws Exception;
	

	
	/**
	 * 
	 * @param AreaSettingDTO
	 * @return int
	 * 
	 */
	public int updateAreaSettingDTO(AreaSettingDTO dto) throws Exception;
	
	/**
	 * 批量导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int batchAddAreaSetting(List<AreaSettingDTO> list) throws Exception ;



}
