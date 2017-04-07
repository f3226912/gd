package com.gudeng.commerce.gd.api.service.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import sun.misc.BASE64Decoder;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.order.service.DemoService;
import com.gudeng.commerce.gd.supplier.service.FileUploadService;

public class LoginTest {

	@Test
	public void testGetFileUploadService() {
		fail("Not yet implemented");
	}

	@Test
	public void testIOSdataToPic() {
		fail("Not yet implemented");
	}

	@Test
	public void testDataToPicMultipartFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testDataToPicString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		String URL = HttpXmlClient.DEV_HOST + "v3/member/login";

		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "13527710395");
		params.put("password", "7171A0D2849E2F14BCB0F3BB860EFA6C");
		params.put("device", "93");
		params.put("device_tokens", "93");
		
		String xml = HttpXmlClient.post(URL, params);
		System.out.println(xml);
		
	}
	@Test
	public void testGetInfo() {
		String URL = HttpXmlClient.DEV_HOST + "v3/member/getInfo";

		Map<String, String> params = new HashMap<String, String>();
		params.put("sid", "0C4F686E4CA182E317303F3AE8AFCE9A");
		
		String xml = HttpXmlClient.post(URL, params);
		System.out.println(xml);
		
	}
	
	
	
	@Test
	public void testLogout() {
		String URL = HttpXmlClient.LOCAL_HOST + "v3/member/logout";

		Map<String, String> params = new HashMap<String, String>();
		params.put("sid", "BBD5576FD26A2A991FAF73C5A79F70B3");
		String xml = HttpXmlClient.post(URL, params);
		System.out.println(xml);
	}
}
