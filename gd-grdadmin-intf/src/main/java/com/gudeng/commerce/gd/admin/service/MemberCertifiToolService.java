package com.gudeng.commerce.gd.admin.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;

/**
 *功能描述：MemberCertifi增删改查实现类
 *
 */
public interface MemberCertifiToolService {


	/**
	 * 通过MemberCertifi对象插入数据库
	 * 
	 * @param MemberCertifiDTO
	 * @return int
	 * 
	 */
	public int addMemberCertifiDTO(MemberCertifiDTO mb) throws Exception;
	 
	/**
	 * 通过ID删除MemberCertifi对象
	 * 
	 * @param id
	 * @return int
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	
	
	/**
	 * 通过MemberCertifi对象更新数据库
	 * 
	 * @param MemberCertifiDTO
	 * @return int
	 * 
	 */
	public int updateMemberCertifiDTO(MemberCertifiDTO mb) throws Exception;
	
	
	/**
	 * 根据ID查询MemberCertifi对象
	 * 
	 * @param id
	 * @return MemberCertifiDTO
	 * 
	 */
	public MemberCertifiDTO getById(String id)throws Exception;
	

	
	public MemberCertifiDTO getByUserId(String string) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;

	
	/**
	 * 根据多条件查询
	 * 
	 *  
	 * @return list
	 * 
	 */
	public List<MemberCertifiDTO> getBySearch(Map map) throws Exception;
	
	
	/*
	 * 查询农速通认证列表
	 */
	public List<MemberCertifiDTO> getNstListBySearch(Map map) throws Exception;
	
	public int getNstTotal(Map<String,Object> map)throws Exception;

	/**
	 * 根据多条件查询
	 * 
	 *  
	 * @return list
	 * 
	 */
	public List<MemberCertifiDTO> getList(Map map) throws Exception;
	

}