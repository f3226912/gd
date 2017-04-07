package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.output.SupplierBusinessBaseinfo;
import com.gudeng.commerce.gd.api.dto.output.TradingDynamicsOutDTO;
import com.gudeng.commerce.gd.api.params.BusinessParamsBean;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;

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
	 * 根据userID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserId(String userId)throws Exception;
	public BusinessBaseinfoDTO getBusinessInfoByUserId(Map<String,Object> map)throws Exception;
	
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
	
	/**
	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
	 * 
	 * @param  map
	 * @return list
	 * 
	 */
	public List<BusinessBaseinfoDTO> getAll(int startRow,int endRow) throws Exception;

	/**
	 * 根据条件查找农批商
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BusinessAppDTO> getShops(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查找农批商总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getShopsTotal(Map<String, Object> map) throws Exception;
	
	
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
	 * 将 BusinessSolrDTO list集合转为  BusinessAppDTO
	 * 
	 * @param  
	 * @return  
	 * 
	 */
	public List<BusinessAppDTO> translate(List<BusinessSolrDTO> list,
			List<BusinessAppDTO> businessAppList,Long userId) throws Exception;
	
	


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
	public int deleteReBusinessCategoryByBusinessId(Long businessId) throws Exception;

	
  

	
	/**
	 * 通过 bussinessId 和 categoryID 查询记录数字
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int getReBusinessCategoryTotal(Map map) throws Exception;
	
  
	
	
	/**
	 * 通过 bussinessId 和 categoryID 查询记录集合
	 * 
	 * @param map
	 * @return list
	 * 
	 */
	public List<ReBusinessCategoryDTO>  getReBusinessCategoryBySearch(Map map) throws Exception;

	
	public void copyPropeties(BusinessBaseinfoDTO dto, BusinessParamsBean params);
	
	/**
	 * 根据名字查询商品名称
	 * @return
	 * @throws Exception
	 */
	public List<BusinessBaseinfoDTO> getShop(BusinessBaseinfoDTO businessBaseinfoDTO) throws Exception;

	public BusinessDetailAppDTO getByMap(Map<String, Object> map) throws Exception;

	/**
	 * 查询秤mac地址
	 */
	public BusinessBaseinfoDTO getByMacAddr(String macAddr)throws Exception;
	
	/**
	 * 根据businessQueryBean 统计各个市场中包含的产品个数
	 * 
	 * @param businessQueryBean
	 * 
	 * @return List<ProductFacetMarketResultDTO>
	 * 
	 */
	List<ProductFacetMarketResultDTO> getFacetMarket(BusinessQueryBean businessQueryBean) throws Exception;
	/**
	 * 商铺查询接口（找买家、精准买家、推荐买家）
	 * @param inputDTO
	 * @return
	 * @throws Exception 
	 */
	List<SupplierBusinessBaseinfo> getBusiness(Map param) throws Exception;
	
	/**
	 * 获取商铺交易动态
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	List<TradingDynamicsOutDTO> getTradingDynamics(Map<String, Object> map) throws Exception;

    Integer getTotalBusiness(Map<String, Object> param) throws Exception;
    
    /**
	 * 根据商铺ID查找
	 * 查找商铺pos关联信息
	 * @param  
	 * @return String 关联的信息ids(用,号隔开)
	 * @throws Exception
	 */
	public String getPosInfoByBusinessId(Long businessId) throws Exception;
}
