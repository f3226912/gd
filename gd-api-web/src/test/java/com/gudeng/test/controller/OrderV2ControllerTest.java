package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClientUtil;
import com.gudeng.test.util.HttpClients;

public class OrderV2ControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/v21/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 买家下单
	 * @throws Exception
	 */
	@Test
	public void testAddOrder() throws Exception {
		url += "add";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("marketId", "1");
		requestParams.put("shopName", "熊琨测试");
		requestParams.put("businessId", "9");
		requestParams.put("memberId", "19");
		requestParams.put("channel", "1");
		requestParams.put("orderAmount", "1400.0");
		requestParams.put("discountAmount", "0");
		requestParams.put("payAmount", "1000");
		
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		OrderProductDetailEntity entity1 = new OrderProductDetailEntity();
		entity1.setProductId(65);
		entity1.setProductName("青鱼");
		entity1.setPurQuantity(new Double(1.0));
		entity1.setPrice(new Double(200.0));
		entity1.setTradingPrice(new Double(200.0));
		entity1.setNeedToPayAmount(new Double(200.0));
		entityList.add(entity1);
		
		OrderProductDetailEntity entity2 = new OrderProductDetailEntity();
		entity2.setProductId(66);
		entity2.setProductName("生蚝");
		entity2.setPurQuantity(new Double(10.0));
		entity2.setPrice(new Double(120.0));
		entity2.setTradingPrice(new Double(1200.0));
		entity2.setNeedToPayAmount(new Double(1200.0));
		entityList.add(entity2);
		
		requestParams.put("jProductDetails", JSONUtils.toJSONString(entityList));
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 卖家制单
	 * @throws Exception
	 */
	@Test
	public void testSellerAddOrder() throws Exception {
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
	 * 取消订单
	 * @throws Exception
	 */
	@Test
	public void testCancelOrder() throws Exception {
		url += "cancel";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "952016000012263");
		requestParams.put("memberId", "227");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 确认收款
	 * @throws Exception
	 */
	@Test
	public void testConfirmReceive() throws Exception {
		url+="confirmReceive";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orderNo", "1449141678390");
		requestParams.put("memberId", "19");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 订单补贴列表
	 * @throws Exception
	 */
	@Test
	public void testSubList() throws Exception {
		url+="subList";
		//待补贴
		Map<String, String> requestParams2 = new HashMap<String, String>();
		requestParams2.put("memberId", "19");
		requestParams2.put("marketId", "1");
		requestParams2.put("subStatus", "0");
		
		Map<String, String> requestParams = new HashMap<String, String>();
		Gson gson=new Gson();
		requestParams.put("param", Des3.encode(gson.toJson(requestParams2)));
		
		HttpClientUtil.httpGet(url, requestParams);
		
		//已补贴
//		Map<String, String> requestParams3 = new HashMap<String, String>();
//		requestParams3.put("memberId", "19");
//		requestParams3.put("marketId", "1");
//		requestParams3.put("subStatus", "1");
//		HttpClientUtil.httpGet(url, requestParams3);
		
//		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
		
		System.out.println("requestData：" + Des3.decode( Des3.encode(gson.toJson(requestParams2))));
		String reponseData=HttpClients.doPost(url, Des3.encode(gson.toJson(requestParams2)));
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
	}
	
	/**
	 * 买家订单列表
	 * @throws Exception
	 */
	@Test
	public void testBuyerOrderList() throws Exception {
		url += "buylist";
		//待付款
		System.out.println("======================== 待付款列表 开始 ===========================");
		Map<String, String> requestParams2 = new HashMap<String, String>();
		requestParams2.put("memberId", "19");
		requestParams2.put("marketId", "1");
		requestParams2.put("orderStatus", "1");
		HttpClientUtil.httpGet(url, requestParams2);
		System.out.println("======================== 待待付款列表 结束 ===========================");
		//已付款
		System.out.println("------------------------ 已付款列表 开始 ---------------------------");
		Map<String, String> requestParams3 = new HashMap<String, String>();
		requestParams3.put("memberId", "19");
		requestParams3.put("marketId", "1");
		requestParams3.put("orderStatus", "3");
		HttpClientUtil.httpGet(url, requestParams3);
		System.out.println("------------------------ 已付款列表 结束 ---------------------------");
		//已关闭
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 开始 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Map<String, String> requestParams4 = new HashMap<String, String>();
		requestParams4.put("memberId", "19");
		requestParams4.put("marketId", "1");
		requestParams4.put("orderStatus", "8");
		requestParams4.put("pageNum", "2");
		requestParams4.put("pageSize", "10");
		HttpClientUtil.httpGet(url, requestParams4);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
	
	/**
	 * 卖家订单列表
	 * @throws Exception
	 */
	@Test
	public void testSellerOrderList() throws Exception {
		url += "sellList";
		//待收款列表
		System.out.println("======================== 待收款列表 开始 ===========================");
		Map<String, String> requestParams1 = new HashMap<String, String>();
		requestParams1.put("businessId", "2");
		requestParams1.put("marketId", "1");
		requestParams1.put("orderStatus", "1");
		HttpClientUtil.httpGet(url, requestParams1);
		
		System.out.println("======================== 待收款列表 结束 ===========================");
		//已收款列表
		System.out.println("------------------------ 已收款列表 开始 ---------------------------");
		Map<String, String> requestParams2 = new HashMap<String, String>();
		requestParams2.put("businessId", "2");
		requestParams2.put("marketId", "1");
		requestParams2.put("orderStatus", "3");
		HttpClientUtil.httpGet(url, requestParams2);
		System.out.println("------------------------ 已收款列表 结束 ---------------------------");
		//已关闭列表
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 开始 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Map<String, String> requestParams3 = new HashMap<String, String>();
		requestParams3.put("businessId", "2");
		requestParams3.put("marketId", "1");
		requestParams3.put("orderStatus", "8");
		HttpClientUtil.httpGet(url, requestParams3);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ 已关闭列表 结束 ~~~~~~~~~~~~~~~~~~~~~~~~~~~");		
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
		requestParams.put("orderNo", "702016000000373");
		requestParams.put("memberId", "19");
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
}
