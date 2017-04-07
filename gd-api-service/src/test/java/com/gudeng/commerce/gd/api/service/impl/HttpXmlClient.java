package com.gudeng.commerce.gd.api.service.impl;
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
  


import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpUriRequest;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpXmlClient {
	public static String LOCAL_HOST="http://localhost:8080/";
	public static String DEV_HOST="http://10.17.1.182:8082/";
	public static String TEST_HOST="http://10.17.1.174:8082/";
	
	private static Logger logger = LoggerFactory.getLogger(HttpXmlClient.class);
      
	    public static String post(String url, Map<String, String> params) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient();  
	        String body = null;  
	          
	        logger.info("create httppost:" + url);  
	        HttpPost post = postForm(url, params);  
	          
	        body = invoke(httpclient, post);  
	          
	        httpclient.getConnectionManager().shutdown();  
	          
	        return body;  
	    }  
	      
	    public static String get(String url) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient();  
	        String body = null;  
	          
	        logger.info("create httppost:" + url);  
	        HttpGet get = new HttpGet(url);  
	        body = invoke(httpclient, get);  
	          
	        httpclient.getConnectionManager().shutdown();  
	          
	        return body;  
	    }  
	    
	    
	    private static String invoke(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	          
	        HttpResponse response = sendRequest(httpclient, httpost);  
	        String body = paseResponse(response);  
	          
	        return body;  
	    }  
	  
	    private static String paseResponse(HttpResponse response) {  
	        logger.info("get response from http server..");  
	        HttpEntity entity = response.getEntity();  
	          
	        logger.info("response status: " + response.getStatusLine());  
	        String charset = EntityUtils.getContentCharSet(entity);  
	        logger.info(charset);  
	          
	        String body = null;  
	        try {  
	            body = EntityUtils.toString(entity);  
	            logger.info(body);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return body;  
	    }  
	  
	    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	        logger.info("execute post...");  
	        HttpResponse response = null;  
	          
	        try {  
	            response = httpclient.execute(httpost);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return response;  
	    }  
	  
	    private static HttpPost postForm(String url, Map<String, String> params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
	          
	        try {  
	            logger.info("set utf-8 form entity to httppost");  
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    }  
	    
	    
	    
	    
	    public static void main(String[] args) {
	    	
	    	String URL=LOCAL_HOST+"gd-api/v2/fileUpload/uploadImageByStr";
	    	String imageStr="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";

	    	Map<String, String> params = new HashMap<String, String>();  
//	    	params.put("memberId", "93");
	    	params.put("imageStr", imageStr);
	    	     
	    	String xml = HttpXmlClient.post(URL, params);  
	    	System.out.println(xml);  
		}
	    
	    
	    
}
