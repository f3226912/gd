package com.gudeng.commerce.info.customer.service;

import com.gudeng.commerce.info.customer.dto.BaiDuDoLoginResponseDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLogoutResponseDTO;
/**
 * 百度API服务调用
 * @author yangjj
 */
public interface BaiDuApiService {
	 /**
	  * 百度登录API
	  * @author yangjj
	  * 
	  * @param userName 登录用户名
      * @param password 登录密码
      * @param token API授权码
	  * @return 服务器响应结果
	  */
	 public BaiDuDoLoginResponseDTO doLogin(String userName,String password,String token);
	 
	 /**
	 * 百度登出API
	 * @author yangjj
	 * 
	 * @param userName 登录用户名
	 * @param token API授权码
	 * @param userId 用户id
	 * @param sessionId 会话id
	 * @return 服务器响应结果
	 */
	 public BaiDuDoLogoutResponseDTO doLogout(String userName,String token,long userId,String sessionId);
	 
	 /**
	  * 百度返回账号下管理的站点、子目录信息API
	  * @author yangjj
	  * 
      * @param userName 登录用户名
      * @param token API授权码
      * @param userId 用户id
      * @param sessionId 会话id
	  * @return 服务器响应结果
	  */
	 public String getSites(String userName, String token,long userId,String sessionId);

}
