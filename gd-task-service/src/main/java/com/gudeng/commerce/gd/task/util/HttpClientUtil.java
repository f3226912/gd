/*
 * 文 件 名:  HttpClientUtil.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  panmin
 * 修改时间:  2013-7-25
 * 跟踪单号:  <需求跟踪单号>
 * 修改单号:  <需求修改单号>
 * 修改内容:  <修改内容>
 */
package com.gudeng.commerce.gd.task.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient工具类
 * 
 * @author panmin
 * @version [版本号, 2013-7-25]
 * @since [产品/模块版本]
 */
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String getRequest(String uri) throws Exception {
		HttpMethod method = null;
		try {
			logger.info("请求地址：" + uri);
			HttpClient client = HttpClientFactory.getHttpClient();
			method = new GetMethod(uri); // 使用GET方法
			client.executeMethod(method); // 请求资源
			int status = method.getStatusCode();
			logger.info("响应状态码" + status);
			if (200 == status) {
				String ret = method.getResponseBodyAsString().trim();
				logger.info("响应报文：" + ret);
				return ret;
			} else {
				throw new Exception("请求失败，响应状态码" + status);
			}
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		} finally {
			if (method != null)
				method.releaseConnection(); // 释放连接
		}
	}
	
	public static String getPostRequest(String uri, String bodyContent) throws Exception {
		PostMethod post = null;
		HttpClient client;
		try {
			client = HttpClientFactory.getHttpClient();
			post = new PostMethod(uri); // 使用GET方法
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			RequestEntity entity = new StringRequestEntity(bodyContent, "application/json", "UTF-8"); 
			post.setRequestEntity(entity);
			logger.info("请求报文：" + bodyContent);
			client.executeMethod(post); // 请求资源
			int status = post.getStatusCode();
			logger.info("响应状态码" + status);
			if (200 == status||302==status) {
				String ret = post.getResponseBodyAsString().trim();
				logger.info("响应报文：" + ret);
				return ret;
			}
			else {
				throw new Exception("请求失败，响应状态码" + status);
			}
		}  finally {
			if (post != null){
				post.releaseConnection(); // 释放连接
				client = null;
			}	
		}
	}
	
	public static void main(String[] args) throws Exception {
		String uri = "http://121.15.169.11:18814/sinxin/queryPayInfo";
		String respContent = getPostRequest(uri, "{\"orderNo\": \"182015000000005\"}");
		System.out.println(respContent);
	}
}
