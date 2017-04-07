package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.service.SearchBusinessService;

public class SearchBusinessTest extends TestCase{
	private static SearchBusinessService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-search/service/searchBusinessService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (SearchBusinessService) factory.create(SearchBusinessService.class, url);				
	}
	
	@SuppressWarnings("static-access")
//	public void testGetDic()throws Exception {
//		SearchBusinessService service=this.getService();
//		List<BusinessSolrDTO> beanList=service.getBySearch("测试");
//	    for(BusinessSolrDTO bsDto :beanList){
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
		SearchBusinessService service=this.getService();
		BusinessQueryBean queryBean=new BusinessQueryBean();
		
		queryBean.setShopsName("产品");
		queryBean.setMainProduct("产品");
//		queryBean.setBusinessModel(0);
//		queryBean.setCateName("果");
//		queryBean.setMarketId(2);
		queryBean.setStartRow(0);
		queryBean.setPageSize(10);

		BusinessSearchResultDTO psdto=new BusinessSearchResultDTO();
		psdto=service.getByBusinessQueryBean(queryBean);
		List<BusinessSolrDTO> beanList=psdto.getList();
		System.out.println("总共个数" + beanList.size());
	    for(BusinessSolrDTO bsDto :beanList){
	     System.out.println("businessId:"+bsDto.getBusinessId());
	     System.out.println("name:"+bsDto.getName());
	     System.out.println("businessModel:"+bsDto.getBusinessModel());
	     System.out.println("companyProfile:"+bsDto.getCompanyProfile());
	     System.out.println("shopsName:"+bsDto.getShopsName());
	     System.out.println("shopDesc:"+bsDto.getShopDesc());
	     System.out.println("shopsUrl:"+bsDto.getShopsUrl());
	     System.out.println("status:"+bsDto.getStatus());
	     System.out.println("marketId:"+bsDto.getMarketId());
	     System.out.println("marketName:"+bsDto.getMarketName());
	     System.out.println("mainProduct:"+bsDto.getMainProduct());
	     System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。");

//	     List listCato=bsDto.getCategoryId();
//	     List listName=bsDto.getCateName();
//	     for(int i=0;i<listCato.size();i++){
//		     System.out.println("CategoryID:"+listCato.get(i)+"  categoryName:"+listName.get(i));
//	     }
//	     
//	     List listP=bsDto.getProductId();
//	     List listU=bsDto.getUrlOrg();
//	     for(int i=0;i<listP.size();i++){
//		     System.out.println("product :"+listP.get(i)+"  url:"+listU.get(i));
//	     }
	     
	    }
	}
	
	
}
