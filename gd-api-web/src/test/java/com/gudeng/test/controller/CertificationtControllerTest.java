package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.Des3;
import com.gudeng.paltform.sms.util.HttpClients;

import junit.framework.TestCase;


public class CertificationtControllerTest extends TestCase {
//	private String url="http://10.17.1.201:8082";
	private String url="http://localhost:8080/gd-api/";


	public void testList() throws Exception {

		url+="v160929/certif/querySpProductForCertif";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productName", "");
		map.put("startRow", 1);
		map.put("endRow", 10);
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));

	}
}
