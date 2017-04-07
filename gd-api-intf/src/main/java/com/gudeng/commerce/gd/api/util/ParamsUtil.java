package com.gudeng.commerce.gd.api.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ParamsUtil {

	private static boolean isMatch(String regex, String orginal){  
        if (orginal == null || orginal.trim().equals("")) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile(regex);  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
    }
	
	public static String getStringFromString(String value){
		return getStringFromString(value, "");
	}
	
	public static String getStringFromString(String value, String defaultValue){
		return StringUtils.isBlank(value) ? defaultValue : value;
	}
	
	public static String getStringFromInteger(Integer value){
		return getStringFromInteger(value, "");
	}
	
	public static String getStringFromInteger(Integer value, String defaultValue){
		return value == null ? defaultValue : value.toString();
	}
	
	public static Integer getIntFromString(String value, Integer defaultValue){
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		
		boolean result = isMatch("[0-9]+", value);
		if(result){
			return Integer.parseInt(value);
		}else{
			return defaultValue;
		}
	}
	
	public static Integer getIntFromString(String value){
		return getIntFromString(value, 0);
	}
	
	public static String getStringFromInt(Integer value, String defaultValue){
		if(value == null){
			return defaultValue;
		}
		
		return value.toString();
	}
	
	public static Double getDoubleFromString(String value, Double defaultValue){
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		
		boolean result = isMatch("[+-]?\\d+(\\.\\d+)?", value);
		if(result){
			return Double.parseDouble((value));
		}else{
			return defaultValue;
		}
	}
	
	public static Double getDoubleFromString(String value){
		return getDoubleFromString(value, 0.00);
	}
	
	public static String getStringFromDouble(Double value, String defaultValue){
		if(value == null){
			return defaultValue;
		}
		
		return value.toString();
	}
	
	public static void main(String[] args) throws IOException {
//        System.out.println(ParamsUtil.getIntFromString("123456"));
        System.out.println(ParamsUtil.getDoubleFromString("123456.01"));
	}

	public static Long getLongFromString(String value, Long defaultValue) {
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		
		boolean result = isMatch("[0-9]+", value);
		if(result){
			return Long.parseLong(value);
		}else{
			return defaultValue;
		}
	}
}
