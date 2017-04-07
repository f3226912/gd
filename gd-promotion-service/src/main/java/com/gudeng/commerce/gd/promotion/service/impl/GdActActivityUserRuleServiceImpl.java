package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
import com.gudeng.commerce.gd.promotion.service.GdActActivityUserRuleService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityUserRuleServiceImpl implements GdActActivityUserRuleService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GdActActivityUserRuleDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GdActActivityUserRuleEntity.getById", map, GdActActivityUserRuleDTO.class);
	}

	@Override
	public List<GdActActivityUserRuleDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityUserRuleEntity.getList", map, GdActActivityUserRuleDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GdActActivityUserRuleEntity.deleteById", map);
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
		return baseDao.batchUpdate("GdActActivityUserRuleEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(GdActActivityUserRuleDTO t) throws Exception {
		return baseDao.execute("GdActActivityUserRuleEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GdActActivityUserRuleEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GdActActivityUserRuleEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GdActActivityUserRuleDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GdActActivityUserRuleEntity.getListPage", map, GdActActivityUserRuleDTO.class);
	}

	@Override
	public Integer insert(GdActActivityUserRuleDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据市场id来获取活动用户规则
	 */
	@Override
	public List<GdActActivityUserRuleEntity> queryByMarketId(Map<String,Object> paraMap) throws Exception {
		return baseDao.queryForList("GdActActivityUserRuleEntity.getRuleEntitys", paraMap,GdActActivityUserRuleEntity.class);
	}
	
	
}