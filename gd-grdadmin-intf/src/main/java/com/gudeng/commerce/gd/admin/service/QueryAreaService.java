package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.AreaDTO;

public interface QueryAreaService {
	
	//查询省
	public List <AreaDTO> findProvince() throws Exception;
	
	//查询市
	public List <AreaDTO> findCity(String code) throws Exception;
	
	//查询县区
	public List <AreaDTO> findCounty(String code) throws Exception;
	
	//根据areaID查询区域名称
	public AreaDTO getArea(String areaID) throws Exception;
	
	//根据areaID查询区域名称
	public AreaDTO getAreaByName(String area) throws Exception;
}
