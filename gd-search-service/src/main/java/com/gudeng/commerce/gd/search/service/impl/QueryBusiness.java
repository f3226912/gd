package com.gudeng.commerce.gd.search.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

public class QueryBusiness {


	
//	public static final String SOLR_URL = "http://192.168.230.128:8983/solr";
  public static final String SOLR_URL = "http://localhost:8080/solr/product";
// http://localhost:8983/solr/#/mytestX/dataimport?command=delta-import

  public static void main(String[] args) throws SolrServerException, IOException {
    HttpSolrServer server = new HttpSolrServer(SOLR_URL);
    server.setMaxRetries(1);
    server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
    server.setConnectionTimeout(5000); // 5 seconds to establish TCP
    //正常情况下，以下参数无须设置
    //使用老版本solrj操作新版本的solr时，因为两个版本的javabin incompatible,所以需要设置Parser
    server.setParser(new XMLResponseParser());
    server.setSoTimeout(1000); // socket read timeout
    server.setDefaultMaxConnectionsPerHost(100);
    server.setMaxTotalConnections(100);
    server.setFollowRedirects(false); // defaults to false
    // allowCompression defaults to false.
    // Server side must support gzip or deflate for this to have any effect.
    server.setAllowCompression(true);

    //使用ModifiableSolrParams传递参数
//		ModifiableSolrParams params = new ModifiableSolrParams();
//		// http://localhost:8080/solr/product?q=video&fl=id,name,price&sort=price asc&start=0&rows=2&wt=json
//		// 设置参数，实现上面URL中的参数配置
//		// 查询关键词
//		params.set("q", "video");
//		// 返回信息
//		params.set("fl", "id,name,price,score");
//		// 排序
//		params.set("sort", "price asc");
//		// 分页,start=0就是从0开始,rows=5当前返回5条记录,第二页就是变化start这个值为5就可以了
//		params.set("start", 2);
//		params.set("rows", 2);
//		// 返回格式
//		params.set("wt", "javabin");
//		QueryResponse response = server.query(params);

    //使用SolrQuery传递参数，SolrQuery的封装性更好
    server.setRequestWriter(new BinaryRequestWriter());
    SolrQuery query = new SolrQuery();
//    http://192.168.22.71:8983/solr/solrtest/select?q=*%3A*&wt=json&indent=true
//    query.setQuery("user:张三1");
    query.setQuery("*:*");
    query.setFields("p_id","p_cateId","p_businessId","p_name","p_price","p_cateName","p_shopsName");
//    query.setSort("price", ORDER.asc);
    query.setStart(0);
    query.setRows(100);
//		query.setRequestHandler("/select");
    QueryResponse response = server.query( query );
    
    // 搜索得到的结果数
    System.out.println("Find:" + response.getResults().getNumFound());
    // 输出结果
    int iRow = 1;
    for (SolrDocument doc : response.getResults()) {
      System.out.println("----------" + iRow + "------------");
//      System.out.println("productId: " + doc.getFieldValue("productId").toString());
//      System.out.println("cateId: " + doc.getFieldValue("cateId").toString());
//      System.out.println("businessId: " + doc.getFieldValue("businessId").toString());
      System.out.println("p_name: " + doc.getFieldValue("p_name").toString());
      System.out.println("p_cateName: "  + doc.getFieldValue("p_cateName").toString());
      System.out.println("p_shopsName: " + doc.getFieldValue("p_shopsName"));
      iRow++;
    }
  }
  
	
}
