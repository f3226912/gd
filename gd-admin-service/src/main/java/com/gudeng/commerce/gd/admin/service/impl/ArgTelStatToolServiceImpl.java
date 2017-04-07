package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.ArgTelStatToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.service.CallstatiSticsService;

/** 
 *功能描述：
 */
@Service
public class ArgTelStatToolServiceImpl implements ArgTelStatToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static CallstatiSticsService callstatiSticsService;

	/**
	 * 功能描述: argTeStatService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected CallstatiSticsService getHessianArgTeStatService() throws MalformedURLException {
		String url = gdProperties.getArgTelStatUrl();
		if(callstatiSticsService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			callstatiSticsService = (CallstatiSticsService) factory.create(CallstatiSticsService.class, url);
		}
		return callstatiSticsService;
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return getHessianArgTeStatService().getTotal(map);
	}
	
	@Override
	public int getTotal2(Map map) throws Exception {
		return getHessianArgTeStatService().getTotal2(map);
	}
	
	@Override
	public List<CallstatiSticsDTO> getBySearch(Map map) throws Exception {
		return getHessianArgTeStatService().getBySearch(map);
	}
	
	@Override
	public List<CallstatiSticsDTO> getBySearch2(Map map) throws Exception {
		return getHessianArgTeStatService().getBySearch2(map);
	}
}
