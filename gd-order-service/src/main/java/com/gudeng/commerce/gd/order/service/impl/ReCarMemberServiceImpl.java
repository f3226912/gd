package com.gudeng.commerce.gd.order.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.entity.ReCarMemberEntity;
import com.gudeng.commerce.gd.order.service.ReCarMemberService;

@Service
public class ReCarMemberServiceImpl implements ReCarMemberService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int addEntity(ReCarMemberEntity entity) {
		return baseDao.execute("ReCarMember.addEntity", entity);
	}

	@Override
	public int countByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("ReCarMember.countByCondition", map, Integer.class);
	}

}
