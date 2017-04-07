package com.gudeng.commerce.gd.m.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;

/**
 *功能描述：BusinessBaseinfo增删改查实现类
 *
 */
public interface BusinessBaseinfoToolService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getById(String id)throws Exception;
	
	
	/**
	 * 根据UserID查询对象
	 * 
	 * @param UserID
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserId(String userId)throws Exception;


	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	/**
	 * 通过Map插入数据库
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int addBusinessBaseinfoByMap(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	

	/**
	 * 通过 BusinessBaseinfoEntity 对象插入数据库
	 * 
	 * @param BusinessBaseinfoEntity
	 * @return Long
	 * 
	 */
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be) throws Exception;
	
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	
	/**
	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
	 * 
	 * @param  map
	 * @return list
	 * 
	 */
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception;

	public BusinessBaseinfoDTO getByBusinessNo(String businessNo) throws Exception;

	
	/**
	 * 根据posNumber查询对象
	 * 
	 * @param posNumber
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByPosNumber(String posNumber)throws Exception;
	
	public BusinessBaseinfoDTO getBusinessShortInfoBySearch(Map<String, Object> params) throws Exception;
	
	public Map<String, Object> getCertifForBusinessBySearch(Map<String, Object> params) throws Exception;

}