package com.gudeng.commerce.info.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.service.ProBszbankService;
@Service
public class ProBszbankServiceImpl implements ProBszbankService {
	@Autowired
	private BaseDao<?> baseDao;
	@Override
	public List<ProBszbankDTO> getTradeByDay(Map<String, Object> map) throws Exception{
		return	baseDao.queryForList("ProBszbank.getTradeByDay", map,ProBszbankDTO.class);
	}
	@Override
	public List<ProBszbankDTO> getOrderByDay(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("ProBszbank.getOrderByDay", map,ProBszbankDTO.class);
	}
	
	
	@Override
	public List<ProBszbankDTO> sumReport(Map<String, Object> map) {
		return baseDao.queryForList("ProBszbank.sumReport", map, ProBszbankDTO.class);
	}
	

}
