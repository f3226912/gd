package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.service.SysRegisterUserService;
import com.gudeng.commerce.info.home.service.SysRegisterUserHomeService;
import com.gudeng.commerce.info.home.util.GdProperties;

@Service
public class SysRegisterUserHomeServiceImpl implements SysRegisterUserHomeService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SysRegisterUserService  sysRegisterUserService;
	/**
	 * 功能描述:sysRegisterUser接口服务
	 * 
	 * @param
	 * @return
	 * @throws MalformedURLException 
	 */
	protected SysRegisterUserService getHessionSysRegisterUserService() throws MalformedURLException{
		String url =gdProperties.getSysRegisterUserUrl();
		if(sysRegisterUserService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysRegisterUserService=(SysRegisterUserService)factory.create(SysRegisterUserService.class, url);
		}
		return sysRegisterUserService;
	}
	@Override
	public SysRegisterUser get(String userID) throws Exception {
		return getHessionSysRegisterUserService().get(userID);
	}
	@Override
	public String updateUserPwd(Map<String, Object> map) throws Exception {
		return getHessionSysRegisterUserService().updateUserPwd(map);
	}

}
