package com.gudeng.test.demo;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class MemberControllerTest extends TestCase {
//	private String url="http://10.17.1.165:8082/";
	private String url="http://localhost:8080/gd-api/";
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
		url+="/memberapi/updateMobile";
//		url+="/memberapi/updatePassword";
//		url+="/memberapi/updateStatus";
//		url+="/memberapi/updateRealName";

		Map map=new HashMap();
		map.put("memberId", 224);
		map.put("mobile", "13430125679");
		map.put("password", "123456");
		map.put("status", "0");
		map.put("realName", "黄");
//		map.put("memberapi_type", "admin");
		

//			marketId=3&pageSize=10&pageNum=1&roleType=4&managementType=

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
