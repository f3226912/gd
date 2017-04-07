package com.gudeng.commerce.gd.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @Version 1.0
 */
public class HttpClients {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(HttpClients.class);
	/** UTF-8 */
	private static final String UTF_8 = "UTF-8";
//	/** 日志记录tag */
//	private static final String TAG = "HttpClients";

	/** 用户host */
	private static String proxyHost = "";
	/** 用户端口 */
	private static int proxyPort = 80;
	/** 是否使用用户端口 */
	private static boolean useProxy = false;

	/** 连接超时 */
	private static final int TIMEOUT_CONNECTION = 60000;
	/** 读取超时 */
	private static final int TIMEOUT_SOCKET = 60000;
	/** 重试3次 */
	private static final int RETRY_TIME = 3;

	/**
	 * 
	 * 发起网络请求
	 * 
	 * @param url
	 *            URL
	 * @param requestData
	 *            requestData
	 * @return INPUTSTREAM
	 * @throws AppException
	 */
	public static String doPost(String url, String requestData) throws Exception {
		String responseBody = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		int statusCode = -1;
		int time = 0;
		try {
			logger.info(url + "?param=" + URLEncoder.encode(requestData, UTF_8));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		do {
			try {
				httpPost = new HttpPost(url);
				httpClient = getHttpClient();
				// 设置HTTP POST请求参数必须用NameValuePair对象
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("param", requestData));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				// 设置HTTP POST请求参数
				httpPost.setEntity(entity);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					System.out.println("HTTP" + "  " + "HttpMethod failed: " + httpResponse.getStatusLine());
				}
				InputStream is = httpResponse.getEntity().getContent();
				responseBody = getStreamAsString(is, HTTP.UTF_8);
				break;
			} catch (UnsupportedEncodingException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();

			} catch (ClientProtocolException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} catch (Exception e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return responseBody;
	}
	
	public static String doPostWithoutEncryption(String url, Map<String, String> paramsMap) throws Exception {
		String responseBody = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		int statusCode = -1;
		int time = 0;
		logger.info("Request post url: " + url);
		logger.info("Request post params map: " + paramsMap);
		do {
			try {
				httpPost = new HttpPost(url);
				httpClient = getHttpClient();
				// 设置HTTP POST请求参数必须用NameValuePair对象
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				for(String key : paramsMap.keySet()){
					params.add(new BasicNameValuePair(key, paramsMap.get(key)));
				}
				
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				// 设置HTTP POST请求参数
				httpPost.setEntity(entity);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					System.out.println("HTTP" + "  " + "HttpMethod failed: " + httpResponse.getStatusLine());
				}
				InputStream is = httpResponse.getEntity().getContent();
				responseBody = getStreamAsString(is, HTTP.UTF_8);
				break;
			} catch (UnsupportedEncodingException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} catch (Exception e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return responseBody;
	}

	/**
	 * 
	 * 将InputStream 转化为String
	 * 
	 * @param stream
	 *            inputstream
	 * @param charset
	 *            字符集
	 * @return
	 * @throws IOException
	 */
	private static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset), 8192);
			StringWriter writer = new StringWriter();

			char[] chars = new char[8192];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 
	 * 得到httpClient
	 * 
	 * @return
	 */
	private static HttpClient getHttpClient() {
		final HttpParams httpParams = new BasicHttpParams();

		if (useProxy) {
			HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}

		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
		HttpClientParams.setRedirecting(httpParams, true);
		final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.14) Gecko/20110218 Firefox/3.6.14";

		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUseExpectContinue(httpParams, false);
		HttpClient client = new DefaultHttpClient(httpParams);

		return client;
	}
}
