package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderV30ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v30/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
	 * 增加出货信息
	 * @throws Exception
	 */
	public void testGetSellerOrderList2() throws Exception {
		url += "sellList";
		//已收款列表
		System.out.println("------------------------ 已收款列表 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", "117");
		map.put("marketId", "1");
		map.put("orderStatus", "3");
		System.out.println("------------------------ 已收款列表 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
