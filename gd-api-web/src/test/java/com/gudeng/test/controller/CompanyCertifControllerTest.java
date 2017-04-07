package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class CompanyCertifControllerTest extends TestCase {
//	private String url="http://10.17.1.195:8082/";
	private String url="http://localhost:8080/gd-api/";

/*	public void testAdd() throws Exception {
		url+="v160929/companyCertif/saveCompanyCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 1212);
		map.put("account", "zhangyz");
		
		map.put("bzl", "asdfghjkl");
		map.put("companyName", "companyName");
		map.put("realName", "老江");
		map.put("bzlPhotoUrl", "asdfghjkl.jpg");
		map.put("idCard", "9999999988");
		map.put("cardPhotoUrl", "qwertyuiop.jpg");
		map.put("appType", "4");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}*/
	
	
	public void testUpdate() throws Exception {
		
		url+="v160929/companyCertif/updateCompanyCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", 3);
		map.put("companyName", "张test");
		map.put("bzl", "14568086547455555");
		map.put("realName", "张");
		map.put("idCard", "1244885668268855");
		map.put("cardPhotoUrl", "2016/9/18/16b94c1ca6534d53964ce76de2adb363.jpg,2016/9/18/05432a435e05489b862affb2293930be.jpg");
		map.put("appType", "4");
		map.put("bzlPhotoUrl", "2016/9/18/2247e07ae17b4906ae93107b276ab2b7.jpg");
		
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	
	
/*	public void testList() throws Exception {

		url+="v160929/companyCertif/queryCompanyCertifInfo";
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
