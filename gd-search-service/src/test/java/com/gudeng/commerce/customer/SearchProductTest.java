package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.search.service.SearchProductService;

public class SearchProductTest extends TestCase{
	private static SearchProductService getService() throws MalformedURLException{
		String url = "http://192.168.20.164:8083/gd-search/service/searchProductService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (SearchProductService) factory.create(SearchProductService.class, url);				
	}
	
	@SuppressWarnings("static-access")
//	public void testGetDic()throws Exception {
//		SearchProductService service=this.getService();
//		List<ProductSolrDTO> beanList=service.getBySearch("测试");
//	    for(ProductSolrDTO bsDto :beanList){
//	    	System.out.println("id:"+bsDto.getId());
//	    	System.out.println("cateId:"+bsDto.getCateId());
//	    	System.out.println("businessId:"+bsDto.getBusinessId());
//	    	System.out.println("price:"+bsDto.getPrice());
//	    	System.out.println("name:"+bsDto.getName());
//	    	System.out.println("cateName:"+bsDto.getCateName());
//	    	System.out.println("shopsName:"+bsDto.getShopsName());
//	    	System.out.println("createTime:"+bsDto.getCreateTime());
//	    	System.out.println("model:"+bsDto.getBusinessModel());
//	    	System.out.println("marketId:"+bsDto.getMarketId());
//	    	System.out.println("marketName:"+bsDto.getMarketName());
//	    }
//	}

//	@SuppressWarnings("static-access")
	public void testGetByQueryBean()throws Exception {
		SearchProductService service=this.getService();
		ProductQueryBean queryBean=new ProductQueryBean();
		
//		queryBean.setName("测试");
//		queryBean.setBusinessModel(1);
//		queryBean.setCateId(1L);
		queryBean.setStartRow(0);
		queryBean.setPageSize(1000);
//		queryBean.setMarketId(1l);
//		queryBean.setRangeDays(7);
		queryBean.setPriceLowest(1.00);
		queryBean.setPriceHighest(11111111.00);
		Map<String,String> map=new HashMap();
//		map.put("price", "desc");
		map.put("price", "asc");
		queryBean.setOrder(map);

		ProductSearchResultDTO psdto=new ProductSearchResultDTO();
		psdto=service.getByProductQueryBean(queryBean);
		List<ProductSolrDTO> beanList=psdto.getList();
//		List<ProductSolrDTO> beanList1=service.getByQueryBean(queryBean);
		System.out.println("总共个数" + beanList.size());
	    for(ProductSolrDTO bsDto :beanList){
	    	System.out.println("id:"+bsDto.getId());
	    	System.out.println("cateId:"+bsDto.getCateId());
	    	System.out.println("businessId:"+bsDto.getBusinessId());
	    	System.out.println("price:"+bsDto.getPrice());
	    	System.out.println("name:"+bsDto.getName());
	    	System.out.println("cateName:"+bsDto.getCateName());
	    	System.out.println("shopsName:"+bsDto.getShopsName());
	    	System.out.println("createTime:"+bsDto.getCreateTime());
	    	System.out.println("model:"+bsDto.getBusinessModel());
	    	System.out.println("marketId:"+bsDto.getMarketId());
	    	System.out.println("marketName:"+bsDto.getMarketName());
	    	System.out.println("urlOrg:"+bsDto.getUrlOrg());
	    }
	}
	
	
}
