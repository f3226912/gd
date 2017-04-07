package com.gudeng.test.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.Des3;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.api.util.JSONUtils;


public class AccBankCardControllerTest extends TestCase {
//	private String url="http://10.17.1.201:8082";
	private String url="http://localhost:8080/gd-api/";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAdd() throws Exception {
		url+="npsOfferPrice/updateOfferPrice";
	//"id":"","purchaseId":"1225","offerPrice":"","userAcc":"325364","remark":""
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id","23");//
		map.put("remark","");//
	    map.put("offerPrice","12345678.00");
	    map.put("purchaseId","1225");
	    map.put("mobilePhone","15697484568");
	    map.put("userAcc","325364");
	    map.put("userName","小坤");
		Gson gson = new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(reponseData);
		System.out.println("返回结果："+Des3.decode(reponseData));
	}
	
	
	public void testList() throws Exception {

		url+="v4/accBankCard/getBankCards";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 1212);

		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));

	}

/*	public void testUpdateBankCardAuditStatus() throws Exception {
		url += "v4/accBankCard/updateBankCardAuditStatus";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infoId", 221);
		map.put("auditStatus", 1);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		// JSONObject paramesObject =JSONUtils.parseObject(reponseData);
		// String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}*/
/*	public void testBankType() throws Exception {

		url+="v4/accBankCard/getNameOfBank";
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("bankCardNo", "62148375 519");

		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));

	}*/
}
