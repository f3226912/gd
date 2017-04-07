package com.gudeng.test.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.util.JSONUtils;

public class TerryTest {

	public static void main(String[] args) {
		Map<String, Object> totalMap = new HashMap<>();
		List<Map<String, Object>> businessMapList = new ArrayList<>();
		totalMap.put("businessDetails", businessMapList);
		
		Map<String, Object> businessMap1 = new HashMap<>();
		businessMapList.add(businessMap1);
		
		businessMap1.put("businessId", "1001");
		businessMap1.put("distributeMode", "0");
		businessMap1.put("message", "测试商铺11");
		
		List<Map<String, String>> productMap1List = new ArrayList<>();
		businessMap1.put("productDetails", productMap1List);
		
		Map<String, String> productMap11 = new HashMap<>();
		productMap1List.add(productMap11);
		
		productMap11.put("productId", "101");
		productMap11.put("purQuantity", "100");
		
		
		Map<String, String> productMap12 = new HashMap<>();
		productMap1List.add(productMap12);
		
		productMap12.put("productId", "102");
		productMap12.put("purQuantity", "200");
		
		totalMap.put("memberId", "2261");
		totalMap.put("orderSource", "2");
		totalMap.put("channel", "1");
		totalMap.put("orderAmount", "2000");
		totalMap.put("payAmount", "2000");
		
		System.out.print(JSONUtils.toJSONString(totalMap));
	}

}
