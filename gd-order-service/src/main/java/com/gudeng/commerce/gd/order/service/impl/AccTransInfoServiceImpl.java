package com.gudeng.commerce.gd.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.service.AccTransInfoService;

public class AccTransInfoServiceImpl implements AccTransInfoService{
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public int add(AccTransInfoDTO accTransInfoDTO) {

		return this.baseDao.execute("accTransInfo.add", accTransInfoDTO);

	}

	@Override
	public List<AccTransInfoDTO> getAccTransInfoListByMemberId(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		return baseDao.queryForList("accTransInfo.getAccTransInfoListByMemberId", map, AccTransInfoDTO.class);
	}

	@Override
	public Long getAccTransInfoListTotalByMemberId(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		return baseDao.queryForObject("accTransInfo.getAccTransInfoListTotalByMemberId", map, Long.class);
	}
 

}
