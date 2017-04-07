package com.gudeng.test.controller.v170209;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderAppControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v170209/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/*@Test
	public void testMiningOrderAdd() throws Exception {
		url += "detail";
		Gson gson = new Gson();
		
		System.out.println("------------------------现场采销详情查询接口 ---------------------------");

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("orderNo", "572017000285933");
		paraMap.put("memberId", "325406");
		
		System.out.println("[Add]Request params：" + gson.toJson(paraMap));
		String requestData = Des3.encode(gson.toJson(paraMap));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------现场采销详情查询接口 结束 ---------------------------");
	}*/
	
 	@Test
	public void testConfirmOrderAdd() throws Exception {
		url += "conirmOrder";
		Gson gson = new Gson();
		
		System.out.println("------------------------现场采销确认订单接口 ---------------------------");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("orderNo", "572017000285988");
		paraMap.put("memberId", "111");
		
		System.out.println("[Add]Request params：" + gson.toJson(paraMap));
		String requestData = Des3.encode(gson.toJson(paraMap));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------现场采销确认订单接口 结束 ---------------------------");
	} 
	/* @Test
	public void testOrderPayfinsh() throws Exception {
		url += "orderPayfinsh";
		Gson gson = new Gson();
		
		System.out.println("------------------------现场支付完成订单接口 ---------------------------");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("orderNo", "572017000285988");
		
		System.out.println("[Add]Request params：" + gson.toJson(paraMap));
		String requestData = Des3.encode(gson.toJson(paraMap));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------现场支付完成订单接口 结束 ---------------------------");
	} */
}
