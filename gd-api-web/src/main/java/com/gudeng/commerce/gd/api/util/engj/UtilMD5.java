package com.gudeng.commerce.gd.api.util.engj;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UtilMD5 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilMD5.class);
	
	/**
	 * @param str需要加密字符串
	 * @return 加密后字符串
	 */
	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			LOGGER.error("str需要加密字符串不能为空");
		}
		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hexString.toString();
	}

}
