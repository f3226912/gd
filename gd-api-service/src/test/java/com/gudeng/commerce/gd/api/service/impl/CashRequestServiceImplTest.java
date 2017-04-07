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

public class CashRequestServiceImplTest {
 
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
    	String URL=HttpXmlClient.DEV_HOST+"v2/cashRequest/withdraw";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("cashAmount", "100");
     	params.put("transPwd", "D6D1EEE86DCEAB167593AE7614CE8148");
     	params.put("bankCardNo", "5522122233");
     	params.put("memberId", "93");
     	params.put("depositBankName", "白痴银行");
     	
//    	params.put("imageStr", imageStr);
    	     
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
 
}
