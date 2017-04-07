package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
/**
 * date 2014-02-27
 * @author Administrator
 *
 */
public interface AreaToolService {
	
	/**
	 * 根据市场id获得所有分类
	 * @return
	 */
	public List<AreaDTO> geAreaByAreaId(String areaId) throws Exception;
	
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
	 * 删除地区
	 * @param areaID
	 * @return
	 * @throws Exception
	 */
	public String deleteArea (String areaID) throws Exception;
	
	/**
	 * 根据城市名来获取area信息
	 * @param city 城市名
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getByAreaName(String city) throws Exception;
	
	/**
	 * 功能描述:根据id查看地区信息
	 * @param AreaDTOID:
	 * @return
	 */
	public AreaDTO getArea(String AreaID) throws Exception;
	
	
	//查询省
	public List <AreaDTO> findProvince() throws Exception;
	
	//查询市
	public List <AreaDTO> findCity(String code) throws Exception;
	
	//查询县区
	public List <AreaDTO> findCounty(String code) throws Exception;
	
}