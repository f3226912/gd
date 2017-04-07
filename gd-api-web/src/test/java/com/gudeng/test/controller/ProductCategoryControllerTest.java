package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class ProductCategoryControllerTest extends TestCase {
//	private String url="http://10.17.1.167:8082/";
	private String url="http://localhost:8080/gd-api/";
//	private String url="http://localhost:8080/gd-m/";
	
/*	public void testList() throws Exception {
		url+="v160929/productCategory/categoryList";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", "1065");
		map.put("marketId", 1);
		map.put("sortByCategory", 1);

		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}*/
	
	
/*	public void testProdList0() throws Exception {
		url+="product/getProductByProdId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", "61192");
		map.put("fromCode", "3");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}*/
	
	public void testProdList() throws Exception {
		url+="v160929/prodCertif/querySpProductForCertif";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", "1212");
		map.put("pageNum", "1");
		map.put("pageSize ", "10");

		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
}
