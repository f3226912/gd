package com.gudeng.commerce.info.home.service;

import java.util.Map;

import com.gudeng.commerce.info.customer.entity.SysRegisterUser;

public interface SysRegisterUserHomeService {
	/**
	 * 根据userID查询对象
	 * 
	 * @param userID
	 * @author liufan
	 * @return SysRegisterUser
	 * 
	 */
	public SysRegisterUser get(String userID)throws Exception;
	
	/**
	 * 修改用户名密码
	 * @param map
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public String updateUserPwd(Map<String,Object> map) throws Exception;
	
	
}
