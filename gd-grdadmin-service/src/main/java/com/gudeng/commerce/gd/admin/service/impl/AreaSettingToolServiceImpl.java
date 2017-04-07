package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.service.AreaSettingService;

public class AreaSettingToolServiceImpl implements AreaSettingToolService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static AreaSettingService areaSettingService;
	
	private AreaSettingService getHessianAreaService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.areaSetting.url");
		if(areaSettingService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaSettingService = (AreaSettingService) factory.create(AreaSettingService.class, hessianUrl);
		}
		return areaSettingService;
	}

	@Override
	public AreaSettingDTO getById(String id) throws Exception {
		
		return  getHessianAreaService().getById(id);
	}

	@Override
	public AreaSettingDTO getByName(String areaName) throws Exception {
		// TODO Auto-generated method stub
		return  getHessianAreaService().getByName(areaName);
	}

	@Override
	public List<AreaSettingDTO> getByCondition(Map<String, Object> map)
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
	public int addAreaSettingDTO(AreaSettingDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().addAreaSettingDTO(dto);
	}

	@Override
	public int updateAreaSettingDTO(AreaSettingDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().updateAreaSettingDTO(dto);
	}

	@Override
	public List<AreaSettingDTO> getAllAreaName(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianAreaService().getAllAreaName(map);
	}

	@Override
	public int batchAddAreaSetting(List<AreaSettingDTO> list) throws Exception {
		 return getHessianAreaService().batchAddAreaSetting(list);
	}
	

}
