package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.ComplaintEntityDTO;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.entity.ComplaintEntity;
import com.gudeng.commerce.gd.customer.service.ComplaintService;

public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<ComplaintEntityDTO> getComplaintList(Map<String, Object> map) {
		List<ComplaintEntityDTO> list = baseDao.queryForList("complaint.getComplaintList",map,ComplaintEntityDTO.class);
		return list;
	}

	@Override
	public ComplaintEntityDTO getComplaint(Long id) {
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		ComplaintEntityDTO complaintEntity=(ComplaintEntityDTO) baseDao.queryForObject("complaint.getComplaint",map, ComplaintEntityDTO.class);
		return complaintEntity;
	}

	@Override
	public Integer replyComplaintSave(ComplaintEntityDTO complaintEntityDTO) {
		return (int) baseDao.execute("complaint.replyComplaintSave", complaintEntityDTO);
	}

	@Override
	public Long addComplaint(ComplaintEntity ComplaintEntity) {
		return (Long) baseDao.persist(ComplaintEntity);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("complaint.getCount", map,
				Integer.class);
	}



}
