package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.DemoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.service.DemoService;


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
	public List<DictDTO> getByBirthday(Map<String, Object> map)
			throws Exception {
		return getHessianDemoService().getByBirthday(map);
	}
	
	
	
	
	
	@Override
	public List<DictDTO> getByName(Map<String, Object> map)
			throws Exception {
		return getHessianDemoService().getByName(map);
	}
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianDemoService().getTotal(map);
	}
	@Override
	public int deleteById(String id) throws Exception {
		 return getHessianDemoService().deleteById(id);
		
	}
	@Override

	public int addDictDTO(Map<String,Object> map)throws Exception{
		 return getHessianDemoService().addDictDTO(map);
	}
	@Override
	public int addDictDTO(DictDTO dic) throws Exception {
		 return getHessianDemoService().addDictDTO(dic);
	}
	@Override
	public int updateDictDTO(DictDTO dic) throws Exception {
		// TODO Auto-generated method stub
		 return getHessianDemoService().updateDictDTO(dic);
	}
	
}
