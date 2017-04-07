package com.gudeng.commerce.pay.util;

import java.net.URLEncoder;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gudeng.commerce.gd.pay.util.Des3Request;

public class Des3RequestTest {

	@ Test
	public void testPOS下单加密() throws Exception{
		String t = "{\"businessId\":114,\"marketId\":3,\"orderAmount\":36,\"payAmount\":37,\"channel\":4,\"jProductDetails\":\"[{\\\"productId\\\":336,\\\"productName\\\":\\\"辣椒\\\",\\\"price\\\":12,\\\"purQuantity\\\":3,\\\"needToPayAmount\\\":36}]\"}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

	}
	
	@ Test
	public void test匿名下单加密() throws Exception{
		String t = "{\"businessId\":114,\"marketId\":3,\"orderAmount\":36,\"payAmount\":37,\"channel\":4}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

	}
	
	@ Test
	public void test支付通知加密() throws Exception{
		String t = "{\"orderNo\":742016000071975,\"tradeStatus\":\"PAY\",\"cashierTradeNo\":\"C201606271148\",\"payType\":2}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
	
	@ Test
	public void test订单详情() throws Exception{
		String t = "{\"orderNo\":972016000072028}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
	
	@ Test
	public void test现金收款() throws Exception{
		String t = "{\"orderNo\":102016000072029,\"memberId\":229}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
	
	@ Test
	public void test订单列表() throws Exception{
		String t = "{\"businessId\":114,\"orderStatus\":1}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
	
	@ Test
	public void test修改订单支付金额() throws Exception{
		String t = "{\"orderNo\":812016000072030,\"payAmount\":38}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
	
	@ Test
	public void test锁住订单() throws Exception{
		String t = "{\"orderNo\":812016000072030,\"businessId\":114}";
		String str = Des3Request.encode(t);
		System.out.println(str);
		String u= URLEncoder.encode(str,"utf-8");
		System.out.println(u);
		String p = Des3Request.decode(str);
		System.out.println(p);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
	}
}
