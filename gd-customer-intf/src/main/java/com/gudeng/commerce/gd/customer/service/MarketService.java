package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MarketDTO;


/**
 *功能描述：后台街市管理
 */
public interface MarketService{
	


	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return MarketDTO
	 * 
	 */
	public MarketDTO getById(String id)throws Exception;
	
	
	
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
	public List<MarketDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 * 根据条件查询list不分页
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketDTO> getAllByCondition(Map<String,Object> map)throws Exception;


	/**
	 * 根据状态查询所有街市
     * @param status,传null查询所用启用和停用
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByStatus(String status) throws Exception;
	
	/**
	 * 根据状态和类型查询
     * @param status,传null查询所用启用和停用
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByStatusAndType(String status, String type) throws Exception;
	
	
	/**
	 * 根据类型查询所有街市
     * @param status,传null查询所用
	 * @return
	 * @throws Exception
	 */
	
	public List<MarketDTO> getAllByType(String type) throws Exception;
		
	
	
	/**
	 * 	  根据name查询对象集合
	 * 
	 *   拓展后，实现多个条件查询
	 *   
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getByName(Map<String, Object> map);

	
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
	public int addMarketDTO(Map<String,Object> map)throws Exception;

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
	 * 返回插入主键
	 * @param MarketDTO
	 * @return String
	 * 
	 */
	public Long addMarket(MarketDTO dto) throws Exception;
	
	/**
	 * 通过对象跟新数据库
	 * 
	 * @param MarketDTO
	 * @return int
	 * 
	 */
	public int updateMarketDTO(MarketDTO dto) throws Exception;


	public List<MarketDTO> getAllByTypes(List<String> types) throws Exception;
	
	public List<MarketDTO> getAllBySearch(Map<String, Object> params) throws Exception;

	/**
	 * 获取最近的市场
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getNearbyMarket(Map<String, Object> map) throws Exception;
	
	public MarketDTO getMarketById(Map<String, Object> map) throws Exception;
	
	public List<MarketDTO> getMarketList(Map<String, Object> paramsMap) throws Exception;
	
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
	 * 市场列表(分页查询,按市场的创建时间倒序排序)
	 * 
	 * @param map
	 * @return List<MarketDTO>
	 */
	public List<MarketDTO> getMarketListByCondition(Map<String,Object> params) throws Exception;
}
