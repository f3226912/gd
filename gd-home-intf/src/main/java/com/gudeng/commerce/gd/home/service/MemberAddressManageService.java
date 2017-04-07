package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

public interface MemberAddressManageService {
	

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return MemberAddressDTO
	 * 
	 */
	public MemberAddressDTO getById(String id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<MemberAddressDTO>
	 */
	public List<MemberAddressDTO> getByCondition(Map<String, Object> map)
			throws Exception;



	/**
	 *  通过name获list集合
	 *  拓展实现多条件查询
	 *  
	 * @param map
	 * @return List<MemberAddressDTO>
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getByName(Map<String, Object> map) throws Exception;
	
	
	/**
	 *  通过userId获list集合
	 *  拓展实现多条件查询
	 *  
	 * @param map
	 * @return List<MemberAddressDTO>
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getListByUserId(Map<String, Object> map) throws Exception;

	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByUserId(Map<String, Object> map) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MemberAddressDTO
	 * @return int
	 * 
	 */
	 public int addMemberAddressDTO(Map<String, Object> map) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MemberAddressDTO
	 * @return int
	 * 
	 */
	public int addMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MemberAddressDTO
	 * @return int
	 * 
	 */
	public int updateMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception;
	
	
	

	/**
	 * 根据 mobile 精确查询 MemberBaseinfo对象
	 * 
	 * @param mobile
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception;

}
