package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class PurchaseOrderV01ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/purchOrder/";
//	private String url = "http://10.17.1.201:8082/purchOrder/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
     * 测试
	 * 增加商品采购信息
	 * @throws Exception
	 */
	public void testAdd() throws Exception {
		url += "add";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 增加商品信息 开始 ---------------------------");
		List<Map<String, Object>> productList = new ArrayList<>();
		Map<String, Object> pMap1 = new HashMap<>();
		pMap1.put("productId", 61124);
		pMap1.put("purQuantity", 50);
		pMap1.put("price", 8);
		productList.add(pMap1);
		
//		Map<String, Object> pMap2 = new HashMap<>();
//		pMap2.put("productId", 38982);
//		pMap2.put("purQuantity", 100);
//		productList.add(pMap2);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("jProductDetails", gson.toJson(productList));
		map.put("businessId", "30890");
		map.put("memberId", "4116");
		map.put("channel", "2");
		map.put("marketId", "1");
		map.put("orderAmount", "400");
		map.put("payAmount", "400");
		System.out.println("------------------------ 增加商品信息 结束 ---------------------------");	
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
     * 测试
	 * 采购订单列表信息
	 * @throws Exception
	 */
	public void testList() throws Exception {
		url += "list";
		Gson gson = new Gson();
		
		//isBuyer=1&memberId=4352&orderStatus=1&pageNum=1
		
		System.out.println("------------------------ 采购订单列表信息 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderStatus", "8");
		map.put("memberId", "4116");
		map.put("isBuyer", "1");
		map.put("pageNum", "1");
		System.out.println("------------------------ 采购订单列表信息 结束 ---------------------------");	
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
     * 测试
	 * 采购订单详细信息
	 * @throws Exception
	 */
	public void testDetail() throws Exception {
		url += "detail";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 采购订单详细信息 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", "722016000072071");
		System.out.println("------------------------ 采购订单详细信息 结束 ---------------------------");	
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
     * 测试
	 * 修改采购订单信息
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
		url += "update";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 取消采购订单 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", "312016000072053");
		map.put("memberId", "4116");
		map.put("opType", "1");
			
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 取消采购订单 结束 ---------------------------");
		
		System.out.println("------------------------ 采购订单确认发货 开始 ---------------------------");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("orderNo", "742016000072059");
		map2.put("memberId", "4116");
		map2.put("opType", "2");
			
		System.out.println("[Add]Request params：" + gson.toJson(map2));
		String requestData2 = Des3.encode(gson.toJson(map2));
		System.out.println("[Add]Request data：" + requestData2);
		String reponseData2 = HttpClients.doPost(url, requestData2);
		System.out.println(Des3.decode(reponseData2));
		System.out.println("------------------------ 采购订单确认发货 结束 ---------------------------");
	}
	
	/**
     * 测试
	 * 搜索订单列表
	 * @throws Exception
	 */
	public void testSearchOrderList() throws Exception {
		url += "search";
		
		System.out.println("------------------------ 按搜索订单列表 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", "31005");
		map.put("searchStr", "测试商品001");
		System.out.println("------------------------ 按搜索订单列表 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
