package com.gudeng.test.controller.v161124;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class MarketV3ControllerTest extends TestCase {
	
//	private String url = "http://10.17.1.201:8082/v1/ordrAct/";
	
	private String url = "http://127.0.0.1:8080/gd-api/v3/market/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 查询最近市场信息
	 * @throws Exception
	 */
	@Test
	public void testGetNearbyMarket() throws Exception {
		url += "getNearbyMarket";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 查询最近市场信息 开始 ---------------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityName", "昆明市");
		
		map.put("cityLng", "10.00");
		map.put("cityLat", "0.0");
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 查询最近市场信息 结束 ---------------------------");
	}
}
