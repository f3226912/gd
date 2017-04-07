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


public class OrderAppV33ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v33/order/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 批量插入订单
	 * @throws Exception
	 */
	/*public void testBatchAdd() throws Exception {
		url += "batchAdd";
		Gson gson = new Gson();
		
		System.out.println("------------------------ 批量插入订单 开始 ---------------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", "7228");
		map.put("orderSource", "2");
		map.put("channel", "2");
		
		Map<String, Object> addressMap = new HashMap<String, Object>();
		addressMap.put("detail", "卓越大厦");
		addressMap.put("district1", "广东省");
		addressMap.put("district2", "深圳市");
		addressMap.put("district3", "福田区");
		addressMap.put("linkman", "泰利测试");
		addressMap.put("mobile", "13527710395");
		map.put("jsonAddress", gson.toJson(addressMap));
		
		List<Map<String, Object>> businessMapList = new ArrayList<>();
		Map<String, Object> businessInfoMap = new HashMap<String, Object>();
		businessInfoMap.put("orderAmount", "660");
		businessInfoMap.put("payAmount", "600");
		
		businessInfoMap.put("message", "泰利留言");
		businessInfoMap.put("buyerCommsn", "0.0");
		businessInfoMap.put("distributeMode", "1");
		businessInfoMap.put("sellerCommsn", "18.2");
		businessInfoMap.put("productDetails", "78422_100.00#_#78428_100.00");
		businessInfoMap.put("orderWeight","123.1");
		businessInfoMap.put("businessId", "33961"); 
		businessMapList.add(businessInfoMap);
		
		Map<String, Object> businessInfoMap2 = new HashMap<String, Object>();
		businessInfoMap2.put("orderAmount", "770");
		businessInfoMap2.put("payAmount", "700");
		
		businessInfoMap2.put("message", "泰利留言2");
		businessInfoMap2.put("buyerCommsn", "0.0");
		businessInfoMap2.put("distributeMode", "1");
		businessInfoMap2.put("sellerCommsn", "10");
		businessInfoMap2.put("orderWeight","123.1");
		businessInfoMap2.put("productDetails", "79535_100.00");
		
		businessInfoMap2.put("businessId", "14486"); 
		businessMapList.add(businessInfoMap2);
		map.put("businessDetailsJsonList", gson.toJson(businessMapList));
		
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 批量插入订单 结束 ---------------------------");
	}*/
	
	@Test
	public void testMiningOrderAdd() throws Exception {
		url += "miningOrderAdd";
		Gson gson = new Gson();
		
		System.out.println("------------------------现场采销订单插入 ---------------------------");
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> orderBaseInfo = new HashMap<String, Object>();
		orderBaseInfo.put("activityType", "2");
		orderBaseInfo.put("payType", "2");
		orderBaseInfo.put("orderStatus", "1");
		orderBaseInfo.put("sellMemberId", "2");
		orderBaseInfo.put("shopName", "老王肉铺");
		orderBaseInfo.put("businessId", "10001");
		orderBaseInfo.put("orderAmount", "200.00");
		orderBaseInfo.put("payAmount", "90.00");
		map.put("orderBaseInfo", gson.toJson(orderBaseInfo));
		
		List<Map<String, Object>> rderProductDetailList = new ArrayList<>();
		Map<String, Object> orderProductDetail = new HashMap<String, Object>();
		orderProductDetail.put("productId", "10");
		orderProductDetail.put("productName", "大杂烩");
		
		orderProductDetail.put("purQuantity", "5");
		orderProductDetail.put("price", "10.00");
		orderProductDetail.put("price1", "10.00");
		orderProductDetail.put("purQuantity1", "4");
		orderProductDetail.put("subTotal1", "40.00");
		orderProductDetail.put("price2","10.00");
		orderProductDetail.put("subTotal", "10.00"); 
		orderProductDetail.put("remark", "只要肉，不带血"); 
		orderProductDetail.put("tradingPrice", "100.00"); 
		rderProductDetailList.add(orderProductDetail);
		
		Map<String, Object> orderProductDetail1 = new HashMap<String, Object>();
		orderProductDetail1.put("productId", "10");
		orderProductDetail1.put("productName", "猪肉");
		
		orderProductDetail1.put("purQuantity", "5");
		orderProductDetail1.put("price", "10.00");
		orderProductDetail1.put("price1", "10.00");
		orderProductDetail1.put("purQuantity1", "4");
		orderProductDetail1.put("subTotal1", "40.00");
		orderProductDetail1.put("price2","10.00");
		orderProductDetail1.put("subTotal", "10.00"); 
		orderProductDetail1.put("remark", "只要肉，不带血"); 
		orderProductDetail1.put("tradingPrice", "100.00"); 
		rderProductDetailList.add(orderProductDetail1);
		map.put("orderProductDetail", gson.toJson(rderProductDetailList));
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 批量插入订单 结束 ---------------------------");
	}
}
