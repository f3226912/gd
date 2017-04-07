package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdUserTeamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserTeamEntity;
import com.gudeng.commerce.gd.promotion.service.GrdUserTeamService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdUserTeamServiceImpl implements GrdUserTeamService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdUserTeamDTO getById(String id) throws Exception {
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("teamId", teamId);
		return baseDao.queryForObject("GrdUserTeamEntity.getById", map, GrdUserTeamDTO.class);*/
		return null;
	}

	@Override
	public List<GrdUserTeamDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserTeamEntity.getList", map, GrdUserTeamDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("teamId", teamId);
		return baseDao.execute("GrdUserTeamEntity.deleteById", map);*/
		return 0;
	}
	
	@Override
	public int deleteByGrdUserId(String grdUserId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grdUserId", grdUserId);
		return baseDao.execute("GrdUserTeamEntity.deleteByGrdUserId", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
/*		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teamId", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdUserTeamEntity.deleteById", batchValues).length;*/
		return 0;
	}

	@Override
	public int update(GrdUserTeamDTO t) throws Exception {
		return baseDao.execute("GrdUserTeamEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdUserTeamEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdUserTeamEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdUserTeamDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserTeamEntity.getListPage", map, GrdUserTeamDTO.class);
	}

	@Override
	public int insert(Map<String, Object> map) throws Exception {
		return baseDao.execute("GrdUserTeamEntity.insert", map);
	}

	@Override
	public List<GrdUserTeamDTO> getReUserTeamList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdUserTeamEntity.getReUserTeamList", map, GrdUserTeamDTO.class);
	}

	@Override
	public Integer insert(GrdUserTeamDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}