package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderV31ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v31/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 搜索订单列表
	 * @throws Exception
	 */
	public void testSearchOrderList() throws Exception {
		url += "search";
		
		System.out.println("------------------------ 按实名搜索订单列表 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", "4983");
		map.put("searchStr", "18120722494");
//		map.put("memberId", "166934");
		System.out.println("------------------------ 按实名搜索订单列表 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
     * 测试
	 * 增加商品信息
	 * @throws Exception
	 */
	public void testAddProduct() throws Exception {
		url += "addProduct";
		Gson gson = new Gson();
		
		List<Map<String, Object>> productList = new ArrayList<>();
		Map<String, Object> pMap1 = new HashMap<>();
		pMap1.put("productId", 332);
		pMap1.put("purQuantity", 10);
		pMap1.put("price", 100);
		productList.add(pMap1);
		
		Map<String, Object> pMap2 = new HashMap<>();
		pMap2.put("productId", 515);
		pMap2.put("purQuantity", 100);
		pMap2.put("price", 8.8);
		productList.add(pMap2);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("jProductDetails", gson.toJson(productList));
		map.put("orderNo", "162016000072231");
		map.put("memberId", "232");
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		final String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		for(int i=0; i<1; i++){
			final int count = i;
			final String urlStr = url;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("------------------------ 增加商品信息 开始 ---------------------------");
						System.out.println("------- i: " + count);
						System.out.println("------- urlStr: " + urlStr);
						System.out.println("------- requestData: " + requestData);
						String reponseData = HttpClients.doPost(urlStr, requestData);
						System.out.println(Des3.decode(reponseData));
						System.out.println("------------------------ 增加商品信息 结束 ---------------------------");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();;
		}
	}
	
	/**
     * 测试
	 * 增加客户信息
	 * @throws Exception
	 */
	public void testAddBuyer() throws Exception {
		url += "addBuyer";
		
		System.out.println("------------------------ 增加客户信息 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", "182016000223313");
		map.put("memberId", "281590");
		map.put("mobile", "18120721234");
		map.put("realName", "泰利测试");
		map.put("version", "1");
		System.out.println("------------------------ 增加客户信息 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
     * 测试
	 * 增加客户信息
	 * @throws Exception
	 */
	public void testAddBuyerAndProduct() throws Exception {
		url += "addBuyerAndProduct";
		
		System.out.println("------------------------ 增加客户商品信息 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", "352015000000002");
		map.put("memberId", "4337");
		map.put("mobile", "13527710395");
		map.put("realName", "泰利测试");
		
		List<Map<String, Object>> productList = new ArrayList<>();
		Map<String, Object> pMap1 = new HashMap<>();
		pMap1.put("productId", 38981);
		pMap1.put("purQuantity", 10);
		productList.add(pMap1);
		
		Map<String, Object> pMap2 = new HashMap<>();
		pMap2.put("productId", 38982);
		pMap2.put("purQuantity", 100);
		productList.add(pMap2);
		
		System.out.println("------------------------ 增加客户商品信息 结束 ---------------------------");	
		
		Gson gson = new Gson();
		map.put("jProductDetails", gson.toJson(productList));
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
