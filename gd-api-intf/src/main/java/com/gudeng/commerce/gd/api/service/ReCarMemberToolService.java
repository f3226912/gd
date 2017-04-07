package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.order.entity.ReCarMemberEntity;

public interface ReCarMemberToolService {

	public int addEntity(ReCarMemberEntity entity) throws Exception;
	
	public boolean isExist(Long carId, Long memberId) throws Exception;
}
