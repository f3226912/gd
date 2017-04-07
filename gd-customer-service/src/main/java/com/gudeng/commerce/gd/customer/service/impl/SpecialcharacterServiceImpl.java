package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.SpecialcharacterDTO;
import com.gudeng.commerce.gd.customer.entity.SpecialcharacterEntity;
import com.gudeng.commerce.gd.customer.service.SpecialcharacterService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class SpecialcharacterServiceImpl implements SpecialcharacterService {
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;
	
	@Override
	public SpecialcharacterDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("Specialcharacter.getById", map, SpecialcharacterDTO.class);
	}

	@Override
	public List<SpecialcharacterDTO> getList(Map<String, Object> map) throws Exception {
//		return baseDao.queryForList("Specialcharacter.getList", map, SpecialcharacterDTO.class);
		return cacheBo.getCharacterList( map,baseDao);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("Specialcharacter.deleteById", map);
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
		return baseDao.batchUpdate("Specialcharacter.deleteById", batchValues).length;
	}

	@Override
	public int update(SpecialcharacterDTO t) throws Exception {
		return baseDao.execute("Specialcharacter.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("Specialcharacter.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(SpecialcharacterEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<SpecialcharacterDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("Specialcharacter.getListPage", map, SpecialcharacterDTO.class);
	}
}