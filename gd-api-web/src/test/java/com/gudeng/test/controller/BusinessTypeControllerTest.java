package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class BusinessTypeControllerTest extends TestCase {
	
		private String url="http://localhost:8080/gd-api/v160526/business/";
//	    private String url="http://10.17.1.167:8082/v160526/business/";

	/**
	 * 农商友用户类型列表
	 * @throws Exception
	 */
	@Test
	public void testGetShopByBusinessId() throws Exception {
		url += "getShopByBusinessId";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("businessId", "34704");
		map.put("memberId", "278630");
		Gson gson = new Gson();
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
	
/*	@Test
	public void testGetShopByUserId() throws Exception {
		url += "getShopByUserId";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", "41585");
		Gson gson = new Gson();
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(reponseData);
		
	}*/
	
}
