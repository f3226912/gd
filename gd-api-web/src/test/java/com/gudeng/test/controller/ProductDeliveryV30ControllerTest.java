package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;


public class ProductDeliveryV30ControllerTest extends TestCase {
	
	private String url = "http://127.0.0.1:8080/gd-api/v30/deliver/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
	 * 增加货源信息
	 * @throws Exception
	 */
	public void testAdd() throws Exception {
		url += "add";
		//已收款列表
		System.out.println("------------------------ 增加货源信息 开始 ---------------------------");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", "300");
		//发货地
		map.put("sProvinceId", "420000");
		map.put("sCityId", "420100");
		map.put("sAreaId", "420105");
		map.put("sDetail", "湖北省/武汉市/汉阳区");
		map.put("sDetailedAddress", "发货地测试详细地址1");
		map.put("sLng", "10");
		map.put("sLat", "10");
		//收货地
		map.put("eProvinceId", "420000");
		map.put("eCityId", "420100");
		map.put("eAreaId", "420106");
		map.put("eDetail", "湖北省/武汉市/武昌区");
		map.put("eDetailedAddress", "收货地测试详细地址1");
		map.put("eLng", "55");
		map.put("eLat", "55");
		//装车时间
		map.put("sendDate", "2016-08-17 10:00");
		//货物类型: 101 蔬菜水果,	  102 干调粮油,    103 活鲜水产,  104 食品饮料,  105 冷冻商品,
		//106 化肥种子,   107 农资农具,    108 日用品,   109 建材设备,   110 其他商品
		map.put("goodsType", "110");
		map.put("totalWeight", "10");
		map.put("totalSize", "10");
		map.put("goodsName", "测试商品");
		//车辆类型: -1:不限		 1:小型面包 		2:金杯 		3:小型平板 		4:中型平板 
		//5:小型厢货 		6:大型厢货		 7:敞车		 8:厢式货车 		9 高栏车
		//10 平板车 		,11 集装箱,			12 保温车,	13 冷藏车,	14 活鲜水车
		map.put("carType", "1");
		//发货方式: 1:不限;		2:整车;		3:零担
		map.put("sendGoodsType", "2");
		map.put("carLength", "-1");
		map.put("freight", "0");  //费用: 0面议
		
		map.put("shipperName", "泰利");
		map.put("shipperMobile", "13527710395");
		map.put("remark", "测试留言");
		//客户端来源: 2 农速通 货主 3 农速通 物流公司 4 农商友 
		//5 农商友 - 农批商 6 农商友-供应商
		map.put("clients", "4");
		
		//订单货源
//		map.put("selectedList", "772016000006452_33231-33232,722016000000670_32877-32888");
		//商品货源
		map.put("selectedList", "#117_532-541-546");
		
		Gson gson = new Gson();
		System.out.println("[Add]Request params：" + gson.toJson(map));
		String requestData = Des3.encode(gson.toJson(map));
		System.out.println("[Add]Request data：" + requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
		System.out.println("------------------------ 增加货源信息 结束 ---------------------------");
	}
}
