package com.gudeng.test.controller.v161124;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class OrderActivityV1ControllerTest extends TestCase {
	
//	private String url = "http://10.17.1.201:8082/v1/ordrAct/";
	
	private String url = "http://127.0.0.1:8080/gd-api/v1/ordrAct/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 查询订单活动信息
	 * @throws Exception
	 */
	@Test
	public void testQuery() throws Exception {
		url += "query";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 查询订单活动信息 开始 ---------------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", "177336");
		
//		map.put("orderNo", "162016000125171");
		
		map.put("orderAmount", "7.2");
		map.put("payAmount", "7.2");
		
		map.put("businessId", "5095"); 
		
		map.put("productListStr", "56348_5.6_1_5.6#_#33300_1.6_1_1.6");
		
		//补贴信息
//		map.put("busiType1", "1");
//		map.put("busiType2", "0");
//		map.put("cardType", "2");
//		map.put("payChannel", "NNCCB");
//		map.put("flag", "1");
//		map.put("sellerSubsidyAmt", "7.00");
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 查询订单活动信息 结束 ---------------------------");
	}
	
	/**
     * 测试
	 * 批量查询订单活动信息
	 * @throws Exception
	 */
	@Test
	public void testBatchQuery() throws Exception {
		url += "batchQuery";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 批量查询订单活动信息 开始 ---------------------------");
		List<Map<String, Object>> mapList = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		mapList.add(map1);
		map1.put("buyerId", "266589");
		map1.put("orderAmount", "660");
		map1.put("payAmount", "600");
		map1.put("businessId", "317"); 
		map1.put("productListStr", "803_100_2.6_260#_#866_200_2_400");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		mapList.add(map2);
		map2.put("buyerId", "266589");
		map2.put("orderAmount", "1000");
		map2.put("payAmount", "1000");
		map2.put("businessId", "318"); 
		map2.put("productListStr", "770_100_10_1000");
		
		System.out.println("[Add]Request params：" + gson.toJson(mapList));
		String requestData = Des3.encode(gson.toJson(mapList));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 批量查询订单活动信息 结束 ---------------------------");
	}
	
	/**
	 * 查询是否同城
	 * @throws Exception
	 */
	@Test
	public void testQuerySameCity() throws Exception {
		url += "querySameCity";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 查询查询是否同城 开始 ---------------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiptCityName", "深圳市");
//		map.put("receiptCityId", "420100"); 
		
		map.put("businessIdListStr", "123#_#127#_#4456#_#259#_#263#_#4424");
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 查询查询是否同城 结束 ---------------------------");
	}
}
