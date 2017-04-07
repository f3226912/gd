package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.service.impl.HttpXmlClient;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class MemberControllerTest extends TestCase {
	
//	private String url = "http://10.17.1.201:8082/";
	private String url = "http://127.0.0.1:8080/gd-api/";
	
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
//		url+="/v1/pay/payPrePaymenSucc";
		url+="/v1/pay/payRestPaymenSucc";
		Map map=new HashMap();
//		memberId 226，orderNo 42201603110001
		map.put("memberId", "226");
		map.put("orderNo", "42201603110001");
		
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
