package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MyAddressDTO;
import com.gudeng.commerce.gd.customer.entity.MyAddress;
import com.gudeng.commerce.gd.customer.service.MyAddressService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class MyAddressServiceImpl implements MyAddressService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MyAddressDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MyAddress.getById", map, MyAddressDTO.class);
	}

	@Override
	public List<MyAddressDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MyAddress.getList", map, MyAddressDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("MyAddress.deleteById", map);
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
		return baseDao.batchUpdate("MyAddress.deleteById", batchValues).length;
	}

	@Override
	public int update(MyAddressDTO t) throws Exception {
		return baseDao.execute("MyAddress.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("MyAddress.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(MyAddress entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MyAddressDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MyAddress.getListPage", map, MyAddressDTO.class);
	}

	@Override
	@Transactional
	public void prefer(Map<String, Object> map) throws Exception {
		baseDao.execute("MyAddress.prefer1", map);
		baseDao.execute("MyAddress.prefer2", map);
	}
}