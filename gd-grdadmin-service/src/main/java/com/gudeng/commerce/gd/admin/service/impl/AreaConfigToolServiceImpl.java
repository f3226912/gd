package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AreaConfigToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaConfigDTO;
import com.gudeng.commerce.gd.customer.service.AreaConfigService;

public class AreaConfigToolServiceImpl implements AreaConfigToolService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static AreaConfigService areaConfigService;
	
	private AreaConfigService getHessianAreaService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.areaConfig.url");
		if(areaConfigService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaConfigService = (AreaConfigService) factory.create(AreaConfigService.class, hessianUrl);
		}
		return areaConfigService;
	}

	@Override
	public AreaConfigDTO getById(String id) throws Exception {
		
		return  getHessianAreaService().getById(id);
	}

	@Override
	public AreaConfigDTO getByName(String areaName) throws Exception {
		// TODO Auto-generated method stub
		return  getHessianAreaService().getByName(areaName);
	}

	@Override
	public List<AreaConfigDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().getByCondition(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return  getHessianAreaService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().deleteById(id);
	}

	@Override
	public int addAreaConfigDTO(AreaConfigDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().addAreaConfigDTO(dto);
	}

	@Override
	public int updateAreaConfigDTO(AreaConfigDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().updateAreaConfigDTO(dto);
	}

	

}
