package com.gudeng.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

import com.gudeng.commerce.gd.api.dto.input.ENongQueryOrderParamsDTO;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.test.util.HttpClientUtil;

public class ENongControllerTest extends TestCase {
	private String url="http://127.0.0.1:8080/gd-api/enong/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * E农订单列表查询
	 * @throws Exception
	 */
	@Test
	public void testOrderList() throws Exception {
		url += "orderList";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("signmsg", "H1D+sipKJ42ICyhPy7Zd7NeYbjcNo9hZJxbXpFxU/trOCS61oR0RcGWatqRzPy5KTJO1j24cr+yFG/qwPFVHiuo+Mrjzqumg2Wgr6Dxnrhw3+Jn4U5IaanUmGGwv7sotTuljrxO1HMcwfZQCuQYLnGFckzPYrW/58XFLNDKQapSSjswAGFOBEJGRUkgIB/kaZ4UeE/nL2Bil81UZgKZ37XodobF21VLL73UhClq6urGtcv4+95Jh1XJ4WYYtXtS37q3/IZbsXd9erta2hHe4+ijM4lKAM+oLAHaSdtaehkjwT51gSy7adabxl3ucwKw0OZDWm369qRQVMUMk+jEBfA==");
		
		ENongQueryOrderParamsDTO entity1 = new ENongQueryOrderParamsDTO();
		entity1.setVersion("1.0");
		entity1.setCharset("1");
		entity1.setMachinenum("p0001");
		entity1.setMerchantnum("20160314001");
		entity1.setReserved("");
		
		requestParams.put("reqdata", JSONUtils.toJSONString(entity1));
		HttpClientUtil.httpPost(url, requestParams, "UTF-8");
		HttpClientUtil.getHttpClient().getConnectionManager().shutdown(); 
	}
}
