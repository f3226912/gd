package com.gudeng.commerce.gd.notify.service;

import com.gudeng.commerce.gd.customer.entity.MemberMessageFlagEntity;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */
public interface MemberMessageFlagToolService {

	public Number insert(MemberMessageFlagEntity entity) throws Exception;

	
	public int update(MemberMessageFlagEntity entity) throws Exception;
}