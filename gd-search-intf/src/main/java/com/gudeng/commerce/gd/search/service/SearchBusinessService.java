package com.gudeng.commerce.gd.search.service;

import java.util.List;

import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;

public interface SearchBusinessService {

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
	 * 根据businessQueryBean 统计各个市场中包含的产品个数
	 * 
	 * @param businessQueryBean 
	 * 
	 * @return List<ProductFacetMarketResultDTO>
	 * 
	 */
	List<ProductFacetMarketResultDTO> getFacetMarket(BusinessQueryBean businessQueryBean) throws Exception;
	 
}