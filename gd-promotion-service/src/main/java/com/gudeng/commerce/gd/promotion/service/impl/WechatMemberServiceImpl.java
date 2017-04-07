package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatMemberEntity;
import com.gudeng.commerce.gd.promotion.service.WechatMemberService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class WechatMemberServiceImpl implements WechatMemberService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public WechatMemberDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("WechatMemberEntity.getById", map, WechatMemberDTO.class);
	}

	@Override
	public List<WechatMemberDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatMemberEntity.getList", map, WechatMemberDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("WechatMemberEntity.deleteById", map);
	}
	
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("WechatMemberEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(WechatMemberDTO t) throws Exception {
		return baseDao.execute("WechatMemberEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("WechatMemberEntity.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(WechatMemberDTO entity) throws Exception {
		return baseDao.execute("WechatMemberEntity.insert", entity);
	}
	
	@Override
	public Long persist(WechatMemberEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<WechatMemberDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("WechatMemberEntity.getListPage", map, WechatMemberDTO.class);
	}
}