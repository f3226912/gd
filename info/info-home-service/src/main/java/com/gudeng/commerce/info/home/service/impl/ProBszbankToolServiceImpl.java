package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.service.ProBszbankService;
import com.gudeng.commerce.info.home.service.ProBszbankToolService;
import com.gudeng.commerce.info.home.util.GdProperties;

@Service
public class ProBszbankToolServiceImpl implements ProBszbankToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static ProBszbankService proBszbankService;
	
	protected ProBszbankService getHessianProBszbankService() throws MalformedURLException {
		String url = gdProperties.getProBszbankUrl();
		if(proBszbankService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			proBszbankService = (ProBszbankService) factory.create(ProBszbankService.class, url);
		}
		return proBszbankService;
	}
	
	@Override
	public List<ProBszbankDTO> getTradeByDay(Map<String, Object> map) throws Exception {

		return getHessianProBszbankService().getTradeByDay(map);
	}

	@Override
	public List<ProBszbankDTO> getOrderByDay(Map<String, Object> parm)  throws Exception{
		return getHessianProBszbankService().getOrderByDay(parm);
	}

	
}
