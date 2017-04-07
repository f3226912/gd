package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.OrderNoToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.RandomIdGenerator;
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

	public Long getOrderNo(String type) throws Exception {
		String seqNumber = RandomIdGenerator.randomId(Long.parseLong(getOrderNoService().getOrderNo(type)));
		String year = DateUtil.getDate(DateUtil.getNow(), "yy");
		Calendar c = Calendar.getInstance();
		String week = String.format("%02d", c.get(Calendar.WEEK_OF_YEAR));
		return Long.parseLong(year + week + seqNumber);
	}

 
	
	


}
