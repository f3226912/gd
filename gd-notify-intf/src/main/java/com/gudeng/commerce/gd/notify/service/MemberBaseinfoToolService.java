package com.gudeng.commerce.gd.notify.service;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */
public interface MemberBaseinfoToolService {

	/**
	 *修改会员
	 * @param username 账号
	 * @param password 密码
	 * @throws Exception
	 */
	public int updateMember(MemberBaseinfoDTO mb) throws Exception;

	MemberBaseinfoDTO getMember(String memberId) throws Exception;
	
	Long addMemberBaseinfoEnt(MemberBaseinfoEntity memberEntity) throws Exception;

}