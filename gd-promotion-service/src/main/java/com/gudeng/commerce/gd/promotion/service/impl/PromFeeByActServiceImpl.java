package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.PromFeeByActDTO;
import com.gudeng.commerce.gd.promotion.entity.PromFeeByActEntity;
import com.gudeng.commerce.gd.promotion.service.PromFeeByActService;

public class PromFeeByActServiceImpl implements PromFeeByActService {

	@Resource
	private BaseDao<?> baseDao;
	
	@Override
	public List<PromFeeByActDTO> getByMemberId(Integer memberId)
			throws Exception {
		return getByMemberIdAndActId(memberId, null);
	}
	

	@Override
	public List<PromFeeByActDTO> getByActId(Integer actId) throws Exception {
		return getByMemberIdListAndActId(null, actId);
	}

	@Override
	public List<PromFeeByActDTO> getByMemberIdAndActId(Integer memberId,
			Integer actId) throws Exception {
		List<Integer> memberIdList = new ArrayList<>();
		memberIdList.add(memberId);
		return getByMemberIdListAndActId(memberIdList, actId);
	}

	@Override
	public List<PromFeeByActDTO> getByMemberIdListAndActId(
			List<Integer> memberIdList, Integer actId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(memberIdList != null && memberIdList.size() > 0){
			map.put("memberIdList", memberIdList);
		}
		
		if(actId != null){
			map.put("actId", actId);
		}
		return baseDao.queryForList("PromFeeByAct.getByMemberIdListAndActId", map , PromFeeByActDTO.class);
	}

	@Override
	public Integer addEntity(PromFeeByActEntity entity) throws Exception {
		return baseDao.persist(entity, Integer.class);
	}
}
