package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.customer.dto.MyAddressDTO;
import com.gudeng.commerce.gd.customer.entity.MyAddress;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;

public class MyAddressControllerTest extends TestCase {
	// private String url="http://10.17.1.167:8082/";
	private String url = "http://localhost:8080/gd-api/";

	MyAddressDTO dto = new MyAddressDTO();
	MyAddress entity = new MyAddress();

	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * 获取token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testtoken() throws Exception {
		url += "myAddress/token";
		Map map = new HashMap();
		map.put("memberId", 232);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	
	@Test
	public void testquery() throws Exception {
		url += "myAddress/query";
		Map map = new HashMap();
		map.put("memberId", 232);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testadd() throws Exception {
		url += "myAddress/save";
		Map map = new HashMap();
		map.put("memberId", 232);
		map.put("district1", 140000);
		map.put("district2", 310000);
		map.put("district3", 330000);
		map.put("detail", "城关镇里辉村邮政编码");
		map.put("linkman", "萧天放");
//		map.put("gender", "0");
		map.put("mobile", "13811111111");
		map.put("prefer", 1);
		map.put("token", "8bce5bfe-60a0-4d16-adda-b74982c3ae06");
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testupdate() throws Exception {
		url += "myAddress/save";
		Map map = new HashMap();
		map.put("memberId", 232);
		map.put("id", 3);
		map.put("district1", 130000);
		map.put("district2", 131000);
		map.put("district3", 131024);
		map.put("detail", "苏州君子堂");
		map.put("linkman", "萧别情");
		map.put("mobile", "13811111111");
		map.put("prefer", 0);
		map.put("token", "9ad2aaad-d0ad-4773-a188-bfd3ba0097a0");
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testview() throws Exception {
		url += "myAddress/view";
		Map map = new HashMap();
		map.put("id", 3);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testdelete() throws Exception {
		url += "myAddress/delete";
		Map map = new HashMap();
		map.put("id", 1);
		map.put("memberId", 232);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testprefer() throws Exception {
		url += "myAddress/prefer";
		Map map = new HashMap();
		map.put("id", 1);
		map.put("memberId", 232);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testprefer2() throws Exception {
		url += "myAddress/preferAddr";
		Map map = new HashMap();
		map.put("memberId", 232);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
