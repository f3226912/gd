package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MarketBerthDTO;

public interface MarketBerthManageService {
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketBerthDTO> getByCondition(Map<String, Object> map)
			throws Exception;
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	public int queryMarketGroupTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketBerthDTO> getByConditionByDtl(Map<String, Object> map)
			throws Exception;
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByDtl(Map<String, Object> map) throws Exception;
	
	public List<MarketBerthDTO> queryMarketGroup(Map<String, Object> map)
			throws Exception;
	
	public int addMerketBerth(MarketBerthDTO marketBerthDto) throws Exception;
	public int updateMarketBerth(MarketBerthDTO marketBerthDto) throws Exception;
	public int deleteMarketBerth(Map<String, Object> map) throws Exception;
	public int updateMarketBerthById(MarketBerthDTO dto)throws Exception;
	public int updateMarketBerthCodeById(MarketBerthDTO dto)throws Exception;
	public int updateMarketBerthBacth(List<MarketBerthDTO> list)throws Exception;

}
