package com.gudeng.commerce.gd.api.service.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DiTuiTest {
	
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
	public void testInviteCustomer() {  
    	String URL=HttpXmlClient.LOCAL_HOST+"gd-api/ditui/inviteCustomer";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("mobile", "13824355574"); 
     	params.put("pageNum", "2"); 
     	params.put("pageSize", "5"); 
     	params.put("date","2016-06");
     	
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
	@Test
	public void testCreateOrder() {  
    	String URL=HttpXmlClient.LOCAL_HOST+"gd-api/ditui/createOrder";

    	Map<String, String> params = new HashMap<String, String>();  
     	params.put("mobile", "1"); 
     	params.put("pageNum", "1"); 
     	params.put("pageSize", "5"); 
     	params.put("date","2016-06");
     	
    	String xml = HttpXmlClient.post(URL, params);  
    	System.out.println(xml);  
	
	
	}
}
