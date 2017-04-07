package com.gudeng.commerce.gd.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserSettingPropertyUtils {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(UserSettingPropertyUtils.class);

    /** 密钥字符串 */ 
//    private static Map<String, Object> encrytphrase; 
    
//    static {
//		InputStream istream = UserSettingPropertyUtils.class.getClassLoader().getResourceAsStream("user-setting.properties");
//		Properties pro = new Properties();
//		try {
//			pro.load(istream);
//			encrytphrase = pro.getProperty("gd.encrypt.key");
//		} catch (IOException e) {
//			logger.error("获取属性异常",e); 
//		}
//	}
//    
	public static String getEncrytphrase(String propertiName) {
		InputStream istream = UserSettingPropertyUtils.class.getClassLoader().getResourceAsStream("user-setting.properties");
		Properties pro = new Properties();
		try {
			pro.load(istream);
			return pro.getProperty(propertiName);
		} catch (IOException e) {
			logger.error("获取属性异常",e); 
		}
		return null;
	
	}
    
	public static void main(String[] args) {
		System.out.println("Encryt String: " + getEncrytphrase("gd.encrypt.key"));
	}
}
