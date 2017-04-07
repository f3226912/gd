package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderV32ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v32/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 购买金牌会员
	 * @throws Exception
	 */
	public void testBuyGoldMedal() throws Exception {
		url += "buyGoldMedal";
		
		System.out.println("------------------------ 购买金牌会员 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", "226");
		map.put("channel", "1");
		System.out.println("------------------------ 购买金牌会员 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
