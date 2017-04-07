package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.entity.PushOffline;
import com.gudeng.commerce.gd.customer.service.PushOfflineService;
import com.gudeng.commerce.gd.home.service.PushOffLineToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class PushOffLineToolServiceImpl implements PushOffLineToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static PushOfflineService pushOfflineService;
	
	protected PushOfflineService getHessianPushOffLineService()
			throws MalformedURLException {
		String url = gdProperties.getPushOffLineUrl();
		if (pushOfflineService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushOfflineService = (PushOfflineService) factory.create(
					PushOfflineService.class, url);
		}
		return pushOfflineService;
	}
	
	@Override
	public Long saveOffLinePushInfo(PushOffline pushOffline)
			throws Exception {
		return getHessianPushOffLineService().saveOffLinePushInfo(pushOffline);
	}


	@Override
	public PushOffline getOffLinePushInCondition(Map<?, ?> map)
			throws Exception {
		return getHessianPushOffLineService().getOffLinePushInCondition(map);
	}

}
