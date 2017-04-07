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

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sun.misc.BASE64Decoder;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.impl.HttpXmlClient;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.order.service.DemoService;
import com.gudeng.commerce.gd.supplier.service.FileUploadService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-cache.xml",
		"classpath:spring/spring-res.xml",
		"classpath:spring/applicationContext.xml" })
public class CallStatisticsTest    {
 
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
	public void testWithDraw() {
    	String URL=HttpXmlClient.LOCAL_HOST+"callstatiStics/addCallstatiStics";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("memberId ", "9203");
     	params.put("b_memberId", "9169");
     	params.put("source", "5522122233");
     	params.put("memberId", "DDXQ");
     	params.put("sysCode", "3");
     	
//    	params.put("imageStr", imageStr);
    	     
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
 
}
