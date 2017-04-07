package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.util.JSONUtils;

public class TerryTest {

	public static void main(String[] args) {
		Map<String, Object> totalMap = new HashMap<>();
		List<Map<String, Object>> businessMapList = new ArrayList<>();
		Map<String, Object> businessMap1 = new HashMap<>();
		businessMap1.put("businessId", "1001");
		businessMap1.put("distributeMode", "0");
		businessMap1.put("message", "测试商铺11");
		
		List<Map<String, String>> productMap1List = new ArrayList<>();
		
		Map<String, String> productMap11 = new HashMap<>();
		productMap11.put("productId", "101");
		productMap11.put("purQuantity", "100");
		productMap1List.add(productMap11);
		
		Map<String, String> productMap12 = new HashMap<>();
		productMap12.put("productId", "102");
		productMap12.put("purQuantity", "200");
		productMap1List.add(productMap12);
		businessMap1.put("productDetails", productMap1List);
		businessMapList.add(businessMap1);
		
		Map<String, Object> businessMap2 = new HashMap<>();
		businessMap2.put("businessId", "1002");
		businessMap2.put("distributeMode", "1");
		businessMap2.put("message", "测试商铺22");
		
		List<Map<String, String>> productMap2List = new ArrayList<>();
		
		Map<String, String> productMap21 = new HashMap<>();
		productMap21.put("productId", "201");
		productMap21.put("purQuantity", "100");
		productMap2List.add(productMap21);
		
		Map<String, String> productMap22 = new HashMap<>();
		productMap22.put("productId", "202");
		productMap22.put("purQuantity", "200");
		productMap2List.add(productMap22);
		businessMap2.put("productDetails", productMap2List);
		businessMapList.add(businessMap2);
		
		totalMap.put("businessDetails", businessMapList);
		
		totalMap.put("memberId", "2261");
		totalMap.put("orderSource", "2");
		totalMap.put("channel", "1");
		totalMap.put("orderAmount", "2000");
		totalMap.put("payAmount", "2000");
		
		System.out.print(JSONUtils.toJSONString(totalMap));
	}

}
