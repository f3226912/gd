package com.gudeng.commerce.customer;

import java.io.BufferedReader;
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
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.task.service.OrderBillTaskService;
import com.gudeng.commerce.gd.task.util.HtmlUtil;

import junit.framework.TestCase;

public class ServiceTest extends TestCase {

	private static OrderBillTaskService orderBillService;
	public HttpClient httpClient = null; // HttpClient实例
	public GetMethod getMethod = null; // GetMethod实例

	@Test
	public void test() {
		System.out.println("111111111111");
		System.out.println(orderBillService);
	}

	@Test
	public void testimportData() throws Exception {
		System.err.println(orderBillService.importData());

	}

	@Test
	public void testGetHTTP() throws Exception {
		System.err.println(HtmlUtil.getHtmlContent("http://121.40.227.44:10808/orderbill/"));
	}

	
	@Test
	public void testGetHtmlElement() throws Exception {
		String htmlContent = HtmlUtil.getHtmlContent("http://121.40.227.44:10808/orderbill/");
		List<String> aTags = HtmlUtil.getATags(htmlContent);
		for (String tag : aTags) {
			System.err.println(HtmlUtil.getAHref(tag));
		}
	}
	
	
	@Override
	protected void setUp() throws Exception {
		System.err.println("junit starting==============");
		String url = "http://127.0.0.1:8080/gd-task/taskService/orderBillTaskService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		ServiceTest.orderBillService = (OrderBillTaskService) factory.create(OrderBillTaskService.class, url);
	}

}
