package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.customer.service.AccTransInfoService;

public class AccTransInfoServiceImpl implements AccTransInfoService{
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public int add(AccTransInfoDTO accTransInfoDTO) {

		return this.baseDao.execute("accTransInfo.add", accTransInfoDTO);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccTransInfoDTO> getByMemberId(Map map) {
		return this.baseDao.queryForList("accTransInfo.getByMemberId", map ,AccTransInfoDTO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AccTransInfoDTO> getByMemberIdAndDay(Map map) {
		return this.baseDao.queryForList("accTransInfo.getByMemberIdAndDay", map ,AccTransInfoDTO.class);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotal(Map map) throws Exception {

		return (int)baseDao.queryForObject("accTransInfo.getTotal", map,Integer.class);
	
	}

}
