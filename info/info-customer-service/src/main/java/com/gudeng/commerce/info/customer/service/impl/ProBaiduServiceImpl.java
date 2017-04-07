package com.gudeng.commerce.info.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.service.ProBaiduService;

public class ProBaiduServiceImpl implements ProBaiduService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public List<ProBaiduEntityDTO> sumReport(Map<String, Object> map) {
		return baseDao.queryForList("ProBaidu.sumReport", map, ProBaiduEntityDTO.class);
	}

}
