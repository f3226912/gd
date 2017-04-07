package com.gudeng.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.Des3;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.exception.RefundException;
import com.gudeng.commerce.gd.order.dto.MsgCons;
import com.gudeng.commerce.gd.order.dto.NstCallbackDTO;
import com.gudeng.commerce.gd.order.util.GSONUtils;

public class CartV1ControllerTest{
	private String url = "http://127.0.0.1:8080/gd-api/";
	//private String url = "http://10.17.1.201:8082";
//	protected void setUp() throws Exception {
//		super.setUp();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
	
	@Test
	public void addProduct() throws Exception {
		url += "/v1/cart/add";

		Map<String, String> requestParams = new HashMap<String, String>();

		requestParams.put("memberId", "596");
		List<MyProduct> entityList = new ArrayList<>();
		MyProduct entity1 = new MyProduct();
		entity1.setProductId(new Long(54256));
		entity1.setQuantity(new Double(20));
		entityList.add(entity1);
		
		MyProduct entity2 = new MyProduct();
		entity2.setProductId(new Long(55992));
		entity2.setQuantity(new Double(20));
		entityList.add(entity2);
		
		requestParams.put("productDetails", JSONUtils.toJSONString(entityList));
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(requestParams));
		String requestData = Des3.encode(gson.toJson(requestParams));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
		
		
		
	}
	
	@Test
	public void deleteProduct() throws Exception {
		url += "/v1/cart/delete";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shoppingItemIds", "15,16");
		map.put("memberId", "596");
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	@Test
	public void modifyProduct() throws Exception {
		url += "/v1/cart/modify";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", "596");
		
		List<ShoppingItem> entityList = new ArrayList<>();
		ShoppingItem item1=new ShoppingItem();
		item1.setShoppingItemId(new Long(17));
		item1.setQuantity(new Double(2));
		item1.setSelected("1");
		entityList.add(item1);
		//ShoppingItem item2=new ShoppingItem();
		//item2.setShoppingItemId(new Long(18));
		//item2.setQuantity(new Double(2));
		//item2.setSelected("1");
		//entityList.add(item2);

		
		map.put("shoppingItemDetails", JSONUtils.toJSONString(entityList));
		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	@Test
	public void queryProduct() throws Exception {
		url += "/v1/cart/query";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", 596);

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	public class MyProduct{
		private Long productId;
		private Double quantity;
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public Double getQuantity() {
			return quantity;
		}
		public void setQuantity(Double quantity) {
			this.quantity = quantity;
		}
		
		
	}
	@Test
	public void test222(){
		String s="123456";
		String s1=s.substring(0, 5);
		System.out.println(s1);
	}
	
	public class ShoppingItem{
		private Long shoppingItemId;
		private Double quantity;
		private String selected;
		
		public Double getQuantity() {
			return quantity;
		}
		public void setQuantity(Double quantity) {
			this.quantity = quantity;
		}
		public Long getShoppingItemId() {
			return shoppingItemId;
		}
		public void setShoppingItemId(Long shoppingItemId) {
			this.shoppingItemId = shoppingItemId;
		}
		public String getSelected() {
			return selected;
		}
		public void setSelected(String selected) {
			this.selected = selected;
		}
		
		
	}
	
	@Test
	public void udpateStatus() throws Exception {
		url += "/callbackOrder/updateStatus";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", 1106145);
		map.put("userId", "281334");
		map.put("type", "4");
//		map.put("driverId", "281205");
//		map.put("companyId", "237777");
//		map.put("distributeMode", "1");
//		map.put("nstOrderNo", "452016121321000015");
		map.put("cancelReason", "阿里骨头");

		Gson gson = new Gson();
		System.out.println("请求参数：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println("reponseData：" + reponseData);
		System.out.println(Des3.decode(reponseData));
	}
	
	@Test
	public void Test2() throws Exception{
		HashMap<String, String> m=new HashMap<String,String>();
		m.put("s1", "2");
		m.put("s2", "3");
		//System.out.println(m.toString());
		//System.out.println(Des3.decode("SbXeAw4/drZubNwHqOuk1+wUXPBXNmuztFXmhLUbDAIdE36dqXNZAmMKlbo0 9EPbSLTkjxMxN4IEbTP5nOgCr+SSuwmM4ETe"));
		Map<String,String> map=new HashMap<String,String>();
		map.put("orderNo", "422016122242000084");
		Gson gson = new Gson();
		String dt=Des3.encode(gson.toJson(map));
		
		
		String apiResult = HttpClients.doPost("http://10.17.1.193:8281/v1/pay/repealPrePaymentSucc", dt);
		// String sNstApiResult = "1";
		if (StringUtils.isNotBlank(apiResult)) {
			// 得到农速通接口结果
			String resultJson = Des3.decode(apiResult);
			NstCallbackDTO callbackDTO = (NstCallbackDTO) GSONUtils.fromJsonToObject(resultJson,
					NstCallbackDTO.class);
			if (callbackDTO.getCode() != 10000) {
				throw new RefundException(MsgCons.C_20007, MsgCons.M_20007);
			}
		}
	}
}
