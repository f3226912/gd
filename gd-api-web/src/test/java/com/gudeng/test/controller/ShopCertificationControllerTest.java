package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class ShopCertificationControllerTest extends TestCase {
	
//	private String url = "http://127.0.0.1:8080/gd-api/";
//	private String url = "http://10.17.1.174:8082/";
	private String url = "http://10.17.1.199:8082/";
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 精准货源
	 */
	public void testQueryProudct() {  
    	url += "v161121/agricultural/proudct";
    	Map<String, String> params = new HashMap<String, String>();
    	//{"memberId":"281577","pageSize":"10","type":"2","pageNum":"1"}
     	params.put("memberId", "281577");
     	params.put("pageNum", "1");
     	params.put("pageSize", "10");
     	params.put("queryMode", "1");
     	params.put("type", "2");
     	Gson gson=new Gson();
     	System.out.println("请求参数："+gson.toJson(params));
     	String requestData= Des3.encode(gson.toJson(params));
     	String reponseData = null;
		try {
			reponseData = HttpClients.doPost(url, requestData);
			System.out.println("返回参数："+Des3.decode(reponseData));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 实体商铺认证申请-查询
	 */
	public void testQueryShopCertifInfo() {  
    	url += "v160929/shopCertif/queryShopCertifInfo";
    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("memberId", "325");
     	Gson gson=new Gson();
     	System.out.println("请求参数："+gson.toJson(params));
     	String requestData= Des3.encode(gson.toJson(params));
     	String reponseData = null;
		try {
			reponseData = HttpClients.doPost(url, requestData);
			System.out.println("返回参数："+Des3.decode(reponseData));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 实体商铺认证申请
	 */
	public void testSaveShopCertifInfo() {  
    	url += "v160929/shopCertif/saveShopCertifInfo";
    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("memberId", "325"); 
     	params.put("businessId", "205"); 
     	params.put("realShopName", "皮胜利姜商行"); 
     	params.put("operatorName", "皮胜利"); 
     	params.put("marketId", "2"); 
     	params.put("address", "仇楼镇 "); 
     	params.put("contractImg", "http://www.gdzhnp.com/images/login/loginLogo.png");
     	Gson gson=new Gson();
     	System.out.println("请求参数："+gson.toJson(params));
     	String requestData= Des3.encode(gson.toJson(params));
     	String reponseData = null;
		try {
			reponseData = HttpClients.doPost(url, requestData);
			System.out.println("返回参数："+Des3.decode(reponseData));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 实体商铺认证编辑
	 */
	public void testEditShopCertifInfo() {  
    	url += "v160929/shopCertif/editShopCertifInfo";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", "30"); 
    	params.put("memberId", "325"); 
     	params.put("businessId", "205"); 
     	params.put("realShopName", "皮胜利姜商行"); 
     	params.put("operatorName", "皮胜利1"); 
     	params.put("marketId", "2"); 
     	params.put("address", "仇楼镇 "); 
     	params.put("contractImg", "http://www.gdzhnp.com/images/login/loginLogo.png");
     	Gson gson=new Gson();
     	System.out.println("请求参数："+gson.toJson(params));
     	String requestData= Des3.encode(gson.toJson(params));
     	String reponseData = null;
		try {
			reponseData = HttpClients.doPost(url, requestData);
			System.out.println("返回参数："+Des3.decode(reponseData));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void testGetStatusCombination() {  
    	url += "v160929/baseCertif/getStatusCombination";
    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("memberId", "281537"); 
     	Gson gson=new Gson();
     	System.out.println("请求参数："+gson.toJson(params));
     	String requestData= Des3.encode(gson.toJson(params));
     	String reponseData = null;
		try {
			reponseData = HttpClients.doPost(url, requestData);
			System.out.println("返回参数："+Des3.decode(reponseData));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
