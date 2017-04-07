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

public class DeliveryAddressControllerTest extends TestCase {
	// private String url="http://10.17.1.167:8082/";
	private String url = "http://localhost:8080/gd-api/";

	MyAddressDTO dto = new MyAddressDTO();
	MyAddress entity = new MyAddress();

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testtoken() throws Exception {
		url += "deliveryAddress/token";
		Map map = new HashMap();
		map.put("orderNo", 761456891);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testquery() throws Exception {
		url += "deliveryAddress/query";
		Map map = new HashMap();
		map.put("orderNo", 76145689);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	@Test
	public void testadd() throws Exception {
		url += "deliveryAddress/save";
		Map map = new HashMap();
		map.put("orderNo", 761456891);
		map.put("memberId", 232);
		map.put("district1", "天南省");
		map.put("district2", "坠星潭");
		map.put("district3", "黑龙殿");
		map.put("detail", "村头老庙");
		map.put("linkman", "向九尘");
		map.put("mobile", "13811111111");
		map.put("token", "4dd94ef7-d0ae-4ec6-8479-3a617644b825");
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
