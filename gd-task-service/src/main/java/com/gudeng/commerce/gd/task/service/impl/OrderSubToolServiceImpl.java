package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.service.OrderSubService;
import com.gudeng.commerce.gd.task.service.OrderSubToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月8日 下午5:35:40
 */
public class OrderSubToolServiceImpl implements OrderSubToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static OrderSubService orderSubService;

	protected OrderSubService getHessianService() throws ServiceException {
		try {
			String url = gdProperties.getProperties().getProperty("gd.orderSubService.url");
			if (orderSubService == null) {
				HessianProxyFactory factory = new HessianProxyFactory();
				factory.setOverloadEnabled(true);
				orderSubService = (OrderSubService) factory.create(OrderSubService.class, url);
			}
			return orderSubService;
		} catch (MalformedURLException e) {
			throw new ServiceException("获取hessian服务失败", e);
		}
	}
	
	@Override
	public void handleOrderSubAmtToDB(Long orderNo) throws ServiceException {
		getHessianService().handleOrderSubAmtToDB(orderNo);
	}

	@Override
	public void checkOrderSubRuleToDB(Long orderNo) throws ServiceException {
		getHessianService().checkOrderSubRuleToDB(orderNo);
	}

}
