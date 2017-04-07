package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.entity.IntegralEntity;
import com.gudeng.commerce.gd.customer.service.IntegralService;

public class IntegralServiceImpl implements IntegralService{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<IntegralDTO> getByCondition(Map<String, Object> map) {
		return baseDao.queryForList("integral.getByCondition", map, IntegralDTO.class);
		
	}
	
	@Override
	public List<IntegralDTO> getByCondition2(Map<String, Object> map) {
		return baseDao.queryForList("integral.getByCondition2", map, IntegralDTO.class);
		
	}
	
	@Override
	public List<IntegralDTO> getByCondition3(Map<String, Object> map) {
		return baseDao.queryForList("integral.getByCondition3", map, IntegralDTO.class);
		
	}
	
	@Override
	public int getTotal(Map<String, Object> map) {
		return (int) baseDao.queryForObject("integral.getTotal", map, Integer.class);
	}

	@Override
	public int insertEntity(IntegralEntity entity) {
		return baseDao.execute("integral.addIntegral", entity);
	}

	@Override
	public IntegralEntity getIntegralEntityById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (IntegralEntity) baseDao.queryForObject("integral.getIntegralEntityById", map, IntegralEntity.class);
	}

	@Override
	public int updateMemberIntegral(Long memberId, Long integral) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("integral", integral);
		return baseDao.execute("integral.updateMemberIntegral", map);
	}
	
	@Override
	public int updateIntegralMemberId(Long memberId, Long memberId_ed) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("memberId_ed", memberId_ed);
		return baseDao.execute("integral.updateIntegralMemberId", map);
	}
	

	@Override
	public int updateIntegralIsReturn(Long integralId, Integer isReturn, String updateUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("integralId", integralId);
		map.put("isReturn", isReturn);
		map.put("updateUserId", updateUserId);
		return baseDao.execute("integral.updateIntegralIsReturn", map);
	}

	@Override
	public List<IntegralDTO> selectIntegralFlow(Long memberId)
			throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("memberId", memberId);
		return baseDao.queryForList("integral.selectIntegralFlow", map, IntegralDTO.class);
	}
	
}
