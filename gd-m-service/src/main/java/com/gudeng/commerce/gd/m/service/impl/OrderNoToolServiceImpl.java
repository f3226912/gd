package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.OrderNoToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.order.service.OrderNoService;


/**
 * 订单编号生成工具类
 * @author steven
 *
 */
public class OrderNoToolServiceImpl implements OrderNoToolService{
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static OrderNoService orderNoService;

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected OrderNoService getOrderNoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderNoService.url");
		if(orderNoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderNoService = (OrderNoService) factory.create(OrderNoService.class, url);
		}
		return orderNoService;
	}

	@Override
	public String getOrderNo() throws Exception {
		return 	getOrderNoService().getOrderNo();
	}

 
	
	


}
