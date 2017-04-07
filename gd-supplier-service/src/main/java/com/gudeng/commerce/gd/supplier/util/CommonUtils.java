package com.gudeng.commerce.gd.supplier.util;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 
     * @param obj
     * @return
     * @throws Exception 
     */
    public static boolean isEmpty(Object obj)  {
    	if (obj instanceof String ){
    		return isEmpty((String)obj);
    	} else if (obj instanceof Object[]){
    		return isEmpty((Object[])obj);
    	} else if (obj instanceof Collection<?>){
    		return isEmpty((Collection<?>)obj);
    	} else if (obj instanceof Map<?,?>){
    		return isEmpty((Map<?,?>)obj);
    	}
    	return obj == null;
    }
    
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input==null || input.trim().isEmpty() ;
	}
	
	/**
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return array==null || array.length==0 ;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c==null || c.isEmpty() ;
	}
	
    /**
     * 
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map) {
    	return map==null || map.isEmpty() ;
    }
    
	/**
	 * 产生随机的六位数
	 * @return
	 */
	public static String getSixCode(){
		Random rad=new Random();
		
		String result  = rad.nextInt(1000000) +"";
		
		if(result.length()!=6){
			return getSixCode();
		}
		return result;
	}
	
	/**
	 * 产生随机的八位数
	 * @return
	 */
	public static String getEightCode(){
		Random rad=new Random();
		
		String result  = rad.nextInt(100000000) +"";
		
		if(result.length()!=8){
			return getEightCode();
		}
		return result;
	}
	
	public static boolean isNumber(String input){
		Pattern pattern = Pattern.compile("\\d{1,}");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
		
	}
}
