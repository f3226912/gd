package com.gudeng.test.controller.v161027;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class PageStatisticV01ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v1/statis/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 浏览金牌供应商统计
	 * @throws Exception
	 */
	@Test
	public void testBrowseGoldMedal() throws Exception {
		url += "browseGoldMedal";
		
		System.out.println("------------------------ 浏览金牌供应商统计 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", "281486");
		map.put("channel", "1"); 
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 浏览金牌供应商统计 结束 ---------------------------");
	}
	
	/**
     * 测试
	 * 搜索精准货源商铺
	 * @throws Exception
	 */
	@Test
	public void testAccurateShop() throws Exception {
		url += "accurateShop";
		
		System.out.println("------------------------ 搜索精准货源商铺 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", "2261");
		map.put("keyWord", "萝卜");
		map.put("pageSize", "10");
		map.put("pageNum", "1"); 
		
		map.put("managementType", "3");
		
//		map.put("shopCityId", "370100");
//		map.put("shopAreaId", "370102");
		
		System.out.println("------------------------ 搜索精准货源商铺 结束 ---------------------------");	
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
