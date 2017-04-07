package com.gudeng.commerce.gd.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.entity.MemberMessageFlagEntity;
import com.gudeng.commerce.gd.customer.service.MemberMessageFlagService;

public class MemberMessageFlagServiceImpl implements MemberMessageFlagService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int insert(MemberMessageFlagEntity entity) throws Exception {
		return baseDao.execute("MemberMessageFlag.insert", entity);
	}

	@Override
	public int update(MemberMessageFlagEntity entity) throws Exception {
		return baseDao.execute("MemberMessageFlag.updateFlag", entity);
	}

}
