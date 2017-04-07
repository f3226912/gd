package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

public class SearchControllerTest extends TestCase {
//	private String url="http://10.17.1.165:8082/";
	private String url="http://localhost:8080/gd-api/";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
    /**
	 * 登录
	 * @throws Exception
	 */
	@Test
	public void testGetFacetMarket() throws Exception {
		url+="/v26/product/getFacetMarket";
		
		Map<String, Object> map=new HashMap<>();
//		map.put("marketId", 1);
//		map.put("roleType", 1);
//		map.put("facetField","name");
//		map.put("facetWord","虾");
		map.put("keyWord", "大白菜");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
	
	/**
	 * 源头好货 - 根据分类ID获取商品列表
	 * @throws Exception
	 */
	@Test
	public void testGetProductCateList() throws Exception {
		url+="/v28/product/getProductCateList";
		
		Map<String, Object> map=new HashMap<>();
		map.put("marketId", 3);
		map.put("roleType", 4);
		map.put("pageSize","10");
		map.put("pageNum","1");
		map.put("categoryId", "1152");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
	
	/**
	 * 源头好货 - 根据商品标签获取商品列表
	 * @throws Exception
	 */
	@Test
	public void testGetProductSignResult() throws Exception {
		url+="/v28/product/getProductSignResult";
		
		Map<String, Object> map=new HashMap<>();
		map.put("marketId", 3);
		map.put("roleType", 4);
		map.put("pageSize","10");
		map.put("pageNum","1");
		map.put("productSign", "山东大蒜");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
}
