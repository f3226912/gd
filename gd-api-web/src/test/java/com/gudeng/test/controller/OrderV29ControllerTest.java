package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderV29ControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/v29/order";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
    /**
	 * 获取出货列表
	 * @throws Exception
	 */
	@Test
	public void testDeliverList() throws Exception {
		url += "/deliverList";
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("businessId", 4815);
//		map.put("memberId", 110749);
		map.put("pageSize", 10);
		map.put("pageNum", 1);
		
		Gson gson = new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	
	@Test
	public void testDetailOrder() throws Exception {
		url += "/detail";
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", 62307);
		map.put("orderNo", "472016000284863");
		
		Gson gson = new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
}
