package com.gudeng.commerce.gd.admin.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.AuditInfoEntity;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;

/**
 *功能描述：AuditInfo 增删改查实现类
 *
 */
public interface AuditInfoToolService {



	/**
	 * 通过AuditInfo对象插入数据库
	 * 
	 * @param AuditInfoDTO
	 * @return int
	 * 
	 */
	public int addAuditInfoDTO(AuditInfoDTO mb) throws Exception;
	
	
	

	/**
	 * 通过AuditInfoEntity 对象插入数据库
	 * 
	 * 返回当前记录的Id值
	 * 
	 * @param AuditInfoEntity
	 * @return Long
	 * 
	 */
	public Long addAuditInfoEnt(AuditInfoEntity me) throws Exception;

	 
	/**
	 * 通过ID删除AuditInfo对象
	 * 
	 * @param id
	 * @return int
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	
	
	/**
	 * 通过AuditInfo对象更新数据库  
	 * 
	 * 目前考虑审核信息不用更新，不写更新方法
	 * 
	 * @param AuditInfoDTO
	 * @return int
	 * 
	 */
//	public int updateAuditInfoDTO(AuditInfoDTO mb) throws Exception;
	
	
	/**
	 * 根据ID查询AuditInfo对象
	 * 
	 * @param id
	 * @return AuditInfoDTO
	 * 
	 */
	public AuditInfoDTO getById(String id)throws Exception;
	
 
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;

	
	/**
	 * 根据查询所有 AuditInfoDTO
	 *  
	 *  
	 * @return list
	 * 
	 */
	public List<AuditInfoDTO> getAll(Map map) throws Exception;

	
	/**
	 * 根据条件查询所有 AuditInfoDTO
	 *  
	 *  
	 * @return list
	 * 
	 */
 
	public List<AuditInfoDTO> getBySearch(Map map) throws Exception;



 


}