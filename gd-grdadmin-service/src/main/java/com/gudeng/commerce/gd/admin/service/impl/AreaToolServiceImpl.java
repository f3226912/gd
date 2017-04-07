package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;

public class AreaToolServiceImpl implements AreaToolService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static AreaService areaService;
	
	private AreaService hessianAreaService() throws MalformedURLException {
		String hessianUrl = gdProperties.getAreaUrl();
		if(areaService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, hessianUrl);
		}
		return areaService;
	}
	
	public List<AreaDTO> geAreaByAreaId(String areaId) throws Exception{
		return hessianAreaService().geAreaByAreaId(areaId);
	}
	
	@Override
	public int addArea(AreaDTO areaDTO) throws Exception {
		return hessianAreaService().addArea(areaDTO);
	}
	
	@Override
	public int updateArea(AreaDTO areaDTO) throws Exception {
		return hessianAreaService().updateArea(areaDTO);
	}
	
	@Override
	public String deleteArea(String areaID) throws Exception {
		
		return hessianAreaService().deleteArea(areaID);
	}
	
	@Override
	public AreaDTO getByAreaName(String city) throws Exception {
		return hessianAreaService().getAreaByName(city);
	}
	@Override
	public AreaDTO getArea(String areaId) throws Exception {
		return hessianAreaService().getArea(areaId);
	}
	
	@Override
	public List<AreaDTO> findProvince() throws Exception {
		return hessianAreaService().listTopArea();
	}

	@Override
	public List<AreaDTO> findCity(String code) throws Exception {
		return hessianAreaService().listChildArea(code);
	}

	@Override
	public List<AreaDTO> findCounty(String code) throws Exception {
		return hessianAreaService().listChildArea(code);
	}
}
