package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MarketDTO;

public interface MarketManageService {
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return MarketDTO
	 * 
	 */
	public MarketDTO getById(String id) throws Exception;
	
	
	
	/**
	 * 
	 * @param marketName
	 * @return
	 * @throws Exception
	 */
	public MarketDTO getMarketByName(String marketName) throws Exception ;
	

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketDTO> getByCondition(Map<String, Object> map)
			throws Exception;

	
    /**
     * 根据状态查询所有街市
     * @param status,传null查询所用启用和停用
     * @return
     * @throws Exception
     */
	public List<MarketDTO> getAllByStatus(String status)
			throws Exception ;
	
	
	 /**
     * 根据街市类型查询所有街市
     * @param type,传null查询所有
     * @return
     * @throws Exception
     */
	public List<MarketDTO> getAllByType(String type)
			throws Exception ;
	
	
	/**
	 *  通过name获list集合
	 *  拓展实现多条件查询
	 *  
	 * @param map
	 * @return List<MarketDTO>
	 * @throws Exception
	 */
	public List<MarketDTO> getByName(Map<String, Object> map) throws Exception;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MarketDTO
	 * @return int
	 * 
	 */
	public int addMarketDTO(Map<String, Object> map) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MarketDTO
	 * @return int
	 * 
	 */
	public int addMarketDTO(MarketDTO dto) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MarketDTO
	 * @return int
	 * 
	 */
	public int updateMarketDTO(MarketDTO market) throws Exception;
	
	
	/**
	 * 根据状态和类型查询
     * @param status,传null查询所用启用和停用
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByStatusAndType(String status, String type) throws Exception;
	

	public List<MarketDTO> getAllByTypes(List<String> type) throws Exception;
	
	/**
	 * 通过marketid获取主营分类
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getCateNamebyMarket(Long marketId) throws Exception;
	
	/**
	 * 查询记录总数
	 * 
	 * @param map
	 * @return 记录总数
	 * 
	 */
	public int getCountOfMarketList(Map<String,Object> params) throws Exception;
	
	/**
	 * 市场列表(分页查询)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketDTO> getListOfMarket(Map<String,Object> params) throws Exception;
	
	public int getMarketCountByCondition(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 市场列表(分页查询,按创建时间倒序排序)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketDTO> getMarketListByCondition(Map<String,Object> params) throws Exception;
	
}
