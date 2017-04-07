package com.gudeng.commerce.gd.pay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.pay.dao.BaseDao;
import com.gudeng.commerce.gd.pay.dto.DictDTO;
import com.gudeng.commerce.gd.pay.service.DemoService;


/**
 *功能描述：
 */
@Service
public class DemoServiceImpl implements DemoService{
	@Autowired
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public DictDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (DictDTO)this.baseDao.queryForObject("Demo.getDic", p, DictDTO.class);
	}

	@Override
	public List<DictDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<DictDTO> list= baseDao.queryForList("Demo.getByCondition", map, DictDTO.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Demo.getTotal", map, Integer.class);
	}
}
