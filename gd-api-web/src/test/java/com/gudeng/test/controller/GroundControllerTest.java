package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class GroundControllerTest extends TestCase {
//	private String url="http://10.17.1.167:8082/";
	private String url="http://localhost:8080/gd-api/";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
    /**
	 * 登录
	 * @throws Exception
	 */
	public void testMemberLogin() throws Exception {
//		url+="/ditui/getAbleGiftNstOrder";
//		url+="/ditui/getAbleGiftOrder";
//		url+="/ditui/getGiftList";
		url+="/ditui/getAbleGiftRecord";
//		url+="/ditui/scan";
//		url+="/ditui/grantGiftNst";
//		url+="/ditui/centralized";
//		url+="/ditui/login";
		
//
//		Integer userId;//地堆用户id
//		String type;//1现场发放，0集中发放
//		String mobile;//买家的手机号吗
//		String carNo;//车牌号
//		String orderDetails;//订单明细
//		String giftDeatils;//礼品明细
		

		
		Map map=new HashMap();
		map.put("customerId", "167538");
//		map.put("sourceType", "2");//获取礼品列表时，需要传入
		map.put("grdUserId", "46");
		map.put("pageNum", 0);
		map.put("pageSize", 20);
//		map.put("type", 0);// 集中领取
		map.put("type", 1);//用户id还是手机号
		map.put("mobile", "15195856395");
		map.put("password", "abc123");
		map.put("recordIds", "330");
//		map.put("carNo", "粤A12312");
//		map.put("orderDetails", "[{'businessId':0,'mobile':'18312108440','orderNo':'首次邀请注册','orderPrice':0,'orderStatus':'','orderTimeStr':'2016-06-16 14:21:27','realName':'222','shopName':'','type':'3'},{'businessId':3234,'mobile':'18312108440','orderNo':'342016000071983','orderPrice':17.2,'orderStatus':'3','orderTimeStr':'2016-06-16 14:26:50','realName':'222','shopName':'涛子渔行','type':'2'}]");
//		map.put("giftDeatils", "[{'giftId':6,'giftName':'id为6的名称','countNo':10},{'giftId':9,'giftName':'id为9的名称','countNo':1}]");
//		map.put("orderDetails", "[{'businessId':0,'mobile':'13800138000','orderNo':'首次邀请注册','orderPrice':0,'orderTime':'2016-06-15 10:23:30','realName':'星安果','shopName':'','type':'3'},{'businessId':3862,'mobile':'13800138000','orderNo':'612016000012559','orderPrice':112,'orderTime':'2016-06-15 15:56:30','realName':'星安果','shopName':'景乐干鲜调料','type':'2'}]");
		map.put("nstOrderDetails", "[{	'code': 0,	'count': 0,	'description': '20160826首次邀请注册',	'id': 0,	'type': '4'},{'code':2016053,'countNo':96,'description':'201605下旬发布货源','id':96,'type':'5'},{'code':2016053,'countNo':105,'description':'201605下旬信息订单','id':105,'type':'6'},{'code':2016082,'countNo':122,'description':'201608中心货运订单','id':122,'type':'7'}]");
		map.put("giftDeatils", "[{'giftId':6,'giftName':'斯蒂芬森的','countNo':10},{'giftId':7,'giftName':'萨房贷告诉对方','countNo':1}]");

		
		
		
		
//		map.put("recordIds", "3");

		
		
//		map.put("marketId", 416);
//		map.put("mobile", "13800138000");
//		map.put("pageNum", "1");
//		map.put("pageSize", "10");
		Gson gson=new Gson();
		System.out.println("请求参数："+gson.toJson(map));
		String requestData= Des3.encode(gson.toJson(map));
		System.out.println("requestData：" + requestData);
		String reponseData=HttpClients.doPost(url, requestData);
//		JSONObject paramesObject =JSONUtils.parseObject(reponseData);
//		String data=paramesObject.getString("object");
		System.out.println(Des3.decode(reponseData));
		
	}
}
