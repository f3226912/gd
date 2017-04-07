package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

public interface LoginToolService {
	/**
	 * 检测登录
	 * 
	 * @param map
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO getLogin(Map<String, Object> map) throws Exception;
	
	/**
	 * 注册验证
	 * 
	 * @param map
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO checkUser(Map<String, Object> map) throws Exception;	
	
	/**
	 * 登录
	 * 
	 * @param map
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public List<MemberBaseinfoDTO> commitLogin(Map<String, Object> map) throws Exception;
	
	/**
	 * 注册
	 * 
	 * @param map
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public Long register(MemberBaseinfoEntity mb) throws Exception;
	
	/**
	 * 根据ID查询MemberBaseinfo对象
	 * 
	 * @param id
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public MemberBaseinfoDTO getById(String id)throws Exception;
	
	/**
	 * 修改密码
	 * 
	 * @param id
	 * @return MemberBaseinfoDTO
	 * 
	 */
	public int updatePassword(MemberBaseinfoDTO mbdto)throws Exception;
}
