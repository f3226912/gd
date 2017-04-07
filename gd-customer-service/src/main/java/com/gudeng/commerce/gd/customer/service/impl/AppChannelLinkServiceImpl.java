package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AppChannelLinkDTO;
import com.gudeng.commerce.gd.customer.entity.AppChannelLink;
import com.gudeng.commerce.gd.customer.service.AppChannelLinkService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class AppChannelLinkServiceImpl implements AppChannelLinkService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public AppChannelLinkDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("AppChannelLink.getById", map, AppChannelLinkDTO.class);
	}

	@Override
	public List<AppChannelLinkDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("AppChannelLink.getList", map, AppChannelLinkDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("AppChannelLink.deleteById", map);
	}
	
	public int deleteBatch(List<String> list,String userId) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			map.put("updateUserId", userId);
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("AppChannelLink.deleteById", batchValues).length;
	}

	@Override
	public int update(AppChannelLinkDTO t) throws Exception {
		return baseDao.execute("AppChannelLink.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("AppChannelLink.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(AppChannelLink entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<AppChannelLinkDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("AppChannelLink.getListPage", map, AppChannelLinkDTO.class);
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
		return baseDao.batchUpdate("AppChannelLink.deleteById", batchValues).length;
	}
}