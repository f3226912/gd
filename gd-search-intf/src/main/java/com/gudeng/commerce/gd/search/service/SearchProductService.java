package com.gudeng.commerce.gd.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.gudeng.commerce.gd.search.dto.FaceWordDTO;
import com.gudeng.commerce.gd.search.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;

public interface SearchProductService {

	/**
	 * 根据店铺名称搜索 
	 * 
	 * @param string 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getBySearch(String pName)throws Exception;
	
	
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getByQueryBean(ProductQueryBean productQueryBean)throws Exception;

	/**
	 * 获取当前分类的所有子类
	 * 
	 * @param long 
	 * @return ProductCategoryDTO
	 * 
	 */
	List<ProductCategoryDTO> getChildProductCategory(Long id);
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public ProductSearchResultDTO getByProductQueryBean(ProductQueryBean productQueryBean) throws Exception;


	/**
	 * 根据productQueryBean 智能提示产品标题中的词
	 * 
	 * @param productQueryBean 
	 * 
	 * @return List<Map<String,Integer>>
	 * 
	 */
	public List<FaceWordDTO> getFacetWord(ProductQueryBean productQueryBean) throws Exception;
	 
	/**
	 * 根据productQueryBean 统计各个市场中包含的产品个数
	 * 
	 * @param productQueryBean 
	 * 
	 * @return List<ProductFacetMarketResultDTO>
	 * 
	 */
	public List<ProductFacetMarketResultDTO> getFacetMarket(ProductQueryBean productQueryBean) throws Exception;
	
}