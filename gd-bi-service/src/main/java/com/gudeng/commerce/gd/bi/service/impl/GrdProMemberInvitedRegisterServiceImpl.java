package com.gudeng.commerce.gd.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.bi.dao.BaseDao;
import com.gudeng.commerce.gd.bi.dto.GrdProMemberInvitedRegisterDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProMemberInvitedRegisterEntity;
import com.gudeng.commerce.gd.bi.service.GrdProMemberInvitedRegisterService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProMemberInvitedRegisterServiceImpl implements GrdProMemberInvitedRegisterService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdProMemberInvitedRegisterDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdProMemberInvitedRegisterEntity.getById", map, GrdProMemberInvitedRegisterDTO.class);
	}

	@Override
	public List<GrdProMemberInvitedRegisterDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProMemberInvitedRegisterEntity.getList", map, GrdProMemberInvitedRegisterDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdProMemberInvitedRegisterEntity.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("GrdProMemberInvitedRegisterEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GrdProMemberInvitedRegisterDTO t) throws Exception {
		return baseDao.execute("GrdProMemberInvitedRegisterEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdProMemberInvitedRegisterEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdProMemberInvitedRegisterEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}
	
	@Override
	public int insert(Map map) throws Exception {
		return baseDao.execute("GrdProMemberInvitedRegisterEntity.insert", map);
	}
	@Override
	public List<GrdProMemberInvitedRegisterDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdProMemberInvitedRegisterEntity.getListPage", map, GrdProMemberInvitedRegisterDTO.class);
	}
}