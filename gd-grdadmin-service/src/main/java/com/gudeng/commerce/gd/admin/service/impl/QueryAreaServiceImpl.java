package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;

@Service
public class QueryAreaServiceImpl implements   QueryAreaService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static AreaService areaService;
	
	/**
	 * 功能描述:接口服务
	 * 
	 * @param
	 * @return
	 */
	protected AreaService getHessianareaService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.area.url");
		if(areaService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}
		return areaService;
	}

	@Override
	public List<AreaDTO> findProvince() throws Exception {
		return getHessianareaService().listTopArea();
	}

	@Override
	public List <AreaDTO>  findCity(String code) throws Exception {
		return getHessianareaService().listChildArea(code);
	}

	@Override
	public List <AreaDTO>  findCounty(String code) throws Exception {
		return getHessianareaService().listChildArea(code);
	}

	@Override
	public AreaDTO getArea(String areaID) throws Exception {
		 return getHessianareaService().getArea(areaID);
	}

	@Override
	public AreaDTO getAreaByName(String area) throws Exception {
		return getHessianareaService().getAreaByName(area);
	}
	
	
}
	
