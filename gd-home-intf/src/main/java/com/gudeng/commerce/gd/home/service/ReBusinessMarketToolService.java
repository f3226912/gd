package com.gudeng.commerce.gd.home.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;

/**
 *功能描述：ReBusinessMarket  增删改查实现类
 *
 */
public interface ReBusinessMarketToolService {


	/**
	 * 通过 ReBusinessMarketDTO 对象插入数据库
	 * 
	 * @param ReBusinessMarketDTO
	 * @return int
	 * 
	 */
	public int addReBusinessMarketDTO(ReBusinessMarketDTO rb) throws Exception;
	
	
	/**
	 * 通过 ReBusinessMarketDTO 对象删除记录
	 * 
	 * @param ReBusinessMarketDTO
	 * @return int
	 * 
	 */
	public int deleteReBusinessMarketDTO(ReBusinessMarketDTO rb) throws Exception;
	
	/**
	 * 通过 businessId 删除记录
	 * 
	 * @param businessId
	 * @return int
	 * 
	 */
	public int deleteByBusinessId(Long businessId) throws Exception;

	
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
	public List<ReBusinessMarketDTO>  getBySearch(Map map) throws Exception;
	
	/**
	 * 通过 bussinessId 和 marketID 查询记录集合
	 * 
	 * @param map
	 * @return list
	 * 
	 * 无翻页
	 */
	public List<ReBusinessMarketDTO>  getAllBySearch(Map map) throws Exception;


	public ReBusinessMarketDTO getByBusinessId(Long businessId) throws Exception;



}