package com.gudeng.commerce.gd.support;

import java.util.HashSet;
import java.util.Set;


public class BusinessSupport {

	private static Set<String> includeList = new HashSet<String>();
	
	static {
		includeList.add("14758");
	}
	
	public static Set<String> getIncludeList() {
		return includeList;
	}

	
}
