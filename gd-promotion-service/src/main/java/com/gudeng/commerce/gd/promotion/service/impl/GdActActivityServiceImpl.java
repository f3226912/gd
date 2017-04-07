package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActProductDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityService;
import com.gudeng.framework.dba.util.DalUtils;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityServiceImpl implements GdActActivityService {
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public GdActActivityDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityEntity.getById", map, GdActActivityDTO.class);
	}

	@Override
	public List<GdActActivityDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getList", map, GdActActivityDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityEntity.deleteById", map);
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
		return baseDao.batchUpdate("GdActActivityEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityDTO t) throws Exception {
		return baseDao.execute("GdActActivityEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityEntity.getTotal", map, Integer.class);
	}
	
	@Override
	public String getSequence() throws Exception {
		return baseDao.queryForObject("GdActActivityEntity.getCodeSequence", null, String.class);
	}

	@Override
	public Long insert(GdActActivityEntity entity) throws Exception {
		
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GdActActivityDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getListPage", map, GdActActivityDTO.class);
	}

	@Override
	public List<GdActActivityDTO> getActivityInfo(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getActivityInfo", params, GdActActivityDTO.class);
	}

	@Override
	public List<GdActActivityDTO> getEffectiveActivityList(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getEffectiveActivityList", params, GdActActivityDTO.class);
	}

	@Override
	public List<GdActActivityUserRuleDTO> getActivityUserList(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getActivityUserList", params, GdActActivityUserRuleDTO.class);
	}

	@Override
	public List<GdActActivityDTO> getActivityList(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("GdActActivityEntity.getActivityList", params, GdActActivityDTO.class);
	}

	@Override
	public int getTotalForActivityList(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("GdActActivityEntity.getTotalForActivityList", params, Integer.class);
	}

	@Override
	public GdActActivityDTO getSpecificActivityInfo(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("GdActActivityEntity.getSpecificActivityInfo", params, GdActActivityDTO.class);
	}
	
	@Override
	public void addRuleComm(GdActActivityCommDTO dto) {
		Map<String, Object> params = DalUtils.convertToMap(dto);
		baseDao.execute("GdActActivityComm.insert", params);
	}

	@Override
	public List<GdActActivityCommDTO> searchRuleCommForAct(Integer actId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("actId", actId);
		
		return baseDao.queryForList("GdActActivityComm.getList", paramMap, GdActActivityCommDTO.class);

	}

	@Override
	public List<ActProductDTO> getActivityProductInfo(Map<String, Object> map) {
		return baseDao.queryForList("GdActActivityEntity.getActivityProductInfo", map, ActProductDTO.class);
	}

	@Override
	public Integer insert(GdActActivityDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GdActActivityDTO> getExistList(Map<String, Object> map) {
		return baseDao.queryForList("GdActActivityEntity.getExistList", map, GdActActivityDTO.class);
	}
}