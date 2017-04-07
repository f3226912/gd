package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.service.ReMemberMarketService;


@Service
public class ReMemberMarketServiceImpl implements ReMemberMarketService {
	
	@Autowired
	private BaseDao baseDao;
	

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public int addReMemberMarketDTO(ReMemberMarketDTO rb) throws Exception {
		return (int) baseDao.execute("ReMemberMarket.addReMemberMarket", rb);
	}


	@Override
	public int deleteReMemberMarketDTO(ReMemberMarketDTO rb)
			throws Exception {
		return (int) baseDao.execute("ReMemberMarket.deleteReMemberMarket", rb);
	}


	@Override
	public int getTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("ReMemberMarket.getTotal", map,Integer.class);


	}


	@Override
	public List<ReMemberMarketDTO> getBySearch(Map map) throws Exception {
		return baseDao.queryForList("ReMemberMarket.getBySearch", map, ReMemberMarketDTO.class);
	}


	@Override
	public List<ReMemberMarketDTO> getByDTO(ReMemberMarketDTO rmm)
			throws Exception {
		return baseDao.queryForList("ReMemberMarket.getByDTO", rmm, ReMemberMarketDTO.class);
	}
}
