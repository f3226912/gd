package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.task.dto.DictDTO;
import com.gudeng.commerce.gd.task.service.DemoService;
import com.gudeng.commerce.gd.task.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class DemoServiceImpl implements DemoService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static DemoService demoService;
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected DemoService getHessianDemoService() throws MalformedURLException {
		String url = gdProperties.getDemoUrl();
		if(demoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			demoService = (DemoService) factory.create(DemoService.class, url);
		}
		return demoService;
	}
	@Override
	public DictDTO getById(String id) throws Exception {
		System.out.println(getHessianDemoService().getById(id));
		return getHessianDemoService().getById(id);
	}
	
}
