package com.gudeng.commerce.gd.report.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.report.dto.DictDTO;

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
	 * 	  根据Birthday查询对象集合
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DictDTO> getByBirthday(Map<String, Object> map) throws Exception;

	

	/**
	 * 	  根据name查询对象集合
	 * 
	 *   拓展后，实现多个条件查询
	 *   
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DictDTO> getByName(Map<String, Object> map);

	
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
	 * 通过Map插入数据库
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int addDictDTO(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param dictDTO
	 * @return int
	 * 
	 */
	public int addDictDTO(DictDTO dic) throws Exception;
	
	/**
	 * 通过对象跟新数据库
	 * 
	 * @param dictDTO
	 * @return int
	 * 
	 */
	public int updateDictDTO(DictDTO dic) throws Exception;


}