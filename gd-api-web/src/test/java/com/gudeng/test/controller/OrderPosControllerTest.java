package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.test.util.HttpClientUtil;

public class OrderPosControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/pos/order/";
//	private String url="http://10.17.1.182:8082/v2/order/";
//	private String url="http://10.17.1.174:8082/v2/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 卖家正常制单
	 * @throws Exception
	 */
	@Test
	public void testAddOrder() throws Exception {
		url += "add";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("marketId", "1");
		requestParams.put("shopName", "泰利商铺");
		requestParams.put("businessId", "2");
//		requestParams.put("memberId", "19");
		requestParams.put("channel", "1");
		requestParams.put("orderAmount", "140.0");
		requestParams.put("discountAmount", "0");
		requestParams.put("payAmount", "100");
		
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
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 卖家匿名制单
	 * @throws Exception
	 */
	@Test
	public void testAddAnonymousOrder() throws Exception {
		url += "addAno";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("marketId", "1");
		requestParams.put("shopName", "泰利商铺");
		requestParams.put("businessId", "2");
//		requestParams.put("memberId", "19");
		requestParams.put("channel", "1");
		requestParams.put("orderAmount", "100.0");
		requestParams.put("discountAmount", "0");
		requestParams.put("payAmount", "100");
		
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 取消订单
	 * @throws Exception
	 */
	@Test
	public void testCancelOrder() throws Exception {
		url+="cancel";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "172016000010682");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 确认收款
	 * @throws Exception
	 */
	@Test
	public void testUpdateOrder() throws Exception {
		url += "update";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "822016000216536");
		requestParams.put("payAmount", "800");
		requestParams.put("version", "1");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	@Test
	public void testLockOrder() throws Exception {
		url += "lock";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "172016000010682");
		requestParams.put("businessId", "4855");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	@Test
	public void testUnlockOrder() throws Exception {
		url += "unlock";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "172016000010682");
		requestParams.put("businessId", "4855");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 卖家订单列表
	 * @throws Exception
	 */
	@Test
	public void testOrderList() throws Exception {
		url += "list";
		//待收款列表
		System.out.println("======================== 待收款列表 开始 ===========================");
		Map<String, String> requestParams1 = new HashMap<String, String>();
		requestParams1.put("businessId", "2");
		requestParams1.put("marketId", "1");
		requestParams1.put("orderStatus", "1");
		HttpClientUtil.httpGet(url, requestParams1);
		
		System.out.println("======================== 待收款列表 结束 ===========================");
		//已收款列表
//		System.out.println("------------------------ 已收款列表 开始 ---------------------------");
//		Map<String, String> requestParams2 = new HashMap<String, String>();
//		requestParams2.put("businessId", "2");
//		requestParams2.put("marketId", "1");
//		requestParams2.put("orderStatus", "3");
//		HttpClientUtil.httpGet(url, requestParams2);
//		System.out.println("------------------------ 已收款列表 结束 ---------------------------");
//		//已关闭列表
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 开始 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		Map<String, String> requestParams3 = new HashMap<String, String>();
//		requestParams3.put("businessId", "2");
//		requestParams3.put("marketId", "1");
//		requestParams3.put("orderStatus", "8");
//		HttpClientUtil.httpGet(url, requestParams3);
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");		
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 订单详情
	 * @throws Exception
	 */
	@Test
	public void testDetailOrder() throws Exception {
		url += "detail";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "722016000072071");
//		requestParams.put("memberId", "19");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 订单确认收款
	 * @throws Exception
	 */
	@Test
	public void testCashReceive() throws Exception {
		url += "cashReceive";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "702016000000373");
		requestParams.put("memberId", "19");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
}
