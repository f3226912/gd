package com.gudeng.commerce.gd.home.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.AreaDTO;

public interface QueryAreaToolService {
	
	//查询省
	public List <AreaDTO> findProvince() throws Exception;
	
	//查询市
	public List <AreaDTO> findCity(String code) throws Exception;
	
	//查询县区
	public List <AreaDTO> findCounty(String code) throws Exception;
	
	//根据areaID查询区域名称
	public AreaDTO getArea(String areaID) throws Exception;
	
	/**
	 * @Description listArea 获取所有的省市区县信息
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月2日 上午9:23:59
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<AreaDTO> listArea() throws Exception;
	
	/**
	 * 获取一级地区树
	 * @return
	 */
	public List<AreaDTO> getAreaParentTree() throws Exception;
	/**
	 * @Description getCityTree 查询2级地区 
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:17
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getCityTree() throws Exception;
	/**
	 * @Description getAreaTree 查询3级及以上地区
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:32
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getAreaTree() throws Exception;
}
