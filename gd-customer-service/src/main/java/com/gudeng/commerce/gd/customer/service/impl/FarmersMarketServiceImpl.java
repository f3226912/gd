package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.FarmersMarketDTO;
import com.gudeng.commerce.gd.customer.entity.FarmersMarketEntity;
import com.gudeng.commerce.gd.customer.service.FarmersMarketService;

@Service
public class FarmersMarketServiceImpl implements FarmersMarketService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long insertEntity(FarmersMarketEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return (int) baseDao.execute("FarmersMarket.deleteById", id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		for(Long id:idList){
			count = baseDao.execute("FarmersMarket.deleteById", id);
			if(count<=0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(FarmersMarketDTO obj) throws Exception {
		int count = baseDao.execute("FarmersMarket.updateDTO", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<FarmersMarketDTO> objList) throws Exception {
		int count=0;
		for(FarmersMarketDTO dto:objList){
			count = baseDao.execute("FarmersMarket.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("FarmersMarket.getTotal", map, Integer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FarmersMarketDTO getById(Long id) throws Exception {
		return (FarmersMarketDTO)this.baseDao.queryForObject("FarmersMarket.getById", id, FarmersMarketDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FarmersMarketDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<FarmersMarketDTO> list= baseDao.queryForList("FarmersMarket.getListByConditionPage", map, FarmersMarketDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FarmersMarketDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<FarmersMarketDTO> list= baseDao.queryForList("FarmersMarket.getListByCondition", map, FarmersMarketDTO.class);
		return list;
	}
}
