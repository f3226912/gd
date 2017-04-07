package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.output.AreaListAppDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessAreaDTO;

public interface AreaToolService {

	/**
	 * 根据城市名来获取area信息
	 * @param city 城市名
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getByAreaName(String city) throws Exception;
	
	/**
	 * 获取所有省份信息
	 * @return
	 * @throws Exception
	 */
	public List<AreaDTO> getAllProvince() throws Exception;
	
	/**
	 * 根据父节点id查找子节点信息
	 * @param provinceId
	 * @return
	 * @throws Exception
	 */
	public List<AreaDTO> getChildrenByParentId(String parentId) throws Exception;

	public void setLngLat(Map<String, String> map)throws Exception;
	
	public AreaDTO getByAreaId(Long city) throws Exception;
	
	/**
	 * 根据城市ID查询城市的经纬度
	 * @param cityId
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getLngAndLatByCityId(String cityId) throws Exception;

	/**
	 * 获取所有省份和城市
	 * @return
	 * @throws Exception
	 */
	public List<AreaListAppDTO> getAllProvinceCity() throws Exception;
	
	/**
	 * 根据地域类型获取所有省份信息
	 * @return
	 * @throws Exception
	 */
	public List<AreaDTO> getAllProvinceByType(String type) throws Exception;
	
	/**
	 * 查找省份城市区县信息
	 * @param provinceId 省份id
	 * @param cityId 城市id
	 * @param countyId 区县id
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getAreaDetail(Long provinceId, Long cityId, Long countyId) throws Exception;

	/**
	 * 根据商铺id列表
	 * 查找商铺所在城市
	 * @param businessIdList
	 * @return
	 * @throws Exception
	 */
	public List<BusinessAreaDTO> getByBusinessIdList(List<String> businessIdList) throws Exception;
}
