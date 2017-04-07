package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class ProductControllerTest extends TestCase {
//	private String url="http://10.17.1.201:8082";
	private String url="http://localhost:8080/gd-api/";

	public void testList1() throws Exception {

		url+="v2Encrypt/product/getShopProductListNew/";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", 317);
		map.put("memberId", 0);
//		map.put("productId", 817);
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));

	}
	
/*	public void testList() throws Exception {

		url+="v2Encrypt/product/productList/";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", 1065);
		map.put("userId", 1212);
		map.put("option", 5);

		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));

	}*/

/*	public void test() throws Exception {

		url+="/v30/UserConcern/productsConcerned";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", 4478);
		map.put("pageNum", 1);
		map.put("pageSize", 100);
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}*/
	
}
