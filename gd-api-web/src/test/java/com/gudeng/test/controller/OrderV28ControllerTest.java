package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.test.util.Des3;
import junit.framework.TestCase;

public class OrderV28ControllerTest extends TestCase {
	/*private String url = "http://10.17.1.207:8082/v28/order";*/
	private String url = "http://localhost:8080/gd-api/v28/order";
//	private String url = "http://10.17.1.201:8082/v28/order";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testAddOrder() throws Exception {
		url += "/add";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("memberId", "19");
		requestParams.put("businessId", "30924");
		requestParams.put("marketId", "1");
		requestParams.put("orderAmount", "140.0");
		requestParams.put("payAmount", "100");
		requestParams.put("discountAmount", "0");
		requestParams.put("shopName", "泰利商铺4");
		requestParams.put("orderSource", "1");

		requestParams.put("channel", "1");
		requestParams.put("distributeMode", "0");

		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		OrderProductDetailEntity entity1 = new OrderProductDetailEntity();
		entity1.setProductId(65);
		entity1.setProductName("青鱼");
		entity1.setPurQuantity(new Double(1.0));
		entity1.setPrice(new Double(20.0));
		entity1.setTradingPrice(new Double(20.0));
		entity1.setNeedToPayAmount(new Double(20.0));
		entityList.add(entity1);

		OrderProductDetailEntity entity2 = new OrderProductDetailEntity();
		entity2.setProductId(66);
		entity2.setProductName("生蚝");
		entity2.setPurQuantity(new Double(10.0));
		entity2.setPrice(new Double(12.0));
		entity2.setTradingPrice(new Double(120.0));
		entity2.setNeedToPayAmount(new Double(120.0));
		entityList.add(entity2);

		requestParams.put("jProductDetails", JSONUtils.toJSONString(entityList));

		Map<String, String> addressMap = new HashMap<>();
		addressMap.put("detail", "村头老庙");
		addressMap.put("district1", "天南省");
		addressMap.put("district2", "坠星潭");
		addressMap.put("district3", "黑龙殿");
		addressMap.put("linkman", "向九尘");
		addressMap.put("mobile", "13811111111");

		requestParams.put("jsonAddress", JSONUtils.toJSONString(addressMap));

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(requestParams));
		String requestData = Des3.encode(gson.toJson(requestParams));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		// JSONObject paramesObject =JSONUtils.parseObject(reponseData);
		// String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}

	/**
	 * 获取订单详情
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDetail() throws Exception {
		url += "/detail";

		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("orderNo", "342016000221396");
		map.put("orderNo", "962016000182581");

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		// JSONObject paramesObject =JSONUtils.parseObject(reponseData);
		// String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
	 * 取消订单
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelOrder() throws Exception {
		url += "/cancel";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", "322016000216512");
		map.put("memberId", 266473);
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
}
