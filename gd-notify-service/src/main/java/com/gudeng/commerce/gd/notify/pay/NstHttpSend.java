package com.gudeng.commerce.gd.notify.pay;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 发送HTTP消息给农速通
 * 
 * @author Ailen
 *
 */
public class NstHttpSend {

	public void sendNstPayResult() throws ClientProtocolException, IOException {
		HttpResponse response = httpPostWithJSON("","");
		HttpEntity entity = response.getEntity();
		
		String result = EntityUtils.toString(entity);
		
		
	}

	/**
	 * 发送JSON公共方法
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static HttpResponse httpPostWithJSON(String url, String json) throws ClientProtocolException, IOException {
		// 将JSON进行UTF-8编码,以便传输中文
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

		StringEntity se = new StringEntity(encoderJson);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		httpPost.setEntity(se);
		return httpClient.execute(httpPost);
	}
}
