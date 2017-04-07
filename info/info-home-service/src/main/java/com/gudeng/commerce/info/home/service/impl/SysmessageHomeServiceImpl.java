package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.dto.SysmessageDTO;
import com.gudeng.commerce.info.customer.service.SysmessageService;
import com.gudeng.commerce.info.home.service.SysmessageHomeService;
import com.gudeng.commerce.info.home.util.GdProperties;

@Service
public class SysmessageHomeServiceImpl implements SysmessageHomeService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SysmessageService  sysmessageService;
	/**
	 * 功能描述:sysMessageService接口服务
	 * 
	 * @param
	 * @return
	 * @throws MalformedURLException 
	 */
	protected SysmessageService getHessionSysMessageService() throws MalformedURLException{
		String url =gdProperties.getSysMessageUrl();
		if(sysmessageService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysmessageService=(SysmessageService)factory.create(SysmessageService.class, url);
		}
		return sysmessageService;
	}
	@Override
	public Integer getUnReadMessageCount(String userID) throws Exception {
		return getHessionSysMessageService().getUnReadMessageCount(userID);
	}
	@Override
	public List<SysmessageDTO> getMessageListByUser(Map<String, Object> map)
			throws Exception {
		return getHessionSysMessageService().getMessageListByUser(map);
	}
	@Override
	public SysmessageDTO getMessageDetail(Map<String, Object> map)
			throws Exception {
		return getHessionSysMessageService().getMessageDetail(map);
	}
	
}
