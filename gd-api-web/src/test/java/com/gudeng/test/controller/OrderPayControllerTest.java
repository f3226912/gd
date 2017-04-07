package com.gudeng.test.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClientUtil;

public class OrderPayControllerTest extends TestCase {
	
	@Test
	public void testPay() throws Exception {
		long a=System.currentTimeMillis();
		String url="http://127.0.0.1:8080/gd-api/v1/pay/confirmPay";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orderNo", "32208110000333");
		map.put("payAmt", "0.01");
		map.put("title", "大白菜");
		map.put("orderTime", "2016-08-18 20:35:45");
		map.put("payerUserId", "10005");
		map.put("payerMobile", "18279771123");
		map.put("payerName", "王里111");
		map.put("payeeUserId", "22");
		map.put("payeeMobile", "15112347543");
		map.put("payeeName", "李四-ios");
		map.put("orderType", "1");
		map.put("id", "9");
		map.put("shipperMemberId",10005);
		map.put("driverMemberId", 2);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		Map<String, String> map2=new HashMap<String, String>();
		map2.put("param", requestData);
		String reponseData= HttpClientUtil.httpGet(url, map2);
		System.out.println(Des3.decode(reponseData)+"最终结果");
		long b=System.currentTimeMillis();
		System.out.println(b-a);
	}
	
	public static void main(String[] args) throws IOException {
		Gson gson=new Gson();
		//gson.fromJson(gson.toJson(resultStr), PayTradeEntity.class);
	}
}
