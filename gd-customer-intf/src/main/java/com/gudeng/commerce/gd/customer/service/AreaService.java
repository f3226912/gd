package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessAreaDTO;

/**
 * 
 * 区域管理
 * @author
 *
 */
public interface AreaService {
	
	
	/**
	 * 功能描述:查询所有地区信息
	 * @return
	 */
	public List<AreaDTO> listArea();
	
	/**
	 * 功能描述:查询一级地区信息
	 * @return
	 */
	public List<AreaDTO> listTopArea();
	
	/**
	 * 功能描述:查询子级地区信息
	 * @return
	 */
	public List<AreaDTO> listChildArea(String id);
	
	/**
	 * 功能描述:根据id查看地区信息
	 * @param AreaDTOID:
	 * @return
	 */
	public AreaDTO getArea(String AreaID);
	
	/**
	 * 获取一级地区树
	 * @return
	 */
	public List<AreaDTO> getAreaParentTree() ;
	/**
	 * @Description getCityTree 查询2级地区 
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:17
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getCityTree() ;
	/**
	 * @Description getAreaTree 查询3级及以上地区
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:32
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getAreaTree() ;
	
	
	/**
	 * 根据父节点获取地区子节点树
	 * @return
	 */
	public List<AreaDTO> getAreaChildTree(String father) ;
	
	/**
	 * 功能描述:根据名字查看地区信息
	 * @param AreaDTOID:
	 * @return
	 */
	public AreaDTO getAreaByName(String Area);

	public void setLngLat(Map<String, String> map);
	
	/**
	 * @return Area集合
	 */
	public List<AreaDTO> geAreaByAreaId(String areaId);
	
	public int addArea(AreaDTO areaDTO) throws Exception ;
	
	/**
	 * 通过Area对象更新数据库
	 * 
	 * @param AreaDTO
	 * @return int
	 * 
	 */
	public int updateArea(AreaDTO areaDTO) throws Exception;
	
	/**
	 * 功能描述:删除地区信息
	 */
	public String deleteArea(String AreaID);
	
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
	public List<AreaDTO> getAllProvinceCity() throws Exception;
	
	/**
	 * 根据地域类型获取
	 * @param type
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
