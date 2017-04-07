package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderComplaintService;

public class NstOrderComplaintServiceImpl implements NstOrderComplaintService{

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long persist(NstOrderComplaintEntity nstOrderComplainEntity) {
		return baseDao.persist(nstOrderComplainEntity, Long.class);
	}

	@Override
	public NstOrderComplaintDTO getById(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("NstOrderComplaint.getById", paramMap, NstOrderComplaintDTO.class);
	}

	@Override
	public List<NstOrderBaseinfoDTO> getOrderComplaintListByCondition(
			Map<String, Object> map) throws Exception {
		List<NstOrderBaseinfoDTO> list= baseDao.queryForList("NstOrderComplaint.getOrderComplaintListByCondition", map, NstOrderBaseinfoDTO.class);
		return list; 
	}

	@Override
	public int getOrderComplaintTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
    	return (int) baseDao.queryForObject("NstOrderComplaint.getOrderComplaintTotal", map, Integer.class);
	}

	@Override
	public NstOrderBaseinfoDTO getById(String id) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("NstOrderComplaint.getOrderInfoById", paramMap, NstOrderBaseinfoDTO.class);
	
	}

	@Override
	public int updateStatus(NstOrderComplaintDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("NstOrderComplaint.updateStatus", dto);

	}

	@Override
	public NstOrderBaseinfoDTO getSameCityOrderById(String id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.queryForObject("NstOrderComplaint.getSameCityOrderById", paramMap, NstOrderBaseinfoDTO.class);
	
	}
}
