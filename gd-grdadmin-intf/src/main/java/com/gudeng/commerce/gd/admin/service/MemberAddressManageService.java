package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;

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
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 根据条件查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByName(Map<String,Object> map)throws Exception;

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
	 * 更新货源信息  member_address 中 isDeleted为1 已删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer updateMemberAdressStatusByid(String memberAdressIds) throws Exception;
	
	
	/**
	 * 会员发布货源统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getListByAddress(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布货源统计(同城)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getListByAddressSameCity(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByAddress(Map<String,Object> map)throws Exception;
		
	/**
	 * 查询记录数(同城)
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByAddressSameCity(Map<String,Object> map)throws Exception;
	

	/**
	 * 货源分配统计列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstMemberAddressDTO> getDistributeAddressList(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 货源分配统计总记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getDistributeAddressTotal(Map<String,Object> map)throws Exception;

		
	/**
	 * 管理后台查询同城货源记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalForConsole(Map<String,Object> map)throws Exception;

	/**
	 *  管理后台查询同城货源列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> queryListForConsole(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 同城货源分配统计列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstMemberAddressDTO> getDistributeSameCityAddressList(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 同城货源分配统计总记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getDistributeSameCityAddressTotal(Map<String,Object> map)throws Exception;

}
