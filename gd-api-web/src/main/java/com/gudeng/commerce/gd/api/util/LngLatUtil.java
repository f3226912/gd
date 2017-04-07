package com.gudeng.commerce.gd.api.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class LngLatUtil {

	public static Map<String, Object> getLntLatByBaidu(String addr){
		try {
			String address=URLEncoder.encode(addr, "utf-8");
			CloseableHttpClient client=HttpClients.createDefault();
			HttpGet get=new HttpGet("http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=3nNb4H2CmkHYghwKGG6Bw6u4p2zGrrbS");
			CloseableHttpResponse response = client.execute(get);
			String jsonStr=EntityUtils.toString(response.getEntity(), "utf-8");
			Map<String, Object> map=(Map<String, Object>) GSONUtils.getJsonValue(jsonStr, "result");
			Map<String, Object> map1=(Map<String, Object>) map.get("location");
			Map<String, Object> lngLatMap=new HashMap<>();
			lngLatMap.put("lat", map1.get("lat"));
			lngLatMap.put("lng", map1.get("lng"));
			//System.out.println("--地地址1-->"+map1.get("lat")+",----------"+map1.get("lng"));
			return lngLatMap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
			getLntLatByBaidu("黑龙江省双鸭山市宝山区");
			
			getLntLatByBaidu("黑龙江省双鸭山市宝清县");
	}
}
