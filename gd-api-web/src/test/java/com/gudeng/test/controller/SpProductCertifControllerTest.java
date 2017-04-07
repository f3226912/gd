package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class SpProductCertifControllerTest extends TestCase {
//	private String url="http://10.17.1.195:8082/";
	private String url="http://localhost:8080/gd-api/";

	public void testUpdate() throws Exception {
		url+="v160929/prodCertif/updateSpProductCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		
/*		map.put("id", "9");
		map.put("units", "你");
		map.put("certifOrg", "国家工商行政管理商标局,中华人民共和国农业部,国家质量监督检验检疫总局");
		map.put("signs", "有他湖椒2123");
		map.put("certifNo", "QA14582552,沉甸甸的,tuugtytt");
		map.put("specialImg", "2016/9/20/7f9a2bebae9c41ae84ea97c4e2b656d2.jpg,2016/9/22/1aef204dfe8e4b71938f691ecf8b2e1c.jpg");
		map.put("companyName", "来给哈哈哈");
		map.put("brand", "给哈哈哈个");*/
		
		map.put("id", "8");
		map.put("units", "5吨/年");
		map.put("certifOrg", "国家工商行政管理商标局,中华人民共和国农业部,国家质量监督检验检疫总局");
		map.put("signs", "有他湖椒2123");
		map.put("certifNo", "QA14582552,沉甸甸的,tuugtytt");
		map.put("specialImg", "2016/9/20/7f9a2bebae9c41ae84ea97c4e2b656d2.jpg,2016/9/22/1aef204dfe8e4b71938f691ecf8b2e1c.jpg");
//		map.put("companyName", "");
		map.put("brand", "");
		
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
		url+="v160929/prodCertif/saveSpProductCertifInfo";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", 4070);
		map.put("memberId", 1212);
		map.put("account", "zhangyz");
		map.put("productName", "红富士");
		map.put("productImg", "123456.jpg");
		map.put("shopsName", "否极泰来");
		map.put("province", 420000);
		map.put("city", 420100);
		map.put("area", 420104);
		map.put("units", "5吨/年");
		map.put("certifOrg", "机构A,机构B,机构C");
		map.put("signs", "南方红富士");
		map.put("certifNo", "ABC,XYZ");
		map.put("specialImg", "sp.jpg");
		map.put("companyName", "什么公司");
		map.put("brand", "大品牌");
		
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

		url+="v160929/prodCertif/querySpProdCertifList";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 266579);
		map.put("stateList", "1");
		
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
