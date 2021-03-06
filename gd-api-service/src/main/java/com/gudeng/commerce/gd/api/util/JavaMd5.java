package com.gudeng.commerce.gd.api.util;
/** 
 * MD5加密方式
 * @author 
 * @version 创建时间：2014年3月18日 下午6:40:44 * 
 */
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Properties;
  
public class JavaMd5 {  
  
    public static String getMD5(String content) {  
        try {  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            digest.update(content.getBytes());  
            return getHashString(digest);  
              
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    private static String getHashString(MessageDigest digest) {  
        StringBuilder builder = new StringBuilder();  
        for (byte b : digest.digest()) {  
            builder.append(Integer.toHexString((b >> 4) & 0xf));  
            builder.append(Integer.toHexString(b & 0xf));  
        }  
        return builder.toString();  
    }  
	private static GdProperties properties;
    public static void main(String[] args){
    	System.out.println(properties.getProductCategoryUrl());
    	
    	
    	System.out.println(JavaMd5.getMD5("123sdfsd45"));
    }
}  