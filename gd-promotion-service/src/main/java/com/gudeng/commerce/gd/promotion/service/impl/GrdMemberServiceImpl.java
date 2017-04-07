package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;
import com.gudeng.commerce.gd.promotion.service.GrdMemberService;

public class GrdMemberServiceImpl implements GrdMemberService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdMemberDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdMember.getByCondition", map, GrdMemberDTO.class);
	}

	@Override
	public List<GrdMemberDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdMember.getByCondition", map, GrdMemberDTO.class);
	}

	@Override
	public List<GrdMemberDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdMember.queryByConditionPage", map, GrdMemberDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdMember.deleteById", map);
	}

	@Override
	public int update(GrdMemberDTO t) throws Exception {
		return baseDao.execute("GrdMember.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdMember.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdMemberEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public GrdMemberDTO getMemberByParams(Map<String, Object> params) {
		return baseDao.queryForObject("GrdMember.getByParams", params, GrdMemberDTO.class);
	}

	@Override
	public List<GrdMemberDTO> queryBySearch(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdMember.queryBySearch", map, GrdMemberDTO.class);
	}

	@Override
	public int countBySearch(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdMember.countBySearch", map, Integer.class);
	}

	@Override
	public int deleteByIds(List<String> ids) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("ids", ids);
		return baseDao.execute("GrdMember.deleteByIds", param);
	}

	@Override
	public int dynamicUpdate(GrdMemberEntity entity) throws Exception {
		return baseDao.dynamicMerge(entity);
	}

	@Override
	public Long save(GrdMemberEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public int resetPwdByIds(Map<String, Object> param) throws Exception {
		return baseDao.execute("GrdMember.resetPwdByIds", param);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int resetStatusByIds(Map<String, Object> param) throws Exception {
		return baseDao.execute("GrdMember.resetStatusByIds", param);
	}

	@Override
	public List<GrdMemberDTO> getChildTeamInfo(Map<String, Object> param)
			throws Exception {
		return baseDao.queryForList("GrdMember.getChildTeamInfo", param, GrdMemberDTO.class);
	}

	@Override
	public List<Integer> getUserType(Integer grdUserId) throws Exception {
		Map map=new HashMap();
		map.put("grdUserId", grdUserId);
		return baseDao.queryForList("GrdMember.getUserType", map, Integer.class);
	}

	@Override
	public int getMember2TeamTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdMember.getMember2TeamTotal", map, Integer.class);
	}

	@Override
	public Integer insert(GrdMemberDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}