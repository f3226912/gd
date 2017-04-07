package com.gudeng.commerce.gd.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.search.dao.BaseDao;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.dto.MarketDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.service.SearchBusinessService;
import com.gudeng.commerce.gd.search.util.GdProperties;

public class SearchBusinessServiceImpl implements SearchBusinessService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	public  GdProperties gdProperties;
	
	protected String  getSolrBusinessUrl() {
		String url = gdProperties.getSolrBusinessUrl();
		return url;
	}
	
	@Override
	public List<BusinessSolrDTO> getBySearch(String pName) throws Exception {
	return null;
	}
	

	@Override
	public BusinessSearchResultDTO getByBusinessQueryBean(
			BusinessQueryBean businessQueryBean) throws Exception {
		BusinessSearchResultDTO bsrDto=new BusinessSearchResultDTO();
		String SOLR_URL=getSolrBusinessUrl();
		HttpSolrServer server = new HttpSolrServer(SOLR_URL);
		server.setMaxRetries(1);
		server.setConnectionTimeout(5000);
		SolrQuery query = new SolrQuery();
		query.setQuery(getQueryString(businessQueryBean));
		query.setStart(businessQueryBean.getStartRow());
		query.setRows(businessQueryBean.getPageSize());
		
		
//		Map<String,String> map=businessQueryBean.getOrder();
//		if(null == map|| map.size()==0){
//			query.setSort("createTime", SolrQuery.ORDER.desc );
//		}
//		for(String key : map.keySet()){
//			if(null != map.get(key) && map.get(key).equals("desc")){
//				query.setSort(key, SolrQuery.ORDER.desc );
//			}else{
//				query.setSort(key, SolrQuery.ORDER.asc );
//			}
//		}
		
		query.setFacet(true);
		QueryResponse response = server.query(query);
		// 搜索得到的结果数
		System.out.println("Find:" + response.getResults().getNumFound());
		// 输出结果
		SolrDocumentList list = response.getResults();
		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();
		List<BusinessSolrDTO> beanList = rsp.getBeans(BusinessSolrDTO.class);
		
		bsrDto.setCount( response.getResults().getNumFound());
		bsrDto.setList(beanList);
		
		return bsrDto;	
		
	}

	@Override
	public List<BusinessSolrDTO> getByQueryBean(
			BusinessQueryBean businessQueryBean) throws Exception {
		return null;
	}
	
	public String getQueryString(BusinessQueryBean businessQueryBean){
		
		
		StringBuffer queryString=new StringBuffer();
		
		
		//经营模式搜索条件
		if(null !=businessQueryBean.getBusinessModel()){
			queryString.append("businessModel:").append(businessQueryBean.getBusinessModel()).append(" ");
		};
		//经营类型搜索条件
		if(null !=businessQueryBean.getManagementType()){
			queryString.append("managementType:").append(businessQueryBean.getManagementType()).append(" ");
		};
		
		if(null !=businessQueryBean.getProvinceId() && !businessQueryBean.getProvinceId().equals("null") && !businessQueryBean.getProvinceId().equals("")&& !businessQueryBean.getProvinceId().equals("0")){
			queryString.append(" provinceId:").append(businessQueryBean.getProvinceId()).append(" ");
		};
		
		if(null !=businessQueryBean.getCityId() && !businessQueryBean.getCityId().equals("null") && !businessQueryBean.getCityId().equals("")&& !businessQueryBean.getCityId().equals("0")){
			queryString.append(" cityId:").append(businessQueryBean.getCityId()).append(" ");
		};
		
		if(null !=businessQueryBean.getAreaId() && !businessQueryBean.getAreaId().equals("null")&& !businessQueryBean.getAreaId().equals("")&& !businessQueryBean.getAreaId().equals("0")){
			queryString.append(" areaId:").append(businessQueryBean.getAreaId()).append(" ");
		};
		
		
		//所属市场
		if(null !=businessQueryBean.getMarketId()){
			queryString.append("marketId:").append(businessQueryBean.getMarketId()).append(" ");
		};
		
		//是否认证
		if(null !=businessQueryBean.getStatus()){
			if(businessQueryBean.getStatus()==1){
				queryString.append("status:").append(businessQueryBean.getStatus()).append(" ");
			}else{
				//未认证的，包括 0 未认证，2已驳回，还有一个未提交认证的未考虑到
//				queryString.append(" (  status:2 OR status:0 ) ");
				queryString.append(" -(status:1)  ");
			}
		};
		
		//店铺名称搜索条件
		if(null !=businessQueryBean.getShopsName() && !businessQueryBean.getShopsName().trim().equals("") ){
			
 			if(null !=businessQueryBean.getMainProduct() && !businessQueryBean.getMainProduct().trim().equals("") ){
 				//店铺名称 和 店铺主营产品描述都不为空
 				queryString.append("( shopsName:").append(businessQueryBean.getShopsName()).append(" OR ");
				queryString.append(" mainProduct:").append(businessQueryBean.getMainProduct()).append(" ) ");
			}else{
 				//店铺名称 不为空
 				queryString.append(" shopsName:").append(businessQueryBean.getShopsName()).append("  ");

			}
		}else{
				//店铺名称 为空，店铺主营产品描述不为空
 			if(null !=businessQueryBean.getMainProduct() && !businessQueryBean.getMainProduct().trim().equals("") ){
				queryString.append(" mainProduct:").append(businessQueryBean.getMainProduct()).append("   ");

 			}
			
		}
		
		
		 
		//经营类目
		if(null !=businessQueryBean.getCateName() && !businessQueryBean.getCateName().trim().equals("") ){
			queryString.append(" cateName:").append(businessQueryBean.getCateName()).append(" ");
		};
		
		//经营类目
		if(null !=businessQueryBean.getCategoryId() && businessQueryBean.getCategoryId() != 0){
			queryString.append(" categoryId:").append(businessQueryBean.getCategoryId()).append(" ");
		};
				
		
		//	  普通买家找 谷登农批  的店铺，   谷登农批找产地供应商店铺
		if(null !=businessQueryBean.getLevel() && businessQueryBean.getLevel()==4){
			queryString.append(" level:").append(businessQueryBean.getLevel()).append(" ");
		} else{
			//不传入时，仅仅检索农批商的店铺
//			queryString.append(" ( level:1 OR level:2 OR level:3 )  ").append(" ");
			queryString.append(" -(level:4)  ").append(" ");

		}
		 
		if(businessQueryBean.getIsApp()){//农商友，农批商搜索，无产品时，商铺不展示
			queryString.append(" productId:*  ").append(" ");
		}
				
		 
		
 
		if(queryString.toString().equals("")){
			return "*:*";
		}
		System.out.println(queryString.toString()+".........................................................................");
		return queryString.toString();
		
	}
	

	@Override
	public List<ProductFacetMarketResultDTO> getFacetMarket(BusinessQueryBean businessQueryBean) throws Exception {
		List<ProductFacetMarketResultDTO> list=new ArrayList<ProductFacetMarketResultDTO>();
		String SOLR_URL=getSolrBusinessUrl();
		HttpSolrServer service = new HttpSolrServer(SOLR_URL);
       
        QueryResponse queryResponse = new QueryResponse();  
        SolrQuery query = new SolrQuery();  
        try {  
           
               query.setFacet(true);  
               query.setRows(0);  
       		   query.setQuery(getQueryString(businessQueryBean));
               query.addFacetField("marketId");
               query.setFacetLimit(1000);
               System.out.println("query.toString() = "+query.toString());  
               queryResponse = service.query(query); 
               NamedList nl = queryResponse.getResponse();
               System.out.println("nl = "+nl);
               NamedList nl2 = (NamedList)nl.get("facet_counts");  
               NamedList nl3 = (NamedList)nl2.get("facet_fields");  
               NamedList nl4 = (NamedList)nl3.get("marketId");
               System.out.println("facet String --------:"+nl4);
               Iterator  it = nl4.iterator();
               while (it.hasNext()) {
                   Entry entry = (Entry) it.next();
                   Integer count=(Integer)entry.getValue();
                   String marketId=(String)entry.getKey();
                   if(count>0 && marketId!=null && !marketId.equals("3")){//个数大于0，且不是产地供应商
                	   ProductFacetMarketResultDTO dto=new ProductFacetMarketResultDTO();
                       dto.setMarketId(marketId);
                       dto.setCount(count);
                       dto.setMarketName(getMarket((String)entry.getKey()).getMarketName());
                	   list.add(dto);
                   }
                  
               }
            } catch (Exception e) {
                e.printStackTrace();  
            }
			return list; 
		}
	
	public MarketDTO getMarket(String marketId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", marketId);
		return (MarketDTO) baseDao.queryForObject("Market.getMarket", params, MarketDTO.class);
	}
}
