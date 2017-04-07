package com.gudeng.commerce.gd.search.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.search.dto.DictDTO;

public interface DemoService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DictDTO
	 * 
	 */
	public DictDTO getById(String id)throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<DictDTO>
	 */
	public List<DictDTO> getByCondition(Map<String,Object> map)throws Exception;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
}