package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.gudeng.test.util.HttpClientUtil;

public class MarketControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/market/";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

    /**
	 * 拿某个市的一个市场
	 * @throws Exception
	 */
	public void testGetOne() throws Exception {
		url+="getOne";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("area", "深圳市");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 根据城市名获取市场列表
	 * @throws Exception
	 */
	public void testGetList() throws Exception {
		url+="getList";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("area", "深圳市");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 关联会员的运营场所
	 * @throws Exception
	 */
	public void testAddReMarket() throws Exception {
		url+="addReMarket";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("memberId", "19");
		requestParams.put("marketId", "3");
		HttpClientUtil.httpGet(url, requestParams );
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 增加会员的运营场所
	 * @throws Exception
	 */
	public void testAddMarket() throws Exception {
		url+="addMarket";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("memberId", "19");
		requestParams.put("cityName", "深圳市");
		requestParams.put("marketName", "深圳市测试市场12");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
}
