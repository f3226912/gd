package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class BaseCertifControllerTest extends TestCase {
	private String url="http://10.17.1.167:8082/";
//	private String url="http://localhost:8080/gd-api/";

	public void testGetStatusCombination() throws Exception {

		url+="v160929/baseCertif/getStatusCombination";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 266457);
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	
/*	public void testAdd() throws Exception {

		url+="v160929/baseCertif/saveBaseCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 1212);
		map.put("businessId", 1065);
		map.put("account", "zhangyz");
		
		map.put("baseName", "baseName");
		map.put("province", 420000);
		map.put("city", 420100);
		map.put("area", 420104);
		map.put("address", "abcd");
		map.put("markets", "1,2");
		map.put("units", "10吨/月");
		map.put("bzl", "123asd");
		map.put("bzlPhotoUrl", "asdf.jpg");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	*/
	
/*	public void testUpdate() throws Exception {

		url+="v160929/baseCertif/updateBaseCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("id", 4);
		map.put("baseName", "baseNamex");
		map.put("province", 420000);
		map.put("city", 420100);
		map.put("area", 420104);
		map.put("address", "abcdx");
		map.put("markets", "1,2,3");
		map.put("units", "109吨/月");
		map.put("bzl", "123asdx");
		map.put("bzlPhotoUrl", "asdfx.jpg");
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}*/
	
/*	public void testList() throws Exception {

		url+="v160929/baseCertif/queryBaseCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 1212);
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
