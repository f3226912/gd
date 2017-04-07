package com.gudeng.test.controller.v161124;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.junit.Test;

import com.google.gson.Gson;
import com.gudeng.test.util.Des3;
import com.gudeng.test.util.HttpClients;

import junit.framework.TestCase;


public class QuickMakeController extends TestCase {
	
//	private String url = "http://10.17.1.201:8082/v1/ordrAct/";
	
	private String url = "http://127.0.0.1:8080/gd-api/quickMakeSheet/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    /**
     * 测试
	 * 标准库添加产品
	 * @throws Exception
	 */
	@Test
	public void testQuery() throws Exception {
		//url += "getStandardLibraryProductList";
		//url += "getQuickMakeSheetList";
		url += "addProduct";
		
		Gson gson = new Gson();
		
		Map<String, Object> map = new HashMap<String, Object>();
		String a ="[{\"memberId\": 12,\"categoryId\": 324,\"productId\": 3566},{\"memberId\": 12,\"categoryId\": 324,\"productId\": 3566},{\"memberId\": 12,\"categoryId\": 324,\"productId\": 3566}]";
		
		
		
		
		System.out.println("[Add]Request params：" + gson.toJson(a));
		String requestData = Des3.encode(gson.toJson(a));
		System.out.println("[Add]Request data：" + requestData);
		
		String reponseData = HttpClients.doPost(url, requestData);
		System.out.println(Des3.decode(reponseData));
	}
	

}
