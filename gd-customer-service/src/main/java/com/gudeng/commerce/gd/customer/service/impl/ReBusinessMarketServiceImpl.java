package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessMarketService;


@Service
public class ReBusinessMarketServiceImpl implements ReBusinessMarketService {
	
	@Autowired
	private BaseDao baseDao;
	

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	@org.springframework.transaction.annotation.Transactional(isolation=Isolation.SERIALIZABLE)
	public synchronized int addReBusinessMarketDTO(ReBusinessMarketDTO rb) throws Exception {
		Map map=new HashMap();
		map.put("businessId", rb.getBusinessId());
		ReBusinessMarketDTO rbmDto = (ReBusinessMarketDTO)baseDao.queryForObject("ReBusinessMarket.getByBusinessId", map, ReBusinessMarketDTO.class);
		if(rbmDto!=null && rbmDto.getBusinessId()>0){
			return -1;
		}
		return (int) baseDao.execute("ReBusinessMarket.addReBusinessMarket", rb);
	}


	@Override
	public int deleteReBusinessMarketDTO(ReBusinessMarketDTO rb)
			throws Exception {
		return (int) baseDao.execute("ReBusinessMarket.deleteReBusinessMarket", rb);
	}
	
	@Override
	public int deleteByBusinessId(Long  businessId)	throws Exception {
		Map map=new HashMap();
		map.put("businessId", businessId);
		return (int) baseDao.execute("ReBusinessMarket.deleteReBusinessMarket", map);
	}


	@Override
	public int getTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("ReBusinessMarket.getTotal", map,Integer.class);


	}


	@Override
	public List<ReBusinessMarketDTO> getBySearch(Map map) throws Exception {
		return baseDao.queryForList("ReBusinessMarket.getBySearch", map, ReBusinessMarketDTO.class);
	}
	
	@Override
	public ReBusinessMarketDTO getByBusinessId(Long businessId) throws Exception {
		Map map=new HashMap();
		map.put("businessId", businessId);
		return (ReBusinessMarketDTO)baseDao.queryForObject("ReBusinessMarket.getByBusinessId", map, ReBusinessMarketDTO.class);
	}
	
	
	@Override
	public List<ReBusinessMarketDTO> getAllBySearch(Map map) throws Exception {
		return baseDao.queryForList("ReBusinessMarket.getAllBySearch", map, ReBusinessMarketDTO.class);
	}
	

	 
	
}
