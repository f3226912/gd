package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;

public interface BusinessBaseinfoToolService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getById(String id)throws Exception;
	

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserId(String userId)throws Exception;
	
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserIdAndMarketId(String userId,String marketId)throws Exception;
	


	
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
	public int addBusinessBaseinfoByMap(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	
	

	/**
	 * 通过 BusinessBaseinfoEntity 对象插入数据库
	 * 
	 * @param BusinessBaseinfoEntity
	 * @return Long
	 * 
	 */
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be) throws Exception;
	
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	
	/**
	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
	 * 
	 * @param  map
	 * @return list
	 * 
	 */
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception;
	
//	/**
//	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
//	 * 
//	 * @param  map
//	 * @return list
//	 * 
//	 */
//	public List<BusinessBaseinfoDTO> getAll(Map map) throws Exception;

	/**
	 * 根据店铺名称搜索 
	 * 
	 * 未实现
	 * 
	 * @param string 
	 * @return BusinessSolrDTO
	 * 
	 */
	public List<BusinessSolrDTO> getBySearch(String bName)throws Exception;
	
	
	/**
	 * 根据 businessQueryBean 搜索 
	 * 
	 * 未 现
	 * 
	 * @param  businessQueryBean 
	 * @return List<BusinessSolrDTO> 
	 * 
	 */
	public List<BusinessSolrDTO> getByQueryBean(BusinessQueryBean businessQueryBean)throws Exception;


	
	/**
	 * 根据 businessQueryBean 搜索 
	 * 
	 * 未 现
	 * 
	 * @param  businessQueryBean 
	 * @return BusinessSearchResultDTO
	 * 
	 */
	
	public BusinessSearchResultDTO getByBusinessQueryBean(BusinessQueryBean businessQueryBean) throws Exception;
	 
	/**
	 * 添加一次浏览次数
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public void addBrowser(Long businessId) throws Exception;

	/**
	 * 获得类目
	 * 通过商铺ID
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessCategoryDTO> getCategoryList(Long businessId,int start, int size) throws Exception;
	
}