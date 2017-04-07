package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessCategoryService;


@Service
public class ReBusinessCategoryServiceImpl implements ReBusinessCategoryService {
	
	@Autowired
	private BaseDao baseDao;
	

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public int addReBusinessCategoryDTO(ReBusinessCategoryDTO rb) throws Exception {
		if(null == rb.getBusinessType() || rb.getBusinessType().equals("")){
			rb.setBusinessType("1");
		}
		return (int) baseDao.execute("ReBusinessCategory.addReBusinessCategory", rb);
	}


	@Override
	public int deleteReBusinessCategoryDTO(ReBusinessCategoryDTO rb)
			throws Exception {
		return (int) baseDao.execute("ReBusinessCategory.deleteReBusinessCategory", rb);
	}


	@Override
	public int getTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("ReBusinessCategory.getTotal", map,Integer.class);


	}


	@Override
	public List<ReBusinessCategoryDTO> getBySearch(Map map) throws Exception {
		return baseDao.queryForList("ReBusinessCategory.getBySearch", map, ReBusinessCategoryDTO.class);
	}


	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		Map map=new HashMap();
		map.put("businessId", businessId);
		return (int) baseDao.execute("ReBusinessCategory.deleteReBusinessCategory", map);
	}
	
	@Override
	public ReBusinessCategoryDTO getCateIdByBusId(String businessId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("businessId",businessId);
		return (ReBusinessCategoryDTO) baseDao.queryForObject("ReBusinessCategory.getCateIdByBusId", map, ReBusinessCategoryDTO.class);
	}
	 
}
