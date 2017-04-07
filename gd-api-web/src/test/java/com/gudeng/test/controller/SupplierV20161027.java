package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.Des3;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.test.util.HttpClientUtil;

import junit.framework.TestCase;

public class SupplierV20161027 extends TestCase {
	//private String url = "http://127.0.0.1:8080/gd-api/";
	private String url = "http://10.17.1.205:8082/";

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testList() throws Exception {
		url += "v161027/supplier/business/";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "3");
		//map.put("marketId", 1);
		map.put("memberId", 225);
		map.put("pageNum", 1);
		map.put("pageSize", 20);

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	public void testDynamic() throws Exception {
		url += "v161027/supplier/dynamic/";
		Map<String, Object> map = new HashMap<String, Object>();
		

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	public void testQuotation() throws Exception {
		url += "v161027/supplier/product/";
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberId", 597);
		map.put("pageNum", 1);
		map.put("pageSize", 20);

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	public void testAgricultural() throws Exception {
		url += "v161027/agricultural/proudct/";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("memberId", 281537);
		map.put("pageNum", 1);
		map.put("pageSize", 20);
		map.put("pageSize", 20);
		map.put("queryMode", 2);
		map.put("priceSort", "asc");
		map.put("type", "2");

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url,requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
		
	}
	public void testUpdatePrice() throws Exception {
		url += "v2Encrypt/product/modifyProductPrice/";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("productId", 79893);
		map.put("price", 101);
		map.put("userId", 20);
	

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url,requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
		
	}
}
