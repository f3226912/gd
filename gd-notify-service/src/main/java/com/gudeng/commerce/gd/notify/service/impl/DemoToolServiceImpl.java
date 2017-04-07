package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.notify.dto.DictDTO;
import com.gudeng.commerce.gd.notify.service.DemoToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class DemoToolServiceImpl implements DemoToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static DemoToolService demoService;
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:demo接口服务
	 * 
	 * @param
	 * @return
	 */
	protected DemoToolService getHessianDemoService() throws MalformedURLException {
		String url = gdProperties.getDemoUrl();
		if(demoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			demoService = (DemoToolService) factory.create(DemoToolService.class, url);
		}
		return demoService;
	}
	@Override
	public DictDTO getById(String id) throws Exception {
		return getHessianDemoService().getById(id);
	}
	@Override
	public List<DictDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return getHessianDemoService().getByCondition(map);
	}
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianDemoService().getTotal(map);
	}
	
}
