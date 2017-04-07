package com.gudeng.commerce.gd.api.service.impl.search;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.search.SearchProductToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.search.dto.FaceWordDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.service.SearchProductService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/** 
* @author  bdhuang 
* @date 创建时间：2016年5月14日 上午10:12:03 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class SearchProductToolServiceImpl implements SearchProductToolService {

	private static final GdLogger logger = GdLoggerFactory
			.getLogger(SearchProductToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;
	
	public SearchProductService searchProductService;
	
	protected SearchProductService getHessianSearchProductService()
			throws MalformedURLException {
		String url = gdProperties.getSearchProductServiceUrl();
		if (searchProductService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			searchProductService = (SearchProductService) factory.create(
					SearchProductService.class, url);
		}
		return searchProductService;
	}
	
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	@Override
	public ProductSearchResultDTO getByProductQueryBean(ProductQueryBean productQueryBean) throws Exception{
		return getHessianSearchProductService().getByProductQueryBean(productQueryBean);
	}

	@Override
	public List<FaceWordDTO> getFacetWord(ProductQueryBean productQueryBean) throws Exception {
		return getHessianSearchProductService().getFacetWord(productQueryBean);
	}
	@Override
	public List<ProductFacetMarketResultDTO> getFacetMarket(ProductQueryBean productQueryBean) throws Exception {
		return getHessianSearchProductService().getFacetMarket(productQueryBean);
	}

}
