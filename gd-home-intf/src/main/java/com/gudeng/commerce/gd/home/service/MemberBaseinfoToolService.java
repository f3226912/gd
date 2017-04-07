package com.gudeng.commerce.gd.home.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

/**
 *功能描述：MemberBaseinfo增删改查实现类
 *
 */
public interface MemberBaseinfoToolService {


	/**
	 * 通过MemberBaseinfo对象插入数据库
	 * 
	 * @param MemberBaseinfoDTO
	 * @return int
	 * 
	 */
	public int addMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception;
	 
	
	public MemberBaseinfoDTO getByAccount(String account) throws Exception;

	
	/**
	 * 通过ID删除MemberBaseinfo对象
	 * 
	 * @param id
	 * @return int
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	
	
	/**
	 * 通过MemberBaseinfo对象更新数据库
	 * 
	 * @param MemberBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception;
	
	
	/**
	 * 根据ID查询MemberBaseinfo对象
	 * 
	 * @param id
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO getById(String id)throws Exception;
	

	/**
	 * 根据 mobile 精确查询 MemberBaseinfo对象
	 * 
	 * @param mobile
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception;

	
	/**
	 * 根据 nickName 查询MemberBaseinfo对象
	 * 
	 * @param id
	 * @return list
	 * 
	 */
	public List<MemberBaseinfoDTO>  getByNickName(Map map) throws Exception;

	
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;

	
	/**
	 * 根据查询所有 MemberBaseinfoDTO
	 *  
	 *  
	 * @return list
	 * 
	 */
	public List<MemberBaseinfoDTO> getAll(Map map) throws Exception;

	
	/**
	 * 根据多条件查询
	 * 
	 *  account like查询
	 *  nickName like查询
	 *  mobile  like查询
	 *  telephone like查询
	 *  email like查询
	 *  qq    like查询
	 *  weixin  like查询
	 *  weibo  like查询
	 *  address like查询
	 *  
	 *  provinceId  等于查询
	 *  level   等于查询
	 *  cityId	   等于查询
	 *  areaId  等于查询
	 *  lon  等于查询
	 *  lat  等于查询
	 *  status  等于查询
	 *  
	 * @return list
	 * 
	 */
	public List<MemberBaseinfoDTO> getBySearch(Map map) throws Exception;

	
	/**
	 * 通过entity 插入数据库，返回当前Id
	 * 
	 * @param MemberBaseinfoEntity
	 * @return Long
	 * 
	 */
	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity mb) throws Exception;



	public int updateUserType(Long memberId,Integer userType) throws Exception;

	public List<MemberBaseinfoDTO> getCompany()throws Exception;



}