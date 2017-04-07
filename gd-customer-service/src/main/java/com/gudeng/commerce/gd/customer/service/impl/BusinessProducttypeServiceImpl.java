package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessProducttypeDTO;
import com.gudeng.commerce.gd.customer.service.BusinessProducttypeService;

@Service
public class BusinessProducttypeServiceImpl implements
		BusinessProducttypeService {
	@Autowired
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<BusinessProducttypeDTO> getByBusinessId(String businessId)
			throws Exception {
		Map map = new HashMap();
		map.put("businessId", businessId);
		return baseDao.queryForList("BusinessProducttype.getByBusinessId", map,
				BusinessProducttypeDTO.class);
	}

	@Override
	public List<BusinessProducttypeDTO> getByParentId(Long parentId)
			throws Exception {
		Map map = new HashMap();
		map.put("parentId", parentId);
		return baseDao.queryForList("BusinessProducttype.getByParentId",
				map, BusinessProducttypeDTO.class);
	}

}
