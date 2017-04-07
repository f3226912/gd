package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AccInfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.service.AccInfoService;

public class AccInfoToolServiceImpl implements AccInfoToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private AccInfoService accInfoService;
	
	protected AccInfoService getHessianAccInfoServiceService() throws MalformedURLException {
		String url = gdProperties.getAccInfoUrl();
		if (accInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accInfoService = (AccInfoService) factory.create(AccInfoService.class, url);
		}
		return accInfoService;
	}
	@Override
	public AccInfoDTO getWalletIndex(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAccInfoServiceService().getWalletIndex(memberId);
	}

}
