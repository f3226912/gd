package com.gudeng.commerce.order.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PaySerialTest extends HttpXmlClient{

	private static Logger logger = LoggerFactory.getLogger(PaySerialTest.class);
      
	@Test
	public void getPaySerialTest()  {

    	String URL=LOCAL_HOST+"v2/paySerialNumber/getByMemberIdAndDay";
    	Map<String, String> params = new HashMap<String, String>();  
    	params.put("businessId", "78");
    	params.put("count", "1");
    	String xml = HttpXmlClient.post(URL, params);  
    	logger.error(xml);
	}
	    
}
