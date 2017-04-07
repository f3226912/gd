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

public class AccBankCardTest {
 
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
	public void testGetBankCard() {
    	String URL=HttpXmlClient.LOCAL_HOST+"v2/accBankCard/getBankCards";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("memberId", "93");
     	
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
 
}
