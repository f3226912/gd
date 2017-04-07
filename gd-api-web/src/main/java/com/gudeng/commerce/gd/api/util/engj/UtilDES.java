package com.gudeng.commerce.gd.api.util.engj;


import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.util.encoders.Base64;

/**
 * 
 * 
 *
 */
public class UtilDES {
	
	static String Key="6yhn@WSX";

	/**
	 * DES加密
	 */
	public static String encryptDES(String encryptString) {
		try {
			
			DESKeySpec dks = new DESKeySpec((Key).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey,  new SecureRandom());
			byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
			return new String(Base64.encode(encryptedData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * DES解密
	 */
	public static String decryptDES(String decryptString) {
		try {
			DESKeySpec dks = new DESKeySpec((Key).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey,  new SecureRandom());
			byte decryptedData[] = cipher.doFinal(Base64.decode(decryptString.getBytes()));
			return new String(decryptedData,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
