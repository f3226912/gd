package com.gudeng.commerce.gd.admin.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;

/**
 *功能描述：查询实现类
 *
 */
public interface ArgTelStatToolService {
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal2(Map map)throws Exception;
	
	/**
	 * 根据多条件查询
	 * 
	 * e_Mobile like查询
	 * shopName like查询
	 * 
	 * @return list
	 * 
	 */
	public List<CallstatiSticsDTO> getBySearch(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 * 
	 * e_Mobile like查询
	 * shopName like查询
	 * 
	 * @return list
	 * 
	 */
	public List<CallstatiSticsDTO> getBySearch2(Map map) throws Exception;
}