package com.gudeng.commerce.gd.bi.util;

import java.util.UUID;

public class IdGenerateUtil {
	
	
	public static String create32UUID(){
		
		return UUID.randomUUID().toString().replace("-", "");
	}

}

