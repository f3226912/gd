package com.gudeng.commerce.gd.api.service.search;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.search.dto.FaceWordDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;

/** 
* @author  bdhuang 
* @date 创建时间：2016年5月14日 上午10:09:20 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface SearchProductToolService {
	
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
	 * 根据productQueryBean 智能提示市场
	 * 
	 * @param productQueryBean 
	 * 
	 * @return List<ProductFacetMarketResultDTO>
	 * 
	 */
	public List<ProductFacetMarketResultDTO> getFacetMarket(ProductQueryBean productQueryBean) throws Exception;
	
	

}
