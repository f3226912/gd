package com.gudeng.commerce.gd.report.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.report.dto.DictDTO;
import com.gudeng.commerce.gd.report.service.DemoService;
import com.gudeng.commerce.gd.report.service.DemoToolService;
import com.gudeng.commerce.gd.report.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class DemoToolServiceImpl implements DemoToolService{
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
