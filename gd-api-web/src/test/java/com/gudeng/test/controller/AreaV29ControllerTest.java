package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class AreaV29ControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/v29/area";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
    /**
	 * 获取出货列表
	 * @throws Exception
	 */
	public void testGetAllProvince() throws Exception {
		url += "/getAllProvince";
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("filter", 1);
		
		Gson gson = new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
