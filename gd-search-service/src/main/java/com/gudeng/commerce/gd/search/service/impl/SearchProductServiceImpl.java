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
import com.gudeng.commerce.gd.search.dto.FaceWordDTO;
import com.gudeng.commerce.gd.search.dto.MarketDTO;
import com.gudeng.commerce.gd.search.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.search.service.SearchProductService;
import com.gudeng.commerce.gd.search.util.Constant;
import com.gudeng.commerce.gd.search.util.GdProperties;

public class SearchProductServiceImpl implements SearchProductService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	public  GdProperties gdProperties;
	
	protected String  getSolrProductUrl() {
		String url = gdProperties.getSolrProductUrl();
		return url;
	}
	
	@Override
	public List<ProductSolrDTO> getBySearch(String pName) throws Exception {
		String SOLR_URL=getSolrProductUrl();
		HttpSolrServer server = new HttpSolrServer(SOLR_URL);
		server.setMaxRetries(1);
		server.setConnectionTimeout(5000);
		SolrQuery query = new SolrQuery();
		query.setQuery("name:" + pName);
		query.setStart(0);
		query.setRows(100);
		query.setFacet(true);
		QueryResponse response = server.query(query);
		// 搜索得到的结果数
		System.out.println("Find:" + response.getResults().getNumFound());
		// 输出结果
		SolrDocumentList list = response.getResults();
		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();
		List<ProductSolrDTO> beanList = rsp.getBeans(ProductSolrDTO.class);
		return beanList;
	}
	
	
	
	@Override
	public List<ProductSolrDTO> getByQueryBean(ProductQueryBean productQueryBean)
			throws Exception {
		ProductSearchResultDTO psrDto= getByProductQueryBean(productQueryBean);
		return psrDto.getList();
	}



	private String getQueryString(ProductQueryBean productQueryBean) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer queryString=new StringBuffer();
		//商铺ID
		if(null !=productQueryBean.getBusinessId()){
			queryString.append(" businessId:").append(productQueryBean.getBusinessId()).append(" ");
		};
		//经营模式搜索条件
		if(null !=productQueryBean.getBusinessModel()){
			queryString.append(" businessModel:").append(productQueryBean.getBusinessModel()).append(" ");
		};
		
		if(null !=productQueryBean.getManagementType()){
			queryString.append(" managementType:").append(productQueryBean.getManagementType()).append(" ");
		};
		
		if(null !=productQueryBean.getProvinceId() && !productQueryBean.getProvinceId().equals("0") && !productQueryBean.getProvinceId().equals("") && !productQueryBean.getProvinceId().equals("null") ){
			queryString.append(" provinceId:").append(productQueryBean.getProvinceId()).append(" ");
		};
		
		if(null !=productQueryBean.getCityId() && !productQueryBean.getCityId().equals("0") && !productQueryBean.getCityId().equals("") && !productQueryBean.getCityId().equals("null")){
			queryString.append(" cityId:").append(productQueryBean.getCityId()).append(" ");
		};
		
		if(null !=productQueryBean.getAreaId() && !productQueryBean.getAreaId().equals("0")&& !productQueryBean.getAreaId().equals("")&& !productQueryBean.getAreaId().equals("null")){
			queryString.append(" areaId:").append(productQueryBean.getAreaId()).append(" ");
		};
		
		if(null !=productQueryBean.getMarketId()){
			queryString.append(" marketId:").append(productQueryBean.getMarketId()).append(" ");
		};
		
		if(null !=productQueryBean.getRoleType() && productQueryBean.getRoleType().equals("4")){
			queryString.append(" roleType:").append(productQueryBean.getRoleType()).append(" ");
		}else{
			//不传入时，仅仅检索农批商的产品
//			queryString.append(" ( roleType:1  OR  roleType:2 OR roleType:3 ) ");
			queryString.append(" -(roleType:4) ");
		}
		
		
		//产品名称搜索条件
		if(null !=productQueryBean.getName() && !productQueryBean.getName().trim().equals("") ){
			queryString.append(" name:").append(productQueryBean.getName()).append(" ");
		};
		
		//默认关键字仅仅搜索name字段
		if(null !=productQueryBean.getKeyWord() && !productQueryBean.getKeyWord().trim().equals("") ){
			queryString.append(" name:").append(productQueryBean.getKeyWord()).append(" ");
		};
		
		//产品名称搜索条件
		if(null !=productQueryBean.getProductSign() && !productQueryBean.getProductSign().trim().equals("") ){
			queryString.append(" productSign:").append(productQueryBean.getProductSign()).append(" ");
		};
		
		//产品分类Id
//		if(null !=productQueryBean.getCateId()){
//			String cateids="( cateId:"+productQueryBean.getCateId();
//			cateids+=getChileRenCateIds(productQueryBean.getCateId());
//			cateids+=" ) ";
//			queryString.append( cateids ).append(" ");
//		};
		
		if(null !=productQueryBean.getCateId()){
			String cateids=" cateId:"+productQueryBean.getCateId();
			queryString.append( cateids ).append(" ");
		};
		
		//多个产品分类Id
		if(null !=productQueryBean.getCateIds() && !productQueryBean.getCateIds().trim().equals("")){
			String cateids="(";
			for(String cateId:productQueryBean.getCateIds().split(",")){
				cateids+= " OR cateId:"+cateId ;
			}
			if(cateids.indexOf("OR")>0){
				cateids=cateids.replaceFirst("OR","");
			}
			cateids+=")";
			queryString.append( cateids ).append(" ");
		};
		
		
		//时间范围搜索条件
		if(null !=productQueryBean.getRangeDays() ){
			queryString.append("createTime:[NOW/DAY-"+productQueryBean.getRangeDays()+"DAY TO *]").append(" ");
		};
		
		//价格范围搜索条件
		if(null !=productQueryBean.getPriceLowest()){
			if(null !=productQueryBean.getPriceHighest()){
				queryString.append("price:["+productQueryBean.getPriceLowest()+" TO "+productQueryBean.getPriceHighest()+"]").append(" ");
			}else{
				queryString.append("price:["+productQueryBean.getPriceLowest()+" TO * ]").append(" ");
			}
		}else{
			if(null !=productQueryBean.getPriceHighest()){
				queryString.append("price:[ * TO "+productQueryBean.getPriceHighest()+" ]").append(" ");
			}
		}
		if(queryString.toString().equals("")){
			return "*:*";
		}
		System.out.println(queryString.toString()+".............................");
		return queryString.toString();
	}
	
	public String getChileRenCateIds(Long id){
		String ids=" OR cateId:"+String.valueOf(id);
		List<ProductCategoryDTO> list=getChildProductCategory(id);
		for(ProductCategoryDTO pc: list){
			ids+=getChileRenCateIds(pc.getCategoryId());
		}
		return ids;
	}
	
	public List<ProductCategoryDTO> getChildProductCategory(Long id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId",id);
		return this.baseDao.queryForList("ProductCategory.listChildProductCategory", params ,ProductCategoryDTO.class);
	}

	public MarketDTO getMarket(String marketId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id",marketId);
		return (MarketDTO) baseDao.queryForObject("Market.getMarket", params ,MarketDTO.class);
	}
	


	@Override
	public ProductSearchResultDTO getByProductQueryBean(
			ProductQueryBean productQueryBean) throws Exception {
		String SOLR_URL=getSolrProductUrl();

		ProductSearchResultDTO psrDto=new ProductSearchResultDTO();
		
		HttpSolrServer server = new HttpSolrServer(SOLR_URL);
		server.setMaxRetries(1);
		server.setConnectionTimeout(5000);
		SolrQuery query = new SolrQuery();
		query.setQuery(getQueryString(productQueryBean));
		query.setStart(productQueryBean.getStartRow());
		query.setRows(productQueryBean.getPageSize());
		
		Map<String,String> map=productQueryBean.getOrder();
		if(null !=map){
			/** 20161121版默认有金牌会员的排第一阶层，非金牌会员的排第二阶层，同阶层的商品遵循原有排序规则 beging**/
			if(String.valueOf(Constant.MemberLevel.MEMBER_LEVEL_4.num).equals(productQueryBean.getRoleType())){
				query.addSort("memberGrade",SolrQuery.ORDER.desc);
			}
			for(String key : map.keySet()){
//				if(productQueryBean.getRoleType()!=null &&productQueryBean.getRoleType().equals("4")&& key.equals("updatePriceTime")){
			//2016-4-28日需求，农批商和产地供应商一样，默认updatePriceTime desc 
				if(key.equals("updatePriceTime")){
					//产地供应商的搜索，默认产品更新时间倒序+价格升序
//					query.clearSorts();
					query.addSort("updatePriceTime",SolrQuery.ORDER.desc);
//					pricesOrder asc
					query.addSort("pricesOrder",SolrQuery.ORDER.asc);
					break;
				}
				if(null != map.get(key) && map.get(key).equals("desc")){
					if(key!=null && key.equals("price")){
						query.addSort("pricesOrder", SolrQuery.ORDER.desc );
					}else{
						query.addSort(key, SolrQuery.ORDER.desc );
					}
				}else{
					if(key!=null && key.equals("price")){
						query.addSort("pricesOrder", SolrQuery.ORDER.asc );
					}else{
						query.addSort(key, SolrQuery.ORDER.asc );
					}
				}
			}
			/** 20161121版默认有金牌会员的排第一阶层，非金牌会员的排第二阶层，同阶层的商品遵循原有排序规则 end**/
		}
		
		query.setFacet(true);
		QueryResponse response = server.query(query);
		// 搜索得到的结果数
		System.out.println("Find:" + response.getResults().getNumFound());
		// 输出结果
		SolrDocumentList list = response.getResults();
		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();
		List<ProductSolrDTO> beanList = rsp.getBeans(ProductSolrDTO.class);
		
		psrDto.setCount(response.getResults().getNumFound());
		psrDto.setList(beanList);
		return psrDto;
		}
	
	
	@Override
	public List<FaceWordDTO> getFacetWord(ProductQueryBean productQueryBean) throws Exception {
		List<FaceWordDTO> list=new ArrayList<FaceWordDTO>();
		String SOLR_URL=getSolrProductUrl();
		HttpSolrServer service = new HttpSolrServer(SOLR_URL);
       
        QueryResponse queryResponse = new QueryResponse();  
        SolrQuery query = new SolrQuery();  
        try {  
           
               query.setFacet(true);  
               query.setRows(0);  
       		   query.setQuery(getQueryString(productQueryBean));
               query.setFacetPrefix(productQueryBean.getFacetWord()); 
               query.addFacetField(productQueryBean.getFacetField());
               query.setFacetLimit(10);
               System.out.println("query.toString() = "+query.toString());  
               queryResponse = service.query(query); 
               NamedList nl = queryResponse.getResponse();
               System.out.println("nl = "+nl);
               NamedList nl2 = (NamedList)nl.get("facet_counts");  
               NamedList nl3 = (NamedList)nl2.get("facet_fields");  
               NamedList nl4 = (NamedList)nl3.get(productQueryBean.getFacetField());
               System.out.println("facet String --------:"+nl4);
               Iterator  it = nl4.iterator();
               while (it.hasNext()) {
                   Entry entry = (Entry) it.next();  
                   FaceWordDTO faceWordDTO=new FaceWordDTO();
                   faceWordDTO.setName((String)entry.getKey());
                   faceWordDTO.setNum((Integer)entry.getValue());
            	   list.add(faceWordDTO);
               }
            } catch (Exception e) {
                e.printStackTrace();  
            }
			return list; 
		}
	
	

	@Override
	public List<ProductFacetMarketResultDTO> getFacetMarket(ProductQueryBean productQueryBean) throws Exception {
		List<ProductFacetMarketResultDTO> list=new ArrayList<ProductFacetMarketResultDTO>();
		String SOLR_URL=getSolrProductUrl();
		HttpSolrServer service = new HttpSolrServer(SOLR_URL);
       
        QueryResponse queryResponse = new QueryResponse();  
        SolrQuery query = new SolrQuery();  
        try {  
           
               query.setFacet(true);  
               query.setRows(0);  
       		   query.setQuery(getQueryString(productQueryBean));
//               query.setFacetPrefix(productQueryBean.getFacetWord()); 
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
	

    public static void main(String[] args) {       
       
    	HttpSolrServer service = new HttpSolrServer("http://localhost:8080/solr/product/");           
       
        List list = new ArrayList();  
        //查询响应结果
        QueryResponse queryResponse = new QueryResponse();  
        //查询传输参数封装
        SolrQuery query = new SolrQuery();  
       
        try {  
           
               query.setFacet(true);  
               query.setRows(0);  
               query.setQuery("name:虾皮");  
//               query.setFacetPrefix("菠"); 
               query.addFacetField("marketId");
               query.setFacetLimit(100000);
              
               System.out.println("query.toString() = "+query.toString());  
              
               queryResponse = service.query(query); 
               NamedList nl = queryResponse.getResponse();
               System.out.println("nl = "+nl);
              
               NamedList nl2 = (NamedList)nl.get("facet_counts");  
               NamedList nl3 = (NamedList)nl2.get("facet_fields");  
               NamedList nl4 = (NamedList)nl3.get("marketId");  
//               System.out.println("nl4.size() = "+nl4.size());  
               Iterator  it = nl4.iterator();
               while (it.hasNext()) {
                   Entry entry = (Entry) it.next();  
                   System.out.println(entry.getKey() + "____" + entry.getValue()); 
               }
              
            } catch (Exception e) {
                e.printStackTrace();  
            } 
        }

}
