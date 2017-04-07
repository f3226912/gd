package com.gudeng.commerce.gd.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
	
	/**
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
     * @param request
     * @return 
     *   true 用户重复提交了表单 
     *   false 用户没有重复提交表单
     */
    public static boolean isRepeatSubmit(HttpServletRequest request) {
        String client_token = request.getParameter("token");
        //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(client_token==null){
            return true;
        }
        //取出存储在Session中的token
        String server_token = (String) request.getSession().getAttribute("token");
        //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if(server_token==null){
            return true;
        }
        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if(!client_token.equals(server_token)){
            return true;
        }
        
        return false;
    }
    
	/**
	 * 生成Token
	 * Token：Nv6RRuGEVvmGjB+jimI/gw==
	 * @return
	 */
	public static String makeToken(){
		//  7346734837483  834u938493493849384  43434384
	    String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
	    //数据指纹   128位长   16个字节  md5
	    try {
	    	MessageDigest md = MessageDigest.getInstance("md5");
	        byte md5[] =  md.digest(token.getBytes());
	        //base64编码--任意二进制编码明文字符   adfsdfsdfsf
	        return Base64.encode(md5);
	     }catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		for(int i=0; i<100;i++){
			System.out.println("Token " + i + ": " + TokenUtil.makeToken());
		}
	}

}
