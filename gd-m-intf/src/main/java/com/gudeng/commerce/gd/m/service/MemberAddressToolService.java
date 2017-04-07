package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;


/**
 *功能描述：收发货地址管理
 */
public interface MemberAddressToolService{
	
	public List<MemberAddressDTO> listMemberAddressByUserId(MemberAddressDTO memberAddressDTO)throws Exception;
	
	public int getTotal(Map<String,Object> map)throws Exception;
	
	public List<MemberAddressDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	public MemberAddressDTO getByIdForOrder(String id, Integer type,Integer source) throws Exception;
	
	/**
	 * 查询物流订单总数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalForOrder(Map<String, Object> query) throws Exception;
	
	/**
	 * 查询物流订单,分页
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getListForOrder(Map<String, Object> query) throws Exception;


	/**
	 * 更新货源信息  member_address 中 isDeleted为1 已删除
	 * @param memberAdressIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateMemberAdressStatusByid(String memberAdressIds) throws Exception;
}
