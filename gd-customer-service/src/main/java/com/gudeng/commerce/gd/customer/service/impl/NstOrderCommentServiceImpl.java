package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderCommentService;

@Service
public class NstOrderCommentServiceImpl implements NstOrderCommentService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public void insert(NstOrderCommentEntity nstOrderCommentEntity){
		baseDao.execute("NstOrderComment.insert", nstOrderCommentEntity);
	}

	@Override
	public Integer getAvgByMemberId(Long memberId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		return baseDao.queryForObject("NstOrderComment.getAvgByMemberId", paramMap, Integer.class);
	}
	
	@Override
	public NstOrderCommentDTO getById(String id) throws Exception {
		
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("id", id);
		return (NstOrderCommentDTO)baseDao.queryForObject("NstOrderComment.getById", map, NstOrderCommentDTO.class);

	}

	@Override
	public List<NstOrderCommentDTO> getUserCommentPage(Map<String, Object> map) {
		return baseDao.queryForList("NstOrderComment.getUserCommentPage", map, NstOrderCommentDTO.class);
	}

	@Override
	public Integer getUserCommentCount(Map<String, Object> map) {
		return baseDao.queryForObject("NstOrderComment.getUserCommentCount", map, Integer.class);
	}

}
