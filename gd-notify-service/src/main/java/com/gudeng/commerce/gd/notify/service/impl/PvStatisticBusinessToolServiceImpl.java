package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;
import com.gudeng.commerce.gd.notify.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;

/**
 * 商铺浏览量业务
 * 
 * @author Ailen
 *
 */
public class PvStatisticBusinessToolServiceImpl implements PvStatisticBusinessToolService {
	
	@Autowired
	public GdProperties gdProperties;
	
	private static PvStatisticBusinessService pvStatisticBusinessService;
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PvStatisticBusinessService getHessianPvStatisticBusinessService() throws MalformedURLException {
		String url = gdProperties.getPvStatisticBusinessServiceUrl();
		if(pvStatisticBusinessService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pvStatisticBusinessService = (PvStatisticBusinessService) factory.create(PvStatisticBusinessService.class, url);
		}
		return pvStatisticBusinessService;
	}
	
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params) throws Exception {
		getHessianPvStatisticBusinessService().sendMsg(memberId, mobile, alidauCode, template, params);
	}

}
