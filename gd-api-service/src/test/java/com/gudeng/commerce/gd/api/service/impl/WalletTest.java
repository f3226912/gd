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

public class WalletTest {
 
	@Test
	public void testGetBankCards() {


    	String URL=HttpXmlClient.DEV_HOST+"v3/accBankCard/getBankCards";

    	Map<String, String> params = new HashMap<String, String>();  
    	params.put("sid", "ECC2DBD964A87D4E8F2524E689408500");
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
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
	public void testUpdateAccInfo() {
    	String URL=HttpXmlClient.LOCAL_HOST+"/v2/wallet/updateTransPwd";

    	Map<String, String> params = new HashMap<String, String>();  
      	params.put("transPwd", "123123");
     	params.put("memberId", "93");
     	params.put("verifyCode", "5555 5555 5555 5555 555");
    	params.put("account", "qq1");
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	
	}

	@Test
	public void testWithDraw() {
    	String URL=HttpXmlClient.LOCAL_HOST+"/v2/cashRequest/withdraw";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("transPwd", "D6D1EEE86DCEAB167593AE7614CE8148");
      	params.put("cashAmount", "100");
     	params.put("memberId", "93");
     	params.put("bankCardNo", "5555 5555 5555 5555 555");
     	
//    	params.put("imageStr", imageStr);
    	     
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
	@Test
	public void getWalletIndex() {
    	String URL=HttpXmlClient.DEV_HOST+"/v3/wallet/getWalletIndex";

    	Map<String, String> params = new HashMap<String, String>();  
    	params.put("sid", "ECC2DBD964A87D4E8F2524E689408500");
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	}
	
	
	@Test
	public void getPaySerialNumber() {
    	String URL=HttpXmlClient.LOCAL_HOST+"v3/paySerialNumber/getByMemberIdAndDay";

    	Map<String, String> params = new HashMap<String, String>();  
    	params.put("sid", "ECC2DBD964A87D4E8F2524E689408500");
    	params.put("businessId", "1");
    	params.put("count", "1");
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	}
	
	
	
}
