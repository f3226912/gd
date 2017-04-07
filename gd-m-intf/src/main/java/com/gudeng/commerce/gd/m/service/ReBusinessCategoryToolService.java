package com.gudeng.commerce.gd.m.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;

/**
 *功能描述：ReBusinessCategoryDTO 增删改查实现类
 *
 */
public interface ReBusinessCategoryToolService {


	/**
	 * 通过 ReBusinessCategoryDTO 对象插入数据库
	 * 
	 * @param ReBusinessCategoryDTO
	 * @return int
	 * 
	 */
	public int addReBusinessCategoryDTO(ReBusinessCategoryDTO rb) throws Exception;
	
	
	/**
	 * 通过 ReBusinessCategoryDTO 对象删除记录
	 * 
	 *  1 当  businessId，categoryId 均有值时，删除对应的一条记录 ,
	 *	2 当categoryId没有值的时候，即 根据 businessId  删除多条记录 ,
	 * 
	 * @param ReBusinessCategoryDTO
	 * @return int
	 * 
	 */
	public int deleteReBusinessCategoryDTO(ReBusinessCategoryDTO rb) throws Exception;
	
	/**
	 * 
	 * 即 根据 businessId  删除多条记录 ,
	 * 
	 * @param ReBusinessCategoryDTO
	 * @return int
	 * 
	 */
	public int deleteByBusinessId(Long businessId) throws Exception;

	
  

	
	/**
	 * 通过 bussinessId 和 categoryID 查询记录数字
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int getTotal(Map map) throws Exception;
	
  
	
	
	/**
	 * 通过 bussinessId 和 categoryID 查询记录集合
	 * 
	 * @param map
	 * @return list
	 * 
	 */
	public List<ReBusinessCategoryDTO>  getBySearch(Map map) throws Exception;




}