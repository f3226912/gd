package com.gudeng.commerce.info.customer.util;

import java.util.UUID;

public class IdGenerateUtil {
	
	
	public static String create32UUID(){
		
		return UUID.randomUUID().toString().replace("-", "");
	}

}

