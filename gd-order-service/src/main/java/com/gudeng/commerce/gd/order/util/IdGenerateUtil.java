package com.gudeng.commerce.gd.order.util;

import java.util.UUID;

public class IdGenerateUtil {
	
	
	public static String create32UUID(){
		
		return UUID.randomUUID().toString().replace("-", "");
	}

}

