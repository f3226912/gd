package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class NsyUersTypeControllerTest extends TestCase {
	
	private String url="http://localhost:8080/gd-api/v160526/nsyUser/";
    /**
	 * 农商友用户类型列表
	 * @throws Exception
	 */
	@Test
	public void testMemberLogin() throws Exception {
		url += "getNsyUserTypeList";
		Map<String, Object> map=new HashMap<String, Object>();
		Gson gson = new Gson();
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(reponseData);
		
	}
}
