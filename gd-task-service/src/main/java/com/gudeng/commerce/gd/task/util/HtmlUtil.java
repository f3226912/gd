package com.gudeng.commerce.gd.task.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

public class HtmlUtil {

	/**
	 * @Description 获取html内容
	 * @param url
	 * @return
	 * @CreationDate 2015年12月24日 下午2:57:02
	 * @Author lidong(dli@gdeng.cn)
	 */
	public static String getHtmlContent(String url) {
		HttpClient httpClient = null; // HttpClient实例
		GetMethod getMethod = null; // GetMethod实例
		InputStream input = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		int statusCode = 0;
		httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
		getMethod.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
		// 执行Get方法并取得返回状态码，200表示正常，其它代码为异常
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200) {
				return null;
			}
			// 读取解析结果
			input = getMethod.getResponseBodyAsStream();
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String line = reader.readLine();
			while (line != null) { // 如果 line 为空说明读完了
				sb.append(line);
				line = reader.readLine(); // 读取下一行
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * @Description 获取html内容的a标签
	 * @param source
	 * @return
	 * @CreationDate 2015年12月24日 下午2:56:44
	 * @Author lidong(dli@gdeng.cn)
	 */
	public static List<String> getATags(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		String reg = "<a\\s+href[^>]*>";
		Matcher matcher = Pattern.compile(reg).matcher(source);
		while (matcher.find()) {
			String r = source.substring(matcher.start(), matcher.end());
			result.add(r);
		}
		return result;
	}

	/**
	 * @Description 获取a标签内的href
	 * @param aTag
	 * @return
	 * @CreationDate 2015年12月24日 下午2:59:42
	 * @Author lidong(dli@gdeng.cn)
	 */
	public static String getAHref(String aTag) {
		if (StringUtils.isEmpty(aTag)) {
			return null;
		}
		return aTag.substring(aTag.indexOf("\"") + 1, aTag.lastIndexOf("\""));
	}

}
