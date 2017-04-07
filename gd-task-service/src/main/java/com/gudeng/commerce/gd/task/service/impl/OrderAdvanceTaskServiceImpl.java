package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.task.service.OrderAdvanceTaskService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class OrderAdvanceTaskServiceImpl implements OrderAdvanceTaskService {
	
	public Logger logger = LoggerFactory.getLogger(OrderAdvanceTaskServiceImpl.class);
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static OrderBaseinfoService orderBaseInfoService;
	
	protected OrderBaseinfoService getHessianService() throws ServiceException {
		try {
			String url = gdProperties.getProperties().getProperty("gd.orderBaseinfoService.url");
			if (orderBaseInfoService == null) {
				HessianProxyFactory factory = new HessianProxyFactory();
				factory.setOverloadEnabled(true);
				orderBaseInfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, url);
			}
			return orderBaseInfoService;
		} catch (MalformedURLException e) {
			throw new ServiceException("获取hessian服务失败", e);
		}
	}
	
	@Override
	public void HandleExpireOrderAdvance() throws Exception {
		int result = getHessianService().updateExpireOrderAdvance();
		logger.info("---------------->   修改为关闭状态个数:"+result+"个");
	}

}
