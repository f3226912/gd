package com.gudeng.commerce.gd.customer.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;

/**
 *功能描述：ReMemberMarketDTO 增删改查实现类
 *
 */
public interface ReMemberMarketService {


	/**
	 * 通过 ReMemberMarketDTO 对象插入数据库
	 * 
	 * @param ReMemberMarketDTO
	 * @return int
	 * 
	 */
	public int addReMemberMarketDTO(ReMemberMarketDTO rb) throws Exception;
	
	
	/**
	 * 通过 ReMemberMarketDTO 对象删除记录
	 * 
	 * @param ReMemberMarketDTO
	 * @return int
	 * 
	 */
	public int deleteReMemberMarketDTO(ReMemberMarketDTO rb) throws Exception;
	
  

	
	/**
	 * 通过 bussinessId 和 marketID 查询记录数字
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int getTotal(Map map) throws Exception;
	
  
	
	
	/**
	 * 通过 bussinessId 和 marketID 查询记录集合
	 * 
	 * @param map
	 * @return list
	 * 
	 */
	public List<ReMemberMarketDTO>  getBySearch(Map map) throws Exception;


	/**
	 * 根据用户id或市场id
	 * 查找记录集合
	 * @param rmm
	 * @return
	 * @throws Exception
	 */
	public List<ReMemberMarketDTO>  getByDTO(ReMemberMarketDTO rmm) throws Exception;

}