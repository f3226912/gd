package com.gudeng.commerce.gd.order.service;

import java.util.Map;

import com.gudeng.commerce.gd.order.entity.ReCarMemberEntity;

public interface ReCarMemberService {
	
	public int addEntity(ReCarMemberEntity entity);

	public int countByCondition(Map<String, Object> map);
}
