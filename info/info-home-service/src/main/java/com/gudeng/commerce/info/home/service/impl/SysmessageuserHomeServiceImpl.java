package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.service.SysmessageService;
import com.gudeng.commerce.info.customer.service.SysmessageuserService;
import com.gudeng.commerce.info.home.service.SysmessageuserHomeService;
import com.gudeng.commerce.info.home.util.GdProperties;
@Service
public class SysmessageuserHomeServiceImpl implements SysmessageuserHomeService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SysmessageuserService  sysmessageuserService;
	/**
	 * 功能描述:sysMessageService接口服务
	 * 
	 * @param
	 * @return
	 * @throws MalformedURLException 
	 */
	protected SysmessageuserService getHessionSysMessageuserService() throws MalformedURLException{
		String url =gdProperties.getSysMessageuserUrl();
		if(sysmessageuserService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysmessageuserService=(SysmessageuserService)factory.create(SysmessageuserService.class, url);
		}
		return sysmessageuserService;
	}
	@Override
	public int updateMessageIsdel(Map<String, Object> map) throws Exception {
		return getHessionSysMessageuserService().updateMessageIsdel(map);
	}

	@Override
	public int updateMessageIsread(Map<String, Object> map) throws Exception {
		return getHessionSysMessageuserService().updateMessageIsread(map);
	}

}
