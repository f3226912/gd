package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class LoginV28ControllerTest extends TestCase {
	
	private String url="http://localhost:8080/gd-api/cdgys/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
	 * 登录
	 * @throws Exception
	 */
	public void testMemberLogin() throws Exception {
		url += "v0630/login";
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("account", "15379782042");
		map.put("password", "7A14A31C5814D7C418FA4A0FD26374D2");
		
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
	
	public void testGetVerifyCode() throws Exception {
		url += "getVerifyCode";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mobile", "13527710395");
		map.put("type", "0");
		map.put("encryptStr", "C6D8B2138DC045B28938021842AACC9F");
		
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
	
	public void testRegister() throws Exception {
		url += "register";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("account", "13527710395");
		map.put("verifyCode", "064829");
		
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		
	}
}
